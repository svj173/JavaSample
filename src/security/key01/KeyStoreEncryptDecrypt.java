package security.key01;


import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.Key;
import java.security.KeyStore;

// Import a package for BASE64 encoding.
// If sun.misc doesn't work for you, replace it with
// the line following
// import com.isnetworks.base64.*;

/**
 * KeyStoreExample.java
 * <p/>
 * Encrypts or decrypts a string from STDIN using a
 * 128-bit Blowfish key. That key is stored to the
 * standard KeyStore in the filesystem.
 *
 * Сначала надо зашифровать текст, т.к. в этом методе сохраняются ключи в хранилище в файле на диске.
 * Шифрация и дешифрация идет симетричным ключом.
 *
 * Ошибка - хранилище не пускает в себя этот ключ.
 *
 */
public class KeyStoreEncryptDecrypt
{
    private static String FILENAME = "/home/svj/projects/SVJ/JavaSample/test/etoken/keystore.ks";

    public static void main ( String[] args )
            throws Exception
    {
        String text, encodeStr, passwd;
        char[] password;

        passwd      = "123456";
        password    = new char[ passwd.length() ];
        passwd.getChars ( 0, passwd.length(), password, 0 );

        text        = "Проверка шифрации файла.";

        /*
        if ( args.length != 3 )
        {
            System.err.println ( "Usage: java KeyStoreExample -e|-d password text" );
            System.exit ( 1 );
        }

        // Convert the password into a char array
        password = new char[ args[ 1 ].length () ];
        args[ 1 ].getChars ( 0, args[ 1 ].length (), password, 0 );

        // Check if the user wants to encrypt or decrypt
        if ( args[ 0 ].equals ( "-e" ) )
            encrypt ( password, text );
        else
            decrypt ( password, text );
            */

        // 1) encode
        encodeStr   = encrypt ( password, text );

        // 2) decode
        decrypt ( password, text );
    }

    /**
     * Encrypts the plaintext passed in and outputs
     * the ciphertext to STDOUT. Also stores the secret key
     * to the filesystem inside a KeyStore.
     */
    private static String encrypt ( char[] password, String plaintext )
            throws Exception
    {
        System.out.println ( "Generating a TripleDES key..." );

        // Create a Blowfish key
        KeyGenerator keyGenerator = KeyGenerator.getInstance ( "TripleDES" );
        keyGenerator.init ( 168 );
        Key key = keyGenerator.generateKey();

        System.out.println ( "Done generating the key." );

        // Create a cipher using that key to initialize it
        Cipher cipher = Cipher.getInstance ( "TripleDES/ECB/PKCS5Padding" );
        cipher.init ( Cipher.ENCRYPT_MODE, key );

        byte[] plaintextBytes = plaintext.getBytes ();

        // Perform the actual encryption
        byte[] cipherText = cipher.doFinal ( plaintextBytes );

        // Now we need to Base64-encode it for ascii display
        BASE64Encoder encoder = new BASE64Encoder ();
        String output = encoder.encode ( cipherText );

        // Z/k0OksWeJ8QZrqNp/v69/hrV1v37HFeFTmxWoXR5rJ/OVoFB7BL7V+3W3RP4Rjt
        System.out.println ( "Ciphertext: " + output );

        /* Create a keystore and place the key in it
        * We're using a jceks keystore, which is provided by Sun's JCE.
        * If you don't have the JCE installed, you can use "JKS",
        * which is the default keystore. It doesn't provide
        * the same level of protection however.
        */
        KeyStore keyStore = KeyStore.getInstance ( "JKS" );

        // This initializes a blank keystore
        keyStore.load ( null, null );

        // Now we add the key to the keystore, protected by the password.
        keyStore.setKeyEntry ( "exampleKey", key, password, null );

        // Store the password to the filesystem, protected
        // by the same password.
        FileOutputStream fos = new FileOutputStream ( FILENAME );
        keyStore.store ( fos, password );
        fos.close ();

        return output;
    }

    /**
     * Decrypts the ciphertext passes in and prints the plaintext
     * to STDOUT. Pulls a key from a KeyStore in the filesystem,
     * using the password given.
     */
    private static void decrypt ( char[] password, String ciphertext )
            throws Exception
    {
        // Create a keystore and load it from the filesystem,
        // using the password given.
        KeyStore keyStore = KeyStore.getInstance ( "JKS" );
        FileInputStream fis = new FileInputStream ( FILENAME );
        keyStore.load ( fis, password );
        fis.close ();
        Key key = keyStore.getKey ( "exampleKey", password );

        // Create a cipher using that key to initialize it
        Cipher cipher = Cipher.getInstance ( "TripleDES/ECB/PKCS5Padding" );
        cipher.init ( Cipher.DECRYPT_MODE, key );

        // Perform the decryption, first BASE64 decoding the ciphertext
        byte[] ciphertextBytes = new BASE64Decoder ().decodeBuffer ( ciphertext );
        byte[] decryptedText = cipher.doFinal ( ciphertextBytes );

        String output = new String ( decryptedText );

        System.out.println ( "Plaintext: " + output );
    }

}
