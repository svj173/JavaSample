package applet;

/*
 * Java SE 5 version.
 * Exercise for the reader: Convert this applet to use SwingWorker
 * and Timer. SwingWorker can be downloaded at:
 * https://swingworker.dev.java.net/
 * SwingWorker must be downloaded for JavaSE 5, but will be included
 * in JavaSE 6.
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.*;

public class TalkClientApplet extends JApplet  implements Runnable, ActionListener
{
    private Socket socket;
    private BufferedWriter os;
    private BufferedReader is;
    private JTextField portField
    ,
    message;
    private JTextArea display;
    private JButton button;
    private JLabel label;
    private int dataPort;
    private boolean trysted;
    private Thread receiveThread;
    private String host;
    private boolean DEBUG = false;

    public void init ()
    {
        //Get the address of the host we came from.
        host = getCodeBase().getHost ();

        //Execute a job on the event-dispatching thread:
        //creating this applet's GUI.
        try
        {
            SwingUtilities.invokeAndWait ( new Runnable()
            {
                public void run ()
                {
                    createGUI ();
                }
            } );
        } catch ( Exception e )
        {
            System.err.println ( "createGUI didn't successfully complete" );
        }
    }

    private void createGUI ()
    {
        JPanel contentPane = new JPanel ( new GridBagLayout () );
        JScrollPane textAreaScroller = new JScrollPane ();
        GridBagConstraints c = new GridBagConstraints ();
        c.insets = new Insets ( 5, 5, 5, 5 );

        message = new JTextField ( "" );
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridwidth = GridBagConstraints.REMAINDER;
        message.addActionListener ( this );
        contentPane.add ( message, c );

        display = new JTextArea ( 5, 40 );
        display.setEditable ( false );
        textAreaScroller.setViewportView ( display );
        c.weighty = 1.0;
        c.fill = GridBagConstraints.BOTH;
        contentPane.add ( textAreaScroller, c );

        label = new JLabel ( "Enter the port (on host " + host
                + ") to send the request to:",
                JLabel.TRAILING );
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridwidth = 1;
        c.weighty = 0.0;
        contentPane.add ( label, c );

        portField = new JTextField ( 6 );
        portField.addActionListener ( this );
        c.weightx = 1.0;
        contentPane.add ( portField, c );

        button = new JButton ( "Connect" );
        button.addActionListener ( this );
        c.weightx = 0.0;
        contentPane.add ( button, c );

        contentPane.setBorder ( BorderFactory.createCompoundBorder (
                BorderFactory.createLineBorder ( Color.black ),
                BorderFactory.createEmptyBorder ( 5, 5, 5, 5 ) ) );
        setContentPane ( contentPane );
    }

    public void start ()
    {
        start ( false );
    }

    private void start ( boolean onEDT )
    {
        if ( DEBUG )
        {
            System.out.println ( "In start() method." );
        }
        if ( receiveThread == null )
        {
            trysted = false;
            os = null;
            is = null;
            socket = null;
            receiveThread = new Thread ( this );
            receiveThread.start ();
            if ( DEBUG )
            {
                System.out.println ( "  Just set everything to null and started thread." );
            }
            enableGUI ( onEDT );

        }
        else if ( DEBUG )
        {
            System.out.println ( "  receiveThread not null! Did nothing!" );
        }
    }

    //Invoked to reenable the GUI.
    private void enableGUI ( boolean onEDT )
    {
        if ( onEDT )
        {
            enableGUI ();
        }
        else
        {
            //Execute a job on the event-dispatching thread:
            //Enabling the GUI.
            try
            {
                SwingUtilities.invokeAndWait ( new Runnable()
                {
                    public void run ()
                    {
                        enableGUI ();
                    }
                } );
            } catch ( Exception e )
            {
            }
        }
    }

    //Assumes it's already on the EDT.
    private void enableGUI ()
    {
        label.setEnabled ( true );
        portField.setEnabled ( true );
        button.setEnabled ( true );
    }

    //Assumes it's already on the EDT.
    private void disableGUI ()
    {
        label.setEnabled ( false );
        portField.setEnabled ( false );
        button.setEnabled ( false );
    }

    public void stop ()
    {
        stop ( false );
    }

    private synchronized void stop ( boolean onEDT )
    {
        if ( DEBUG )
        {
            System.out.println ( "In stop() method." );
        }
        receiveThread = null;
        trysted = false;
        enableGUI ( onEDT );
        notify ();

        try
        { //Close input stream.
            if ( is != null )
            {
                is.close ();
                is = null;
            }
        } catch ( Exception e )
        {
        } //Ignore exceptions.

        try
        { //Close output stream.
            if ( os != null )
            {
                os.close ();
                os = null;
            }
        } catch ( Exception e )
        {
        } //Ignore exceptions.

        try
        { //Close socket.
            if ( socket != null )
            {
                socket.close ();
                socket = null;
            }
        } catch ( Exception e )
        {
        } //Ignore exceptions.
    }

    //As an event listener, this is invoked on the event-dispatching
    //thread, so we can directly manipulate components here.
    public void actionPerformed ( ActionEvent event )
    {
        int port;

        if ( DEBUG )
        {
            System.out.println ( "In actionPerformed() method." );
        }

        if ( receiveThread == null )
        {
            start ( true );
        }

        if ( !trysted )
        {
            //We need to attempt a rendezvous.

            if ( DEBUG )
            {
                System.out.println ( "  trysted = false. "
                        + "About to attempt a rendezvous." );
            }

            //Get the port the user entered...
            try
            {
                port = Integer.parseInt ( portField.getText () );
            } catch ( NumberFormatException e )
            {
                //No integer entered.
                appendToDisplay ( "Please enter an integer below.\n" );
                return;
            }
            if ( DEBUG )
            {
                System.out.println ( "About to attempt rendezvous with port " + port + "." );
            }

            //...and rendezvous with it.
            rendezvous ( port );

        }
        else
        { //We've already rendezvoused. Just send data over.
            if ( DEBUG )
            {
                System.out.println ( "  trysted = true. "
                        + "About to send data." );
            }
            String str = message.getText ();
            message.selectAll ();

            try
            {
                os.write ( str );
                os.newLine ();
                os.flush ();
            } catch ( IOException e )
            {
                appendToDisplay ( "ERROR: Applet couldn't write to socket.\n" );
                appendToDisplay ( "...Disconnecting.\n" );
                stop ( true );
                return;
            } catch ( NullPointerException e )
            {
                appendToDisplay ( "ERROR: No output stream!\n" );
                appendToDisplay ( "...Disconnecting.\n" );
                stop ( true );
                return;
            }
            appendToDisplay ( "SENT TO SERVER: " + str + "\n" );
        }
    }

    private synchronized void waitForTryst ()
    {
        //Wait for notify() call from action().
        try
        {
            wait ();
        } catch ( InterruptedException e )
        {
        }

        if ( DEBUG )
        {
            System.out.println ( "waitForTryst about to return. "
                    + "trysted = " + trysted + "." );
        }

        return;
    }

    public void run ()
    {
        String received = null;

        waitForTryst ();

        //OK, now we can send messages.
        while ( Thread.currentThread () == receiveThread )
        {
            try
            {
                //Wait for data from the server.
                received = is.readLine ();

                //Display it.
                if ( received != null )
                {
                    appendToDisplay ( "RECEIVED FROM SERVER: " + received + "\n", false );
                }
                else
                { //success but no data...
                    System.err.println ( "End of stream?" );
                    return;
                }
            } catch ( IOException e )
            { //Perhaps a temporary problem?
                appendToDisplay ( "NOTE: Couldn't read from socket.\n", false );
                return;
            }
        }
    }

    private void appendToDisplay ( String s, boolean onEDT )
    {
        if ( onEDT )
        {
            appendToDisplay ( s );
        }
        else
        {
            final String newText = s;
            //Execute a job on the event-dispatching thread:
            //creating this applet's GUI.
            try
            {
                SwingUtilities.invokeAndWait ( new Runnable()
                {
                    public void run ()
                    {
                        appendToDisplay ( newText );
                    }
                } );
            } catch ( Exception e )
            {
            }
        }
    }

    //Assumes it IS called from the EDT.
    private void appendToDisplay ( String s )
    {
        display.append ( s );
        display.setCaretPosition ( display.getText ().length () );
    }

    //Invoked by actionPerformed, and thus executing on the EDT.
    private synchronized void rendezvous ( int port )
    {
        //Try to open a socket to the port.
        try
        {
            socket = new Socket ( host, port );
        } catch ( UnknownHostException e )
        {
            appendToDisplay ( "ERROR: Can't find host: " + host + "\n" );
            return;
        } catch ( IOException e )
        {
            appendToDisplay ( "ERROR: Can't open socket on rendezvous port "
                    + port + " (on host " + host + ").\n" );
            return;
        }
        appendToDisplay ( "Successfully opened socket on rendezvous port.\n" );

        //Try to open streams to read and write from the socket.
        try
        {
            os = new BufferedWriter (
                    new OutputStreamWriter ( socket.getOutputStream () ) );
            is = new BufferedReader (
                    new InputStreamReader ( socket.getInputStream () ) );
        } catch ( IOException e )
        {
            appendToDisplay ( "ERROR: Created data socket but can't "
                    + "open stream on it.\n" );
            appendToDisplay ( "...Disconnecting.\n" );
            stop ( true );
            return;
        }
        appendToDisplay ( "Successfully opened I/O streams on rendezvous port.\n" );

        if ( ( os != null ) & ( is != null ) )
        {
            if ( DEBUG )
            {
                System.out.println ( "Successful rendezvous." );
                System.out.println ( "socket = " + socket );
                System.out.println ( "output stream = " + os );
                System.out.println ( "input stream = " + is );
            }
            //Let the main applet thread know we've successfully rendezvoused.
            disableGUI ();
            trysted = true;
            notify ();
        }
        else
        {
            appendToDisplay ( "ERROR: Port is valid but communication failed. "
                    + "Please TRY AGAIN.\n" );
        }
    }

}


