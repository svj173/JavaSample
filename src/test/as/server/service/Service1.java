package test.as.server.service;

import org.apache.logging.log4j.*;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: svj
 * Date: 16.02.2007
 * Time: 16:13:53
 */
public class Service1
{
    private static Logger logger = LogManager.getFormatterLogger ( Service1.class );

    
    public void sleep(Long millis)
    {
        //logger.debug ( "Start" );
        try
        {
            Thread.sleep (millis.longValue());
        } catch (Exception e) {
            logger.error ("Sleep error", e );
        }
        //logger.debug ( "Finish" );
    }

    public Date getCurrentDate()
    {
        //logger.debug ( "Start" );
        Date date = new Date();
        //logger.debug ( "Finish. result = " + date );
        return date;
    }

}
