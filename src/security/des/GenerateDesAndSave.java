package security.des;


import com.sun.net.ssl.internal.ssl.Provider;
import tools.FileTools;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.io.FileOutputStream;
import java.security.KeyStore;
import java.security.Security;

/**
 * Генерим DES ключ. Заносим его в хранилище. Хранилище сохраняем в файле.
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 24.10.2012 16:19
 */
public class GenerateDesAndSave
{
    public static void main ( String[] args )
    {
        SecretKey   key;
        KeyStore    ks;
        String      storeFileName, textFileName, cryptFileName, alias, text;
        char[]      password;
        FileOutputStream fos;
        byte[]      bbText, bbCrypt;
        GenerateDesAndSave manager;
        KeyStore.SecretKeyEntry skEntry;

        try
        {
            Security.addProvider ( new Provider() );

            manager         = new GenerateDesAndSave();
            alias           = "kkk";
            password        = new char[] { 'a', 's', 'D', '3', '7', 'q', 'A', '8', '0' };

            storeFileName   = "/home/svj/projects/SVJ/JavaSample/test/security/ksdes.jks";
            textFileName    = "/home/svj/projects/SVJ/JavaSample/test/security/test2.txt";
            cryptFileName   = "/home/svj/projects/SVJ/JavaSample/test/security/test2_crypt.bin";

            // Генерим DES ключ.
            key             = KeyGenerator.getInstance ( "DES" ).generateKey();

            System.out.println ( "key = " + key );

            // Читаем текст из файла
            bbText  = FileTools.readFile ( textFileName );

            // Шифруем текст этим ключом
            bbCrypt = manager.crypt ( key, bbText );

            // Сохраняем зашифрованный текст в файле
            FileTools.save ( cryptFileName, bbCrypt );

            // Создаем хранилище
            //ks      = KeyStore.getInstance( KeyStore.getDefaultType() );
            //ks      = KeyStore.getInstance( "JKS" );
            ks      = KeyStore.getInstance( "JCEKS" );   // OK
            //ks      = KeyStore.getInstance( "JCE" );   // KeyStoreException: JCE not found
            ks.load (  null, null );

            // Заносим ключ в хранилище.
            skEntry = new KeyStore.SecretKeyEntry ( key );
            ks.setEntry ( alias, skEntry, new KeyStore.PasswordProtection(password) );   // KeyStoreException: Cannot store non-PrivateKeys -- другой тип хранилища - JCEKS

            // 1) может другой провайдер нужен?   - применил KeyStore.getInstance( "JCEKS" ); -- ОК
            // 2) сохранять ключ как ява-обьект?

            // Хранилище сохраняем в файле.
            fos = null;
            try
            {
                fos = new FileOutputStream ( storeFileName );
                ks.store ( fos, password );

            } catch ( Exception e ) {
                e.printStackTrace();
            } finally {
                if ( fos != null )  fos.close();
            }

        } catch ( Exception e ) {
            e.printStackTrace();
        }
    }

    private byte[] crypt ( SecretKey key, byte[] bbText ) throws Exception
    {
        Cipher ecipher;
        byte[] result;

        try
        {
            ecipher = Cipher.getInstance ( "DES" );
            ecipher.init ( Cipher.ENCRYPT_MODE, key );
            result  = ecipher.doFinal ( bbText );

        } catch ( Exception e )        {
            e.printStackTrace();
            throw e;
        }
        return result;
    }

}
