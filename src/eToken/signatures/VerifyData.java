package eToken.signatures;


import eToken.EtokenManager;
import iaik.pkcs.pkcs11.Mechanism;
import iaik.pkcs.pkcs11.Module;
import iaik.pkcs.pkcs11.Session;
import iaik.pkcs.pkcs11.Token;
import iaik.pkcs.pkcs11.objects.Object;
import iaik.pkcs.pkcs11.objects.RSAPublicKey;
import iaik.pkcs.pkcs11.wrapper.PKCS11Constants;

/**
 *
 * Проверяем подпись файла. Подпись - в виде набора байт.
 *
 */
public class VerifyData extends EtokenManager
{
    public static void main ( String[] args )
    {
        VerifyData          manager;
        Token               token;
        Module              module;
        String              pin, libName, fileName, signFileName;
        Session             session;
        int                 slot;
        byte[]              data, signatureData;
        RSAPublicKey        verifyKey, tmpKey;
        Object[]            foundObjs;
        Mechanism           signatureMechanism;

        module      = null;
        session     = null;

        libName     = "libeTPkcs11.so";
        slot        = 0;   // USB-0

        fileName        = "/home/svj/projects/SVJ/JavaSample/test/etoken/modules.xml";
        //fileName    = "/home/svj/projects/SVJ/JavaSample/test/etoken/modules3.xml";
        signFileName    = "/home/svj/projects/SVJ/JavaSample/test/etoken/sign_modules.data";

        pin         = "Hbrfitn11";

        manager     = new VerifyData ();

        try
        {
            module      = manager.getModule ( libName );
            token       = manager.getToken ( module, slot );
            //System.out.println ( "Token for slot #" + slot + " = " + token.getTokenInfo() );

            session     = manager.createRoSession ( token, pin );

            // читаем оба файла
            data            = manager.loadFile ( fileName );
            System.out.println ( "load data file. size = " + data.length );
            signatureData   = manager.loadFile ( signFileName );
            System.out.println ( "load data file. size = " + signatureData.length );

            // ищем публичный ключ
            //System.out.println ( "find verifyKey" );
            tmpKey = new RSAPublicKey();
            tmpKey.getVerify().setBooleanValue ( Boolean.TRUE );
            //tmpKey.getId().setByteArrayValue( signatureKey.getId().getByteArrayValue());


            session.findObjectsInit ( tmpKey );

            foundObjs = session.findObjects ( 1 ); // find first

            if ( foundObjs.length > 0 )
            {
                verifyKey = ( RSAPublicKey ) foundObjs[ 0 ];
                System.out.println ( "find signatureKey (public): " );
                //System.out.println ( signatureKey );
            }
            else
            {
                System.out.println ( "No RSA public key found that can sign!" );
                throw new Exception ( "No RSA public key found that can sign!" );
            }
            session.findObjectsFinal();

            // читаем файл -
            // - для механизма CKM_RSA_PKCS в этом файле требуется внутри наличие дайджеста в формате PKCS1

            // готовим сессию для подписи
            //signatureMechanism = Mechanism.get ( PKCS11Constants.CKM_RSA_PKCS );    // iaik.pkcs.pkcs11.wrapper.PKCS11Exception: CKR_DATA_LEN_RANGE
            signatureMechanism = Mechanism.get ( PKCS11Constants.CKM_SHA1_RSA_PKCS );
            //signatureMechanism = Mechanism.SHA1_RSA_PKCS;                           // err
       	    // initialize for signing
       	    session.verifyInit ( signatureMechanism, verifyKey );

            // Проверяем подпись. Если не ОК - генерится исключение - TokenException.
            session.verify ( data, signatureData );
            System.out.println ( "Verified the signature successfully" );

        } catch ( Exception e )         {
            e.printStackTrace();
        } finally        {
            manager.closeModule ( session, module );
        }
    }

}
