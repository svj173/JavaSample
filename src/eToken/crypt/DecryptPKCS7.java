package eToken.crypt;

//import iaik.asn1.structures.AlgorithmID;
//import iaik.pkcs.PKCSException;
import iaik.pkcs.pkcs11.TokenException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.spec.InvalidKeySpecException;

//import iaik.pkcs.pkcs7.EncryptedContentInfoStream;
//import iaik.pkcs.pkcs7.EnvelopedDataStream;
//import iaik.pkcs.pkcs7.IssuerAndSerialNumber;
//import iaik.pkcs.pkcs7.RecipientInfo;
//import iaik.security.provider.IAIK;
//import iaik.x509.X509Certificate;


/**
 * This demo shows how to use a PKCS#11 token to decrypt a PKCS#7 encrypted
 * object. It only supports RSA decryption. This sample just decrypts the
 * included symmetric key on the token and uses the symmetric key to decrypt
 * the content on the host, i.e. in software.
 * 
 * Use util.EncryptPKCS7EnvelopedData for creating the necessary files.
 *
 * @author <a href="mailto:Karl.Scheibelhofer@iaik.at"> Karl Scheibelhofer </a>
 * @version 0.1
 * @invariants
 */
public class DecryptPKCS7 {

	static PrintWriter output_;

	static BufferedReader input_;

	static {
		try {
			//output_ = new PrintWriter(new FileWriter("GetInfo_output.txt"), true);
			output_ = new PrintWriter(System.out, true);
			input_ = new BufferedReader(new InputStreamReader(System.in));
		} catch (Throwable thr) {
			thr.printStackTrace();
			output_ = new PrintWriter(System.out, true);
			input_ = new BufferedReader(new InputStreamReader(System.in));
		}
	}

