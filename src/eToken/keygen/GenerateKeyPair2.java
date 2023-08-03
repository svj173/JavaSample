package eToken.keygen;


import eToken.EtokenManager;
import iaik.pkcs.pkcs11.*;
import iaik.pkcs.pkcs11.objects.KeyPair;
import iaik.pkcs.pkcs11.objects.Object;
import iaik.pkcs.pkcs11.objects.RSAPrivateKey;
import iaik.pkcs.pkcs11.objects.RSAPublicKey;
import iaik.pkcs.pkcs11.wrapper.PKCS11Constants;

import java.io.FileOutputStream;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import java.util.HashSet;

/**
 * This demo program generates a 2048 bit RSA key-pair on the token and writes the public key to a file.
 *
 * Генерит пару ключей средством брелка. Оба ключа сохраняются в брелке как два разных обьекта.
 * Публичный ключ сохраняем в файле, в бинарном виде.
 * В конце работы - находим в брелке свежесгенерированный публичный ключ по его ИД.
 *
 */
public class GenerateKeyPair2  extends EtokenManager
{
    public static void main ( String[] args )
    {
        GenerateKeyPair2    manager;
        Token               token;
        Module              module;
        String              pin, libName, fileName;
        Session             session;
        int                 slot;
        byte[]              pubKeyId;
        KeyPair             keyPair;
        RSAPublicKey        publicKey;

        module      = null;
        session     = null;

        libName     = "libeTPkcs11.so";
        slot        = 0;   // USB-0
        // для сохранения публичного ключа, в виде описания х509 - но не сам сертификат.
        fileName    = "/home/svj/projects/SVJ/JavaSample/test/etoken/pub_key_509.xpk";

        //pin         = "12345678";
        pin         = "Hbrfitn11";

        //label       = "EltexEMS";

        manager     = new GenerateKeyPair2();

        try
        {
            module      = manager.getModule ( libName );
            token       = manager.getToken ( module, slot );
            System.out.println ( "Token for slot #" + slot + " = " + token.getTokenInfo() );

            session     = manager.createSession ( token, pin );

            // ID публичного ключа. По нему также можно искать ключ в брелке.
            pubKeyId    = manager.createId();

            // Брелок генерит пару ключей, сохраняя их у себя как два обьекта.
            keyPair     = manager.generateKeyPair ( token, session, pubKeyId );

            manager.savePublicKeyToFile ( keyPair, fileName );

            publicKey   = manager.findPublicKey ( session, pubKeyId );
            System.out.println ( "pubKeyId = " + pubKeyId );
            System.out.println ( "find publicKey = " + publicKey );

        } catch ( Exception e )         {
            e.printStackTrace();
        } finally        {
            manager.closeModule ( session, module );
        }
    }

    private RSAPublicKey findPublicKey ( Session session, byte[] pubKeyId ) throws Exception
    {
        RSAPublicKey    publicKey, result;
        Object[]        foundPublicKeys;

        publicKey = new RSAPublicKey();
        publicKey.getId().setByteArrayValue ( pubKeyId );

        session.findObjectsInit ( publicKey );
        foundPublicKeys = session.findObjects ( 1 );

        session.findObjectsFinal();

        if ( foundPublicKeys == null || foundPublicKeys.length == 0 )
            result = null;
        else
            result = (RSAPublicKey) foundPublicKeys[0];

        return result;
    }

    private void savePublicKeyToFile ( KeyPair keyPair, String fileName ) throws Exception
    {
        RSAPublicKey        publicKey;
        RSAPrivateKey       privateKey;
        BigInteger          modulus, publicExponent;
        RSAPublicKeySpec    rsaPublicKeySpec;
        KeyFactory          keyFactory;
        java.security.interfaces.RSAPublicKey javaRsaPublicKey;
        X509EncodedKeySpec  x509EncodedPublicKey;
        FileOutputStream    fos;

        publicKey   = ( RSAPublicKey ) keyPair.getPublicKey();
        privateKey  = ( RSAPrivateKey ) keyPair.getPrivateKey();

        System.out.println ( "The public key is" );
        System.out.println ( publicKey );
        System.out.println ( "The private key is" );
        System.out.println ( privateKey );

        // write the public key to file
        System.out.println ( "################################################################################" );
        System.out.println ( "Writing the public key of the generated key-pair to file: " + fileName );

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
    }

