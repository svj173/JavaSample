package swing.opaque;


/**
 * Прозрачный фрейм.
 // Поддержка - с JDK 6 update 16.
 // Exception in thread "AWT-EventQueue-0" java.lang.UnsupportedOperationException: The TRANSLUCENT translucency kind is not supported.
 // Обнаружил что прозрачность не поддерживается в линуксе. java version “1.6.0_15″
 AWTUtilities.setWindowOpacity ( frame, 0.5f );
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 26.04.2012 17:19:56
 */

import com.sun.awt.AWTUtilities;

import javax.swing.*;
import java.awt.*;

public class TestFrame //extends JFrame
{
    public static void createGUI ()
    {
        JFrame frame = new JFrame ( "Test frame" );
        frame.setDefaultCloseOperation ( JFrame.EXIT_ON_CLOSE );

        String html = "<html>The Java Tutorials are practical guides "
                + "for programmers who want to use the Java programming "
                + "language to create applications. They include hundreds "
                + "of complete, working examples, and dozens of lessons. "
                + "Groups of related lessons are organized into \"trails.\" "
                + "The Java Tutorials include features through the Java SE 6 version. "
                + "Selected tutorials have been printed; see the Related "
                + "Resources box to the right.</html>";

        JLabel label = new JLabel ( html );
        label.setOpaque ( true );

        frame.getContentPane().add ( label );

        frame.setPreferredSize ( new Dimension ( 250, 230 ) );
        frame.pack();
        frame.setLocationRelativeTo ( null );
        frame.setVisible ( true );

        // Убрать рамку фрейма и крестик
        //frame.setUndecorated ( true );

        frame.getRootPane().setOpaque ( true );   // не помогает

        // Поддержка - с JDK 6 update 16.
        // Exception in thread "AWT-EventQueue-0" java.lang.UnsupportedOperationException: The TRANSLUCENT translucency kind is not supported.
        // Обнаружил что прозрачность не поддерживается в линуксе. java version “1.6.0_15″
        AWTUtilities.setWindowOpacity ( frame, 0.5f );
    }

    public static void main ( String[] args )
    {
        javax.swing.SwingUtilities.invokeLater ( new Runnable()
        {
            public void run ()
            {
                JFrame.setDefaultLookAndFeelDecorated ( true );
                createGUI ();
            }
        } );
    }
    
}
