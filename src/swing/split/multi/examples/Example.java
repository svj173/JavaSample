package swing.split.multi.examples;

//import javax.jnlp.BasicService;
//import javax.jnlp.FileContents;
//import javax.jnlp.PersistenceService;
//import javax.jnlp.ServiceManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;

/**
 * Требует библиотеки javax.jnlp
 *
 * @author Hans Muller (Hans.Muller@Sun.COM)
 */
public class Example
{
    protected JFrame mainFrame;

    private File appLocalHome ()
    {
        return new File ( System.getProperty ( "user.home" ), ".multisplitpane" );
    }

    protected InputStream openResourceInput ( String name ) throws IOException
    {
        /*
        try
        {
            BasicService bs = ( BasicService ) ServiceManager.lookup ( "javax.jnlp.BasicService" );
            PersistenceService ps = ( PersistenceService ) ServiceManager.lookup ( "javax.jnlp.PersistenceService" );
            URL fileURL = new URL ( bs.getCodeBase (), name );
            return new BufferedInputStream ( ps.get ( fileURL ).getInputStream () );
        } catch ( Exception e )
        {
            // TBD log and error
        }
        */
        File file = new File ( appLocalHome (), name );
        return new BufferedInputStream ( new FileInputStream ( file ) );
    }

    protected OutputStream openResourceOutput ( String name ) throws IOException
    {
        /*
        try
        {
            BasicService bs = ( BasicService ) ServiceManager.lookup ( "javax.jnlp.BasicService" );
            PersistenceService ps = ( PersistenceService ) ServiceManager.lookup ( "javax.jnlp.PersistenceService" );
            FileContents fc = null;
            URL fileURL = new URL ( bs.getCodeBase (), name );
            try
            {
                fc = ps.get ( fileURL );
            } catch ( FileNotFoundException e )
            {
                long maxSizeRequest = 131072L;
                long maxSize = ps.create ( fileURL, maxSizeRequest );
                if ( maxSize >= maxSizeRequest )
                {
                    fc = ps.get ( fileURL );
                }
            }
            if ( ( fc != null ) && ( fc.canWrite () ) )
            {
                return new BufferedOutputStream ( fc.getOutputStream ( true ) );
            }
        } catch ( Exception e )
        {
            // TBD log and error
        }
        */
        File file = new File ( appLocalHome (), name );
        File dir = file.getParentFile ();
        if ( !dir.isDirectory () )
        {
            dir.mkdirs ();
        }
        return new BufferedOutputStream ( new FileOutputStream ( file ) );
    }

    protected void initialize ( String[] ignore )
    {
        Toolkit.getDefaultToolkit ().setDynamicLayout ( true );
        mainFrame = new JFrame ( "MultiSplitPane " + this.getClass ().getName () );
        Container cp = mainFrame.getContentPane ();
        cp.setLayout ( new BorderLayout () );
    }

    protected void show ()
    {
        mainFrame.setDefaultCloseOperation ( JFrame.DO_NOTHING_ON_CLOSE );
        mainFrame.addWindowListener ( new ExampleWindowListener () );
        mainFrame.pack ();
        mainFrame.setVisible ( true );
    }

    protected void quit ()
    {
        mainFrame.setVisible ( false );
    }

    private class ExampleWindowListener extends WindowAdapter
    {
        public void windowClosing ( WindowEvent e )
        {
            try
            {
                quit ();
            } catch ( Exception ignore )            {
            } finally            {
                System.exit ( 0 );
            }
        }
    }

    protected static void launch ( final Class exampleClass, final String[] args )
    {
        Runnable doCreateAndShowGUI = new Runnable ()
        {
            public void run ()
            {
                try
                {
                    Example app = ( Example ) ( exampleClass.newInstance () );
                    app.initialize ( args );
                    app.show ();
                } catch ( Exception e )
                {
                    // TBD log an error
                }
            }
        };
        SwingUtilities.invokeLater ( doCreateAndShowGUI );
    }

    /*
    public static void main(final String[] args) {
        launch(MyExample.class, args);
    }
    */
}
