package security.sign;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;

/**
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 10.12.2014 14:14
 */
public class SignFile
{
    /**
     *     Формирование подписи

     После создания хранилища можно приступать к формированию подписи, это можно сделать с помощью следующего кода:

     * @param pkcs12Store
     * @param pass
     * @param alias
     * @param keyPassword
     * @param data
     * @param random
     * @return
     * @throws SignatureException
     */
    public static byte[] signPkcs7SignalCom ( String pkcs12Store, String pass, String alias,
                                              String keyPassword, byte[] data, SecureRandom random ) throws SignatureException
    {
        /*
        try
        {
            FileInputStream in = null;
            ByteArrayInputStream inp = null;
            ByteArrayOutputStream out = new ByteArrayOutputStream ();
            OutputStream sigOut = null;
            try
            {
                in = new FileInputStream ( KEYSTORE_PATH + pkcs12Store );
                KeyStore keyStore = getKeystore ( pkcs12Store, keyPassword, "PKCS12", "SC", random );
                keyStore.load ( in, pass.toCharArray () );
                List certs = new ArrayList ();
                X509Certificate cert = ( X509Certificate ) keyStore.getCertificate ( alias );
                //указываем, каким сертификатом будем подписывать
                certs.add ( cert );
                PrivateKey priv = ( PrivateKey ) keyStore.getKey ( alias, pass.toCharArray () );

                SignedDataGenerator generator = new SignedDataGenerator ( out );
                // экземпляр объекта SecureRandom должен быть предварительно проинициализирован!
                generator.addSigner ( new Signer ( priv, cert, random ) );
                generator.addCertificatesAndCRLs ( CertStore.getInstance ( "Collection", new CollectionCertStoreParameters ( certs ) ) );
                //для создания отсоединенной подписи, т.е такой, которая не включает в себя данные, ставим true
                generator.setDetached ( true );
                sigOut = generator.open ();
                inp = new ByteArrayInputStream ( data );
                byte[] buf = new byte[ 1024 ];
                int len;
                while ( ( len = inp.read ( buf ) ) > 0 )
                {
                    sigOut.write ( buf, 0, len );
                }
                generator.close ();
                byte[] signedData = out.toByteArray ();
                // для тестовых целей можно провалидировать подпись, но в общем случае это не нужно
                //boolean res = verifyPkcs7Sign(signedData, data);
                return signedData;

            } finally
            {
                if ( in != null )
                    in.close ();
                if ( inp != null )
                    inp.close ();
                if ( out != null )
                    out.close ();
                if ( sigOut != null )
                    sigOut.close ();
            }

        } catch ( Exception e )        {
            throw new SignatureException ( "Signing error", e );
        }
        */
        return null;
    }

    /**
     * SecureRandom нужно предварительно проинициализовать.
     *
     * @param privateKeyDirectory   путь до директории с файлами kek.opq masks.db3 mk.db3 rand.opq. причем для файла rand.opq должны быть выдано право на запись.
     * @param pass
     * @return
     * @throws SignatureException
     */
    public SecureRandom createRandom ( String privateKeyDirectory, String pass )  throws SignatureException
    {
        SecureRandom random = null;
        try {
            random = SecureRandom.getInstance("GOST28147PRNG", "SC");
            random.setSeed(new String(privateKeyDirectory + ";NonInteractive;Password=" + pass).getBytes());
        } catch (Exception e) {
            throw new SignatureException("Cannot init class secure random", e);
        }
        return random;
    }

