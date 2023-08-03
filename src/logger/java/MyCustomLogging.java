package logger.java;


/**
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 29.03.2013 14:58
 */

import java.util.Vector;
import java.util.logging.*;

public class MyCustomLogging {

	public MyCustomLogging() {
		super();
	}

	public static void initializeLogging() {

		// Get the logger that you want to attach a custom Handler to
		String defaultResourceBundleName = "com.myCompany.Messages";
		Logger logger = Logger.getLogger ( "com.myCompany", defaultResourceBundleName );

		// Set up a custom Handler (see MyCustomHandler example)
		Handler handler = new MyCustomHandler("MyOutputFile.log");

		// Set up a custom Filter (see MyCustomFilter example)
		Vector acceptableLevels = new Vector();
		acceptableLevels.add(Level.INFO);
		acceptableLevels.add(Level.SEVERE);

        //Filter filter = new MyCustomFilter (acceptableLevels);  // ???
		Filter filter = new MyFilter ();

		// Set up a custom Formatter (see MyCustomFormatter example)
		Formatter formatter = new MyCustomFormatter();

		// Connect the filter and formatter to the handler
		handler.setFilter(filter);
		handler.setFormatter(formatter);

		// Connect the handler to the logger
		logger.addHandler(handler);

		// avoid sending events logged to com.myCompany showing up in WebSphere
		// Application Server logs
		logger.setUseParentHandlers(false);

	}

	public static void main(String[] args)
    {
		initializeLogging();

		Logger logger = Logger.getLogger ( "com.myCompany" );

		logger.info("This is a test INFO message");
		logger.warning("This is a test WARNING message");
		logger.logp(Level.SEVERE, "MyCustomLogging", "main", "This is a test SEVERE message");
	}
}