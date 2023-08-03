package eToken.util;

//import iaik.asn1.structures.AlgorithmID;
//import iaik.pkcs.pkcs7.EnvelopedDataStream;
//import iaik.pkcs.pkcs7.RecipientInfo;
//import iaik.security.provider.IAIK;
//import iaik.x509.X509Certificate;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

/**
 * This helper class encrypts the given data using TrippleDES and encrypts the
 * symmetric key using the public key in the given certificate.
 *
 * @author <a href="mailto:Karl.Scheibelhofer@iaik.at"> Karl Scheibelhofer </a>
 * @version 0.1
 * @invariants
 */
public class EncryptPKCS7EnvelopedData
{

	public static void main(String[] args)
	    throws NoSuchAlgorithmException, CertificateException, IOException
	{
		if (args.length != 3) {
			printUsage();
			System.exit(1);
		}

        /*
		Security.addProvider(new IAIK());

		System.out.println("Encrypting data from file: " + args[0]);
		InputStream dataInputStream = new FileInputStream(args[0]);

		EnvelopedDataStream envelopedData = new EnvelopedDataStream(dataInputStream,
		    AlgorithmID.des_EDE3_CBC);

		System.out.println("using recipient certificate from: " + args[1]);
		InputStream certificateInputStream = new FileInputStream(args[1]);

		X509Certificate recipientCertificate = new X509Certificate(certificateInputStream);
		System.out.println("which is: ");
		System.out.println(recipientCertificate.toString(true));

		RecipientInfo recipient = new RecipientInfo(recipientCertificate,
		    AlgorithmID.rsaEncryption);

		envelopedData.setRecipientInfos(new RecipientInfo[] { recipient });

		System.out.println("writing enveloped data to: " + args[2]);
		OutputStream envelopedDataOutputStream = new FileOutputStream(args[2]);
		envelopedData.writeTo(envelopedDataOutputStream);
        */
	}

	public static void printUsage() {
		System.out
		    .println("Usage: EncryptPKCS7EnvelopedData <data to encrypt file> <recipient certificate> <PKCS#7 enveloped data file>");
		System.out
		    .println(" e.g.: EncryptPKCS7EnvelopedData contentData.dat recipientCertificte.der envelopedData.p7");
	}

}
