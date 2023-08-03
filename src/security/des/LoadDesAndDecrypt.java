package security.des;


import com.sun.net.ssl.internal.ssl.Provider;
import tools.FileTools;

import javax.crypto.Cipher;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.Key;
import java.security.KeyStore;
import java.security.Security;

/**
 * Загружаем созданный ключ и расшифровываем старый текст.
 * <BR/> Используемые файлы были созданы в классе GenerateDesAndSave.
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 24.10.2012 17:18
 */
public class LoadDesAndDecrypt
{
    public static void main ( String[] args )
    {
        //SecretKey key;
        Key key;
        KeyStore ks;
        String      storeFileName, textFileName, cryptFileName, alias, ksType;
        char[]      password;
        FileOutputStream fos;
        byte[]      bbText, bbCrypt;
        LoadDesAndDecrypt manager;
        KeyStore.SecretKeyEntry skEntry;

        try
        {
            Security.addProvider ( new Provider () );

            manager         = new LoadDesAndDecrypt();
            alias           = "kkk";
            ksType          = "JCEKS";
            password        = new char[] { 'a', 's', 'D', '3', '7', 'q', 'A', '8', '0' };

            storeFileName   = "/home/svj/projects/SVJ/JavaSample/test/security/ksdes.jks";
            textFileName    = "/home/svj/projects/SVJ/JavaSample/test/security/test2.txt";
            cryptFileName   = "/home/svj/projects/SVJ/JavaSample/test/security/test2_crypt.bin";

            ks  = manager.loadStore ( storeFileName, password, ksType );

            System.out.println ( "ks = " + ks );

            // Взять ключ
            key     = ks.getKey ( alias, password );
            System.out.println ( "key = " + key );

            // Читаем шифрованный текст из файла
            bbCrypt  = FileTools.readFile ( cryptFileName );

            // РасШифруем текст этим ключом
            bbText = manager.decrypt ( key, bbCrypt );
            System.out.println ( "bbText = \n" + new String ( bbText, "UTF-8" ) );

        } catch ( Exception e ) {
            e.printStackTrace();
        }
    }

    private byte[] decrypt ( Key key, byte[] bbCrypt ) throws Exception
    {
        Cipher ecipher;
        byte[] result;

        try
        {
            ecipher = Cipher.getInstance ( "DES" );
            ecipher.init ( Cipher.DECRYPT_MODE, key );
            result  = ecipher.doFinal ( bbCrypt );

        } catch ( Exception e )        {
            e.printStackTrace();
            throw e;
        }
        return result;
    }

    /**
     * Загрузить файл-хранилище.
     */
    public KeyStore loadStore ( String  fileName, char[] password, String ksType )
         throws Exception
    {
       FileInputStream fs;
       String			type;
       KeyStore keyStore;

        password   = null;

       if ( ksType == null )   ksType = "PKCS12";
       if ( ksType.equalsIgnoreCase ( "PKCS12" ) ) type = "BC";   // BouncyCastle
       else  type  = "SUN";

       System.out.println ( "load '" + fileName + "' file. ksType = " + ksType + ", type = " + type );
       fs			= new FileInputStream ( fileName );

       //keyStore = KeyStore.getInstance ( ksType, type );
       keyStore = KeyStore.getInstance ( ksType );
       keyStore.load ( fs, password );

       fs.close ();

        System.out.println ( "keyStore = " + keyStore );
        return keyStore;
    }

}
