package eToken.signatures;

//import iaik.asn1.structures.AlgorithmID;
//import iaik.asn1.structures.Name;
//import iaik.pkcs.pkcs10.CertificateRequest;

import eToken.adapters.KeyAndCertificate;
import eToken.util.Util;
import iaik.pkcs.pkcs11.*;
import iaik.pkcs.pkcs11.objects.PrivateKey;
import iaik.pkcs.pkcs11.objects.RSAPrivateKey;
import iaik.pkcs.pkcs11.wrapper.PKCS11Constants;

import java.io.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.security.cert.CertificateException;
import java.util.Arrays;
import java.util.List;

//import iaik.utils.PemOutputStream;
//import iaik.utils.RFC2253NameParser;
//import iaik.utils.RFC2253NameParserException;


/**
 * Signs a PKCS#10 certificate request using a token. The actual PKCS#10
 * specific operations are in the last section of this demo.
 * The hash is calculated outside the token. This implementation just uses raw
 * RSA.
 * This example works as follows. In general, the 
 * <code>CertificateRequest</code> class from the IAIK JCE toolkit works with 
 * JCA private keys only. To get it to work with the wrapper, we need a special
 * <code>AlgorithmID</code> class
 * which provides a special <code>Signature</code> engine object 
 *  to the certificate
 * request object. This signature engine only accepts keys of type 
 *  , which just wraps PKCS#11 key
 * objects. All these helper classes are required if you want to sign a 
 * certificate request with the PKCS#11 wrapper. If you use the IAIK PKCS#11
 * provider, everything is much easier. In this case, you do not need any
 * of these helper classes.
 *  
 *
 * @author <a href="mailto:Karl.Scheibelhofer@iaik.at"> Karl Scheibelhofer </a>
 * @version 0.1
 * @invariants
 */
public class SignCertificateRequest
{

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
	    throws IOException, TokenException, InvalidKeyException, CertificateException,
	    NoSuchAlgorithmException, SignatureException
	{
		if (args.length < 4) {
			printUsage();
			System.exit(1);
		}

		Module pkcs11Module = Module.getInstance(args[0]);
		pkcs11Module.initialize(null);

		Token token;
		if (4 < args.length) token = Util.selectToken ( pkcs11Module, output_, input_, args[ 4 ] );
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
		if (5 < args.length) session = Util.openAuthorizedSession(token,
		    Token.SessionReadWriteBehavior.RW_SESSION, output_, input_, args[5]);
		else session = Util.openAuthorizedSession(token,
		    Token.SessionReadWriteBehavior.RW_SESSION, output_, input_, null);

		// first we search for private RSA keys that we can use for signing
		RSAPrivateKey privateSignatureKeyTemplate = new RSAPrivateKey();
		privateSignatureKeyTemplate.getSign().setBooleanValue(Boolean.TRUE);

		boolean bot = false;
		if (6 < args.length) bot = true;

		KeyAndCertificate selectedSignatureKeyAndCertificate = Util.selectKeyAndCertificate(
		    session, privateSignatureKeyTemplate, output_, input_, bot);
		if (selectedSignatureKeyAndCertificate == null) {
			output_.println("We have no signature key to proceed. Finished.");
			output_.flush();
			System.exit(0);
		}

		PrivateKey selectedSignatureKey = (PrivateKey) selectedSignatureKeyAndCertificate.getKey();

		// here the interesting code starts

		output_
		    .println("################################################################################");
		output_.println("generating and signing PKCS#10 certificate request");

		InputStream publicKeyInputStream = new FileInputStream(args[1]);
        /*
		iaik.security.rsa.RSAPublicKey subjectPublicKey = new iaik.security.rsa.RSAPublicKey(
		    publicKeyInputStream);

		RFC2253NameParser subjectNameParser = new RFC2253NameParser(args[2]);
		Name subjectName = subjectNameParser.parse();

		CertificateRequest certificateRequest = new CertificateRequest(subjectPublicKey,
		    subjectName);

		Signature tokenSignatureEngine = new PKCS11SignatureEngine("SHA1withRSA", session,
		    Mechanism.get(PKCS11Constants.CKM_RSA_PKCS), AlgorithmID.sha1);
		AlgorithmIDAdapter pkcs11Sha1RSASignatureAlgorithmID = new AlgorithmIDAdapter(
		    AlgorithmID.sha1WithRSAEncryption);
		pkcs11Sha1RSASignatureAlgorithmID.setSignatureInstance(tokenSignatureEngine);

		java.security.PrivateKey tokenSignatureKey = new TokenPrivateKey(selectedSignatureKey);

		output_.print("signing certificate request... ");
		certificateRequest.sign(pkcs11Sha1RSASignatureAlgorithmID, tokenSignatureKey);
		output_.println("finished");

		output_.print("writing certificate request to file \"");
		output_.print(args[3]);
		output_.print("\"... ");
		String firstLine = "-----BEGIN NEW CERTIFICATE REQUEST-----";
		String lastLine = "-----END NEW CERTIFICATE REQUEST-----";
		OutputStream certificateStream = new PemOutputStream(new FileOutputStream(args[3]),
		    firstLine, lastLine);
		certificateRequest.writeTo(certificateStream);
		certificateStream.flush();
		certificateStream.close();
		output_.println("finished");
         */
		output_
		    .println("################################################################################");

		session.closeSession();
		pkcs11Module.finalize(null);
	}

	public static void printUsage() {
		output_
		    .println("Usage: SignCertificateRequest <PKCS#11 module> <DER-encoded X.509 public RSA key file> <RFC2253 subject name> <PEM-encoded PKCS#10 certificate request output file> [<slot>] [<pin>] [bot]");
		output_
		    .println(" e.g.: SignCertificateRequest pk2priv.dll publicKey.xpk \"CN=Karl Scheibelhofer,O=IAIK,C=AT,EMAIL=karl.scheibelhofer@iaik.at\" certificateRequest.p10");
		output_.println("The given DLL must be in the search path of the system.");
	}

}
