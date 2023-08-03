package eToken.keygen;


import eToken.util.Util;
import iaik.pkcs.pkcs11.*;
import iaik.pkcs.pkcs11.objects.KeyPair;
import iaik.pkcs.pkcs11.objects.Object;
import iaik.pkcs.pkcs11.objects.RSAPrivateKey;
import iaik.pkcs.pkcs11.objects.RSAPublicKey;
import iaik.pkcs.pkcs11.wrapper.Functions;
import iaik.pkcs.pkcs11.wrapper.PKCS11Constants;

import java.io.*;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;

/**
 * This demo program generates a 2048 bit RSA key-pair on the token and writes
 * the public key to a file.
 *
 * @author <a href="mailto:Karl.Scheibelhofer@iaik.at"> Karl Scheibelhofer </a>
 * @version 0.1
 */
public class GenerateKeyPair {

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
	    throws IOException, TokenException, NoSuchAlgorithmException,
	    InvalidKeySpecException
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

		Slot selectedSlot;
		if (2 < args.length) selectedSlot = slots[Integer.parseInt(args[2])];
		else selectedSlot = slots[0];
		Token token = selectedSlot.getToken();
		TokenInfo tokenInfo = token.getTokenInfo();

		output_
		    .println("################################################################################");
		output_.println("Information of Token:");
		output_.println(tokenInfo);
		output_
		    .println("################################################################################");

		Session session;
		if (3 < args.length) session = Util.openAuthorizedSession ( token,
                                                                    Token.SessionReadWriteBehavior.RW_SESSION, output_, input_, args[ 3 ] );
		else session = Util.openAuthorizedSession(token,
		    Token.SessionReadWriteBehavior.RW_SESSION, output_, input_, null);

		output_
		    .println("################################################################################");
		output_.print("Generating new 2048 bit RSA key-pair... ");
		output_.flush();

		// first check out what attributes of the keys we may set
		HashSet supportedMechanisms = new HashSet(Arrays.asList(token.getMechanismList()));

		MechanismInfo signatureMechanismInfo;
		if (supportedMechanisms.contains(Mechanism.get(PKCS11Constants.CKM_RSA_PKCS))) {
			signatureMechanismInfo = token.getMechanismInfo(Mechanism
			    .get(PKCS11Constants.CKM_RSA_PKCS));
		} else if (supportedMechanisms.contains(Mechanism.get(PKCS11Constants.CKM_RSA_X_509))) {
			signatureMechanismInfo = token.getMechanismInfo(Mechanism
			    .get(PKCS11Constants.CKM_RSA_X_509));
		} else if (supportedMechanisms.contains(Mechanism.get(PKCS11Constants.CKM_RSA_9796))) {
			signatureMechanismInfo = token.getMechanismInfo(Mechanism
			    .get(PKCS11Constants.CKM_RSA_9796));
		} else if (supportedMechanisms.contains(Mechanism
		    .get(PKCS11Constants.CKM_RSA_PKCS_OAEP))) {
			signatureMechanismInfo = token.getMechanismInfo(Mechanism
			    .get(PKCS11Constants.CKM_RSA_PKCS_OAEP));
		} else {
			signatureMechanismInfo = null;
		}

		Mechanism keyPairGenerationMechanism = Mechanism
		    .get(PKCS11Constants.CKM_RSA_PKCS_KEY_PAIR_GEN);
		RSAPublicKey rsaPublicKeyTemplate = new RSAPublicKey();
		RSAPrivateKey rsaPrivateKeyTemplate = new RSAPrivateKey();

		// set the general attributes for the public key
		rsaPublicKeyTemplate.getModulusBits().setLongValue(new Long(2048));
		byte[] publicExponentBytes = { 0x01, 0x00, 0x01 }; // 2^16 + 1
		rsaPublicKeyTemplate.getPublicExponent().setByteArrayValue(publicExponentBytes);
		rsaPublicKeyTemplate.getToken().setBooleanValue(Boolean.TRUE);
		byte[] id = new byte[20];
		new Random().nextBytes(id);
		rsaPublicKeyTemplate.getId().setByteArrayValue(id);
		//rsaPublicKeyTemplate.getLabel().setCharArrayValue(args[2].toCharArray());

		rsaPrivateKeyTemplate.getSensitive().setBooleanValue(Boolean.TRUE);
		rsaPrivateKeyTemplate.getToken().setBooleanValue(Boolean.TRUE);
		rsaPrivateKeyTemplate.getPrivate().setBooleanValue(Boolean.TRUE);
		rsaPrivateKeyTemplate.getId().setByteArrayValue(id);
		//byte[] subject = args[1].getBytes();
		//rsaPrivateKeyTemplate.getSubject().setByteArrayValue(subject);
		//rsaPrivateKeyTemplate.getLabel().setCharArrayValue(args[2].toCharArray());

