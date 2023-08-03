package eToken.init;


import eToken.util.Util;
import iaik.pkcs.pkcs11.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

/**
 * This program initializes a token. Note that this erases all data on the
 * token.
 *
 * @author <a href="mailto:Karl.Scheibelhofer@iaik.at"> Karl Scheibelhofer </a>
 * @version 0.1
 * @invariants
 */
public class InitToken {

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

		Token token;
		if (2 < args.length) token = Util.selectToken ( pkcs11Module, output_, input_, args[ 2 ] );
		else token = Util.selectToken(pkcs11Module, output_, input_, null);

		if (token == null) {
			output_.println("We have no token to proceed. Finished.");
			output_.flush();
			System.exit(0);
		}

		TokenInfo tokenInfo = token.getTokenInfo();

		output_
		    .println("################################################################################");
		output_.println("Information of Token to be initialized:");
		output_.println(tokenInfo);
		output_
		    .println("################################################################################");

		/*      
		 output_.println("################################################################################");
		 output_.println("ATTENTION! Initialization will start in 10 seconds. You have time to remove the token or press any key to abort. Countdown... ");

		 InputStreamReader inputReader = new InputStreamReader(System.in);
		 for (int i = 10; i >= 0; i--) {
		 output_.print("\r");
		 output_.print(i);
		 output_.print(' ');
		 output_.flush();
		 Thread.sleep(1000);
		 if (inputReader.ready()) {
		 output_.println("Aborted...EXIT");
		 output_.flush();
		 pkcs11Module.finalize(null);
		 System.exit(0);
		 }
		 }
		 output_.println();
		 */
		output_.print("initializing... ");

		String soPINString = null;
		if (tokenInfo.isProtectedAuthenticationPath()) {
			output_.print("Please enter the SO-PIN at the PIN-pad of your reader.");
			token.initToken(null, args[1]);; // the token prompts the PIN by other means; e.g. PIN-pad
		} else {
			output_.print("Enter the SO-PIN and press [return key]: ");
			output_.flush();
			if (3 < args.length) {
				soPINString = args[3];
				output_.print(args[3] + "\n");
			} else soPINString = input_.readLine();
			token.initToken(soPINString.toCharArray(), args[1]);
		}
		output_.println("FINISHED");

		// login security officer
		if (tokenInfo.isLoginRequired()) {
			output_.print("initializing user-PIN... ");
			Session session = token.openSession(Token.SessionType.SERIAL_SESSION,
			    Token.SessionReadWriteBehavior.RW_SESSION, null, null);

			if (tokenInfo.isProtectedAuthenticationPath()) {
				output_.print("Please enter the SO-PIN at the PIN-pad of your reader.");
				output_.flush();
				session.login(Session.UserType.SO, null); // the token prompts the PIN by other means; e.g. PIN-pad
				output_.print("Please enter the user-PIN at the PIN-pad of your reader.");
				output_.flush();
				session.initPIN(null);
			} else {
				if (soPINString != null) {
					session.login(Session.UserType.SO, soPINString.toCharArray());
				} else {
					output_.print("Enter the SO-PIN and press [return key]: ");
					output_.flush();
					if (3 < args.length) {
						soPINString = args[3];
						output_.print(args[3] + "\n");
					} else soPINString = input_.readLine();
					session.login(Session.UserType.SO, soPINString.toCharArray());
				}
				output_.print("Enter the user-PIN and press [return key]: ");
				output_.flush();
				String userPINString;
				if (4 < args.length) {
					userPINString = args[4];
					output_.print(args[4] + "\n");
				} else userPINString = input_.readLine();
				session.initPIN(userPINString.toCharArray());
			}
			session.closeSession();
			output_.println("FINISHED");
		}

		output_
		    .println("################################################################################");

		tokenInfo = token.getTokenInfo();

		output_
		    .println("################################################################################");
		output_.println("Information of initialized Token:");
		output_.println(tokenInfo);
		output_
		    .println("################################################################################");

		pkcs11Module.finalize(null);
	}

	public static void printUsage() {
		output_
		    .println("Usage: InitToken <PKCS#11 module> \"Card Label\" [<slot>] [<SO Pin>] [<User Pin>]");
		output_.println(" e.g.: InitToken pk2priv.dll \"My Test Card\"");
		output_.println("ATTENTION: Any data on the card will get lost upon initialization!");
		output_.println("The given DLL must be in the search path of the system.");
	}

}
