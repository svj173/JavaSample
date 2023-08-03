package security.crypt;


import javax.crypto.*;
import java.io.*;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

/**
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 27.09.12 15:47
 */
public class EncDecryptSample
{
    public static void exportPrivateKey() throws Exception
    {
        //Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
        Security.addProvider ( new com.sun.net.ssl.internal.ssl.Provider() );

        KeyStore keystore = KeyStore.getInstance("PKCS12", "BC");
        keystore.load(new FileInputStream ("mykeystore.pfx"),
                    "lala".toCharArray());
        KeyPair keyPair = getPrivateKey(keystore, "mykeystore",
                    "lala".toCharArray());
        PrivateKey privateKey = keyPair.getPrivate();
        /**
        * THIS DOES NOT WORK, GENERATES INCORRECT SEQUENCE
        *
        * String encoded = new String(Base64.encode(privateKey.getEncoded()));
        *
        * FileWriter fw = new FileWriter("private.key");
        * fw.write("-----BEGIN RSA PRIVATE KEY-----\n"); fw.write(encoded);
        * fw.write("\n"); fw.write("-----END RSA PRIVATE KEY-----");
        * fw.close();
        **/
        /*
        FileWriter fw = new FileWriter ("private.pem");
        PEMWriter writer = new PEMWriter(fw);
        writer.writeObject ( privateKey );
        writer.close();
        */
    }

    /**
    * Extract the Private Key from the Keystore.
    *
    * @param keystore
    * @param alias
    * @param password
    * @return
    */
    private static KeyPair getPrivateKey(KeyStore keystore, String alias,
                char[] password) {
        try {
            Key key = keystore.getKey(alias, password);
            if (key instanceof PrivateKey) {
                java.security.cert.Certificate cert = keystore
                        .getCertificate ( alias );
                PublicKey publicKey = cert.getPublicKey();
                return new KeyPair (publicKey, (PrivateKey ) key);
            }
        } catch (UnrecoverableKeyException e) {
        } catch (NoSuchAlgorithmException e) {
        } catch (KeyStoreException e) {
        }
        return null;
    }

    private static PublicKey getPublicKey ( String certFilename ) throws CertificateException,
            FileNotFoundException
    {
        InputStream certFileIs = new FileInputStream ( certFilename );
        CertificateFactory cf = CertificateFactory.getInstance ( "X.509" );
        X509Certificate cert = (X509Certificate ) cf
                    .generateCertificate ( certFileIs );
        return cert.getPublicKey();
    }

    private static void encryptFile ( File file, Key key, String transformation )
            throws NoSuchAlgorithmException, NoSuchPaddingException,
            InvalidKeyException, IOException {
        Cipher c = Cipher.getInstance(transformation);
        c.init( Cipher.ENCRYPT_MODE, key);
        FileInputStream is = new FileInputStream(file);
        CipherOutputStream os = new CipherOutputStream (new FileOutputStream(
                    new File (file.getName() + "_enc")), c);
        copy ( is, os );
        os.close();
    }

    private static Key getPrivateKeyFromPem ( String privateKeyName ) throws IOException
    {
        /*
        PEMReader pemReader = new PEMReader(new InputStreamReader(
                new FileInputStream(privateKeyName)));
        Object obj;
        while ((obj = pemReader.readObject()) != null) {
            if (obj instanceof X509Certificate) {
                System.out.println("X509Certificate found");
            } else if (obj instanceof PrivateKey) {
                PrivateKey key = (PrivateKey) obj;
                System.out.println("Private Key found");
            } else if (obj instanceof KeyPair) {
                KeyPair keyPair = (KeyPair) obj;
                System.out.println("KeyPair found");
                return keyPair.getPrivate();
            } else {
                System.out.println(" SOMETING ELSE FOUND");
            }
        }
        */
        return null;
    }

    private static void decryptFile(File encryptedFile, File decryptedFile,
            Key privateKey, String transformation)
            throws NoSuchAlgorithmException, NoSuchPaddingException,
            InvalidKeyException, IOException
    {
        Cipher c = Cipher.getInstance(transformation);
        c.init(Cipher.DECRYPT_MODE, privateKey);
        CipherInputStream is = new CipherInputStream (new FileInputStream(
                    encryptedFile), c);
        FileOutputStream os = new FileOutputStream(decryptedFile);
        copy (is, os);
        is.close();
        os.close();
    }

    /* Копировать из потока в поток. */
    private static void copy ( InputStream is, OutputStream os )
    {
    }

    private static Key getRandomKey() throws NoSuchAlgorithmException,
                NoSuchProviderException
    {
        //Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
        Security.addProvider ( new com.sun.net.ssl.internal.ssl.Provider() );

        KeyGenerator generator = KeyGenerator.getInstance ( "AES", "BC" );
        generator.init(256);
        Key randomKey = generator.generateKey();
        return randomKey;
    }

}
