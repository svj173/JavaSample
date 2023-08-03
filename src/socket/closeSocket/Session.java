package socket.closeSocket;


import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;

/**
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 28.08.2014 10:15
 */
public class Session  implements Runnable
{
    private Socket socket;
    InputStream is;
    OutputStream os;
    BufferedInputStream bis;
    ObjectOutputStream out;
    ObjectInputStream in;

    public Session ( Socket socket ) throws IOException
    {
        this.socket = socket;
        this.socket.setSoTimeout ( 3000 );
        os = socket.getOutputStream ();
        is = socket.getInputStream ();
        bis = new BufferedInputStream ( is );
        out = new ObjectOutputStream ( os );
        in = new ObjectInputStream ( bis );
    }

    public void run ()
    {
        try
        {
            //System.out.println("buffered_input_stream.markSupported() = "+bis.markSupported());
            byte b;
            while ( !socket.isClosed () )
            {
                bis.mark ( 1 );
                if ( bis.read () == -1 )
                    break;
                bis.reset ();
                b = in.readByte ();
                if ( b == 0 )
                    System.out.println ( "I hear heartbeat of client..." );
                else
                    System.out.println ( "Received command " + b );
            }
            System.out.println ( "Socket closed by client." );
        } catch ( SocketTimeoutException e )
        {
            System.out.println ( "SocketTimeoutException:" + e.getMessage () );
        } catch ( EOFException e )
        {
            System.out.println ( "EOFException:" + e.getMessage () );
        } catch ( SocketException e )
        {
            System.out.println ( "SocketException:" + e.getMessage () );
        } catch ( Exception e )
        {
            e.printStackTrace ();
        } finally
        {
            try
            {
                socket.close ();
                System.out.println ( "Клиент отключился." );
            } catch ( IOException e )
            {
                System.err.println ( "Не удалось закрыть сокет." );
            }
        }
    }
}
