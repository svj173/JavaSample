package eToken.signatures;

//import iaik.asn1.structures.AlgorithmID;

import eToken.adapters.KeyAndCertificate;
import eToken.util.Util;
import iaik.pkcs.pkcs11.*;
import iaik.pkcs.pkcs11.objects.PrivateKey;
import iaik.pkcs.pkcs11.objects.RSAPrivateKey;
import iaik.pkcs.pkcs11.wrapper.PKCS11Constants;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CRLException;
import java.util.Arrays;
import java.util.List;

//import iaik.x509.X509CRL;


/**
 * Signs an X.509 CRL using a token. The actual CRL specific operations
 * are in the last section of this demo.
 * The hash is calculated outside the token. This implementation just uses raw
 * RSA.
 *
 * @author <a href="mailto:Karl.Scheibelhofer@iaik.at"> Karl Scheibelhofer </a>
 * @version 0.1
 * @invariants
 */
public class SignCRL {

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
	    throws IOException, TokenException, NoSuchAlgorithmException, CRLException,
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

		PrivateKey selectedSignatureKey = (PrivateKey) selectedSignatureKeyAndCertificate.getKey ();

		// here the interesting code starts

		output_
		    .println("################################################################################");
		output_.println("signing demo CRL");

        /*
		Signature tokenSignatureEngine = new PKCS11SignatureEngine("SHA1withRSA", session,
		    Mechanism.get(PKCS11Constants.CKM_RSA_PKCS), AlgorithmID.sha1);
		AlgorithmIDAdapter pkcs11Sha1RSASignatureAlgorithmID = new AlgorithmIDAdapter(
		    AlgorithmID.sha1WithRSAEncryption);
		pkcs11Sha1RSASignatureAlgorithmID.setSignatureInstance(tokenSignatureEngine);

		InputStream templateCRLStream = new FileInputStream(args[1]);
		X509CRL crl = new X509CRL(templateCRLStream);

		java.security.PrivateKey tokenSignatureKey = new TokenPrivateKey (selectedSignatureKey);

		output_.print("signing CRL... ");
		crl.setSignatureAlgorithm(pkcs11Sha1RSASignatureAlgorithmID);
		crl.sign(tokenSignatureKey);
		output_.println("finished");

		output_.print("writing CRL to file \"");
		output_.print(args[2]);
		output_.print("\"... ");
		OutputStream crlStream = new FileOutputStream(args[2]);
		crl.writeTo(crlStream);
        */
		output_.println("finished");

		output_
		    .println("################################################################################");

		session.closeSession();
		pkcs11Module.finalize(null);
	}

	public static void printUsage() {
		output_
		    .println("Usage: SignCRL <PKCS#11 module> <DER-encoded X.509 template CRL> <DER-encoded CRL output file> [<slot>] [<pin>] [bot]");
		output_.println(" e.g.: SignCRL pk2priv.dll templateCRL.der signedCRL.der");
		output_.println("The given DLL must be in the search path of the system.");
	}

}
