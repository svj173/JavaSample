package eToken.signatures;


import eToken.EtokenManager;
import iaik.pkcs.pkcs11.Mechanism;
import iaik.pkcs.pkcs11.Module;
import iaik.pkcs.pkcs11.Session;
import iaik.pkcs.pkcs11.Token;
import iaik.pkcs.pkcs11.objects.Object;
import iaik.pkcs.pkcs11.objects.RSAPublicKey;

import java.io.FileOutputStream;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;


/**
 * Извлекаем из брелка публичный ключ и сохраняем его в файле.
 */
public class SaveSign extends EtokenManager
{
    public static void main ( String[] args )
    {
        SaveSign manager;
        Token               token;
        Module              module;
        String              pin, libName, fileName, signFileName;
        Session             session;
        int                 slot;
        byte[]              data, signatureValue;
        RSAPublicKey        publicKey, tmpKey;
        Object[]            foundObjs;
        Mechanism           signatureMechanism;
        BigInteger modulus, publicExponent;
        RSAPublicKeySpec rsaPublicKeySpec;
        KeyFactory keyFactory;
        java.security.interfaces.RSAPublicKey javaRsaPublicKey;
        X509EncodedKeySpec x509EncodedPublicKey;
        FileOutputStream fos;

        module      = null;
        session     = null;

        libName     = "libeTPkcs11.so";
        slot        = 0;   // USB-0
        fileName    = "/home/svj/projects/SVJ/JavaSample/test/etoken/etoken_public.bin";
        //fileName    = "/home/svj/projects/SVJ/JavaSample/test/etoken/modules3.xml";

        pin         = "Hbrfitn11";

        manager     = new SaveSign ();

        try
        {
            module      = manager.getModule ( libName );
            token       = manager.getToken ( module, slot );
            //System.out.println ( "Token for slot #" + slot + " = " + token.getTokenInfo() );

            session     = manager.createRoSession ( token, pin );

            // ищем приватный ключ
            System.out.println ( "find private signature key" );
            tmpKey = new RSAPublicKey();
            tmpKey.getVerify().setBooleanValue(Boolean.TRUE);
         	// we search for a public key with the same ID
            //tmpKey.getId().setByteArrayValue( signatureKey.getId().getByteArrayValue());

            session.findObjectsInit ( tmpKey );

            foundObjs = session.findObjects ( 1 ); // find first

            if ( foundObjs.length > 0 )
            {
                publicKey = ( RSAPublicKey ) foundObjs[ 0 ];
                System.out.println ( "find signatureKey (public): " );
                //System.out.println ( signatureKey );
            }
            else
            {
                System.out.println ( "No RSA public key found that can sign!" );
                throw new Exception ( "No RSA public key found that can sign!" );
            }
            session.findObjectsFinal();

            // Сохраняем ключ в файле
            System.out.println ( "Writing the public key to file: " + fileName );

            modulus             = new BigInteger ( 1, publicKey.getModulus().getByteArrayValue () );
            publicExponent      = new BigInteger ( 1, publicKey.getPublicExponent ().getByteArrayValue() );
            rsaPublicKeySpec    = new RSAPublicKeySpec ( modulus, publicExponent );
            keyFactory          = KeyFactory.getInstance ( "RSA" );
            javaRsaPublicKey    = ( java.security.interfaces.RSAPublicKey ) keyFactory.generatePublic ( rsaPublicKeySpec );

            x509EncodedPublicKey = keyFactory.getKeySpec ( javaRsaPublicKey, X509EncodedKeySpec.class );

            fos = new FileOutputStream ( fileName );
            fos.write ( x509EncodedPublicKey.getEncoded() );
            fos.flush();
            fos.close();

        } catch ( Exception e )         {
            e.printStackTrace();
        } finally        {
            manager.closeModule ( session, module );
        }
    }

}
