package eToken.keygen;


import eToken.util.Util;
import iaik.pkcs.pkcs11.*;
import iaik.pkcs.pkcs11.objects.DES3SecretKey;
import iaik.pkcs.pkcs11.objects.Key;
import iaik.pkcs.pkcs11.objects.SecretKey;
import iaik.pkcs.pkcs11.parameters.DesCbcEncryptDataParameters;
import iaik.pkcs.pkcs11.wrapper.PKCS11Constants;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

/**
 * This demo program shows how to derive a DES3 key.
 *
 * @invariants
 */
public class DeriveKeyDemo {

	static BufferedReader input_;
	static PrintWriter output_;

	static {
		try {
			//output_ = new PrintWriter(new FileWriter("SignAndVerify_output.txt"), true);
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
		if (args.length < 1) {
			printUsage();
			System.exit(1);
		}

		Module pkcs11Module = Module.getInstance(args[0]);
		pkcs11Module.initialize(null);

		Token token;
		if (1 < args.length) token = Util.selectToken ( pkcs11Module, output_, input_, args[ 1 ] );
		else token = Util.selectToken(pkcs11Module, output_, input_);
		TokenInfo tokenInfo = token.getTokenInfo();

		output_
		    .println("################################################################################");
		output_.println("Using token:");
		output_.println(tokenInfo);
		output_
		    .println("################################################################################");

		Session session;
		if (2 < args.length) session = Util.openAuthorizedSession(token,
		    Token.SessionReadWriteBehavior.RW_SESSION, output_, input_, args[2]);
		else session = Util.openAuthorizedSession(token,
		    Token.SessionReadWriteBehavior.RW_SESSION, output_, input_, null);

		Mechanism keyGenerationMechanism = Mechanism.get(PKCS11Constants.CKM_DES3_KEY_GEN);

		List supportedMechanisms = Arrays.asList(token.getMechanismList());
		if (!supportedMechanisms.contains(Mechanism.get(PKCS11Constants.CKM_DES3_KEY_GEN))) {
			output_.println("Mechanism not supported: DES3_KEY_GEN");
			return;
		}

		DES3SecretKey baseKeyTemplate = new DES3SecretKey();

		baseKeyTemplate.getDerive().setBooleanValue(Boolean.TRUE);
		// we only have a read-only session, thus we only create a session object
		baseKeyTemplate.getToken().setBooleanValue(Boolean.FALSE);
		baseKeyTemplate.getSensitive().setBooleanValue(Boolean.FALSE);
		baseKeyTemplate.getExtractable().setBooleanValue(Boolean.TRUE);
		SecretKey baseKey = (SecretKey) session.generateKey(keyGenerationMechanism,
		    baseKeyTemplate);

		System.out.println("Base key: ");
		System.out.println(baseKey.toString());

		output_
		    .println("################################################################################");
		output_.println("derive key");

		//DES3 Key Template
		DES3SecretKey derived3DESKeyTemplate = new DES3SecretKey();
		SecretKey derivedKeyTemplate = derived3DESKeyTemplate;

		derivedKeyTemplate.getSensitive().setBooleanValue(Boolean.FALSE);
		derivedKeyTemplate.getExtractable().setBooleanValue(Boolean.TRUE);

		byte[] iv = new byte[8];
		byte[] data = new byte[24];

		DesCbcEncryptDataParameters param = new DesCbcEncryptDataParameters(iv, data);
		Mechanism mechanism = Mechanism.get(PKCS11Constants.CKM_DES3_CBC_ENCRYPT_DATA);

		if (!supportedMechanisms.contains(Mechanism
		    .get(PKCS11Constants.CKM_DES3_CBC_ENCRYPT_DATA))) {
			output_.println("Mechanism not supported: DES3_CBC_ENCRYPT_DATA");
			return;
		}

		mechanism.setParameters(param);

		System.out.println("Derivation Mechanism: ");
		output_.println(mechanism.toString());
		output_
		    .println("--------------------------------------------------------------------------------");

		Key derivedKey = session.deriveKey(mechanism, baseKey, derivedKeyTemplate);

		if (derivedKey == null) {
			output_.println("Found NO key that can be used for encryption.");
			output_.flush();
			System.exit(0);
		}
		System.out.println("Derived key: ");
		output_.println(derivedKey.toString());

		output_
		    .println("################################################################################");
		output_.println("finished");

		session.closeSession();
		pkcs11Module.finalize(null);
	}

	public static void printUsage() {
		output_.println("Usage: TestDeriveParams <PKCS#11 module> [<slot>] [<pin>]");
		output_.println(" e.g.: TestDeriveParams cryptoki.dll");
		output_.println("The given DLL must be in the search path of the system.");
	}

}
