package security.crypt;


import javax.crypto.Cipher;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.Key;
import java.security.KeyStore;
import java.security.Security;
import java.security.cert.X509Certificate;

//import org.bouncycastle.jce.provider.BouncyCastleProvider;

/**
 * Зашифровать данные приватным ключом, взятом из хранилища,
 * Исходные данные также берутся из файла на диске.
 * <BR/>
 * <BR/> Размер исходного файла = 79. Должен быть не больше чем 2048 / 8 - 11 -- особенность шифрации RSA
 * <BR/> Размер рез файла = 256.
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 27.09.12 15:31
 */
class EncryptFromFile
{
    public static void main ( String[] args )
    {
        KeyStore        keystore;
        String          alias, dataFileName, dataEncryptFileName, keyStoreFileName, providerName, algoritmName, keyStoreType, keyPasswd;
        X509Certificate cert;
        Cipher          rsaCipher;
        File f;
        int sizecontent;
        byte[] data, encrypteddata;
        FileInputStream freader;
        FileOutputStream sigfos;
        Key key;

        keyStoreType        = "JKS";
        //keyStoreType        = "PKCS12";

        //providerName        = "BC";
        //providerName        = "SUN";
        //providerName        = "SunJSSE";
        providerName        = "SunJCE";    // у этого провайдера есть алгоритм RSA для Chipher. У SUN - нет.

        // "algorithm/mode/padding"  или "algorithm"
        algoritmName        = "RSA/ECB/PKCS1Padding";     // NoSuchAlgorithmException: No such algorithm:
        //algoritmName        = "RSA";
        //algoritmName        = "SHA1withRSA";

        //alias               = "mykey";
        alias               = "eltex";

        keyPasswd           = "123456";

        //keyStoreFileName    = "/home/svj/projects/SVJ/JavaSample/test/etoken_ems/eltex_ems_master.p12";
        keyStoreFileName    = "/home/svj/projects/SVJ/JavaSample/test/etoken_ems/eltex_ems_master.jks";

        dataFileName        = "/home/svj/projects/SVJ/JavaSample/test/etoken_ems/files/pasport.txt";
        dataEncryptFileName = "/home/svj/projects/SVJ/JavaSample/test/etoken_ems/files/pasport_encrypt.bin";

        try
        {
            //Security.addProvider ( new BouncyCastleProvider() ); /* Use existing keystore */
            Security.addProvider ( new com.sun.net.ssl.internal.ssl.Provider() );

            keystore    = KeyStore.getInstance ( keyStoreType );
            keystore.load ( new FileInputStream ( keyStoreFileName ), null );     // IOException: DerInputStream.getLength(): lengthTag=109, too big -- если задаешь неверный тип хранилища.

            //cert        = ( X509Certificate ) keystore.getCertificate ( alias );
            key         = keystore.getKey ( alias, keyPasswd.toCharArray() );

            rsaCipher   = Cipher.getInstance ( algoritmName, providerName );
            //rsaCipher.init ( Cipher.ENCRYPT_MODE, cert );    // жалуется что этот ключ DSA а не RSA.
            rsaCipher.init ( Cipher.ENCRYPT_MODE, key );

            System.out.println ( "rsaCipher BlockSize : " + rsaCipher.getBlockSize() );
            System.out.println ( "rsaCipher algorithm : " + rsaCipher.getAlgorithm() );

            f           = new File ( dataFileName );
            freader     = new FileInputStream ( f );
            sigfos      = new FileOutputStream ( dataEncryptFileName );

            //*
            //------ Get the content data from file -------------
            sizecontent = ( ( int ) f.length () );
            data        = new byte[ sizecontent ];

            try
            {
                System.out.println ( "Content Bytes: " + freader.read ( data, 0, sizecontent ) );
                freader.close();

            } catch ( IOException ioe ) {
                System.out.println ( ioe.toString() );
                return;
            }

            System.out.println ( "Content Bytes 2: " + data.length );

            encrypteddata = rsaCipher.doFinal ( data );         // IllegalBlockSizeException: Data must not be longer than 245 bytes

            EncryptFromFile.displayData ( encrypteddata );

            // Save to file
            sigfos.write ( encrypteddata );
            sigfos.close();
            //*/

            /*
            CipherOutputStream cos = new CipherOutputStream ( sigfos, rsaCipher );

            byte[] block = new byte[8];
            int i;
            while ((i = freader.read(block)) != -1)
            {
                System.out.println ( " --" + i );
                cos.write ( block, 0, i );
            }
            cos.flush ();
            cos.close();        // почему-то файл пустой, хотя cos.write отрабатывает на весь файл.
            */

        } catch ( Exception e )         {
            e.printStackTrace();
            //System.err.println ( "Caught exception : " + e.toString () );
        }
    }

    private static void displayData ( byte[] data )
    {
        System.out.println ( "Size of encrypted data: " + data.length );

        int bytecon;
        //to get unsigned byte representation
        for ( int i = 0; i < data.length; i++ )
        {
            bytecon = data[ i ] & 0xFF;
            // byte-wise AND converts signed byte to unsigned.
            if ( bytecon < 16 ) System.out.print ( "0" + Integer.toHexString ( bytecon ).toUpperCase () + " " );
                // pad on left if single hex digit.
            else System.out.print ( Integer.toHexString ( bytecon ).toUpperCase () + " " );
            // pad on left if single hex digit.
        }
    }
}