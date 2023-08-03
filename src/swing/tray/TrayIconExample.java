package swing.tray;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

/**
 * Кидает в трей иконку с выпадающем меню и едиснтвенным пунком Exit.
 */
public class TrayIconExample
{

    public static final String APPLICATION_NAME = "TrayIconExample";
    public static final String ICON_STR = "/images/icon32x32.png";

    public static void main ( String[] args )
    {
        SwingUtilities.invokeLater ( new Runnable ()
        {
            @Override
            public void run ()
            {
                createGUI ();
            }
        } );
    }

    private static void createGUI ()
    {
        JFrame frame = new JFrame ( APPLICATION_NAME );
        frame.setMinimumSize ( new Dimension ( 300, 200 ) );
        frame.setDefaultCloseOperation ( WindowConstants.EXIT_ON_CLOSE );
        frame.pack ();
        frame.setVisible ( true );

        setTrayIcon ();
    }

    private static void setTrayIcon ()
    {
        if ( ! SystemTray.isSupported() )    return;

        PopupMenu trayMenu = new PopupMenu();
        MenuItem item = new MenuItem ( "Exit" );
        item.addActionListener ( new ActionListener ()
        {
            @Override
            public void actionPerformed ( ActionEvent e )
            {
                System.exit ( 0 );
            }
        } );
        trayMenu.add ( item );

        URL imageURL = TrayIconExample.class.getResource ( ICON_STR );

        Image icon = Toolkit.getDefaultToolkit ().getImage ( imageURL );
        TrayIcon trayIcon = new TrayIcon ( icon, APPLICATION_NAME, trayMenu );
        trayIcon.setImageAutoSize ( true );

        SystemTray tray = SystemTray.getSystemTray();
        try
        {
            tray.add ( trayIcon );
        } catch ( AWTException e )     {
            e.printStackTrace ();
        }

        trayIcon.displayMessage ( APPLICATION_NAME, "Application started!", TrayIcon.MessageType.INFO );
    }

}