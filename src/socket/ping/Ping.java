package socket.ping;


import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.regex.Pattern;

/**
 * что-то не заработало - тайм-аут на все хосты.
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 16.07.2010 11:04:24
 */
public class Ping
{
    // The default daytime port
    static int DAYTIME_PORT = 13;
    static int ECHO_PORT = 7;

    // The port we'll actually use
    static int port = ECHO_PORT;


    // Representation of a ping target
    //

    static class Target
    {

        InetSocketAddress address;
        SocketChannel channel;
        Exception failure;
        long connectStart;
        long connectFinish = 0;
        boolean shown = false;

        Target ( String host )
        {
            try
            {
                address = new InetSocketAddress ( InetAddress.getByName ( host ), port );
            } catch ( IOException x )
            {
                failure = x;
            }
        }

        void show ()
        {
            String result;
            if ( connectFinish != 0 )
                result = Long.toString ( connectFinish - connectStart ) + "ms";
            else if ( failure != null )
                result = failure.toString ();
            else
                result = "Timed out";
            System.out.println ( address + " : " + result );
            shown = true;
        }

    }


    // Thread for printing targets as they're heard from
    //

    static class Printer
            extends Thread
    {
        LinkedList pending = new LinkedList ();

        Printer ()
        {
            setName ( "Printer" );
            setDaemon ( true );
        }

        void add ( Target t )
        {
            synchronized ( pending )
            {
                pending.add ( t );
                pending.notify ();
            }
        }

        public void run ()
        {
            try
            {
                for (; ; )
                {
                    Target t = null;
                    synchronized ( pending )
                    {
                        while ( pending.size () == 0 )
                            pending.wait ();
                        t = ( Target ) pending.removeFirst ();
                    }
                    t.show ();
                }
            } catch ( InterruptedException x )
            {
                return;
            }
        }

    }


    // Thread for connecting to all targets in parallel via a single selector
    //

    static class Connector
            extends Thread
    {
        Selector sel;
        Printer printer;

        // List of pending targets.  We use this list because if we try to
        // register a channel with the selector while the connector thread is
        // blocked in the selector then we will block.
        //
        LinkedList pending = new LinkedList ();

        Connector ( Printer pr ) throws IOException
        {
            printer = pr;
            sel = Selector.open ();
            setName ( "Connector" );
        }

        // Initiate a connection sequence to the given target and add the
        // target to the pending-target list
        //

        void add ( Target t )
        {
            SocketChannel sc = null;
            try
            {

                // Open the channel, set it to non-blocking, initiate connect
                sc = SocketChannel.open ();
                sc.configureBlocking ( false );

                boolean connected = sc.connect ( t.address );

                // Record the time we started
                t.channel = sc;
                t.connectStart = System.currentTimeMillis ();

                if ( connected )
                {
                    t.connectFinish = t.connectStart;
                    sc.close ();
                    printer.add ( t );
                }
                else
                {
                    // Add the new channel to the pending list
                    synchronized ( pending )
                    {
                        pending.add ( t );
                    }

                    // Nudge the selector so that it will process the pending list
                    sel.wakeup ();
                }
            } catch ( IOException x )
            {
                if ( sc != null )
                {
                    try
                    {
                        sc.close ();
                    } catch ( IOException xx )
                    {
                    }
                }
                t.failure = x;
                printer.add ( t );
            }
        }

        // Process any targets in the pending list
        //

        void processPendingTargets () throws IOException
        {
            synchronized ( pending )
            {
                while ( pending.size () > 0 )
                {
                    Target t = ( Target ) pending.removeFirst ();
                    try
                    {

                        // Register the channel with the selector, indicating
                        // interest in connection completion and attaching the
                        // target object so that we can get the target back
                        // after the key is added to the selector's
                        // selected-key set
                        t.channel.register ( sel, SelectionKey.OP_CONNECT, t );

                    } catch ( IOException x )
                    {

                        // Something went wrong, so close the channel and
                        // record the failure
                        t.channel.close ();
                        t.failure = x;
                        printer.add ( t );

                    }

                }
            }
        }

        // Process keys that have become selected
        //

        void processSelectedKeys () throws IOException
        {
            for ( Iterator i = sel.selectedKeys ().iterator (); i.hasNext (); )
            {

                // Retrieve the next key and remove it from the set
                SelectionKey sk = ( SelectionKey ) i.next ();
                i.remove ();

                // Retrieve the target and the channel
                Target t = ( Target ) sk.attachment ();
                SocketChannel sc = ( SocketChannel ) sk.channel ();

                // Attempt to complete the connection sequence
                try
                {
                    if ( sc.finishConnect () )
                    {
                        sk.cancel ();
                        t.connectFinish = System.currentTimeMillis ();
                        sc.close ();
                        printer.add ( t );
                    }
                } catch ( IOException x )
                {
                    sc.close ();
                    t.failure = x;
                    printer.add ( t );
                }
            }
        }

        volatile boolean shutdown = false;

        // Invoked by the main thread when it's time to shut down
        //

        void shutdown ()
        {
            shutdown = true;
            sel.wakeup ();
        }

        // Connector loop
        //

        public void run ()
        {
            for (; ; )
            {
                try
                {
                    int n = sel.select ();
                    if ( n > 0 )
                        processSelectedKeys ();
                    processPendingTargets ();
                    if ( shutdown )
                    {
                        sel.close ();
                        return;
                    }
                } catch ( IOException x )
                {
                    x.printStackTrace ();
                }
            }
        }

    }


    public static void main ( String[] args )    throws InterruptedException, IOException
    {
        if ( args.length < 1 )
        {
            System.err.println ( "Usage: java Ping [port] host..." );
            return;
        }
        int firstArg = 0;

        // Проверяем чтобы аргумент с номером порта состоял только из цифр
        if ( Pattern.matches ( "[0-9]+", args[ 0 ] ) )
        {
            port = Integer.parseInt ( args[ 0 ] );
            firstArg = 1;
        }

        // Create the threads and start them up
        Printer printer = new Printer ();
        printer.start ();
        Connector connector = new Connector ( printer );
        connector.start ();

        // Create the targets and add them to the connector
        LinkedList targets = new LinkedList ();
        for ( int i = firstArg; i < args.length; i++ )
        {
            Target t = new Target ( args[ i ] );
            targets.add ( t );
            connector.add ( t );
        }

        // Wait for everything to finish
        Thread.sleep ( 2000 );
        connector.shutdown ();
        connector.join ();

        // Print status of targets that have not yet been shown
        for ( Iterator i = targets.iterator (); i.hasNext (); )
        {
            Target t = ( Target ) i.next ();
            if ( !t.shown )
                t.show ();
        }
    }

}
