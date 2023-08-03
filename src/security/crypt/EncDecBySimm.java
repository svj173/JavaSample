package security.crypt;


import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

/**
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 28.09.12 12:11
 */
public class EncDecBySimm
{
    Cipher ecipher;
    Cipher dcipher;

    EncDecBySimm ( SecretKey key )
    {
        try
        {
            ecipher = Cipher.getInstance ( "DES" );
            dcipher = Cipher.getInstance ( "DES" );
            ecipher.init ( Cipher.ENCRYPT_MODE, key );
            dcipher.init ( Cipher.DECRYPT_MODE, key );

        } catch ( Exception e )        {
            e.printStackTrace();
        }
    }

    public String encrypt ( String str )
    {
        try
        {
            // Encode the string into bytes using utf-8
            byte[] utf8 = str.getBytes ( "UTF8" );

            // Encrypt
            byte[] enc = ecipher.doFinal ( utf8 );

            // Encode bytes to base64 to get a string
            return new sun.misc.BASE64Encoder ().encode ( enc );

        } catch ( Exception e )        {
            e.printStackTrace();
        }
        return null;
    }

    public String decrypt ( String str )
    {
        try
        {
            // Decode base64 to get bytes
            byte[] dec = new sun.misc.BASE64Decoder().decodeBuffer ( str );

            // Decrypt
            byte[] utf8 = dcipher.doFinal ( dec );

            // Decode using utf-8
            return new String ( utf8, "UTF8" );
        } catch ( Exception e )        {
            e.printStackTrace();
        }
        return null;
    }

    public static void main ( String[] args )
    {
        SecretKey       key;
        EncDecBySimm    encrypter;
        String          text, encrypted, decrypted;

        try
        {
            // Generate a temporary key. In practice, you would save this key.
            // See also Encrypting with DES Using a Pass Phrase.
            key     = KeyGenerator.getInstance ( "DES" ).generateKey();

            text    = "Don't tell anybody!";
            System.out.println ( "text = " + text );

            // Create encrypter/decrypter class
            encrypter = new EncDecBySimm(key);

            // Encrypt
            encrypted = encrypter.encrypt ( text );
            System.out.println ( "encrypted = " + encrypted );

            // Decrypt
            decrypted = encrypter.decrypt ( encrypted );
            System.out.println ( "decrypted = " + decrypted );

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}