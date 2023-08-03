package logger.java;


/**
 * This class filters out all log messages starting with SECJ022E, SECJ0373E, or SECJ0350E.
 * <BR/>
 //This code will register the above log filter with the root Logger's handlers (including the WAS system logs):
 ...
 Logger rootLogger = LogManager.getFormatterLogger("");
 rootLogger.setFilter(new MyFilter());
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 29.03.2013 14:56
 */
import java.util.logging.Filter;
import java.util.logging.LogRecord;

public class MyFilter implements Filter
{
	public boolean isLoggable(LogRecord lr)
    {
		String msg = lr.getMessage();
		if (msg.startsWith("SECJ0222E") || msg.startsWith("SECJ0373E") || msg.startsWith("SECJ0350E")) {
			return false;
		}
		return true;
	}
}
