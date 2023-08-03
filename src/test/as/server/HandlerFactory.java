package test.as.server;


import com.svj.utils.socket.SocketHandlerFactory;
import com.svj.utils.socket.SocketHandler;
import org.apache.logging.log4j.*;

/**
 * User: svj
 * Date: 16.02.2007
 * Time: 15:25:46
 */
public class HandlerFactory  implements SocketHandlerFactory
{
    private static Logger logger = LogManager.getFormatterLogger ( HandlerFactory.class );


    public HandlerFactory ()
    {
    }

    public SocketHandler getSocketHandler ()
    {
        SocketHandler   handler;

        //logger.debug ( "Start" );
        handler = new test.as.server.Handler();
        //handler.init ();
        //logger.debug ( "Finish" );

        return handler;
    }

}
