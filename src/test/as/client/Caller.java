package test.as.client;

import org.apache.logging.log4j.*;


/**
 * Created by IntelliJ IDEA.
 * User: svj
 * Date: 16.02.2007
 * Time: 17:00:35
 * To change this template use File | Settings | File Templates.
 */
public class Caller implements Runnable
{
    private Logger logger = LogManager.getFormatterLogger(Caller.class);
    private Client c;

    public Caller(Client c)
    {
        this.c = c;
    }

    public void run ()
    {
        try
        {
            while ( true )
            {
                c.remoteCall("Service1", "sleep", new Object[] {new Long(1000)});
                Object obj = c.remoteCall ( "Service1", "getCurrentDate", new Object[]{});
                logger.info ( "Current Date is:" + obj );
            }
        } catch (Exception e) {
            logger.error ( "Start client error.", e);
        }
    }

}
