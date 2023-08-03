package logger.java;


import java.io.IOException;
import java.util.logging.*;


/**
 * Создание java-логгера, который выводит в два разных файла.
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 09.04.2015 11:52
 */
public class TwoFileLogger
{
    static private FileHandler      fileTxt;
    static private SimpleFormatter  formatterTxt;
    static private FileHandler      fileHTML;
    static private Formatter        formatterHTML;

    //static final Logger logger = LogManager.getFormatterLogger ( Logger.GLOBAL_LOGGER_NAME );


    static public void setup ( String txtFile, String htmlFile ) throws IOException
    {
        // get the global logger to configure it
        Logger logger = Logger.getLogger ( Logger.GLOBAL_LOGGER_NAME );

        // suppress the logging output to the console
        Logger rootLogger = Logger.getLogger ( "" );
        Handler[] handlers = rootLogger.getHandlers ();
        if ( handlers[ 0 ] instanceof ConsoleHandler )
        {
            rootLogger.removeHandler ( handlers[ 0 ] );
        }

        logger.setLevel ( Level.INFO );
        fileTxt  = new FileHandler ( txtFile );
        fileHTML = new FileHandler ( htmlFile );

        // create a TXT formatter
        formatterTxt = new SimpleFormatter ();
        fileTxt.setFormatter ( formatterTxt );
        logger.addHandler ( fileTxt );

        // create an HTML formatter
        formatterHTML = new MyHtmlFormatter ();
        fileHTML.setFormatter ( formatterHTML );
        logger.addHandler ( fileHTML );
    }

}
