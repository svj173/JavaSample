package swing.opaque;


import com.sun.awt.AWTUtilities;

import javax.swing.*;
import java.awt.*;


/**
 * Попытк ссоздать фрейм сквозь который было бы видно фон монитора (рабочую панель).
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 26.04.2012 17:00:54
 */
public class OpaqueFrame  extends JFrame
{
    public OpaqueFrame () throws HeadlessException
    {
        super ( "Прозрачный фрейм" );

        setSize ( 400, 400 );
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel;
        JLabel label;
        JButton button;

        panel   = new JPanel();
        //panel.setOpaque ( true );
        panel.setOpaque ( false );

        label = new JLabel ( "test" );
        label.setBackground ( Color.yellow );
        //panel.add ( label );

        button = new JButton ( "test" );

        panel.add ( button );

        getContentPane().add ( panel );
    }

    public static void main ( String args[] )
    {
        float  koef;
        OpaqueFrame frame;

        frame = new OpaqueFrame();

        koef    = 0.5f;
        AWTUtilities.setWindowOpacity ( frame, koef ); 

        frame.setVisible(true);
    }

}
