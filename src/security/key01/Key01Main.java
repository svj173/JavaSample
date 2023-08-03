package security.key01;


import com.sun.net.ssl.internal.ssl.Provider;
import com.sun.org.apache.bcel.internal.util.ByteSequence;
import com.svj.utils.string.Conversion;
import tools.DumpTools;
import tools.FileTools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.*;
import java.util.Enumeration;

/**
 * Попытка занести приватный ключ внутрь кода и им читать подписи конфиг-файлов.
 * <BR/>
 * <BR/>  - alias=mykey
  - storepass=123456
  - keypass=123456

 Просмотр содержимого
 keytool -list -v -keystore ./svj.jks -storepass 123456

 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 13.09.12 10:52
 */
public class Key01Main
{
    private static final String ALGORITM    = "SHA1withDSA";    // SHA1withDSA, MD5withRSA

    private  KeyStore keyStore = null;
    private  String   fileName = "";
    private  String   algoritm = ALGORITM;

    public Key01Main ()
    {
        // Add Providers
        // - Add PKCS12 provider
        //Security.addProvider ( new BouncyCastleProvider () );
        // - Add SSL provider
        Security.addProvider ( new Provider() );

        keyStore = null;
    }

    /**
     * Загрузить из файла хранилище ключей (в виде массива байт) и распечатать их в символьно-цифровом виде.
     * @param fileName    Имя файла.
     * @throws Exception
     */
    public void loadStoreAsBytes ( String  fileName )
         throws Exception
    {
        FileInputStream  fs;
        String			 str;
        File file;
        long size;
        byte[] bb;
        int ic;

        System.out.println ( "loadStoreAsBytes = '" + fileName + "'" );
        file = new File ( fileName );
        size = file.length();
        System.out.println ( "size = " + size );
        bb  = new byte[(int)size];

        fs			= new FileInputStream ( file );
        ic  = fs.read ( bb );
        System.out.println ( "ic = " + ic );

        fs.close ();
        this.fileName  = fileName;

        str = DumpTools.listArray ( bb, ',' );
        System.out.println ( "result = \n" + str );
    }


    /**
     * Загрузить файл-хранилище.
     */
    public void loadStore ( String  fileName, String filePassword, String ksType )
         throws Exception
    {
       FileInputStream  fs;
       String			type;
       char[] password   = null;

       if ( filePassword != null )
          password = filePassword.toCharArray ();
       if ( ksType == null )   ksType = "PKCS12";
       if ( ksType.equalsIgnoreCase ( "PKCS12" ) ) type = "BC";   // BouncyCastle
       else  type  = "SUN";

       System.out.println ( "load '" + fileName + "' file. ksType = " + ksType + ", type = " + type );
       fs			= new FileInputStream ( fileName );
       keyStore = KeyStore.getInstance ( ksType, type );
       keyStore.load ( fs, password );

       fs.close ();
       this.fileName  = fileName;

        System.out.println ( "keyStore = " + keyStore );
    }

    /**
     * Создать хранилище на основе массива байт - байтового представления хранилища.
     * @param byteStore
     * @param filePassword
     * @param ksType
     * @throws Exception
     */
    public void loadStore ( byte[] byteStore, String filePassword, String ksType )
         throws Exception
    {
        ByteSequence bs;
       //FileInputStream  fs;
       String			type;
       char[] password   = null;

       if ( filePassword != null )
          password = filePassword.toCharArray ();
       if ( ksType == null )   ksType = "PKCS12";
       if ( ksType.equalsIgnoreCase ( "PKCS12" ) ) type = "BC";   // BouncyCastle
       else  type  = "SUN";

       System.out.println ( "load '" + fileName + "' file. ksType = " + ksType + ", type = " + type );

       //fs			= new FileInputStream ( fileName );
        bs  = new ByteSequence ( byteStore );
       keyStore = KeyStore.getInstance ( ksType, type );
       keyStore.load ( bs, password );

       bs.close ();

        System.out.println ( "keyStore = " + keyStore );
    }

    public Enumeration aliases () throws KeyStoreException
    {
       return   keyStore.aliases ();
    }

