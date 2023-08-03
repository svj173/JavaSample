package key;

import javax.crypto.Cipher;
import java.math.BigInteger;
import java.security.*;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;

/**
 * Пример рабоыт с ключами. Получает из ключей два числа, зная которые можно восстановить ключ.
 * <BR/> RSA Ключ (как закрытый, так и открытый) можно разложить на два BigInteger: Modulus и PublicExponent.
 * Этих чисел достаточно для создания ключа.
 * Так вот, чтоб ключ сохранить получим эти числа и сохраним в файл, а чтоб востановить, соответственно обратная операция.
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 16.11.2016 16:44
 */
public class ResporeKeyByBigInteger
{
    public static void main(String[] args)
    {
        try
        {
            // Генерируем ключи.
            KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
            KeyPair kpair = kpg.genKeyPair();
            PrivateKey privateKey = kpair.getPrivate();
            PublicKey publicKey = kpair.getPublic();

            // "Разбираем" RSA ключи.
            KeyFactory kf = KeyFactory.getInstance("RSA");
            RSAPrivateKeySpec prks = kf.getKeySpec(privateKey, RSAPrivateKeySpec.class);
            // Достачно знать эти 2 числа, чтобы востановить секретный ключ. Сохранять нужно их.
            BigInteger pr_m = prks.getModulus();
            BigInteger pr_x = prks.getPrivateExponent();

            RSAPublicKeySpec pubks =  kf.getKeySpec(publicKey, RSAPublicKeySpec.class);
            // Достачно знать эти 2 числа, чтобы востановить открытый ключ. Сохранять нужно их.
            BigInteger pub_m = pubks.getModulus();
            BigInteger pub_x = pubks.getPublicExponent();

            // Востанавливаем ключ из полученных ранее BigInteger-ов.
            RSAPrivateKeySpec new_prks = new RSAPrivateKeySpec(pr_m, pr_x);
            RSAPublicKeySpec new_pubks = new RSAPublicKeySpec(pub_m, pub_x);
            PrivateKey new_private = kf.generatePrivate(new_prks);
            PublicKey new_public = kf.generatePublic(new_pubks);

            Cipher c = Cipher.getInstance("RSA");
            // Шифруем оригинальным ключом.
            c.init(Cipher.ENCRYPT_MODE, publicKey);
            byte [] encrypted = c.doFinal("Test string".getBytes());
            // Расшифруем репродуцированным ключом.
            c.init(Cipher.DECRYPT_MODE, new_private);
            byte [] decrypted = c.doFinal(encrypted);

            String s = new String(decrypted);
            System.out.println(s);

            /*
Save Public Key:

    X509EncodedKeySpec x509ks = new X509EncodedKeySpec( publicKey.getEncoded());
    FileOutputStream fos = new FileOutputStream(strPathFilePubKey);
    fos.write(x509ks.getEncoded());

Load Public Key:

    byte[] encodedKey = IOUtils.toByteArray(new FileInputStream(strPathFilePubKey));
    KeyFactory keyFactory = KeyFactory.getInstance("RSA", p);
    X509EncodedKeySpec pkSpec = new X509EncodedKeySpec( encodedKey);
    PublicKey publicKey = keyFactory.generatePublic(pkSpec);

Save Private Key:

    PKCS8EncodedKeySpec pkcsKeySpec = new PKCS8EncodedKeySpec( privateKey.getEncoded());
    FileOutputStream fos = new FileOutputStream(strPathFilePrivbKey);
    fos.write(pkcsKeySpec.getEncoded());

Load Private Key:

    byte[] encodedKey = IOUtils.toByteArray(new FileInputStream(strPathFilePrivKey));
    KeyFactory keyFactory = KeyFactory.getInstance("RSA", p);
    PKCS8EncodedKeySpec privKeySpec = new PKCS8EncodedKeySpec( encodedKey);
    PrivateKey privateKey = keyFactory.generatePrivate(privKeySpec);

             */
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