		// set the attributes in a way netscape does, this should work with most tokens
		if (signatureMechanismInfo != null) {
			rsaPublicKeyTemplate.getVerify().setBooleanValue(
			    new Boolean(signatureMechanismInfo.isVerify()));
			rsaPublicKeyTemplate.getVerifyRecover().setBooleanValue(
			    new Boolean(signatureMechanismInfo.isVerifyRecover()));
			rsaPublicKeyTemplate.getEncrypt().setBooleanValue(
			    new Boolean(signatureMechanismInfo.isEncrypt()));
			rsaPublicKeyTemplate.getDerive().setBooleanValue(
			    new Boolean(signatureMechanismInfo.isDerive()));
			rsaPublicKeyTemplate.getWrap().setBooleanValue(
			    new Boolean(signatureMechanismInfo.isWrap()));

			rsaPrivateKeyTemplate.getSign().setBooleanValue(
			    new Boolean(signatureMechanismInfo.isSign()));
			rsaPrivateKeyTemplate.getSignRecover().setBooleanValue(
			    new Boolean(signatureMechanismInfo.isSignRecover()));
			rsaPrivateKeyTemplate.getDecrypt().setBooleanValue(
			    new Boolean(signatureMechanismInfo.isDecrypt()));
			rsaPrivateKeyTemplate.getDerive().setBooleanValue(
			    new Boolean(signatureMechanismInfo.isDerive()));
			rsaPrivateKeyTemplate.getUnwrap().setBooleanValue(
			    new Boolean(signatureMechanismInfo.isUnwrap()));
		} else {
			// if we have no information we assume these attributes
			rsaPrivateKeyTemplate.getSign().setBooleanValue(Boolean.TRUE);
			rsaPrivateKeyTemplate.getDecrypt().setBooleanValue(Boolean.TRUE);

			rsaPublicKeyTemplate.getVerify().setBooleanValue(Boolean.TRUE);
			rsaPublicKeyTemplate.getEncrypt().setBooleanValue(Boolean.TRUE);
		}

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

		// write the public key to file
		output_
		    .println("################################################################################");
		output_.println("Writing the public key of the generated key-pair to file: "
		    + args[1]);
		RSAPublicKey exportableRsaPublicKey = generatedRSAPublicKey;
		BigInteger modulus = new BigInteger(1, exportableRsaPublicKey.getModulus()
		    .getByteArrayValue());
		BigInteger publicExponent = new BigInteger(1, exportableRsaPublicKey
		    .getPublicExponent().getByteArrayValue());
		RSAPublicKeySpec rsaPublicKeySpec = new RSAPublicKeySpec(modulus, publicExponent);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		java.security.interfaces.RSAPublicKey javaRsaPublicKey = (java.security.interfaces.RSAPublicKey) keyFactory
		    .generatePublic(rsaPublicKeySpec);
		X509EncodedKeySpec x509EncodedPublicKey = (X509EncodedKeySpec) keyFactory.getKeySpec(
		    javaRsaPublicKey, X509EncodedKeySpec.class);

		FileOutputStream publicKeyFileStream = new FileOutputStream(args[1]);
		publicKeyFileStream.write(x509EncodedPublicKey.getEncoded());
		publicKeyFileStream.flush();
		publicKeyFileStream.close();

		output_
		    .println("################################################################################");

		// now we try to search for the generated keys
		output_
		    .println("################################################################################");
		output_
		    .println("Trying to search for the public key of the generated key-pair by ID: "
		        + Functions.toHexString(id));
		// set the search template for the public key
		RSAPublicKey exportRsaPublicKeyTemplate = new RSAPublicKey();
		exportRsaPublicKeyTemplate.getId().setByteArrayValue(id);

		session.findObjectsInit(exportRsaPublicKeyTemplate);
		Object[] foundPublicKeys = session.findObjects(1);
		session.findObjectsFinal();

		if (foundPublicKeys.length != 1) {
			output_.println("Error: Cannot find the public key under the given ID!");
		} else {
			output_.println("Found public key!");
			output_
			    .println("_______________________________________________________________________________");
			output_.println(foundPublicKeys[0]);
			output_
			    .println("_______________________________________________________________________________");
		}

		output_
		    .println("################################################################################");

		session.closeSession();
		pkcs11Module.finalize(null);
	}

	public static void printUsage() {
		output_
		    .println("Usage: GenerateKeyPair2048 <PKCS#11 module> <X.509 encoded public key file> [<slot>] [<pin>]");
		output_.println(" e.g.: GenerateKeyPair2048 pk2priv.dll publicKey.xpk");
		output_.println("The given DLL must be in the search path of the system.");
	}

}
