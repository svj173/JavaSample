package socket.send;

import com.svj.utils.Utils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.AccessControlException;

/**
 * <BR>
 * <BR>
 * <BR> User: svj
 * <BR> Date: 16.06.2006
 * <BR> Time: 10:59:37
 */
public class Server  extends Thread
{
    private static Logger logger = LogManager.getFormatterLogger ( Server.class );
    private ServerSocket serverSocket;
    /* The port number on which we listen for input requests from Merchant. */
    private int     port;

    private boolean started = false;

    /* Timeout value on the incoming connection. Note : a value of 0 means no timeout.*/
    private int connectionTimeout = 60000;


    public Server ( String processName ) throws Exception
    {
        logger.debug ( "Start" );
        setName ( processName );
        started     = true;
        logger.debug ( " Finish" );
    }

    public void init ( int timeout, int port )
            throws Exception
    {
        logger.debug ( "Start" );
        this.port           = port;
        connectionTimeout   = timeout;

        // Simple connect
        logger.info ( "Create PLAIN server socket." );
        serverSocket = new ServerSocket ( port );
        logger.debug ( "Finish" );

    }

    public void run ()
    {
        Socket          socket;

        // Loop until we receive a shutdown command
        socket = null;
        while ( started )
        {
            logger.debug ( "Start listen incoming port." );
            // Accept the next incoming connection from the server socket
            socket  = Utils.closeSocket ( socket );
            try
            {
                logger.debug ( "Wait socket." );
                socket = serverSocket.accept ();
                logger.debug ( "Get new socket." );
                if ( connectionTimeout > 0 )
                {
                    socket.setSoTimeout ( connectionTimeout );
                }
                socket.setTcpNoDelay ( true );
                socket.setKeepAlive ( true );

                logger.debug ( "InetAddress = " + socket.getInetAddress () );
                logger.debug ( "RemoteSocketAddress = " + socket.getRemoteSocketAddress () );

                Util.sendFileToSocket ( socket, 0 );
                //Util.sendToSocket ( socket, 0 );
                //Util.readFromSocket ( socket );

                //Util.sendZipToSocket ( socket, 0 );
                //Util.readZipFromSocket ( socket );

            } catch ( AccessControlException ace )
            {
                logger.error ( "Socket accept security exception" + ace.getMessage (), ace );
                continue;
            } catch ( IOException ioe )
            {
                logger.error ( "IOException. Reconnect server.", ioe );
                try
                {
                    serverSocket.close ();
                    serverSocket = new ServerSocket ( port );
                } catch ( IOException ioe1 )
                {
                    logger.error ( "Socket reopen, io problem: " + ioe1.getMessage (), ioe1 );
                    break;
                }
                continue;
            } catch ( Throwable te )
            {
                logger.error ( "Big error !!!", te );
            }

            socket  = Utils.closeSocket ( socket );
            logger.info ( "Listener is free and waiting new socket." );
        }

    }

    public static void main ( String[] args )
    {
        Server  server;

        try
        {
            // logger
            // PropertyConfigurator.configure ( args[0] );

            server  = new Server ( "Server" );
            server.init ( 60000, 8000 );
            server.start();
            server.join ();
        } catch ( Exception e )
        {
            e.printStackTrace ();
        }
        logger.debug ( "Finish" );
        System.exit ( 0 );
    }

}
