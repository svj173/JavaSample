package socket.s1;

/**
 * <BR>
 * <BR>
 * <BR> User: svj
 * <BR> Date: 22.05.2006
 * <BR> Time: 18:05:46
 */

import java.net.*;
import java.io.*;

/**
 * This small class simply provides a usable main that
 * can be used to test some kinds of datagram servers.
 * Basically, this program expects to get a host name
 * and port number on the command line.  If the port
 * number is not supplied, it defaults to 7.  The
 * program reads lines from standard input, and packages
 * up each line as a datagram packet.  After sending a
 * packet, it waits for and prints the response.  This
 * program exits on end-of-file.
 */
public class DatagramTest
{
    public static void main ( String args[] )
    {
        if ( args.length < 1 )
        {
            System.err.println ( "Usage: java DatagramTest host [port] <file" );
            System.exit ( 0 );
        }

        String host = args[0];
        int port = 7;
        if ( args.length > 1 )
        {
            try
            {
                port = Integer.parseInt ( args[1] );
            } catch ( NumberFormatException nfe )
            {
            }
        }

        BufferedReader br;
        br = new BufferedReader ( new InputStreamReader ( System.in ) );

        InetAddress addr = null;
        try
        {
            addr = InetAddress.getByName ( host );
        }
        catch ( UnknownHostException uhe )
        {
            System.err.println ( "No such host: " + host );
            System.exit ( 0 );
        }

        /*
      * The approach used here, send then immediately
      * receive, will usually work, but is not totally
      * reliable.  In a very fast network, or with loopback,
      * it is possible for the response to arrive before
      * Java manages to crawl from send to receive.
      */
        DatagramSocket ds = null;
        DatagramPacket rec, send;
        String line;
        rec = new DatagramPacket ( new byte[65530], 65530 );
        try
        {
            ds = new DatagramSocket ();
            while ( ( line = br.readLine () ) != null )
            {
                byte [] buf = line.getBytes ();
                if ( buf.length > 0 )
                {
                    send = new DatagramPacket ( buf, buf.length, addr, port );
                    rec.setLength ( 65530 );
                    ds.send ( send );
                    ds.receive ( rec );
                    line = new String ( rec.getData (), 0, rec.getLength () );
                    System.out.println ( line );
                }
            }
        }
        catch ( IOException ie )
        {
            System.err.println ( "IO exception in datagram test: " + ie );
        }
        finally
        {
            if ( ds != null )
            {
                try
                {
                    ds.close ();
                } catch ( Exception e )
                {
                }
            }
        }
    }
}
