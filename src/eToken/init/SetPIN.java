package eToken.init;

import iaik.pkcs.pkcs11.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

/**
 * This program sets the normal user's PIN.
 *
 * @author <a href="mailto:Karl.Scheibelhofer@iaik.at"> Karl Scheibelhofer </a>
 * @version 0.1
 * @invariants
 */
public class SetPIN {

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
			if (2 < args.length) {
				selectedTokenNumberString = args[2];
				output_.print(args[2] + "\n");
			} else selectedTokenNumberString = input_.readLine();
			int selectedTokenNumber = Integer.parseInt(selectedTokenNumberString);
			token = slots[selectedTokenNumber].getToken();
		}

		TokenInfo tokenInfo = token.getTokenInfo();

		output_
		    .println("################################################################################");
		output_.println("Information of selsected Token:");
		output_.println(tokenInfo);
		output_
		    .println("################################################################################");

		boolean userType = Session.UserType.USER;
		if (args[1].equalsIgnoreCase("USER")) {
			userType = Session.UserType.USER;
		} else if (args[1].equalsIgnoreCase("SO")) {
			userType = Session.UserType.SO;
		} else {
			output_.println("Unknown user type: " + args[1]);
			printUsage();
			pkcs11Module.finalize(null);
			System.exit(1);
		}
		String userTypeName = (userType == Session.UserType.USER) ? "user"
		    : "security officer";

		output_
		    .println("################################################################################");
		output_.println("setting " + userTypeName + " PIN");

		Session session = token.openSession(Token.SessionType.SERIAL_SESSION,
		    Token.SessionReadWriteBehavior.RW_SESSION, null, null);
		if (tokenInfo.isLoginRequired()) {
			if (tokenInfo.isProtectedAuthenticationPath()) {
				session.login(userType, null); // the token prompts the PIN by other means; e.g. PIN-pad
				session.setPIN(null, null);
			} else {
				output_.print("Enter current " + userTypeName + " PIN: ");
				output_.flush();
				String pinString;
				if (3 < args.length) {
					pinString = args[3];
					output_.print(args[3] + "\n");
				} else pinString = input_.readLine();
				char[] pin = pinString.toCharArray();
				// login user
				session.login(userType, pin);

				char[] newPIN = null;
				boolean repeat = false;
				do {
					output_.print("Enter new " + userTypeName + " PIN: ");
					output_.flush();
					String newPINString;
					if (3 < args.length) {
						newPINString = args[3];
						output_.print(args[3] + "\n");
					} else newPINString = input_.readLine();
					output_.print("Enter new " + userTypeName + " PIN again for confirmation: ");
					output_.flush();
					String confirmedNewPINString;
					if (3 < args.length) {
						confirmedNewPINString = args[3];
						output_.print(args[3] + "\n");
					} else confirmedNewPINString = input_.readLine();
					if (!newPINString.equals(confirmedNewPINString)) {
						output_.println("The two entries do not match. Try again.");
						repeat = true;
					} else {
						newPIN = newPINString.toCharArray();
						repeat = false;
					}
				} while (repeat);
				session.setPIN(pin, newPIN);
			}
		} else {
			output_.println("This token does not require a login. It does not use a PIN.");
		}

		output_.println("FINISHED");
		output_
		    .println("################################################################################");

		pkcs11Module.finalize(null);
	}

	public static void printUsage() {
		output_
		    .println("Usage: SetPIN <PKCS#11 module> (USER|SO) [<slot>] [<currentPin>] [<newPin>]");
		output_.println(" e.g.: SetPIN pk2priv.dll USER");
		output_.println("The given DLL must be in the search path of the system.");
	}

}
