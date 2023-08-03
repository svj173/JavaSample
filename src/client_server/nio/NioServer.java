package client_server.nio;



import java.net.InetSocketAddress;
import java.nio.channels.*;
import java.io.*;
import java.nio.ByteBuffer;
import java.util.*;

/**
 * тестирвоание соединения клиент-сервер  - сокетного канала.
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 14.02.2012 9:42:07
 */
public class NioServer extends Thread
{
    private static int selectCount = 0;

    private Selector selector;


    public static void main ( String[] args ) throws Exception
    {
        NioServer ns = new NioServer ( 5000 );
        ns.start ();
    }

    public NioServer ( int port ) throws Exception
    {
        selector = Selector.open ();
        registerServerSocketChannel ( port );
    }

    private void registerServerSocketChannel ( int port ) throws Exception
    {
        // Create  non-blocking server socket
        ServerSocketChannel channel = ServerSocketChannel.open ();
        channel.configureBlocking ( false );
        channel.socket ().bind ( new InetSocketAddress ( port ) );

        // Register channel with selector
        int interestOps = SelectionKey.OP_ACCEPT;
        String attachment = "Acceptor";//name
        channel.register ( selector, interestOps, attachment );
    }

    private void registerSocketChannel ( SocketChannel channel ) throws Exception
    {
        channel.configureBlocking ( false );

        // Register channel with selector
        int interestOps = SelectionKey.OP_READ | SelectionKey.OP_WRITE;
        String attachment = "Client";//name
        channel.register ( selector, interestOps, attachment );
    }

    public void run ()
    {
        try
        {
            while ( true )
            {

                Set<SelectionKey> keys = selector.keys ();
                System.out.println ( "\n------------- selector has " + keys.size () + " keys,  selectCount=" + ( selectCount++ ) );

                /** select active channels   */

                int selected = selector.select ();

                if ( selected > 0 )
                {

                    // Get iterator of selection keys
                    Iterator it = selector.selectedKeys ().iterator ();

                    // Process each key
                    while ( it.hasNext () )
                    {

                        // Get next selection key
                        SelectionKey selKey = ( SelectionKey ) it.next ();

                        //important to remove selected key!
                        it.remove ();

                        String name = ( String ) selKey.attachment ();
                        System.out.println ( "\nselected: " + name + ", isValid:" + selKey.isValid () );

                        if ( selKey.isValid () && selKey.isAcceptable () )
                        {
                            accept ( selKey, selector );
                        }
                        else
                        {

                            if ( selKey.isValid () && selKey.isReadable () )
                            {
                                System.out.println ( name + " is readable" );
                                read ( selKey, selector );
                            }

                            if ( selKey.isValid () && selKey.isWritable () )
                            {
                                System.out.println ( name + " is writable" );
                                write ( selKey, selector );
                            }
                        }

                    }//while
                }//if

                //only for test
                Thread.currentThread().sleep ( 500 );

            }//while

        } catch ( Exception e )         {
            e.printStackTrace ();
        }
    }//run

    public void accept ( SelectionKey selKey, Selector selector )
    {

        try
        {
            // Get channel with connection request
            ServerSocketChannel serverSocketChannel = ( ServerSocketChannel ) selKey.channel ();

            // Accept all pending connection requests.
            SocketChannel channel = null;
            while ( ( channel = serverSocketChannel.accept () ) != null )
            {
                registerSocketChannel ( channel );
                System.out.println ( "channel accepted" );
            }

            System.out.println ( "there are no pending connections" );
        }
        catch ( Exception ex )
        {
            ex.printStackTrace ();
        }
    }

    void read ( SelectionKey selKey, Selector selector ) throws IOException
    {
        SocketChannel channel = ( SocketChannel ) selKey.channel ();

        ByteBuffer b = ByteBuffer.allocate ( 1 );
        channel.read ( b );
        int p = b.position ();
        System.out.println ( "READ " + p + " BYTES, channel : isOpen=" + channel.isOpen () + ", isConnected=" + channel.isConnected () );
    }

    void write ( SelectionKey selKey, Selector selector ) throws IOException
    {
        SocketChannel channel = ( SocketChannel ) selKey.channel ();

        ByteBuffer b = ByteBuffer.allocate ( 1 );
        b.put ( ( byte ) 10 );
        b.flip ();
        int w = channel.write ( b );
        System.out.println ( "WRITE " + w + " BYTES, channel : isOpen=" + channel.isOpen () + ", isConnected=" + channel.isConnected () );
    }

}
