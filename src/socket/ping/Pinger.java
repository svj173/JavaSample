package socket.ping;


import java.io.*;

import java.net.*;

/**
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 16.07.2010 10:47:12
 */
public class Pinger
{
    // byte[] addr1 = new byte[]{(byte)192,(byte)168,(byte)2,(byte)5};

    public long testDBConn ( byte[] addr1, int port, int timeoutMs )
    {
        //pass in a byte array with the ipv4 address, the port & the max time out required
        long start = -1; //default check value
        long end = -1; //default check value
        long total = -1; // default for bad connection

        //make an unbound socket
        Socket theSock = new Socket ();

        try
        {
            InetAddress addr = InetAddress.getByAddress ( addr1 );

            SocketAddress sockaddr = new InetSocketAddress ( addr, port );

            // Create the socket with a timeout
            //when a timeout occurs, we will get timout exp.
            //also time our connection this gets very close to the real time
            start = System.currentTimeMillis ();
            theSock.connect ( sockaddr, timeoutMs );
            end = System.currentTimeMillis ();

            /*
            // InetAddress. use ECHO port (7)
    public boolean isReachable(int timeout) throws IOException {
	return isReachable(null, 0 , timeout);
    }
             */
        } catch ( UnknownHostException e )        {
            start = -1;
            end = -1;
        } catch ( SocketTimeoutException e )        {
            start = -1;
            end = -1;
        } catch ( IOException e )        {
            start = -1;
            end = -1;
        } finally        {
            if ( theSock != null )
            {
                try
                {
                    theSock.close ();
                } catch ( IOException e )
                {
                }
            }

            if ( ( start != -1 ) && ( end != -1 ) )
            {
                total = end - start;
            }
        }

        return total; //returns -1 if timeout
    }

    public static void main ( String[] args )
    {
         //byte[] addr = new byte[]{(byte)192,(byte)168,(byte)1,(byte)51};
         byte[] addr = new byte[]{(byte)192,(byte)168,(byte)0,(byte)215};

        Pinger pinger = new Pinger();

        long result = pinger.testDBConn ( addr, 7, 5000 );
        System.out.println ( " result = " + result );
    }
}
