package socket.send;

import com.svj.utils.Utils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * <BR>
 * <BR>
 * <BR> User: svj
 * <BR> Date: 16.06.2006
 * <BR> Time: 10:58:03
 */
public class Client
{
    private static Logger logger = LogManager.getFormatterLogger ( Client.class );

    public static void main ( String[] args )
    {
        Socket  socket;
        String  host, msg;
        int     port, ic;
        TestObject  obj;
        OutputStream         out;
        ObjectOutputStream   oos;
        InputStream     is;
        ObjectInputStream   ois;

        logger.debug ( "Start" );
        socket  = null;
        try
        {
            // logger
            // PropertyConfigurator.configure ( args[0] );
            //client  = new Client ();

            host    = "localhost";
            port    = 8000;
            socket  = new Socket ( host, port );
            socket.setKeepAlive ( true );

            //Util.sendToSocket ( socket, 100 );
            //Util.readFromSocket ( socket );
            Util.readFileFromSocket ( socket );

            //Util.sendZipToSocket ( socket, 200 );
            //Util.readZipFromSocket ( socket );

        } catch ( Exception e )
        {
            logger.debug ( "Error", e );
            //e.printStackTrace ();
        } finally{
            socket  = Utils.closeSocket ( socket );
        }
        logger.debug ( "Finish" );
        System.exit ( 0 );
    }

}
