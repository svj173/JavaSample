package eToken.signatures;


import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.security.Signature;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

/**
 * Верификация.
 * - Читаем файл
 * - Читает сигнатуру
 * - Читае сертификат (как файл) Х509
 * - Устанавливает провайдера IAIK  - содержится в другом, неизвестном нам, jar.
 * - Проверяет подпись
 */
public class VerifySignature
{
	protected X509Certificate certificate_;

	protected Signature verifyEngine_;

	public VerifySignature ( X509Certificate certificate, String algorithm )
	    throws GeneralSecurityException
	{
		certificate_ = certificate;
		//todo verifyEngine_ = Signature.getInstance(algorithm, "IAIK");
		verifyEngine_ = Signature.getInstance(algorithm);
		verifyEngine_.initVerify(certificate.getPublicKey());
	}

	public boolean verify(InputStream data, byte[] signature)
	    throws IOException, GeneralSecurityException
	{
		byte[] buffer = new byte[1024];
		int bytesRead;

		while ((bytesRead = data.read(buffer, 0, buffer.length)) >= 0) {
			verifyEngine_.update(buffer, 0, bytesRead);
		}

		return verifyEngine_.verify(signature);
	}

	public static void main(String[] args)
	    throws IOException, GeneralSecurityException
	{
		if (args.length < 4) {
			printUsage();
			System.exit(1);
		}

        FileInputStream certificateInput = new FileInputStream(args[2]);
		FileInputStream dataInput = new FileInputStream(args[0]);

		byte[] signature = null;
        //todo signature = Util.readStream ( new FileInputStream ( args[ 1 ] ) );
		//todo Security.addProvider(new IAIK());

		CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509", "IAIK");
		//    CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
		X509Certificate certificate = (X509Certificate) certificateFactory.generateCertificate(certificateInput);

		VerifySignature verifier = new VerifySignature(certificate, args[3]);

		if (verifier.verify(dataInput, signature)) {
			System.out.println("Verified signature successfully.");
		} else {
			System.out.println("Signature is INVALID.");
			//      Cipher rsa = Cipher.getInstance("RSA/ECB/NoPadding");
			//      rsa.init(Cipher.DECRYPT_MODE, certificate.getPublicKey());
			//      byte[] signedBlock = rsa.doFinal(signature);
			//      System.out.println("Decrypted signature value is (hex):");
			//      System.out.println(new BigInteger(1, signedBlock).toString(16));
		}
	}

	public static void printUsage() {
		System.out
		    .println("Usage: VerifySignature <data file> <signature file> <X.509 certificate file> <algorithm>");
		System.out
		    .println(" e.g.: VerifySignature data.dat signature.bin signerCertificate.der SHA1withRSA");
	}

}
