package eToken.signatures;

//import iaik.asn1.ASN;
//import iaik.asn1.ASN1Object;
//import iaik.asn1.DerCoder;
//import iaik.asn1.OCTET_STRING;
//import iaik.asn1.ObjectID;
//import iaik.asn1.structures.AlgorithmID;
//import iaik.asn1.structures.Attribute;
//import iaik.asn1.structures.ChoiceOfTime;

import eToken.adapters.KeyAndCertificate;
import eToken.util.Util;
import iaik.pkcs.pkcs11.*;
import iaik.pkcs.pkcs11.objects.PrivateKey;
import iaik.pkcs.pkcs11.objects.RSAPrivateKey;
import iaik.pkcs.pkcs11.objects.X509PublicKeyCertificate;
import iaik.pkcs.pkcs11.wrapper.PKCS11Constants;

import java.io.*;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.List;

//import iaik.pkcs.pkcs7.DigestInfo;
//import iaik.pkcs.pkcs7.IssuerAndSerialNumber;
//import iaik.pkcs.pkcs7.SignedData;
//import iaik.pkcs.pkcs7.SignerInfo;
//import iaik.x509.X509Certificate;


/**
 * Creates a signature on a token. The hash is calculated outside the token.
 * The signed data and the signature are encoded into a PKCS#7 signed data
 * object. This implementation just uses raw RSA.
 *
 * @author <a href="mailto:Karl.Scheibelhofer@iaik.at"> Karl Scheibelhofer </a>
 * @version 0.1
 * @invariants
 */
