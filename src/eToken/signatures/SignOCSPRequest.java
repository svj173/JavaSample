package eToken.signatures;

//import iaik.asn1.structures.AlgorithmID;
//import iaik.asn1.structures.GeneralName;

import eToken.adapters.KeyAndCertificate;
import eToken.util.Util;
import iaik.pkcs.pkcs11.*;
import iaik.pkcs.pkcs11.objects.PrivateKey;
import iaik.pkcs.pkcs11.objects.RSAPrivateKey;
import iaik.pkcs.pkcs11.objects.X509PublicKeyCertificate;
import iaik.pkcs.pkcs11.wrapper.PKCS11Constants;

import java.io.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.Arrays;
import java.util.List;

//import iaik.x509.X509Certificate;
//import iaik.x509.ocsp.OCSPException;
//import iaik.x509.ocsp.OCSPRequest;
//import iaik.x509.ocsp.ReqCert;
//import iaik.x509.ocsp.Request;


/**
 * Signs an OCSP request using a token. The certificate of interest is given as
 * argument. The signed OCSP request is written to the given output file
 * DER-encoded. The actual OCSP specific operations are in the last section
 * of this demo.
 * The hash is calculated outside the token. This implementation just uses raw
 * RSA.
 *
 * @author <a href="mailto:Karl.Scheibelhofer@iaik.at"> Karl Scheibelhofer </a>
 * @version 0.1
 * @invariants
 */
public class SignOCSPRequest {

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
	    InvalidKeyException
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
		}

		PrivateKey selectedSignatureKey = (PrivateKey) selectedSignatureKeyAndCertificate
		    .getKey();
		X509PublicKeyCertificate pkcs11SignerCertificate = selectedSignatureKeyAndCertificate
		    .getCertificate();
        /*
		X509Certificate signerCertificate = (pkcs11SignerCertificate != null) ? new X509Certificate(
		    pkcs11SignerCertificate.getValue().getByteArrayValue()) : null;

		// here the interesting code starts

		output_
		    .println("################################################################################");
		output_.println("creating and signing OCSP request");

		InputStream subjectCertificateStream = new FileInputStream(args[1]);
		X509Certificate subjectCertificate = new X509Certificate(subjectCertificateStream);

		ReqCert reqCert = new ReqCert(ReqCert.pKCert, subjectCertificate);
		Request request = new Request(reqCert);
		OCSPRequest ocspRequest = new OCSPRequest();
		ocspRequest.setRequestList(new Request[] { request });

		if (signerCertificate != null) {
			// if we have the signer certificate set it in the OCSP request
			GeneralName requestorName = new GeneralName(GeneralName.directoryName,
			    signerCertificate.getSubjectDN());
			ocspRequest.setRequestorName(requestorName);
			ocspRequest.setCertificates(new X509Certificate[] { signerCertificate });
		}

		Signature tokenSignatureEngine = new PKCS11SignatureEngine("SHA1withRSA", session,
		    Mechanism.get(PKCS11Constants.CKM_RSA_PKCS), AlgorithmID.sha1);
		AlgorithmIDAdapter pkcs11Sha1RSASignatureAlgorithmID = new AlgorithmIDAdapter (
		    AlgorithmID.sha1WithRSAEncryption);
		pkcs11Sha1RSASignatureAlgorithmID.setSignatureInstance(tokenSignatureEngine);

		java.security.PrivateKey tokenSignatureKey = new TokenPrivateKey (selectedSignatureKey);

		output_.print("signing OCSP request... ");
		ocspRequest.sign(pkcs11Sha1RSASignatureAlgorithmID, tokenSignatureKey);
        */
		output_.println("finished");

		output_.print("writing OCSP request to file \"");
		output_.print(args[2]);
		output_.print("\"... ");
		OutputStream certificateStream = new FileOutputStream(args[2]);
		//ocspRequest.writeTo(certificateStream);
		output_.println("finished");

		output_
		    .println("################################################################################");

		session.closeSession();
		pkcs11Module.finalize(null);
	}

	public static void printUsage() {
		output_
		    .println("Usage: SignOCSPRequest <PKCS#11 module> <DER-encoded X.509 subject certificate> <DER-encoded OCSP request output file> [<slot>] [<pin>] [bot]");
		output_
		    .println(" e.g.: SignOCSPRequest pk2priv.dll subjectCert.der signedOCSPrequest.der");
		output_.println("The given DLL must be in the search path of the system.");
	}

}
