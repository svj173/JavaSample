package socket.closeSocket;


import java.io.IOException;
import java.net.BindException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 28.08.2014 10:15
 */
public class Server
{
    static private byte[] ipaddres;
    static private int port = 8888;

    public static void main ( String[] args )
    {
        try
        {
            InetAddress addr;
            if ( ipaddres == null )
                addr = InetAddress.getByName ( null );
            else
                addr = InetAddress.getByAddress ( ipaddres );
            ServerSocket ss = new ServerSocket ( port, 100, addr );
            System.out.println ( "Сервер запущен на " + addr + ":" + port );
            try
            {
                while ( true )
                {
                    Socket socket = ss.accept ();
                    System.out.println ( "Подключился клиент." );
                    Thread thread = new Thread ( new Session ( socket ) );
                    thread.run ();
                }
            } finally     {
                ss.close ();
            }
        } catch ( BindException e )
        {
            System.out.println ( "Порт " + port + " занят." );
        } catch ( IOException e )
        {
            System.err.println ( "Порт " + port + " IOException: " + e.getMessage () );
        }
    }

}
