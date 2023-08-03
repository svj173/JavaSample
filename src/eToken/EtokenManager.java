package eToken;


import iaik.pkcs.pkcs11.*;
import iaik.pkcs.pkcs11.objects.Data;
import iaik.pkcs.pkcs11.objects.RSAPrivateKey;

import java.io.*;
import java.util.Random;

/**
 * <BR/>
 * <BR/> http://www.aladdin.ru/support/download/464/
 * <BR/> default password (PIN) - 1234567890
 * <BR/>
 * <BR/> user-PIN = Hbrfitn11 - установил посредством PKiMonitor -- createPassword. Именно им авторизуется сессия.
 * <BR/>
 * <BR/> Подпись - для механизма CKM_RSA_PKCS в этом файле требуется внутри наличие дайджеста в формате PKCS1
 * <BR/>
 * <BR/> Советы
 * <BR/> 1) Привязывать ключ к серийному номеру etoken (т.е. sn забить в код проверок).
 * <BR/>
 * <BR/> Links
 * <BR/> 1) http://java.sun.com/j2se/1.5.0/docs/guide/security/p11guide.html
 * Поддержка eToken в java.
 * Не нашел, как обращаться к брелку по номеру слота (USB).
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 25.09.12 17:11
 */
public class EtokenManager
{
    protected Token getToken ( Module module, int slot )  throws Exception
    {
        //Token result;
        Slot[] slots;

        slots = module.getSlotList ( Module.SlotRequirement.TOKEN_PRESENT );
        if ( slots.length == 0 ) throw new Exception ( "Tokens is absent." );

        return slots[slot].getToken();
    }

    protected Module getModule ( String libName ) throws Exception
    {
        Module result;

        result  = Module.getInstance ( libName );
        result.initialize ( null );

        return result;
    }

    protected Data createDataObj ( byte[] data, String label )
    {
        Data dataObj = new Data();

        // we could also set the name that manages this data object
        //dataObj.getApplication().setCharArrayValue("Application Name");

        // - label
        dataObj.getLabel().setCharArrayValue ( label.toCharArray() );

        // - data content
        dataObj.getValue().setByteArrayValue ( data );

        // ensure that it is stored on the token and not just in this session
        dataObj.getToken().setBooleanValue ( Boolean.TRUE );

        return dataObj;
    }

    /* Прочитать файл с диска в виде набора байт. */
    protected byte[] loadFile ( String fileName )  throws Exception
    {
        InputStream fis;
        ByteArrayOutputStream bufferStream;
        byte[] buffer;
        int bytesRead;

        fis             = new FileInputStream ( fileName );
        bufferStream    = new ByteArrayOutputStream ( 256 );

        // read the data from the file
        buffer = new byte[ 4096 ];
        while ( ( bytesRead = fis.read ( buffer ) ) >= 0 )
        {
            bufferStream.write ( buffer, 0, bytesRead );
        }
        fis.close ();

        return bufferStream.toByteArray ();
    }

    protected void closeModule ( Session session, Module module )
    {
        if ( session != null )
        {
            try
            {
                session.closeSession();
            } catch ( TokenException e )             {
                e.printStackTrace();
            }
        }

        if ( module != null )
        {
            try
            {
                module.finalize ( null );
            } catch ( TokenException e )             {
                e.printStackTrace();
            }
        }
    }

    // pin - для доступа к eToken
    protected Session createSession ( Token token, String pin )  throws Exception
    {
        Session     session;
        TokenInfo   tokenInfo;
        String      userPINString;

        // Типы открытия сессии всего два: RO_SESSION, RW_SESSION.
        session = token.openSession ( Token.SessionType.SERIAL_SESSION, Token.SessionReadWriteBehavior.RW_SESSION, null, null );

        // login - если необходимо
        tokenInfo = token.getTokenInfo();
        if ( tokenInfo.isLoginRequired() )
        {
            if ( tokenInfo.isProtectedAuthenticationPath() )
            {
                //output.print("Please enter the user-PIN at the PIN-pad of your reader.");
                session.login ( Session.UserType.USER, null ); // the token prompts the PIN by other means; e.g. PIN-pad
            }
            else
            {
                if ( null != pin )
                    userPINString = pin;
                else
                    userPINString = "1234567890";
                System.out.println ( "Enter user-PIN = " + userPINString );
                session.login ( Session.UserType.USER, userPINString.toCharArray() );
            }
        }

        return session;
    }

    /**
     * Подписать данные приватным ключом, взятым из eToken.
     * @param session    Сессия
     * @param data       Данные
     * @throws Exception   Ошибки
     */
    protected byte[] signData ( Session session, byte[] data ) throws Exception
    {
        Object[]        matchingKeys;
        RSAPrivateKey   searchTemplate, signatureKey;
        Mechanism       signatureMechanism;
        byte[]          signatureValue;

        signatureValue = null;

        // Подготавливаем RSA private key который будем использовать для подписи данных
        searchTemplate = new RSAPrivateKey();
        searchTemplate.getSign().setBooleanValue ( Boolean.TRUE );

        // Вытаскиваем этот ключ из eToken
        session.findObjectsInit ( searchTemplate );
        if ( ( matchingKeys = session.findObjects ( 1 ) ).length > 0 )
        {
            signatureKey = ( RSAPrivateKey ) matchingKeys[0];
            System.out.println ( "--- signatureKey = " + signatureKey );

            // do not forget to finish the find operation
            session.findObjectsFinal();

            // select the signature mechanism, ensure your token supports it
            signatureMechanism = Mechanism.SHA1_RSA_PKCS;    // VENDOR_DEFINED
            // initialize for signing
            session.signInit ( signatureMechanism, signatureKey );

            signatureValue = session.sign(data);     // RSA signature according to PKCS#1 (v 1.5).
            System.out.println ( "--- signatureValue size = " + signatureValue.length );
        }
        else
        {
            // we have not found a suitable key, we cannot contiue
            //signatureKey    = null;
            System.out.println ( "--- NONE signatureKey" );
        }

        return signatureValue;
    }

    /**
     * Создать случайный массив из 20 байт.
     * @return   Массив.
     */
    protected byte[] createId ()
    {
        byte[] result;

        // генерим случайные 20 байт
        result = new byte[ 20 ];
        new Random().nextBytes ( result );

        return result;
    }

    protected Session createRoSession ( Token token, String userPin )     throws Exception
    {
        Session session;

        session = token.openSession ( Token.SessionType.SERIAL_SESSION, Token.SessionReadWriteBehavior.RO_SESSION, null, null );
      	session.login ( Session.UserType.USER, userPin.toCharArray() );

        return session;
    }

    protected void saveFile ( byte[] data, String signFileName )   throws Exception
    {
        OutputStream signatureOutput = new FileOutputStream ( signFileName );
        signatureOutput.write ( data );
        signatureOutput.flush();
        signatureOutput.close();
    }

}
