package client_server.nio;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;


/**
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 14.02.2012 9:42:42
 */
public class NioClient
{

    public static void main ( String[] args ) throws IOException, InterruptedException
    {
        System.out.println ( "client start" );
        Socket s = null;

        s = new Socket ();
        s.connect ( new InetSocketAddress ( "127.0.0.1", 5000 ) );
        System.out.println ( "client isClosed " + s.isClosed () + ", isConnected " + s.isConnected () );


        OutputStream os = s.getOutputStream ();
        InputStream is = s.getInputStream ();

        int b = 10;

        System.out.println ( "client writes " + b );
        os.write ( b );
        os.flush ();

        Thread.currentThread ().sleep ( 1000 );

        b = is.read ();
        System.out.println ( "client read " + b );


        System.out.println ( "close client socket" );
        os.close ();
        is.close ();
        s.close ();

        System.out.println ( "client isClosed " + s.isClosed () + ", isConnected " + s.isConnected () );
        System.out.println ( "client exit" );
    }
    
}
