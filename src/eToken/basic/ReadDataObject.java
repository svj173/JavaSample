package eToken.basic;


import eToken.util.Util;
import iaik.pkcs.pkcs11.*;
import iaik.pkcs.pkcs11.objects.Data;
import iaik.pkcs.pkcs11.objects.Object;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

/**
 * This demo program read a data object with a specific label from the token.
 *
 * @author <a href="mailto:Karl.Scheibelhofer@iaik.at"> Karl Scheibelhofer </a>
 * @version 0.1
 * @invariants
 */
public class ReadDataObject {

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
		else token = Util.selectToken(pkcs11Module, output_, input_);
		if (token == null) {
			output_.println("We have no token to proceed. Finished.");
			output_.flush();
			System.exit(0);
		}
		TokenInfo tokenInfo = token.getTokenInfo();

		output_
		    .println("################################################################################");
		output_.println("Information of Token:");
		output_.println(tokenInfo);
		output_
		    .println("################################################################################");

		// open an read-write user session
		Session session;
		if (3 < args.length) session = Util.openAuthorizedSession(token,
		    Token.SessionReadWriteBehavior.RW_SESSION, output_, input_, args[3]);
		else session = Util.openAuthorizedSession(token,
		    Token.SessionReadWriteBehavior.RW_SESSION, output_, input_, null);

		output_
		    .println("################################################################################");
		output_
		    .println("searching for data object on the card using this search template... ");
		output_.flush();

		// create certificate object template
		Data dataObjectTemplate = new Data();

		// we could also set the name that manages this data object
		//dataObjectTemplate.getApplication().setCharArrayValue("Application Name");

		// set the data object's label
		dataObjectTemplate.getLabel().setCharArrayValue(args[1].toCharArray());

		// print template
		output_.println(dataObjectTemplate);

		// start find operation
		session.findObjectsInit(dataObjectTemplate);

		Object[] foundDataObjects = session.findObjects(1); // find first

		Data dataObject;
		if (foundDataObjects.length > 0) {
			dataObject = (Data) foundDataObjects[0];
			output_
			    .println("________________________________________________________________________________");
			output_.print("found this data object with handle: ");
			output_.println(dataObject.getObjectHandle());
			output_.println(dataObject);
			output_
			    .println("________________________________________________________________________________");
			// FIXME, there may be more than one that matches the given template, the label is not unique in general
			// foundDataObjects = session.findObjects(1); //find next
		} else {
			dataObject = null;
		}

		session.findObjectsFinal();

		session.closeSession();
		pkcs11Module.finalize(null);
	}

	protected static void printUsage() {
		output_
		    .println("ReadDataObject <PKCS#11 module name> <data object label> [<slot>] [<pin>]");
		output_.println("e.g.: ReadDataObject gclib.dll \"Student Data\" data.dat");
	}

}