public class SignDataAndOutputInPKCS7Format {

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
	    throws Exception
	{
		if (args.length < 3) {
			printUsage();
			System.exit(1);
		}

		Module pkcs11Module = Module.getInstance(args[0]);
		pkcs11Module.initialize(null);

		Token token;
		if (3 < args.length) token = Util.selectToken ( pkcs11Module, output_, input_, args[ 3 ] );
		else token = Util.selectToken(pkcs11Module, output_, input_);
		if (token == null) {
			output_.println("We have no token to proceed. Finished.");
			output_.flush();
			System.exit(0);
		}

		List supportedMechanisms = Arrays.asList(token.getMechanismList());
		if (!supportedMechanisms.contains(Mechanism.get(PKCS11Constants.CKM_RSA_PKCS))) {
			output_.print("This token does not support raw RSA signing!");
			output_.flush();
			System.exit(0);
		} else {
			MechanismInfo rsaMechanismInfo = token.getMechanismInfo(Mechanism
			    .get(PKCS11Constants.CKM_RSA_PKCS));
			if (!rsaMechanismInfo.isSign()) {
				output_.print("This token does not support RSA signing according to PKCS!");
				output_.flush();
				System.exit(0);
			}
		}

		Session session;
		if (4 < args.length) session = Util.openAuthorizedSession(token,
		    Token.SessionReadWriteBehavior.RW_SESSION, output_, input_, args[4]);
		else session = Util.openAuthorizedSession(token,
		    Token.SessionReadWriteBehavior.RW_SESSION, output_, input_, null);

		// first we search for private RSA keys that we can use for signing
		RSAPrivateKey privateSignatureKeyTemplate = new RSAPrivateKey();
		privateSignatureKeyTemplate.getSign().setBooleanValue(Boolean.TRUE);

		boolean bot = false;
		if (5 < args.length) bot = true;

		KeyAndCertificate selectedSignatureKeyAndCertificate = Util.selectKeyAndCertificate(
		    session, privateSignatureKeyTemplate, output_, input_, bot);
		if (selectedSignatureKeyAndCertificate == null) {
			output_.println("We have no signature key to proceed. Finished.");
			output_.flush();
			System.exit(0);
		} else output_.println("Found a suitable key");
		if (selectedSignatureKeyAndCertificate.getCertificate() == null) {
			output_.println("We have no corresponding certificate to proceed. Finished.");
			throw new Exception("We have no corresponding certificate to proceed. Finished.");
		}

		PrivateKey selectedSignatureKey = (PrivateKey) selectedSignatureKeyAndCertificate
		    .getKey();
		X509PublicKeyCertificate pkcs11SignerCertificate = selectedSignatureKeyAndCertificate
		    .getCertificate();
		//X509Certificate signerCertificate = (pkcs11SignerCertificate != null) ? new X509Certificate(
		//    pkcs11SignerCertificate.getValue().getByteArrayValue()) : null;

		// here the interesting code starts

		output_
		    .println("################################################################################");
		output_.println("signing data from file: " + args[1]);

		InputStream dataInputStream = new FileInputStream(args[1]);

		// we do digesting outside the card, because some cards do not support on-card hashing
		MessageDigest digestEngine = MessageDigest.getInstance("SHA-1");

		// we buffer the content to have it after hashing for the PKCS#7 content
		ByteArrayOutputStream contentBuffer = new ByteArrayOutputStream();
		byte[] dataBuffer = new byte[1024];
		int bytesRead;

		// feed all data from the input stream to the message digest
		while ((bytesRead = dataInputStream.read(dataBuffer)) >= 0) {
			// hash the data
			digestEngine.update(dataBuffer, 0, bytesRead);
			// and buffer the data
			contentBuffer.write(dataBuffer, 0, bytesRead);
		}
		byte[] contentHash = digestEngine.digest();
		contentBuffer.close();

        /*
		// create the SignedData
		SignedData signedData = new SignedData(contentBuffer.toByteArray(),
		    SignedData.IMPLICIT);
		// set the certificates
		signedData.setCertificates(new X509Certificate[] { signerCertificate });

		// create a new SignerInfo
		SignerInfo signerInfo = new SignerInfo(new IssuerAndSerialNumber(signerCertificate),
		    AlgorithmID.sha1, null);

		// define the authenticated attributes
		iaik.asn1.structures.Attribute[] authenticatedAttributes = {
		    new Attribute(ObjectID.contentType, new ASN1Object[] { ObjectID.pkcs7_data }),
		    new Attribute(ObjectID.signingTime,
		        new ASN1Object[] { new ChoiceOfTime().toASN1Object() }),
		    new Attribute(ObjectID.messageDigest, new ASN1Object[] { new OCTET_STRING(
		        contentHash) }) };
		// set the authenticated attributes
		signerInfo.setAuthenticatedAttributes(authenticatedAttributes);

		// encode the authenticated attributes, which is the data that we must sign
		byte[] toBeSigned = DerCoder.encode(ASN.createSetOf(authenticatedAttributes, true));

		// we do digesting outside the card, because some cards do not support on-card hashing
		// we can use the digest engine from above
		byte[] hashToBeSigned = digestEngine.digest(toBeSigned);

		// according to PKCS#11 building the DigestInfo structure must be done off-card
		DigestInfo digestInfoEngine = new DigestInfo(AlgorithmID.sha1, hashToBeSigned);

		byte[] toBeEncrypted = digestInfoEngine.toByteArray();

		// initialize for signing
		session.signInit(Mechanism.get(PKCS11Constants.CKM_RSA_PKCS), selectedSignatureKey);

		// sign the data to be signed
		byte[] signatureValue = session.sign(toBeEncrypted);

		// set the signature value in the signer info
		signerInfo.setEncryptedDigest(signatureValue);

		// and add the signer info object to the PKCS#7 signed data object
		signedData.addSignerInfo(signerInfo);

		output_.println("Writing signature to file: " + args[2]);

		OutputStream signatureOutput = new FileOutputStream(args[2]);
		signedData.writeTo(signatureOutput);
		signatureOutput.flush();
		signatureOutput.close();

		output_
		    .println("################################################################################");

*/
		session.closeSession();
		pkcs11Module.finalize(null);
	}

	public static void printUsage() {
		output_
		    .println("Usage: SignPKCS7 <PKCS#11 module> <file to be signed> <PKCS#7 signed data file> [<slot>] [<pin>] [bot]");
		output_.println(" e.g.: SignPKCS7 pk2priv.dll data.dat signedData.p7");
		output_.println("The given DLL must be in the search path of the system.");
	}

}
