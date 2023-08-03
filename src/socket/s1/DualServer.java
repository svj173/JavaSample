package socket.s1;

import java.net.*;
import java.io.*;

/**
 * This class, and its several inner classes, provide a simple
 * echo server for TCP and UDP.
 */
public class DualServer
{
    protected Thread tcpThread;
    protected Thread udpThread;

    /**
     * Default port on which to provide TCP and UDP
     * echo services.
     * This is not final, so a caller could change it
     * to provide echo services on a different port.
     */
    public static int ECHO_PORT = 7;

    /**
     * Create a DualServer object to offer echo service
     * on TCP and UDP ports on the default IP address.
     */
    public DualServer ()
    {
        try
        {
            tcpThread = new StreamEchoService ( ECHO_PORT );
        } catch ( IOException ie )
        {
            System.err.println ( "Couldn't start TCP echo service: " + ie );
        }
        try
        {
            udpThread = new DatagramEchoService ( ECHO_PORT );
        } catch ( IOException ie )
        {
            System.err.println ( "Couldn't start UDP echo service: " + ie );
        }
    }

    /**
     * Start up this echo service.
     */
    public void start ()
    {
        tcpThread.start ();
        udpThread.start ();
        return;
    }


    static class DatagramEchoService extends Thread
    {
        DatagramSocket ds;

        DatagramEchoService ( int port )
                throws IOException
        {
            super ( "datagram service" );
            ds = new DatagramSocket ( port );
        }

        private static final int LEN = 65530;

        public void run ()
        {
            DatagramPacket dp;
            dp = new DatagramPacket ( new byte[LEN], LEN );
            while ( true )
            {
                try
                {
                    dp.setLength ( LEN );
                    ds.receive ( dp );
                    ds.send ( dp );
                }
                catch ( IOException e )
                {
                    System.err.println ( "Exception on UDP service: " + e );
                }
            }
        }
    }

    static class StreamEchoServer extends Thread
    {
        private Socket s;

        public StreamEchoServer ( Socket cs )
        {
            super ( "server" );
            s = cs;
        }

        public void run ()
        {
            InputStream is = null;
            OutputStream os = null;
            try
            {
                is = s.getInputStream ();
                os = s.getOutputStream ();
                byte [] buf = new byte[1024];
                int cc;
                do
                {
                    cc = is.read ( buf );
                    if ( cc > 0 ) os.write ( buf, 0, cc );
                }
                while ( cc >= 0 );
            }
            catch ( IOException ie )
            {
            }
            finally
            {
                try
                {
                    is.close ();
                } catch ( IOException ie )
                {
                }
                try
                {
                    os.close ();
                } catch ( IOException ie )
                {
                }
                try
                {
                    s.close ();
                } catch ( IOException ie )
                {
                }
            }
        }
    }

    static class StreamEchoService extends Thread
    {
        ServerSocket ss;

        StreamEchoService ( int port )
                throws IOException
        {
            super ( "stream service" );
            ss = new ServerSocket ( port );
        }

        public void run ()
        {
            Socket cs;
            while ( true )
            {
                try
                {
                    cs = ss.accept ();
                    ( new StreamEchoServer ( cs ) ).start ();
                }
                catch ( IOException ie )
                {
                    break;
                }
            }
        }
    }

    /**
     * Test main - test out the echo server on its
     * default port, or on a port specified by args[0].
     */
    public static void main ( String args[] )
    {
        if ( args.length > 0 )
        {
            try
            {
                int prt = Integer.parseInt ( args[0] );
                if ( prt > 0 && prt < 65535 )
                {
                    ECHO_PORT = prt;
                }
            }
            catch ( Exception e )
            {
            }
        }
        System.out.println ( "Starting echo service on port " + ECHO_PORT );
        DualServer ds = new DualServer ();
        ds.start ();
        return;
    }
}