    public String  getFileName ()
    {
       return fileName;
    }

    /**
     * Подписать текстовые данные.
     * @param privateKey Прив ключ для подписи.
     * @param text       Исходный текст.
     * @return           Подпись - Массив байт.
     * @throws Exception
     */
    public byte[]  sign ( PrivateKey privateKey, String text )
         throws Exception
    {
        return sign ( privateKey, text.getBytes() );
    }

    /* Подписать массив байт. */
    public byte[]  sign ( PrivateKey privateKey, byte[] text )
         throws Exception
    {
       String   result;
       Signature sig;

        // createSignature ниже более круче

       // Set up signature object.
       sig  = Signature.getInstance ( algoritm, "SUN" );
       //sig = Signature.getInstance ( "MD5withRSA", "BC" );
       sig.initSign ( privateKey );
       //sig.initSign ( my_private, sec_rand ); // Initialize with my private signing key.
       sig.update ( text );
       // Получить подписанную строку
       byte[] b = sig.sign();
       // Преобразовать байты в символьный текст - Это только подпись.
       result   = Conversion.byteArrayToBase64String ( b );
        System.out.println ( "sig = " + result );

       return b;
    }

    /* Создать сигнатуру на основе приватного ключа.
     * Почему? Ведь сигнатура, это подписанный публичный ключ. Может здесь - публичный ключ создается на основе приватного? */
    private Signature createSignature ( PrivateKey privateKey )
         throws Exception
    {
        Signature       result;
        SecureRandom    sec_rand;
        byte[]          lock;

       // Генератор случайных чисел
       lock     = new byte[24];
       sec_rand = SecureRandom.getInstance ( "SHA1PRNG", "SUN" );

       sec_rand.nextBytes ( lock );

       result = Signature.getInstance ( ALGORITM, "BC" );
       result.initSign ( privateKey, sec_rand );
       result.update ( lock ); // put plain text of lock data into signature.

       // либо
       //SecureRandom   sec_rand = new SecureRandom ();
       //result.initSign ( privateKey, sec_rand );

       return result;
    }

    /**
     * Взять из хранилища публичный ключ по его имени.
     * @param alias    Имя
     * @return
     * @throws Exception
     */
    public PublicKey getPublicKey ( String alias )
         throws Exception
    {
        java.security.cert.Certificate cert;

        cert    = keyStore.getCertificate ( alias );
        return cert.getPublicKey();
    }

    /* Взять их хранилища приватный ключ - по имени и паролю доступа к ключу. */
    public PrivateKey getPrivateKey ( String alias, String ksPassword )
         throws Exception
    {
        System.out.println ( "get Key. alias = " + alias + ", passw =  " + ksPassword );
       PrivateKey  prKey = null;
       char[]   passwd   = ksPassword.toCharArray ();
       if ( keyStore.isKeyEntry ( alias) )
           System.out.println ( "KeyStore has alias = " + alias );
       //Object   obj   = keyStore.getKey(alias, passwd); // Get private key.
       //logger.debug ( "Key " + obj.getClass ().getName() );
       //Key key  = keyStore.getKey(alias, passwd); // Get private key.
       prKey = (PrivateKey) keyStore.getKey(alias, passwd); // Get private key.
       //my_cert = (X509Certificate) keyStore.getCertificate(l); // Get Signing Certificate
       return   prKey;
    }

    /**
     * Подпсиать текст (взятым из файла) приватным ключом (взятом из хранилища).
     * @param fileName   Имя файла с текстом.
     * @param alias      Имя приватного ключа.
     * @param keyPassword  Пароль на доступ к приватному ключу.
     * @return             Подпись в виде массива байт.
     * @throws Exception
     */
    public byte[] signText ( String fileName, String alias, String keyPassword ) throws Exception
    {
        PrivateKey  privateKey;
        File file;
        long size;
        byte[] bb, signText;
        FileInputStream fis;

        privateKey  = getPrivateKey ( alias, keyPassword );

        // читаем текст
        file = new File ( fileName );
        size = file.length();
        bb   = new byte[(int)size];
        fis  = new FileInputStream ( file );
        fis.read ( bb );
        fis.close();

        // подписываем
        signText    = sign ( privateKey, bb );

        return signText;
    }


