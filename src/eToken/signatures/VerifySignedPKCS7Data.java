package eToken.signatures;

//import iaik.pkcs.pkcs7.SignedDataStream;
//import iaik.pkcs.pkcs7.SignerInfo;
//import iaik.security.provider.IAIK;
//import iaik.x509.X509Certificate;

/**
 * This helper class simply verifies the signature of a PKCS#7 signed data
 * object and extracts the verified content data.
 *
 * @author <a href="mailto:Karl.Scheibelhofer@iaik.at"> Karl Scheibelhofer </a>
 * @version 0.1
 * @invariants
 */
public class VerifySignedPKCS7Data {

	public static void main(String[] args) {
		if ((args.length != 1) && (args.length != 2)) {
			printUsage();
			System.exit(1);
		}
        /*
		try {
			Security.addProvider(new IAIK());

			System.out.println("Verifying PKCS#7 signed data from file: " + args[0]);
			InputStream dataInput = new FileInputStream(args[0]);

			SignedDataStream signedData = new SignedDataStream(dataInput);

			InputStream contentStream = signedData.getInputStream();
			OutputStream verifiedContentStream = (args.length == 2) ? new FileOutputStream(
			    args[1]) : null;
			byte[] buffer = new byte[1024];
			int bytesRead;

			if (verifiedContentStream != null) {
				while ((bytesRead = contentStream.read(buffer)) > 0) {
					verifiedContentStream.write(buffer, 0, bytesRead);
				}
				verifiedContentStream.flush();
				verifiedContentStream.close();
				System.out.println("Verified content written to: " + args[1]);
				System.out
				    .println("________________________________________________________________________________");
			} else {
				System.out.println("The signed content data is: ");
				System.out
				    .println("________________________________________________________________________________");
				while ((bytesRead = contentStream.read(buffer)) > 0) {
					System.out.write(buffer, 0, bytesRead);
				}
				System.out.println();
				System.out
				    .println("________________________________________________________________________________");
			}

			// get the signer infos
			SignerInfo[] signerInfos = signedData.getSignerInfos();
			// verify the signatures
			for (int i = 0; i < signerInfos.length; i++) {
				try {
					// verify the signature for SignerInfo at index i
					X509Certificate signerCertificate = signedData.verify(i);
					// if the signature is OK the certificate of the signer is returned
					System.out.println("Signature OK from signer with certificate: ");
					System.out.println(signerCertificate);
					System.out.println();
				} catch (SignatureException ex) {
					// if the signature is not OK a SignatureException is thrown
					System.out.println("Signature ERROR from signer with certificate: ");
					System.out.println(signedData.getCertificate(signerInfos[i]
					    .getIssuerAndSerialNumber()));
					System.out.println();
					ex.printStackTrace();
				}
			}

		} catch (Throwable thr) {
			thr.printStackTrace();
		}
		*/
	}

	public static void printUsage() {
		System.out
		    .println("Usage: VerifyPKCS7SignedData <PKCS#7 signed data file> <verified content data>");
		System.out
		    .println(" e.g.: VerifyPKCS7SignedData signedData.p7 verifiedContentData.dat");
	}

}
