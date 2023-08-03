package swing.split.multi.examples;


import swing.split.multi.MultiSplitLayout;
import swing.split.multi.MultiSplitPane;

import javax.swing.*;
import java.awt.*;
import java.beans.*;
import java.io.File;

/**
 * @author Hans Muller (Hans.Muller@Sun.COM)
 */
public class Example4 extends Example
{
    MultiSplitPane multiSplitPane = null;
    File file = null;

    /* By default, if an exception occurs in XMLEncoder.writeObject(),
     * or XMLDecoder.readObject(), an error message is printed and the
     * system carries on.  We punt instead.
     */
    private static class AbortExceptionListener implements ExceptionListener
    {
        public void exceptionThrown ( Exception e )
        {
            throw new Error ( e );
        }
    }

    protected void initialize ( String[] ignoreArgs )
    {
        super.initialize ( ignoreArgs );

        multiSplitPane = new MultiSplitPane ();
        MultiSplitLayout mspLayout = multiSplitPane.getMultiSplitLayout ();

        MultiSplitLayout.Node model = null;
        XMLDecoder d = null;
        try
        {
            d = new XMLDecoder ( openResourceInput ( "Example4.xml" ) );
            d.setExceptionListener ( new AbortExceptionListener () );
            model = ( MultiSplitLayout.Node ) ( d.readObject () );
        } catch ( Throwable ignore )
        {
        } finally
        {
            if ( d != null )
            {
                d.close ();
            }
        }

        if ( model == null )
        {
            String layoutDef = "(COLUMN (ROW weight=1.0 left (COLUMN middle.top middle middle.bottom) right) bottom)";
            model = MultiSplitLayout.parseModel ( layoutDef );
        }
        else
        {
            mspLayout.setFloatingDividers ( false );

            /* This is just a hack.  It fails to restore
            * the mainFrame to its original location and
            * we probably don't really want to change the
            * multiSplitPane's preferred size permanently.
            * A general solution must also contend with
            * having the mainFrame restored on a different
            * display configuration as well as enabling the
            * developer to constrain the initial position
            * of the mainFrame, e.g. "center it".
            */
            multiSplitPane.setPreferredSize ( model.getBounds ().getSize () );
        }

        mspLayout.setModel ( model );
        multiSplitPane.add ( new JButton ( "Left Column" ), "left" );
        multiSplitPane.add ( new JButton ( "Right Column" ), "right" );
        multiSplitPane.add ( new JButton ( "Bottom Row" ), "bottom" );
        multiSplitPane.add ( new JButton ( "Middle Column Top" ), "middle.top" );
        multiSplitPane.add ( new JButton ( "Middle" ), "middle" );
        multiSplitPane.add ( new JButton ( "Middle Bottom" ), "middle.bottom" );

        Container cp = mainFrame.getContentPane ();
        cp.add ( multiSplitPane, BorderLayout.CENTER );
    }

    protected void quit ()
    {
        super.quit ();
        XMLEncoder e = null;
        try
        {
            e = new XMLEncoder ( openResourceOutput ( "Example4.xml" ) );
            e.setPersistenceDelegate ( Rectangle.class, new RectanglePD () );
            e.setExceptionListener ( new AbortExceptionListener () );
            MultiSplitLayout.Node model = multiSplitPane.getMultiSplitLayout ().getModel ();
            e.writeObject ( model );
        } catch ( Exception ignore )
        {
        } finally
        {
            if ( e != null )
            {
                e.close ();
            }
        }
    }

    /* There are some (old) Java classes that aren't proper beans.  Rectangle
     * is one of these.  When running within the secure sandbox, writing a 
     * Rectangle with XMLEncoder causes a security exception because 
     * DefaultPersistenceDelegate calls Field.setAccessible(true) to gain
     * access to private fields.  This is a workaround for that problem.
     * A bug has been filed, see http://monaco.sfbay/detail.jsf?cr=4741757  
     */
    private static class RectanglePD extends DefaultPersistenceDelegate
    {
        public RectanglePD ()
        {
            super ( new String[] { "x", "y", "width", "height" } );
        }

        protected Expression instantiate ( Object oldInstance, Encoder out )
        {
            Rectangle oldR = ( Rectangle ) oldInstance;
            Object[] constructorArgs = new Object[] {
                    oldR.x, oldR.y, oldR.width, oldR.height
            };
            return new Expression ( oldInstance, oldInstance.getClass (), "new", constructorArgs );
        }
    }

    public static void main ( String[] args )
    {
        launch ( Example4.class, args );
    }
}
