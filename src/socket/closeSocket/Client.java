package socket.closeSocket;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NoRouteToHostException;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

/**
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 28.08.2014 10:17
 */
public class Client
{
    static private byte[] ipAddress = new byte[] { ( byte ) 127, ( byte ) 0, ( byte ) 0, ( byte ) 1 };
    static private int port = 8888;
    static private int connect_timeout = 3500;

    static private ObjectOutputStream out;
    static private ObjectInputStream in;
    static private volatile Socket socket;

    public static void main ( String[] args )
    {
        try
        {
            InetAddress addr = InetAddress.getByAddress ( ipAddress );
            InetSocketAddress isa = new InetSocketAddress ( addr, port );
            socket = new Socket ();
            socket.connect ( isa, connect_timeout );
            in = new ObjectInputStream ( socket.getInputStream () );
            out = new ObjectOutputStream ( socket.getOutputStream () );
            // create heartbeat
            Thread t = new Thread ( new Runnable ()
            {
                @Override
                public void run ()
                {
                    while ( !socket.isClosed () )
                    {
                        try
                        {
                            out.writeByte ( 0 );
                            out.flush ();
                        } catch ( IOException e )
                        {
                            e.printStackTrace ();
                            break;
                        }
                        try
                        {
                            Thread.sleep ( 2000 );
                        } catch ( InterruptedException e )
                        {
                            e.printStackTrace ();
                            break;
                        }
                    }
                    System.out.println ( "HeartBeat stopped." );
                }
            } );
            // launch heartbeat
            t.start ();
            System.out.println ( "Ожидание 7сек" );
            try
            {
                Thread.sleep ( 7000 );
            } catch ( InterruptedException e )
            {
                e.printStackTrace ();
            }
            System.out.println ( "Отправляю байт 7" );
            out.writeByte ( 7 );
            out.flush ();

            System.out.println ( "Ожидание 5сек" );

            try
            {
                Thread.sleep ( 5005 );
            } catch ( InterruptedException e )
            {
                e.printStackTrace ();
            }
            System.out.println ( "Клиентская программа завершена." );
            socket.close ();
            System.out.println ( "Socket closed." );
        } catch ( ConnectException e )
        {
            System.out.println ( "Ошибка: Сервер в оффлайне." );
        } catch ( NoRouteToHostException e )
        {
            System.out.println ( "Ошибка: Не удалось подключиться к серверу." );
        } catch ( SocketException e )
        {
            System.out.println ( "Ошибка: Не удалось подключиться к серверу." );
        } catch ( SocketTimeoutException e )
        {
            System.out.println ( "Ошибка: Таймаут соединения." );
        } catch ( UnknownHostException e )
        {
            System.out.println ( "Ошибка: Hostname can not be resolved." );
        } catch ( IOException e )
        {
            System.out.println ( "Ошибка: IOException: " + e.getMessage () );
        }
    }
}
