package swing.key;


import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

/**
 * Новая технология по работе с акциями нажатия клавиш клавиатуры - применение InputMap, ActionMap.
 * <BR/> Copy-Paste на текстовых полях.
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 17.09.2015 11:20
 */
public class TestKeyActions extends JFrame
{

    JTextField txt1 = new JTextField ( 32 );
    JTextField txt2 = new JTextField ( 32 );
    JTextArea  txt3 = new JTextArea ( 3, 32 );

    TestKeyActions ()
    {
        super ( "Программирование действий по нажатию на клавиши" );

        try
        {
            UIManager.setLookAndFeel (
                    UIManager.getSystemLookAndFeelClassName () );
        } catch ( Exception e )
        {
        }

        JComponent c = ( JComponent ) getContentPane ();
        JPanel pn1 = new JPanel ();
        BoxLayout b1 = new BoxLayout ( pn1, BoxLayout.Y_AXIS );
        pn1.setLayout ( b1 );
        c.add ( pn1, BorderLayout.CENTER );
        JPanel apanel = new JPanel ();
        pn1.add ( apanel );
        apanel.add ( new JLabel ( "Поле 1" ) );
        apanel.add ( txt1 );
        apanel = new JPanel ();
        pn1.add ( apanel );
        apanel.add ( new JLabel ( "Поле 2" ) );
        apanel.add ( txt2 );
        apanel = new JPanel ();
        pn1.add ( apanel );
        apanel.add ( new JLabel ( "Поле 3" ) );
        apanel.add ( txt3 );

//--  Блок модификации InputMap и ActionMap
        // Вариант 1. - распространен только на первое текстовое поле.
        /*
        txt1.getInputMap ().put (
                KeyStroke.getKeyStroke ( "control INSERT" ),
                "copy-to-clipboard" );
        txt2.getInputMap ().put (
                KeyStroke.getKeyStroke ( "shift INSERT" ),
                "paste-from-clipboard" );
        */
        // Вариант 2 - распространен на все гуи-компоненты - из-за применения WHEN_ANCESTOR_OF_FOCUSED_COMPONENT
        InputMap inputMap = c.getInputMap ( JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT );
        inputMap.put ( KeyStroke.getKeyStroke ( "control INSERT" ), "copy-to-clipboard" );
        inputMap.put ( KeyStroke.getKeyStroke ( "shift INSERT" ),  "paste-from-clipboard" );
        ActionMap actionMap = c.getActionMap();
        Action action = txt1.getActionMap().get ( "copy-to-clipboard" );
        actionMap.put ( "copy-to-clipboard", action );
        action = txt1.getActionMap().get ( "paste-from-clipboard" );
        actionMap.put ( "paste-from-clipboard", action );

//--  Конец блока


        WindowListener wndCloser = new WindowAdapter ()
        {
            public void windowClosing ( WindowEvent e )
            {
                System.exit ( 0 );
            }
        };
        addWindowListener ( wndCloser );

        pack ();
        setVisible ( true );
    }

    public static void main ( String[] args )
    {
        TestKeyActions d = new TestKeyActions ();
    }
}
