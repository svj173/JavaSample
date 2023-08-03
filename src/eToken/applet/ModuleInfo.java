package eToken.applet;

import iaik.pkcs.pkcs11.Info;
import iaik.pkcs.pkcs11.Module;

import java.applet.Applet;

/**
 * This demo program lists information about a module.
 *
 * @author <a href="mailto:Karl.Scheibelhofer@iaik.at"> Karl Scheibelhofer </a>
 * @version 0.1
 * @invariants
 */
public class ModuleInfo extends Applet {

	/**
	 * auto-generated serial id
	 */
	private static final long serialVersionUID = -8680181239066723664L;
	protected Module pkcs11Module_;

	/**
	 *  this allows us do an automated test of this demo
	 */
	private String moduleName_;

	/**
	 *  this allows us do an automated test of this demo
	 */
	public ModuleInfo(String moduleName) {
		this.moduleName_ = moduleName;
	}

	/**
	 * Initialize this applet. Loads and initializes the module.
	 */
	public void init() {
		String moduleName = moduleName_; // this allows us do an automated test of this demo
		if (null == moduleName) // this allows us do an automated test of this demo
		moduleName = getParameter("ModuleName");

		System.out.println("initializing module " + moduleName + " ... ");
		try {
			pkcs11Module_ = Module.getInstance(moduleName);
			pkcs11Module_.initialize(null);
		} catch (Throwable ex) {
			ex.printStackTrace();
		}
		System.out.println("...finished initializing");
	}

	/**
	 * Start this applet. Gets info about the module and dumps it to the console.
	 */
	public void start() {
		System.out.println("starting... ");

		try {
			System.out.print("getting module info...");
			Info info = pkcs11Module_.getInfo();
			System.out.println("finished");
			System.out.println("module info is:");
			System.out.println(info);
		} catch (Throwable ex) {
			ex.printStackTrace();
		}
		System.out.flush();
		System.err.flush();

		System.out.println("...finished starting");
	}

	/**
	 * Stop this applet. Does effectively nothing.
	 */
	public void stop() {
		System.out.print("stopping... ");
		System.out.println("finished");
	}

	/**
	 * Destroy this applet. Finalizes the module.
	 */
	public void destroy() {
		System.out.print("preparing for unloading...");
		try {
			pkcs11Module_.finalize(null);
			pkcs11Module_ = null;
		} catch (Throwable ex) {
			ex.printStackTrace();
		}
		System.out.println("finished");
	}

}
