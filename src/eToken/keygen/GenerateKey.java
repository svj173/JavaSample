package eToken.keygen;

import iaik.pkcs.pkcs11.Mechanism;
import iaik.pkcs.pkcs11.Module;
import iaik.pkcs.pkcs11.Session;
import iaik.pkcs.pkcs11.Slot;
import iaik.pkcs.pkcs11.Token;
import iaik.pkcs.pkcs11.TokenException;
import iaik.pkcs.pkcs11.TokenInfo;
import iaik.pkcs.pkcs11.objects.GenericSecretKey;
import iaik.pkcs.pkcs11.wrapper.PKCS11Constants;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

/**
 * This demo program shows how to generate secret keys.
 *
 * @author <a href="mailto:Karl.Scheibelhofer@iaik.at"> Karl Scheibelhofer </a>
 * @version 0.1
 * @invariants
 */
public class GenerateKey {

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
	    throws IOException, TokenException
	{
		if ((args.length < 1)) {
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
		if (1 < args.length) selectedSlot = slots[Integer.parseInt(args[1])];
		else selectedSlot = slots[0];

		Token token = selectedSlot.getToken();
		TokenInfo tokenInfo = token.getTokenInfo();

		output_
		    .println("################################################################################");
		output_.println("Information of Token:");
		output_.println(tokenInfo);
		output_
		    .println("################################################################################");

		List supportedMechanisms = Arrays.asList(token.getMechanismList());

		Session session = token.openSession(Token.SessionType.SERIAL_SESSION,
		    Token.SessionReadWriteBehavior.RW_SESSION, null, null);

		// if we have to user PIN login user
		if (2 < args.length) {
			session.login(Session.UserType.USER, args[2].toCharArray());
		}

		if (supportedMechanisms.contains(Mechanism
		    .get(PKCS11Constants.CKM_GENERIC_SECRET_KEY_GEN))) {
			output_
			    .println("################################################################################");
			output_.println("Generating generic secret key");

			Mechanism keyGenerationMechanism = Mechanism
			    .get(PKCS11Constants.CKM_GENERIC_SECRET_KEY_GEN);

			GenericSecretKey secretKeyTemplate = new GenericSecretKey();
			secretKeyTemplate.getValueLen().setLongValue(new Long(16));

			GenericSecretKey secretKey = (GenericSecretKey) session.generateKey(
			    keyGenerationMechanism, secretKeyTemplate);

			output_.println("the secret key is");
			output_.println(secretKey.toString());

			output_
			    .println("################################################################################");
		} else output_.println("Mechanism not supported: GENERIC_SECRET_KEY_GEN");
		session.closeSession();
		pkcs11Module.finalize(null);
	}

	public static void printUsage() {
		output_.println("Usage: GenerateKey <PKCS#11 module> [<slot>] [<user-PIN>]");
		output_.println(" e.g.: GenerateKey cryptoki.dll");
		output_.println("The given DLL must be in the search path of the system.");
	}

}
