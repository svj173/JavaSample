package socket.ping;

import java.io.*;
import java.net.*;

/**
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 16.07.2010 11:01:26
 */
public class ReachableTest
{
    public static void main ( String args[] )
    {
        String host = null;
        long start, end;
        try
        {
            //host = "web.mit.edu";   // 18.9.22.69
            //host = "192.168.1.55";        // ok
            //host = "192.168.1.51";
            host = "192.168.1.101";        // ok
            //host = "192.168.0.215";
            InetAddress address = InetAddress.getByName ( host );
            System.out.println ( "Name: " + address.getHostName() );
            System.out.println ( "Addr: " + address.getHostAddress() );


            start = System.currentTimeMillis ();
            System.out.println ( "Reach: " + address.isReachable ( 3000 ) );
            end = System.currentTimeMillis ();

            System.out.println ( "Time (msec): " + (end-start) );

            /*
String host = "172.16.0.2"
int timeOut = 3000; // I recommend 3 seconds at least
boolean status = InetAddress.getByName(host).isReachable(timeOut)
             */
        //} catch ( InterruptedException e )         {
        //    System.err.println ( "IO error - " + host );
        } catch ( UnknownHostException e )        {
            System.err.println ( "Unknown Host - " + host );
        } catch ( IOException e )         {
            System.err.println ( "IO error - " + host );
        } catch ( Exception e )         {
            System.err.println ( "Error - " + host );
        }
    }
    
}
