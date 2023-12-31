package comm.ParallelBlackBox;

import java.lang.Thread;
import java.io.IOException;
import java.awt.Panel;
import java.awt.Label;
import java.awt.TextArea;
import java.awt.Checkbox;
import java.awt.Color;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.TextListener;
import java.awt.event.TextEvent;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import javax.comm.ParallelPort;

/**
 * Class declaration
 *
 * @author
 * @version 1.8, 05/04/00
 */
public class Transmitter extends Panel implements TextListener, ItemListener,
        Runnable
{
    private Panel p
    ,
    p1;
    private TextArea text;
    private Checkbox auto;
    private ByteStatistics counter;
    private ParallelPortDisplay owner;
    private Thread thr;
    private Color onColor
    ,
    offColor;
    private boolean first;
    static String testString =
            "ABCDEFGHIJKLMNOPQRSTUVWXYZ\r\nabcdefghijklmnopqrstuvwxyz\r\n1234567890\r\n";

    /**
     * Constructor declaration
     *
     * @param owner
     * @param rows
     * @param cols
     * @see
     */
    public Transmitter ( ParallelPortDisplay owner, int rows, int cols )
    {
        super ();

        this.first = true;
        this.owner = owner;

        this.setLayout ( new BorderLayout () );

        p = new Panel ();

        p.setLayout ( new FlowLayout () );

        p1 = new Panel ();

        p1.setLayout ( new BorderLayout () );
        p1.add ( "West", new Label ( "Auto Transmit" ) );

        auto = new Checkbox ();

        auto.addItemListener ( this );
        p1.add ( "East", auto );
        p.add ( p1 );
        this.add ( "North", p );

        this.text = new TextArea ( rows, cols );

        this.text.append ( "Type here" );
        this.text.addTextListener ( this );
        this.add ( "Center", text );

        this.counter = new ByteStatistics ( "Bytes Sent", 10, owner.port,
                false );

        this.add ( "South", this.counter );

        this.thr = null;
        this.onColor = Color.green;
        this.offColor = Color.black;
    }

    /**
     * Method declaration
     *
     * @param port
     * @see
     */
    public void setPort ( ParallelPort port )
    {
        this.counter.setPort ( port );
    }

    /**
     * Method declaration
     *
     * @see
     */
    public void showValues ()
    {
        this.counter.showValues ();
    }

    /**
     * Method declaration
     *
     * @see
     */
    public void clearValues ()
    {
        this.counter.clearValues ();
    }

    /**
     * Method declaration
     *
     * @param val
     * @see
     */
    public void setBitsPerCharacter ( int val )
    {
        this.counter.setBitsPerCharacter ( val );
    }

    /*
    * Handler for transmit text area events
    */

    /**
     * Method declaration
     *
     * @param ev
     * @see
     */
    public void textValueChanged ( TextEvent ev )
    {
        if ( first && ( this.text.getCaretPosition () > 0 ) ) {
            first = false;

            this.text.replaceRange ( "", 0, this.text.getCaretPosition () - 1 );
        }

        if ( !first ) {
            this.sendData ();
        }
    }

    /**
     * Method declaration
     *
     * @see
     */
    public void run ()
    {
        this.sendData ();
    }

    /**
     * Method declaration
     *
     * @param str
     * @see
     */
    public void sendString ( String str )
    {
        int count;

        count = str.length ();

        if ( count > 0 ) {
            this.owner.ctlSigs.BE = false;

            try {
                this.owner.out.write ( str.getBytes () );
                this.counter.incrementValue ( ( long ) count );
                this.owner.ctlSigs.showValues ();
                this.owner.ctlSigs.showErrorValues ();
            } catch ( IOException ex ) {
                if ( this.owner.open ) {
                    System.out.println ( owner.port.getName ()
                            + ": Cannot write to output stream" );
                    this.auto.setState ( false );
                }
            }
        }
    }

    /**
     * Method declaration
     *
     * @see
     */
    private void sendData ()
    {
        String str;

        if ( this.owner.open && this.auto.getState () ) {
            while ( this.owner.open && this.auto.getState () ) {
                sendString ( testString );
            }
        }
        else {
            str = this.text.getText ();

            sendString ( str );
            this.text.setText ( "" );
        }
    }

    /*
    * Handler for checkbox events
    */

    /**
     * Method declaration
     *
     * @param ev
     * @see
     */
    public void itemStateChanged ( ItemEvent ev )
    {
        if ( this.auto.getState () && ( thr == null ) && this.owner.open ) {
            this.auto.setForeground ( this.onColor );
            startTransmit ();
        }
        else {
            stopTransmit ();
        }
    }

    /**
     * Method declaration
     *
     * @see
     */
    private void startTransmit ()
    {
        if ( this.owner.open && ( thr == null ) ) {
            counter.resetRate ();

            thr = new Thread ( this, "Xmt " + owner.port.getName () );

            thr.start ();
        }
    }

    /**
     * Method declaration
     *
     * @see
     */
    public void stopTransmit ()
    {
        thr = null;

        this.auto.setState ( false );
        this.auto.setForeground ( this.offColor );
    }

}




