package socket.ping;


import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;


/**
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 16.07.2010 10:58:59
 */
public class PseudoPing
{
    public static void main ( String args[] )
    {
        String host;
        try
        {
            //host = args[0];
            host = "lib.ru";
            System.out.println ( "host = " + host );
            Socket t = new Socket ( host, 7 );
            System.out.println ( "create socket = " + t );
            DataInputStream dis = new DataInputStream ( t.getInputStream() );
            PrintStream ps = new PrintStream ( t.getOutputStream() );
            System.out.println ( "run write" );
            ps.println ( "Hello" );
            System.out.println ( "run read" );
            String str = dis.readLine ();
            if ( str.equals ( "Hello" ) )
                System.out.println ( "Alive!" );
            else
                System.out.println ( "Dead or echo port not responding" );
            t.close ();
        } catch ( Exception e )         {
            e.printStackTrace ();
        }
    }
}
