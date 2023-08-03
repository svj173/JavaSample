package eToken.signatures;

import eToken.EtokenManager;
import iaik.pkcs.pkcs11.Mechanism;
import iaik.pkcs.pkcs11.Module;
import iaik.pkcs.pkcs11.Session;
import iaik.pkcs.pkcs11.Token;
import iaik.pkcs.pkcs11.objects.Object;
import iaik.pkcs.pkcs11.objects.RSAPrivateKey;
import iaik.pkcs.pkcs11.wrapper.PKCS11Constants;

/**
 * Signs some raw data on the token. This means, the given data is directly
 * input to the signature creation mechanism without any prior hashing. For
 * instance, the user can provide the encoding of an already calculated
 * DigestInfo object. See file data.dat.sh1, which is the DigestInfo encoding
 * of the SHA-1 hash of data.dat.
 *
 * Подписываем файл. Подпись - в виде набора байт.
 *
 * @author <a href="mailto:Karl.Scheibelhofer@iaik.at"> Karl Scheibelhofer </a>
 * @version 0.1
 * @invariants
 */
public class SignRawData  extends EtokenManager
{
    public static void main ( String[] args )
    {
        SignRawData         manager;
        Token               token;
        Module              module;
        String              pin, libName, fileName, signFileName;
        Session             session;
        int                 slot;
        byte[]              data, signatureValue;
        RSAPrivateKey       signatureKey, templateSignatureKey;
        Object[]            foundObjs;
        Mechanism           signatureMechanism;

        module      = null;
        session     = null;

        libName     = "libeTPkcs11.so";
        slot        = 0;   // USB-0
        fileName    = "/home/svj/projects/SVJ/JavaSample/test/etoken/modules.xml";
        //fileName    = "/home/svj/projects/SVJ/JavaSample/test/etoken/modules3.xml";

        pin         = "Hbrfitn11";

        manager     = new SignRawData();

        try
        {
            module      = manager.getModule ( libName );
            token       = manager.getToken ( module, slot );
            //System.out.println ( "Token for slot #" + slot + " = " + token.getTokenInfo() );

            session     = manager.createRoSession ( token, pin );

            // ищем приватный ключ
            System.out.println ( "find private signature key" );
            templateSignatureKey = new RSAPrivateKey();
            templateSignatureKey.getSign().setBooleanValue ( Boolean.TRUE );

            session.findObjectsInit ( templateSignatureKey );

            foundObjs = session.findObjects ( 1 ); // find first

            if ( foundObjs.length > 0 )
            {
                signatureKey = ( RSAPrivateKey ) foundObjs[ 0 ];
                System.out.println ( "find signatureKey (private): " );
                //System.out.println ( signatureKey );
            }
            else
            {
                System.out.println ( "No RSA private key found that can sign!" );
                throw new Exception ( "No RSA private key found that can sign!" );
            }
            session.findObjectsFinal();

            // читаем файл -
            // - для механизма CKM_RSA_PKCS в этом файле требуется внутри наличие дайджеста в формате PKCS1
            data    = manager.loadFile ( fileName );
            System.out.println ( "load data file. size = " + data.length );

            // готовим сессию для подписи
            //signatureMechanism = Mechanism.get ( PKCS11Constants.CKM_RSA_PKCS );
            signatureMechanism = Mechanism.get ( PKCS11Constants.CKM_SHA1_RSA_PKCS );
       	    // initialize for signing
       	    session.signInit ( signatureMechanism, signatureKey );

            // подписываем данные
            signatureValue  = session.sign ( data );
            //System.out.println ( "The signature value is: " + new BigInteger(1, signatureValue).toString(16) );
            System.out.println ( "sign data size = " + signatureValue.length );

            // Сохраняем подпись в отдельном файле
            signFileName    = "/home/svj/projects/SVJ/JavaSample/test/etoken/sign_modules.data";
            manager.saveFile ( data, signFileName );

        } catch ( Exception e )         {
            e.printStackTrace();
        } finally        {
            manager.closeModule ( session, module );
        }
    }

}