	public static void main(String[] args)
	    throws IOException, TokenException, CertificateException, NoSuchAlgorithmException,
	    InvalidKeySpecException, InvalidKeyException
	{
		if (2 > args.length) {
			printUsage();
			System.exit(1);
		}

        /*
		Security.addProvider(new IAIK());

		Module pkcs11Module = Module.getInstance(args[0]);
		pkcs11Module.initialize(null);

		Token token;
		if (2 < args.length) token = Util.selectToken(pkcs11Module, output_, input_, args[2]);
		else token = Util.selectToken(pkcs11Module, output_, input_);
		if (token == null) {
			output_.println("We have no token to proceed. Finished.");
			output_.flush();
			System.exit(0);
		}

		// check, if this token can do RSA decryption
		List supportedMechanisms = Arrays.asList(token.getMechanismList());
		if (!supportedMechanisms.contains(Mechanism.get(PKCS11Constants.CKM_RSA_PKCS))) {
			output_.print("This token does not support RSA!");
			output_.flush();
			System.exit(0);
		} else {
			MechanismInfo rsaMechanismInfo = token.getMechanismInfo(Mechanism
			    .get(PKCS11Constants.CKM_RSA_PKCS));
			if (!rsaMechanismInfo.isDecrypt()) {
				output_.print("This token does not support RSA decryption according to PKCS!");
				output_.flush();
				System.exit(0);
			}
		}

		Session session;
		if (3 < args.length) session = Util.openAuthorizedSession(token,
		    Token.SessionReadWriteBehavior.RW_SESSION, output_, input_, args[3]);
		else session = Util.openAuthorizedSession(token,
		    Token.SessionReadWriteBehavior.RW_SESSION, output_, input_, null);

		// read all certificates that are on the token
		List tokenCertificates = new Vector();
		X509PublicKeyCertificate certificateTemplate = new X509PublicKeyCertificate();
		session.findObjectsInit(certificateTemplate);
		Object[] tokenCertificateObjects;

		while ((tokenCertificateObjects = session.findObjects(1)).length > 0) {
			tokenCertificates.add(tokenCertificateObjects[0]);
		}
		session.findObjectsFinal();

		output_
		    .println("################################################################################");

		output_
		    .println("################################################################################");
		output_.println("reading encrypted data from file: " + args[1]);

		FileInputStream encryptedInputStream = new FileInputStream(args[1]);

		EnvelopedDataStream envelopedData = new EnvelopedDataStream(encryptedInputStream);

		RecipientInfo[] recipientInfos = envelopedData.getRecipientInfos();

		// search through the recipients and look, if we have one of the recipients' certificates on the token
		boolean haveDecryptionKey = false;
		InputStream decryptedDataInputStream = null;
		for (int i = 0; i < recipientInfos.length; i++) {
			IssuerAndSerialNumber issuerAndSerialNumber = recipientInfos[i]
			    .getIssuerAndSerialNumber();

			// look if there is a certificate on our token with the given issuer and serial number
			X509PublicKeyCertificate matchingTokenCertificate = null;
			Iterator tokenCertificatesIterator = tokenCertificates.iterator();
			while (tokenCertificatesIterator.hasNext()) {
				X509PublicKeyCertificate tokenCertificate = (X509PublicKeyCertificate) tokenCertificatesIterator
				    .next();
				X509Certificate parsedTokenCertificate = new X509Certificate(tokenCertificate
				    .getValue().getByteArrayValue());
				if (issuerAndSerialNumber.isIssuerOf(parsedTokenCertificate)) {
					output_
					    .println("________________________________________________________________________________");
					output_.println("Found matching certificate on the token:");
					output_.println(parsedTokenCertificate.toString(true));
					output_
					    .println("________________________________________________________________________________");
					matchingTokenCertificate = tokenCertificate;
					break;
				}
			}

			if (matchingTokenCertificate != null) {
				// find the corresponding private key for the certificate
				PrivateKey privateKeyTemplate = new PrivateKey();
				privateKeyTemplate.getId().setByteArrayValue(
				    matchingTokenCertificate.getId().getByteArrayValue());

				session.findObjectsInit(privateKeyTemplate);
				Object[] correspondingPrivateKeyObjects;
				PrivateKey correspondingPrivateKey = null;

				if ((correspondingPrivateKeyObjects = session.findObjects(1)).length > 0) {
					correspondingPrivateKey = (PrivateKey) correspondingPrivateKeyObjects[0];
					output_
					    .println("________________________________________________________________________________");
					output_.println("Found corresponding private key:");
					output_.println(correspondingPrivateKey);
					output_
					    .println("________________________________________________________________________________");
				} else {
					output_
					    .println("Found no private key with the same ID as the matching certificate.");
				}
				session.findObjectsFinal();

				// check, if the private key is a decrpytion key
				PrivateKey decryptionKey = ((correspondingPrivateKey != null) && (correspondingPrivateKey
				    .getDecrypt().getBooleanValue().booleanValue())) ? correspondingPrivateKey
				    : null;

				if (decryptionKey != null) {
					haveDecryptionKey = true;
					output_.print("decrypting symmetric key... ");
					byte[] encryptedSymmetricKey = recipientInfos[i].getEncryptedKey();
					// decrypt the encrypted symmetric key using the e.g. RSA on the smart-card
					session.decryptInit(Mechanism.get(PKCS11Constants.CKM_RSA_PKCS), decryptionKey);
					byte[] decryptedSymmetricKey = session.decrypt(encryptedSymmetricKey);
					output_.println("finished");

					// construct the symmetric key
					output_.print("constructing symmetric key for software decryption... ");
					EncryptedContentInfoStream encryptedContentInfo = (EncryptedContentInfoStream) envelopedData
					    .getEncryptedContentInfo();
					AlgorithmID contentEncryptionAlgorithm = encryptedContentInfo
					    .getContentEncryptionAlgorithm();
					SecretKeyFactory secretKeyFactory = SecretKeyFactory
					    .getInstance(contentEncryptionAlgorithm.getRawImplementationName());

					javax.crypto.SecretKey secretKey;
					if (contentEncryptionAlgorithm.getRawImplementationName().equalsIgnoreCase(
					    "DESede")) {
						/ *
						 * we now that the content encryption algorithm is DES3 if we run our EncryptPKCS7EnvelopedData-test
						 * to generate the data. Providing the appropriate keyspec is necessary for JKDs < 1.6. For JDKs >= 1.6
						 * the else path works as well for DES keys.
						 * /
						DESedeKeySpec secretKeySpec = new DESedeKeySpec(decryptedSymmetricKey);
						secretKey = secretKeyFactory.generateSecret(secretKeySpec);
					} else {
						SecretKeySpec secretKeySpec = new SecretKeySpec(decryptedSymmetricKey,
						    contentEncryptionAlgorithm.getRawImplementationName());
						secretKey = secretKeyFactory.generateSecret(secretKeySpec);
					}
					output_.println("finished");

					// decrypt the data (in software)
					encryptedContentInfo.setupCipher(secretKey);
					decryptedDataInputStream = encryptedContentInfo.getInputStream();

					// read decrypted data from decryptedDataInputStream
				}
			}
		}

		if (!haveDecryptionKey) {
			output_
			    .print("Found no decryption key that matches any recipient info in the encrypted PKCS#7 object.");
			output_.flush();
			System.exit(0);
		}

		if (decryptedDataInputStream == null) {
			output_.print("Could not decrypt the PKCS#7 object.");
			output_.flush();
			System.exit(0);
		}
		output_
		    .println("################################################################################");

		output_
		    .println("################################################################################");
		OutputStream decryptedContentStream = (args.length == 5) ? new FileOutputStream(
		    args[4]) : null;
		byte[] buffer = new byte[1024];
		int bytesRead;
		output_.println("The decrypted content data is: ");
		output_
		    .println("________________________________________________________________________________");
		while ((bytesRead = decryptedDataInputStream.read(buffer)) > 0) {
			char[] charbuffer = new String(buffer).toCharArray();
			output_.write(charbuffer, 0, bytesRead);
			if (decryptedContentStream != null) {
				decryptedContentStream.write(buffer, 0, bytesRead);
			}
		}
		output_.println();
		output_
		    .println("________________________________________________________________________________");
		if (decryptedContentStream != null) {
			output_.println("Decrypted content written to: " + args[2]);
			decryptedContentStream.flush();
			decryptedContentStream.close();
		}
		output_
		    .println("################################################################################");

		session.closeSession();
		pkcs11Module.finalize(null);
		*/
	}

	public static void printUsage() {
		output_
		    .println("Usage: DecryptPKCS7 <PKCS#11 module> <PKCS#7 encrypted data file> [<slot>] [<pin>] [<decrypted content data>]");
		output_
		    .println(" e.g.: DecryptPKCS7 slbck.dll encryptedData.p7 decryptedContent.dat");
		output_.println("The given DLL must be in the search path of the system.");
	}

}
