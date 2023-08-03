package logger.java;


import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 22.04.2015 9:21
 */
public class UseLogger
{
    private final static Logger LOGGER = Logger.getLogger ( Logger.GLOBAL_LOGGER_NAME );


    public void doSomeThingAndLog ()
    {
        // ... more code

        // now we demo the logging

        // set the LogLevel to Severe, only severe Messages will be written
        LOGGER.setLevel ( Level.SEVERE );
        LOGGER.severe ( "Info Log 1" );
        LOGGER.warning ( "Info Log 1" );
        LOGGER.info ( "Info Log 1" );
        LOGGER.finest ( "Really not important 1" );

        // set the LogLevel to Info, severe, warning and info will be written
        // finest is still not written
        LOGGER.setLevel ( Level.INFO );
        LOGGER.severe ( "Info Log 2" );
        LOGGER.warning ( "Info Log 2" );
        LOGGER.info ( "Info Log 2" );
        LOGGER.finest ( "Really not important 2" );

        LOGGER.setLevel ( Level.SEVERE );
        LOGGER.severe ( "Info Log 3" );
        LOGGER.warning ( "Info Log 3" );
        LOGGER.info ( "Info Log 3" );
        LOGGER.finest ( "Really not important 3" );
    }


    public static void main ( String[] args )
    {
        String txtFile, htmlFile;
        UseLogger tester;

        tester = new UseLogger ();

        try
        {
            txtFile  = "/home/svj/tmp/logger/txtFile_%g.txt";
            htmlFile = "/home/svj/tmp/logger/htmlFile_%g.html";
            TwoFileLogger.setup ( txtFile, htmlFile );

        } catch ( IOException e )  {
            e.printStackTrace ();
            throw new RuntimeException ( "Problems with creating the log files" );
        }

        tester.doSomeThingAndLog();
    }

}