    public boolean verifySig ( byte[] data, PublicKey key, byte[] sig ) throws Exception
    {
        Signature signer = Signature.getInstance ( ALGORITM );
        signer.initVerify ( key );
        signer.update ( data );
        return ( signer.verify ( sig ) );
    }

    private void saveSign ( byte[] bbSign, String fileSign ) throws Exception
    {
        FileOutputStream fos;

        try
        {
            fos = new FileOutputStream ( fileSign );
            fos.write ( bbSign );
            fos.flush();
            fos.close();

        } catch ( Exception e )        {
            e.printStackTrace();
            throw e;
        }
    }


    public static void main ( String[] args )
    {
        Key01Main   manager;
        String      fileName, fileTextCopy, dirName, fileSign1, fileSign2;
        byte[]      bbSign, bbText, bbStore;
        boolean     b;
        PrivateKey  privateKey;
        PublicKey   publicKey;

        dirName     = "/home/svj/projects/SVJ/JavaSample/test/security";
        fileName    = "/home/svj/projects/SVJ/JavaSample/key/svj.jks";
        fileTextCopy    = "/home/svj/projects/SVJ/JavaSample/test/test1.txt";
        fileSign1   = dirName + "/sign1.txt";
        fileSign2   = "/home/svj/projects/SVJ/JavaSample/test/sign1.txt";

        try
        {
            manager     = new Key01Main();

            // грузим из файла
            //manager.loadStore ( fileName, "123456", "jks" );

            // Грузим хранилище ключей из массива байт
            //bb  = new byte[] { 1, 2, 3, 4, 5 };
            bbStore  = new byte[] { -2, -19, -2, -19, 0, 0, 0, 2, 0, 0, 0, 1, 0, 0, 0, 1, 0, 5, 109, 121, 107, 101, 121, 0, 0, 1, 42,
                    23, 120, 85, 109, 0, 0, 1, -113, 48, -126, 1, -117, 48, 14, 6, 10, 43, 6, 1, 4, 1, 42, 2, 17, 1, 1, 5, 0, 4,
                    -126, 1, 119, 12, -47, -88, 91, 103, -123, 62, -36, 108, 13, -116, 50, 27, -66, 108, 89, 120, -1, -9, 93,
                    -20, -50, -11, -76, 55, 32, -111, 84, -92, -30, -58, -85, 49, -108, -120, 90, -120, 52, -90, 25, -5, -66,
                    35, 68, -100, 28, -24, 96, 52, -72, -74, 72, -54, -38, -121, 96, 21, 88, -46, -2, 47, 56, 60, -61, 29, 83,
                    -105, -17, 43, -80, 92, 9, 31, -45, -27, -23, 126, 31, -59, 57, 94, -77, 61, 2, -104, -74, -85, 113, -106,
                    -125, -17, 79, -42, 118, 53, -92, 111, -88, -127, -4, 116, 7, -58, 10, -99, -41, -51, 38, 33, -59, -5, 100,
                    115, -58, 98, -109, 91, -17, 28, -79, -50, -65, 79, -7, -121, 74, 1, -78, -11, 33, 104, 90, 20, -88, 24, -32,
                    -113, 87, -64, -22, -37, -51, 77, 88, -33, 31, 72, -79, 3, 47, 105, -94, 36, -112, 103, -51, -86, -94, -55, -11,
                    111, -107, -97, -62, -24, -100, -6, -121, -19, -4, -30, -89, 125, 107, 96, 53, 35, 50, 30, 127, 95, -87, -71, 98,
                    56, -17, -64, -80, -120, 31, -112, -110, 126, -72, 104, -75, 28, -116, 104, -65, 40, -57, -59, 37, -61, 103, -62,
                    96, 88, -32, 93, -107, 114, 69, -118, 57, 84, -122, 67, -65, 79, 10, 11, -2, 18, -91, 23, 68, -78, 107, 108, -104,
                    -112, 120, -63, -53, -69, -20, 58, 80, -127, 42, -94, 75, 36, 88, 34, 111, 8, -3, -100, -56, 73, 125, 53, 22, -114,
                    87, 125, 30, -26, -72, 125, 7, -39, -33, 45, -76, -94, -109, -70, -20, 61, 111, 8, 35, 57, 117, -128, -104, 84,
                    -18, 26, 75, 64, 4, 20, -13, 14, -52, -100, -99, 31, -58, -125, -2, 121, -106, -72, 15, -8, -115, -119, 89, -23, 95,
                    -100, 15, -116, -24, 82, -93, -37, -104, -11, -65, 102, -64, -112, -19, -34, 41, 30, 37, 46, 0, 81, 46, 55, 65, -22, -102, 31, 38, 116, -124, -1, -85, -121, 35, -75, 69, -104, 24, 74, 47, -118, -119, -121, 0, -15, 50, -28, -18, -43, -99, -80, 73, 121, -95, -29, 113, 114, -114, -51, -29, -54, -41, -37, 46, -61, 2, -37, 5, 27, 0, 0, 0, 1, 0, 5, 88, 46, 53, 48, 57, 0, 0, 2, 91, 48, -126, 2, 87, 48, -126, 2, 21, -96, 3, 2, 1, 2, 2, 4, 76, 79, -68, -128, 48, 11, 6, 7, 42, -122, 72, -50, 56, 4, 3, 5, 0, 48, 15, 49, 13, 48, 11, 6, 3, 85, 4, 3, 19, 4, 71, 97, 116, 101, 48, 30, 23, 13, 49, 48, 48, 55, 50, 56, 48, 53, 49, 51, 51, 54, 90, 23, 13, 50, 48, 48, 55, 50, 53, 48, 53, 49, 51, 51, 54, 90, 48, 15, 49, 13, 48, 11, 6, 3, 85, 4, 3, 19, 4, 71, 97, 116, 101, 48, -126, 1, -73, 48, -126, 1, 44, 6, 7, 42, -122, 72, -50, 56, 4, 1, 48, -126, 1, 31, 2, -127, -127, 0, -3, 127, 83, -127, 29, 117, 18, 41, 82, -33, 74, -100, 46, -20, -28, -25, -10, 17, -73, 82, 60, -17, 68, 0, -61, 30, 63, -128, -74, 81, 38, 105, 69, 93, 64, 34, 81, -5, 89, 61, -115, 88, -6, -65, -59, -11, -70, 48, -10, -53, -101, 85, 108, -41, -127, 59, -128, 29, 52, 111, -14, 102, 96, -73, 107, -103, 80, -91, -92, -97, -97, -24, 4, 123, 16, 34, -62, 79, -69, -87, -41, -2, -73, -58, 27, -8, 59, 87, -25, -58, -88, -90, 21, 15, 4, -5, -125, -10, -45, -59, 30, -61, 2, 53, 84, 19, 90, 22, -111, 50, -10, 117, -13, -82, 43, 97, -41, 42, -17, -14, 34, 3, 25, -99, -47, 72, 1, -57, 2, 21, 0, -105, 96, 80, -113, 21, 35, 11, -52, -78, -110, -71, -126, -94, -21, -124, 11, -16, 88, 28, -11, 2, -127, -127, 0, -9, -31, -96, -123, -42, -101, 61, -34, -53, -68, -85, 92, 54, -72, 87, -71, 121, -108, -81, -69, -6, 58, -22, -126, -7, 87, 76, 11, 61, 7, -126, 103, 81, 89, 87, -114, -70, -44, 89, 79, -26, 113, 7, 16, -127, -128, -76, 73, 22, 113, 35, -24, 76, 40, 22, 19, -73, -49, 9, 50, -116, -56, -90, -31, 60, 22, 122, -117, 84, 124, -115, 40, -32, -93, -82, 30, 43, -77, -90, 117, -111, 110, -93, 127, 11, -6, 33, 53, 98, -15, -5, 98, 122, 1, 36, 59, -52, -92, -15, -66, -88, 81, -112, -119, -88, -125, -33, -31, 90, -27, -97, 6, -110, -117, 102, 94, -128, 123, 85, 37, 100, 1, 76, 59, -2, -49, 73, 42, 3, -127, -124, 0, 2, -127, -128, 74, 33, -75, 70, -16, -44, -97, 61, -96, 101, -25, 23, -14, 17, 105, 49, 23, -61, 71, 82, -1, -62, 119, 83, -38, -51, -71, -48, -69, 101, -42, -62, 8, -120, 0, 86, 39, -17, 47, -17, -10, -100, 86, -2, 61, 102, 0, 87, -20, -90, 89, 34, 71, -76, 36, -114, -122, -70, 2, -104, 108, 23, 5, 92, -50, -3, -15, -117, 60, 26, -27, -61, 45, 12, -84, -59, -80, 79, -119, -78, -113, 107, -47, -96, -5, -111, 12, 88, 63, -90, 71, 10, 89, 24, -114, 79, 119, -37, 104, -48, 84, 15, 105, 107, 0, 15, 111, 33, 39, 75, -69, -61, 124, 1, 71, 4, 69, 96, -101, -28, -86, 117, 124, 87, -91, 97, 23, 41, 48, 11, 6, 7, 42, -122, 72, -50, 56, 4, 3, 5, 0, 3, 47, 0, 48, 44, 2, 20, 14, 16, -51, 37, -59, 4, 83, -118, 6, -123, 113, -68, -38, 79, 2, -123, 31, 91, 59, 4, 2, 20, 73, 108, -66, 4, -114, 38, -7, 4, -75, 12, 97, -36, -96, -69, -37, -4, -40, -69, 15, -5, 88, -68, 18, 75, -93, 119, 112, -68, -123, -78, 110, 86, -50, -96, -100, -120, -26, 35, -39, 111
            };
            manager.loadStore ( bbStore, "123456", "jks" );

            // загрузить ключ из файла и распечатать их в символьно-цифровом виде как массив байт.
            //manager.loadStoreAsBytes ( fileName );

            // Читаем файл
            bbText  = FileTools.readFile ( dirName + "/test1.txt" );

            /*
            // приватный ключ
            privateKey  = manager.getPrivateKey ( "mykey", "123456" );

            // подписываем  -- MC0CFH2dYbU84rEJf9dUCzjGmC06oSUUAhUAkVsul54RnFo9Z1wwiDbuMWxthmE=
            bbSign    = manager.sign ( privateKey, bbText );

            // Скидываем подпись в файл
            manager.saveSign ( bbSign, fileSign1 );

            // Подписываем файл. Получаем сигнатуру.
            //bb = manager.signText ( dirName + "/text1.txt", "mykey", "123456" );
            //System.out.println ( "signForText = " + signForText );
            */

            // Читаем подпись файла - из другого файла
            bbSign      = FileTools.readFile ( fileSign1 );

            // Взять публичный ключ
            publicKey   = manager.getPublicKey ( "mykey" );

            // Читаем подпись
            b       = manager.verifySig ( bbText, publicKey, bbSign );
            System.out.println ( "--- 1) verify sign for TEXT1.TXT = " + b );

            // TEXT2
            //bbText  = manager.readFile ( dirName + "/test2.txt" );
            //b       = manager.verifySig ( bbText, publicKey, bbSign );
            //System.out.println ( "--- 2) verify sign for TEXT2.TXT = " + b );

            // Проверяем копию подписи на валидном файле - TRUE. Т.е. отдельную сигнатуру можно копировать куда угодно.
            //bbSign  = manager.readFile ( fileSign2 );
            //b       = manager.verifySig ( bbText, publicKey, bbSign );
            //System.out.println ( "--- 3) verify sign for TEXT1.TXT (with copy sign) = " + b );

            // Проверяем валидную сигнатуру на копии валидного файла. - TRUE. Т.е. файл можно копировать куда угодно.
            bbText  = FileTools.readFile ( fileTextCopy );
            b       = manager.verifySig ( bbText, publicKey, bbSign );
            System.out.println ( "--- 4) verify sign for copy TEXT1.TXT = " + b );

        } catch ( Exception e )         {
            e.printStackTrace();
        }
    }

}
