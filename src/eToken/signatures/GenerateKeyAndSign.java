package eToken.signatures;


import eToken.util.Util;
import iaik.pkcs.pkcs11.*;
import iaik.pkcs.pkcs11.objects.KeyPair;
import iaik.pkcs.pkcs11.objects.RSAPrivateKey;
import iaik.pkcs.pkcs11.objects.RSAPublicKey;
import iaik.pkcs.pkcs11.wrapper.Functions;
import iaik.pkcs.pkcs11.wrapper.PKCS11Constants;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Random;


/**
 * This demo program generates a 1024 bit RSA key-pair on the token and signs some data with it.
 *
 * @author <a href="mailto:Karl.Scheibelhofer@iaik.at"> Karl Scheibelhofer </a>
 * @version 0.1
 * @invariants
 */
public class GenerateKeyAndSign {

	static BufferedReader input_;

	static PrintWriter output_;

	static {
		try {
			output_ = new PrintWriter(System.out, true);
			input_ = new BufferedReader(new InputStreamReader(System.in));
		} catch (Throwable thr) {
			thr.printStackTrace();
			output_ = new PrintWriter(System.out, true);
			input_ = new BufferedReader(new InputStreamReader(System.in));
		}
	}

	public static void main(String[] args)
	    throws TokenException, IOException
	{
		if (args.length < 2) {
			printUsage();
			System.exit(1);
		}

		Module pkcs11Module = Module.getInstance(args[0]);
		pkcs11Module.initialize(null);

		Slot[] slots = pkcs11Module.getSlotList(Module.SlotRequirement.TOKEN_PRESENT);

		if (slots.length == 0) {
			output_.println("No slot with present token found!");
			System.exit(0);
		}

		int slotIndex = Integer.parseInt(args[1]);
		Slot selectedSlot = slots[slotIndex];
		Token token = selectedSlot.getToken();
		TokenInfo tokenInfo = token.getTokenInfo();

		output_
		    .println("################################################################################");
		output_.println("Information of Token:");
		output_.println(tokenInfo);
		output_
		    .println("################################################################################");

		Session session;
		if (2 < args.length) session = Util.openAuthorizedSession ( token,
                                                                    Token.SessionReadWriteBehavior.RW_SESSION, output_, input_, args[ 2 ] );
		else session = Util.openAuthorizedSession(token,
		    Token.SessionReadWriteBehavior.RW_SESSION, output_, input_, null);

		output_
		    .println("################################################################################");
		int keySize = 1024;
		output_.print("Generating new " + keySize + " bit RSA key-pair... ");
		output_.flush();

		Mechanism keyPairGenerationMechanism = Mechanism
		    .get(PKCS11Constants.CKM_RSA_PKCS_KEY_PAIR_GEN);
		RSAPublicKey rsaPublicKeyTemplate = new RSAPublicKey();
		RSAPrivateKey rsaPrivateKeyTemplate = new RSAPrivateKey();

		// set the general attributes for the public key
		rsaPublicKeyTemplate.getModulusBits().setLongValue(new Long(keySize));
		byte[] publicExponentBytes = { 0x01, 0x00, 0x01 }; // 2^16 + 1
		rsaPublicKeyTemplate.getPublicExponent().setByteArrayValue(publicExponentBytes);
		rsaPublicKeyTemplate.getToken().setBooleanValue(Boolean.FALSE);
		byte[] id = new byte[20];
		new Random().nextBytes(id);
		rsaPublicKeyTemplate.getId().setByteArrayValue(id);
		//rsaPublicKeyTemplate.getLabel().setCharArrayValue(args[2].toCharArray());

		rsaPrivateKeyTemplate.getSensitive().setBooleanValue(Boolean.TRUE);
		rsaPrivateKeyTemplate.getToken().setBooleanValue(Boolean.FALSE);
		rsaPrivateKeyTemplate.getPrivate().setBooleanValue(Boolean.TRUE);
		rsaPrivateKeyTemplate.getId().setByteArrayValue(id);
		//rsaPrivateKeyTemplate.getLabel().setCharArrayValue(args[2].toCharArray());

		rsaPrivateKeyTemplate.getSign().setBooleanValue(Boolean.TRUE);
		rsaPublicKeyTemplate.getVerify().setBooleanValue(Boolean.TRUE);

		// netscape does not set these attribute, so we do no either
		rsaPublicKeyTemplate.getKeyType().setPresent(false);
		rsaPublicKeyTemplate.getObjectClass().setPresent(false);
		rsaPrivateKeyTemplate.getKeyType().setPresent(false);
		rsaPrivateKeyTemplate.getObjectClass().setPresent(false);

		KeyPair generatedKeyPair = session.generateKeyPair(keyPairGenerationMechanism,
		    rsaPublicKeyTemplate, rsaPrivateKeyTemplate);
		RSAPublicKey generatedRSAPublicKey = (RSAPublicKey) generatedKeyPair.getPublicKey();
		RSAPrivateKey generatedRSAPrivateKey = (RSAPrivateKey) generatedKeyPair
		    .getPrivateKey();
		// no we may work with the keys...

		output_.println("Success");
		output_.println("The public key is");
		output_
		    .println("_______________________________________________________________________________");
		output_.println(generatedRSAPublicKey);
		output_
		    .println("_______________________________________________________________________________");
		output_.println("The private key is");
		output_
		    .println("_______________________________________________________________________________");
		output_.println(generatedRSAPrivateKey);
		output_
		    .println("_______________________________________________________________________________");

		output_
		    .println("################################################################################");
		output_.print("Signing Data... ");

		Mechanism signatureMechanism = Mechanism.get(PKCS11Constants.CKM_RSA_PKCS);
		session.signInit(signatureMechanism, generatedRSAPrivateKey);
		byte[] dataToBeSigned = "12345678901234567890123456789012345".getBytes("ASCII");
		byte[] signatureValue = session.sign(dataToBeSigned);
		output_.println("Finished");
		output_.println("Signature Value: " + Functions.toHexString(signatureValue));
		output_
		    .println("################################################################################");

		session.closeSession();
		pkcs11Module.finalize(null);
	}

	public static void printUsage() {
		output_.println("Usage: GenerateAndSign4096 <PKCS#11 module> <slot index> [<pin>]");
		output_.println(" e.g.: GenerateAndSign4096 cs2_pkcs11.dll 3");
		output_.println("The given DLL must be in the search path of the system.");
	}

}
