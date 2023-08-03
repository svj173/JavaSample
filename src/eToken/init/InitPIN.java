package eToken.init;

import iaik.pkcs.pkcs11.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

/**
 * This program sets the normal user's PIN.
 *
 * Навесить Пины на брелок. -- Не работает.
 *
 * 		output_.println("Usage: InitPIN <PKCS#11 module> <SO-PIN> <user-PIN> [<SlotNr>]");
 		output_.println(" e.g.: InitPIN pk2priv.dll 12345678 1234");

 * InitPIN libeTPkcs11.so 12345678 1234 0
 *
 *
 * @author <a href="mailto:Karl.Scheibelhofer@iaik.at"> Karl Scheibelhofer </a>
 * @version 0.1
 * @invariants
 */
public class InitPIN {

	static PrintWriter output_;

	static BufferedReader input_;

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
	    throws IOException, TokenException
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

		Token token = null;
		if (slots.length == 1) {
			Slot selectedSlot = slots[0];
			token = selectedSlot.getToken();
		} else {
			output_.println("Found several tokens: ");
			for (int i = 0; i < slots.length; i++) {
				token = slots[i].getToken();
				output_
				    .println("________________________________________________________________________________");
				output_.print("Info of Token number ");
				output_.println(i);
				output_.println(token.getTokenInfo());
				output_
				    .println("________________________________________________________________________________");
			}
			output_.println();
			output_
			    .print("For which token do you want to set the PIN? Please enter its number [0..");
			output_.print(slots.length);
			output_.print("]: ");
			output_.flush();
			String selectedTokenNumberString;
			if (args.length == 4) {
				selectedTokenNumberString = args[3];
				output_.print(args[3]);
			} else selectedTokenNumberString = input_.readLine();

			int selectedTokenNumber = Integer.parseInt(selectedTokenNumberString);
			token = slots[selectedTokenNumber].getToken();
		}

		TokenInfo tokenInfo = token.getTokenInfo();

		output_
		    .println("\n################################################################################");
		output_.println("Information of selsected Token:");
		output_.println(tokenInfo);
		output_
		    .println("################################################################################");

		output_
		    .println("################################################################################");
		output_.print("initializing user-PIN... ");
		Session session = token.openSession(Token.SessionType.SERIAL_SESSION,
		    Token.SessionReadWriteBehavior.RW_SESSION, null, null);
		// login security officer
		session.login(Session.UserType.SO, args[1].toCharArray());
		session.initPIN(args[2].toCharArray());
		output_.println("FINISHED");

		output_
		    .println("################################################################################");

		pkcs11Module.finalize(null);
	}

	public static void printUsage() {
		output_.println("Usage: InitPIN <PKCS#11 module> <SO-PIN> <user-PIN> [<SlotNr>]");
		output_.println(" e.g.: InitPIN pk2priv.dll 12345678 1234");
		output_.println("The given DLL must be in the search path of the system.");
	}

}
