package eToken.random;

import iaik.pkcs.pkcs11.Module;
import iaik.pkcs.pkcs11.Session;
import iaik.pkcs.pkcs11.Slot;
import iaik.pkcs.pkcs11.Token;
import iaik.pkcs.pkcs11.TokenException;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * This demo program uses a PKCS#11 module to produce random data.
 * Optionally the random data can be written to file.
 *
 * @author <a href="mailto:Karl.Scheibelhofer@iaik.at"> Karl Scheibelhofer </a>
 * @version 0.1
 * @invariants
 */
public class GenerateRandom {

	public static void main(String[] args)
	    throws IOException, TokenException
	{
		if ((args.length != 2) && (args.length != 3)) {
			printUsage();
			System.exit(1);
		}
		int numberOfBytes = -1;
		try {
			numberOfBytes = Integer.parseInt(args[1]);
		} catch (Exception ex) {
			printUsage();
			System.exit(1);
		}

		Module pkcs11Module = Module.getInstance(args[0]);
		pkcs11Module.initialize(null);

		Slot[] slots = pkcs11Module.getSlotList(Module.SlotRequirement.TOKEN_PRESENT);

		if (slots.length == 0) {
			System.out.println("No slot with present token found!");
			System.exit(0);
		}

		Slot selectedSlot = slots[0];
		Token token = selectedSlot.getToken();

		Session session = token.openSession(Token.SessionType.SERIAL_SESSION,
		    Token.SessionReadWriteBehavior.RO_SESSION, null, null);

		System.out
		    .println("################################################################################");
		System.out.print("generating " + numberOfBytes + " bytes of random data... ");

		byte[] dataBuffer = session.generateRandom(numberOfBytes);

		System.out.println("finished");
		System.out
		    .println("################################################################################");

		OutputStream dataOutput;
		if (args.length == 3) {
			System.out
			    .println("################################################################################");
			System.out.println("writing random data to file : " + args[2]);

			dataOutput = new FileOutputStream(args[2]);
		} else {
			System.out.println("random is:");
			dataOutput = System.out;
		}

		dataOutput.write(dataBuffer);
		dataOutput.flush();
		if (dataOutput instanceof FileOutputStream) dataOutput.close();
		System.out
		    .println("################################################################################");

		session.closeSession();
		pkcs11Module.finalize(null);
	}

	public static void printUsage() {
		System.out
		    .println("Usage: GenerateRandom <PKCS#11 module> <number of bytes> [<output file>]");
		System.out.println(" e.g.: GenerateRandom pk2priv.dll 128 random.dat");
		System.out.println("The given DLL must be in the search path of the system.");
	}

}
