package swing.table.popup.example1;


/**
 * Движок примера
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 19.10.2010 11:56:11
 */

import javax.swing.UIManager;
import java.awt.*;

public class MainController
{
    boolean packFrame = false;

    public MainController ()
    {
        MainFrame frame = new MainFrame();

        if ( packFrame )
        {
            frame.pack ();
        }
        else
        {
            frame.validate ();
        }

        //Center the window
        Dimension screenSize = Toolkit.getDefaultToolkit ().getScreenSize ();
        Dimension frameSize = frame.getSize ();
        if ( frameSize.height > screenSize.height )
        {
            frameSize.height = screenSize.height;
        }
        if ( frameSize.width > screenSize.width )
        {
            frameSize.width = screenSize.width;
        }
        frame.setLocation ( ( screenSize.width - frameSize.width ) / 2, ( screenSize.height - frameSize.height ) / 2 );
        frame.setVisible ( true );
    }

    //Main method -- it all starts here
    public static void main ( String[] args )
    {
        try
        {
            UIManager.setLookAndFeel ( UIManager.getSystemLookAndFeelClassName() );
        }
        catch ( Exception e )
        {
            e.printStackTrace ();
        }
        new MainController();
    }

}