    /**
     *     Валидация подписи

     Для валидации подписи необходим сертификат, которым была осуществлена подпись и, если подпись была не присоединенной,
     исходные данные, которые были подписаны. В простейшем случае проверка подписи осуществляется так
     (этот код только проверяет, что подпись была осуществлена данным сертификатом, цепочка сертификатов не валидируется):

     * @param certKeystore
     * @param keystorePass
     * @param alias
     * @param signed
     * @param data
     * @return
     * @throws Exception
     */
    public static boolean verifyPkcs7SignatureSignalCom ( String certKeystore, String keystorePass,
                                                          String alias, byte[] signed, byte[] data ) throws Exception
    {
        /*
        //List certStores = new ArrayList();

        FileInputStream inp = new FileInputStream ( KEYSTORE_PATH + certKeystore );
        KeyStore store = KeyStore.getInstance ( "PKCS12", "SC" );
        store.load ( inp, keystorePass.toCharArray () );
        inp.close ();
        // получаем сертификат, которым производилась подпись
        X509Certificate cert = ( X509Certificate ) store.getCertificate ( alias );
        if ( cert == null )
        {
            throw new SignatureException ( "Certificate not found" );
        }
        InputStream in = new ByteArrayInputStream ( signed );
        ContentInfoParser cinfoParser = ContentInfoParser.getInstance ( in );
        if ( !( cinfoParser instanceof SignedDataParser ) )
        {
            throw new RuntimeException ( "SignedData expected here" );
        }
        SignedDataParser parser = ( SignedDataParser ) cinfoParser;
        InputStream content = parser.getContent ();
        if ( content == null )
        {
            // отсоединённая подпись
            if ( data == null )
            {
                throw new RuntimeException ( "signature detached, please provide data to compare with" );
            }
            parser.setContent ( new ByteArrayInputStream ( data ) );
        }
        parser.process ();

        // почему-то не работает эта часть кода, хотя в документации сертификат получается именно таким способом.
        //certStores.add(parser.getCertificatesAndCRLs());


        SignerInfo signerInfo;
        try
        {
            Collection signerInfos = parser.getSignerInfos ();
            Iterator it = signerInfos.iterator ();
            for ( Object item : signerInfos )
            {
                signerInfo = (SignerInfo) item;
                // собственно валидация
                boolean res = signerInfo.verify ( cert );
                return res;
            }
        } finally  {
            parser.close ();
            in.close ();
        }
        */
        return false;
    }

    /**
     *     Формирование хранилища

     SignalCOM использует для хранения приватного ключа формат pkcs#8. Приватный ключ связан каким-то образом с
     генератором случайных чисел (при инициализации генератора случайных чисел требуется пароль от приватного ключа).
     Для работы в java удобнее всего сформировать хранилище в формате JKS или PKCS#12, для этого нужен приватный ключ и цепочка сертификатов.

     Ниже приведен пример сборки хранилища для формата PKCS#12; формирование хранилища в формате JKS ничего принципиально от него ничем не отличается.
     Позднее, используя keytool, всегда можно сконвертировать хранилище в нужный формат, главное не забыть указать опцию -srcprovidername SC,
     поскольку не все провайдеры могут работать с гостовыми сертификатами. Также нужно учесть, что пароль на хранилище и
     приватный ключ будет одинаковым, даже если первоначально приватный ключ не имел пароля.

     Пример конвертирования хранилища из формата PKCS#12 в JKS с использованием keytool

    $keytool -importkeystore -srcstoretype pkcs12 -deststoretype JKS -srckeystore store.pfx -destkeystore test.jks -srcprovidername SC

     */
    public void createStore ()
    {
        String root = "/home/gr/contact/TestKey";
        String certRoot = "/home/gr/contact/TestKey/CA";
        String clientCertRoot = "/home/gr/contact/TestKey/OpenKey";
        String passwd = "changeit";

        try
        {
            // Инициализация хранилища
            KeyStore store = KeyStore.getInstance ( "PKCS#12", "SC" );
            store.load(null, null);

            // Чтение секретного ключа PKCS#8
            KeyFactory keyFac = KeyFactory.getInstance ( "PKCS#8", "SC" );
            //byte[] encoded = fread ( root + File.separator + "Keys" + File.separator + "00000001.key");
            byte[] encoded = null;
            KeySpec privkeySpec = new PKCS8EncodedKeySpec (encoded);
            PrivateKey priv = keyFac.generatePrivate(privkeySpec);

            // Чтение сертификата УЦ
            CertificateFactory cf = CertificateFactory.getInstance ( "X.509", "SC" );
            FileInputStream in = new FileInputStream(certRoot + File.separator + "certca_1902.pem"); // CA 7
            X509Certificate cacert = (X509Certificate) cf.generateCertificate(in);
            in.close();

            // Чтение собственного сертификата
            in = new FileInputStream (clientCertRoot + File.separator + "cert_9342.pem"); // CA 7
            X509Certificate cert = (X509Certificate ) cf.generateCertificate(in);
            in.close();

            // Формирование цепочки сертификатов
            Certificate[] chain = new Certificate[2];
            chain[0] = cert;
            chain[1] = cacert;

            // Помещение в хранилище секретного ключа с цепочкой сертификатов
            store.setKeyEntry ("Test key", priv, passwd.toCharArray(), chain);
            // Помещение в хранилище сертификата УЦ
            store.setCertificateEntry("Test CA", cacert);

            // Запись хранилища
            FileOutputStream out = new FileOutputStream (new File (root + File.separator + "store.pfx"));
            store.store(out, passwd.toCharArray());
            out.close();

        } catch ( Exception e )     {
            e.printStackTrace ();
        }
    }
}