    /**
     * Брелок генерит пару ключей, сохраняя их у себя как два обьекта.
     * @param token     Брелок
     * @param session   Сессия брелка
     * @param pubKeyId  ИД публичного ключа. По нему можно искать публичный ключ в брелке.
     * @return          Пара ключей.
     * @throws Exception  Ошибки генерации.
     */
    private KeyPair generateKeyPair ( Token token, Session session, byte[] pubKeyId ) throws Exception
    {
        KeyPair             keyPair;
        HashSet<Mechanism>  supportedMechanisms;
        MechanismInfo       signatureMechanismInfo;
        Mechanism           keyPairGenerationMechanism;
        RSAPublicKey        publicKey;
        RSAPrivateKey       privateKey;
        byte[]              publicExponentBytes;

        supportedMechanisms = new HashSet<Mechanism> ( Arrays.asList ( token.getMechanismList() ) );

        if ( supportedMechanisms.contains ( Mechanism.get ( PKCS11Constants.CKM_RSA_PKCS ) ) )
        {
            signatureMechanismInfo = token.getMechanismInfo ( Mechanism.get ( PKCS11Constants.CKM_RSA_PKCS ) );
        }
        else if ( supportedMechanisms.contains ( Mechanism.get ( PKCS11Constants.CKM_RSA_X_509 ) ) )
        {
            signatureMechanismInfo = token.getMechanismInfo ( Mechanism.get ( PKCS11Constants.CKM_RSA_X_509 ) );
        }
        else if ( supportedMechanisms.contains ( Mechanism.get ( PKCS11Constants.CKM_RSA_9796 ) ) )
        {
            signatureMechanismInfo = token.getMechanismInfo ( Mechanism.get ( PKCS11Constants.CKM_RSA_9796 ) );
        }
        else if ( supportedMechanisms.contains ( Mechanism.get ( PKCS11Constants.CKM_RSA_PKCS_OAEP ) ) )
        {
            signatureMechanismInfo = token.getMechanismInfo ( Mechanism.get ( PKCS11Constants.CKM_RSA_PKCS_OAEP ) );
        }
        else
        {
            signatureMechanismInfo = null;
        }

        keyPairGenerationMechanism = Mechanism.get ( PKCS11Constants.CKM_RSA_PKCS_KEY_PAIR_GEN );

        publicKey   = new RSAPublicKey();
        privateKey  = new RSAPrivateKey();

        // set the general attributes for the public key
        publicKey.getModulusBits().setLongValue ( ( long ) 2048 );

        publicExponentBytes = new byte[] { 0x01, 0x00, 0x01 }; // 2^16 + 1

        publicKey.getPublicExponent().setByteArrayValue ( publicExponentBytes );
        publicKey.getToken().setBooleanValue ( Boolean.TRUE );

        publicKey.getId().setByteArrayValue ( pubKeyId );
        //rsaPublicKeyTemplate.getLabel().setCharArrayValue(slotName.toCharArray());

        privateKey.getSensitive().setBooleanValue ( Boolean.TRUE );
        privateKey.getToken().setBooleanValue ( Boolean.TRUE );
        privateKey.getPrivate().setBooleanValue ( Boolean.TRUE );
        privateKey.getId().setByteArrayValue ( pubKeyId );
        //byte[] subject = fileName.getBytes();
        //rsaPrivateKeyTemplate.getSubject().setByteArrayValue(subject);
        //rsaPrivateKeyTemplate.getLabel().setCharArrayValue(slotName.toCharArray());

        // set the attributes in a way netscape does, this should work with most tokens
        if ( signatureMechanismInfo != null )
        {
            publicKey.getVerify().setBooleanValue ( signatureMechanismInfo.isVerify() );
            publicKey.getVerifyRecover().setBooleanValue ( signatureMechanismInfo.isVerifyRecover() );
            publicKey.getEncrypt().setBooleanValue ( signatureMechanismInfo.isEncrypt() );
            publicKey.getDerive().setBooleanValue ( signatureMechanismInfo.isDerive() );
            publicKey.getWrap().setBooleanValue ( signatureMechanismInfo.isWrap() );

            privateKey.getSign().setBooleanValue ( signatureMechanismInfo.isSign() );
            privateKey.getSignRecover().setBooleanValue ( signatureMechanismInfo.isSignRecover() );
            privateKey.getDecrypt().setBooleanValue ( signatureMechanismInfo.isDecrypt() );
            privateKey.getDerive().setBooleanValue ( signatureMechanismInfo.isDerive() );
            privateKey.getUnwrap().setBooleanValue ( signatureMechanismInfo.isUnwrap() );
        }
        else
        {
            // if we have no information we assume these attributes
            privateKey.getSign().setBooleanValue ( Boolean.TRUE );
            privateKey.getDecrypt().setBooleanValue ( Boolean.TRUE );

            publicKey.getVerify().setBooleanValue ( Boolean.TRUE );
            publicKey.getEncrypt().setBooleanValue ( Boolean.TRUE );
        }

        // netscape does not set these attribute, so we do no either
        publicKey.getKeyType().setPresent ( false );
        publicKey.getObjectClass().setPresent ( false );

        privateKey.getKeyType().setPresent ( false );
        privateKey.getObjectClass().setPresent ( false );

        keyPair = session.generateKeyPair ( keyPairGenerationMechanism, publicKey, privateKey );

        return keyPair;
    }


}
