package eToken.init;

import iaik.pkcs.pkcs11.*;

import java.io.*;

/**
 * This demo program tries to call initialize with some arguments.
 *
 * @author <a href="mailto:Karl.Scheibelhofer@iaik.at"> Karl Scheibelhofer </a>
 * @version 0.1
 * @invariants
 */
public class InitArgs implements InitializeArgs {

	static PrintWriter output_;

	static {
		try {
			//output_ = new PrintWriter(new FileWriter("GetInfo_output.txt"), true);
			output_ = new PrintWriter(System.out, true);
		} catch (Throwable thr) {
			thr.printStackTrace();
			output_ = new PrintWriter(System.out, true);
		}
	}

	public static void main(String[] args)
	    throws TokenException, IOException
	{
		if ((args.length == 1) || (args.length == 2)) {
			output_
			    .println("################################################################################");
			output_.println("load and initialize module \"" + args[0]
			    + "\" using InitializeArgs");
			output_.flush();
			Module pkcs11Module = Module.getInstance(args[0]);
			byte[] reservedParameter = (args.length >= 2) ? readStream(new FileInputStream(
			    args[1])) : null;
			InitializeArgs initArgs = new InitArgs(reservedParameter);
			pkcs11Module.initialize(initArgs);

			Info info = pkcs11Module.getInfo();
			output_.println(info);
			output_
			    .println("################################################################################");
		} else {
			printUsage();
		}
		System.gc(); // to finalize and disconnect the pkcs11Module
	}

	protected static void printUsage() {
		output_
		    .println("InitArgs <PKCS#11 module name> [<file providing reserved parameter>]");
		output_.println("e.g.: InitArgs slbck.dll");
	}

	/**
	 * Read the contents of the stream into a byte array. The stream is read
	 * until it returns EOF.
	 */
	protected static byte[] readStream(InputStream in)
	    throws IOException
	{
		ByteArrayOutputStream bufferStream = new ByteArrayOutputStream(256); // initial size
		int bytesRead;
		byte[] buffer = new byte[256];
		while ((bytesRead = in.read(buffer)) >= 0) {
			bufferStream.write(buffer, 0, bytesRead);
		}
		return bufferStream.toByteArray();
	}

	protected byte[] reservedParameter_;

	public InitArgs(byte[] reservedParameter) {
		reservedParameter_ = reservedParameter;
	}

	/**
	 * Get the handler object that handes mutex objects.
	 *
	 * @return The mutex handler object or null, if there is none set.
	 * @preconditions
	 * @postconditions
	 */
	public MutexHandler getMutexHandler() {
		output_.println("getMutexHandler() called");
		return null;
	}

	/**
	 * Checks, if the library is not allowed to create operating system threads.
	 *
	 * @return True, if the library is not allowed to create operating system
	 *         threads; false, otherwise.
	 * @preconditions
	 * @postconditions
	 */
	public boolean isLibraryCantCreateOsThreads() {
		output_.println("isLibraryCantCreateOsThreads() called");
		return false;
	}

	/**
	 * Checks, if the library is allowed to use locking mechanisms of the
	 * operating system.
	 *
	 * @return True, if the library is allowed to use locking mechanisms of the
	 *         operating system.
	 * @preconditions
	 * @postconditions
	 */
	public boolean isOsLockingOk() {
		output_.println("isOsLockingOk() called");
		return true;
	}

	/**
	 * Get the reserved parameter. This is always null as of version 2.11 of
	 * PKCS#11.
	 *
	 * @return null as of version 2.11 of PKCS#11.
	 * @preconditions
	 * @postconditions (result == null)
	 */
	public java.lang.Object getReserved() {
		output_.println("getReserved() called");
		return reservedParameter_;
	}

}
