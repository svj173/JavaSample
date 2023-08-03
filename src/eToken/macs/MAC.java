package eToken.macs;

import iaik.pkcs.pkcs11.Mechanism;
import iaik.pkcs.pkcs11.Module;
import iaik.pkcs.pkcs11.Session;
import iaik.pkcs.pkcs11.Slot;
import iaik.pkcs.pkcs11.Token;
import iaik.pkcs.pkcs11.TokenException;
import iaik.pkcs.pkcs11.objects.GenericSecretKey;
import iaik.pkcs.pkcs11.wrapper.PKCS11Constants;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.Arrays;

/**
 * This demo program uses a PKCS#11 module to MAC a given file and test
 * if the MAC can be verified.
 *
 * @author <a href="mailto:Karl.Scheibelhofer@iaik.at"> Karl Scheibelhofer </a>
 * @version 0.1
 * @invariants
 */
public class MAC {

	static PrintWriter output_;

	static {
		try {
			//output_ = new PrintWriter(new FileWriter("Encrypt_output.txt"), true);
			output_ = new PrintWriter(System.out, true);
		} catch (Throwable thr) {
			thr.printStackTrace();
			output_ = new PrintWriter(System.out, true);
		}
	}

	public static void main(String[] args)
	    throws TokenException, IOException
	{
		if (args.length < 3) {
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
		if (3 < args.length) selectedSlot = slots[Integer.parseInt(args[3])];
		else selectedSlot = slots[0];
		Token token = selectedSlot.getToken();

		Session session = token.openSession(Token.SessionType.SERIAL_SESSION,
		    Token.SessionReadWriteBehavior.RO_SESSION, null, null);

		// login user
		session.login(Session.UserType.USER, args[1].toCharArray());

		output_
		    .println("################################################################################");
		output_.println("generate secret MAC key");

		//    Mechanism keyMechanism = Mechanism.DES3_KEY_GEN;
		//    DES3SecretKey secretMACKeyTemplate = new DES3SecretKey();
		//    secretMACKeyTemplate.getSign().setBooleanValue(Boolean.TRUE);
		//    secretMACKeyTemplate.getVerify().setBooleanValue(Boolean.TRUE);
		//
		//    DES3SecretKey secretMACKey = (DES3SecretKey) session.generateKey(keyMechanism, secretMACKeyTemplate);

		GenericSecretKey secretMACKeyTemplate = new GenericSecretKey();
		secretMACKeyTemplate.getSign().setBooleanValue(Boolean.TRUE);
		secretMACKeyTemplate.getVerify().setBooleanValue(Boolean.TRUE);
		secretMACKeyTemplate.getToken().setBooleanValue(Boolean.FALSE);

		// generate some bytes random data that we can use as test key
		byte[] randomData = session.generateRandom(16);
		secretMACKeyTemplate.getValue().setByteArrayValue(randomData);
		secretMACKeyTemplate.getValueLen().setLongValue(new Long(randomData.length));

		GenericSecretKey secretMACKey = (GenericSecretKey) session
		    .createObject(secretMACKeyTemplate);

		output_
		    .println("################################################################################");

		output_
		    .println("################################################################################");
		output_.println("MACing data from file: " + args[2]);

		InputStream dataInputStream = new FileInputStream(args[2]);

		//be sure that your token can process the specified mechanism
		Mechanism signatureMechanism = Mechanism.get(PKCS11Constants.CKM_SHA_1_HMAC);
		// initialize for signing
		session.signInit(signatureMechanism, secretMACKey);

		byte[] dataBuffer = new byte[1024];
		int bytesRead;
		ByteArrayOutputStream streamBuffer = new ByteArrayOutputStream();

		// feed in all data from the input stream
		while ((bytesRead = dataInputStream.read(dataBuffer)) >= 0) {
			streamBuffer.write(dataBuffer, 0, bytesRead);
		}
		Arrays.fill(dataBuffer, (byte) 0); // ensure that no data is left in the memory
		streamBuffer.flush();
		streamBuffer.close();
		dataInputStream.close();
		byte[] rawData = streamBuffer.toByteArray();

		byte[] macValue = session.sign(rawData);

		output_.println("The MAC value is: " + new BigInteger(1, macValue).toString(16));

		output_
		    .println("################################################################################");

		output_
		    .println("################################################################################");
		output_.print("verification of the MAC... ");

		dataInputStream = new FileInputStream(args[2]);

		// initialize for verification
		session.verifyInit(signatureMechanism, secretMACKey);

		streamBuffer = new ByteArrayOutputStream();

		// feed in all data from the input stream
		while ((bytesRead = dataInputStream.read(dataBuffer)) >= 0) {
			streamBuffer.write(dataBuffer, 0, bytesRead);
		}
		Arrays.fill(dataBuffer, (byte) 0); // ensure that no data is left in the memory
		streamBuffer.flush();
		streamBuffer.close();
		dataInputStream.close();
		rawData = streamBuffer.toByteArray();

		try {
			session.verify(rawData, macValue); // throws an exception upon unsuccessful verification
			output_.println("successful");
		} catch (TokenException ex) {
			output_.println("FAILED: " + ex.getMessage());
		}

		output_
		    .println("################################################################################");

		session.closeSession();
		pkcs11Module.finalize(null);
	}

	public static void printUsage() {
		output_.println("Usage: MAC <PKCS#11 module> <user-PIN> <file to be MACed> [<slot>]");
		output_.println(" e.g.: MAC pk2priv.dll password data.dat");
		output_.println("The given DLL must be in the search path of the system.");
	}

}
