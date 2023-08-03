package swing.add_delete;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * Тоже что и AddDEleteTestFrame01, только без использования списка
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 11.06.2010 16:07:36
 */
public class AddDeleteTestFrame02 extends JFrame
{
    public static void createGUI ()
    {
        final JFrame frame = new JFrame ( "Test frame" );
        frame.setDefaultCloseOperation ( JFrame.EXIT_ON_CLOSE );

        final Font font = new Font ( "Verdana", Font.PLAIN, 25 );

        JPanel butPanel = new JPanel ();

        JButton addButton = new JButton ( "+" );
        addButton.setFont ( font );
        addButton.setFocusable ( false );
        butPanel.add ( addButton );

        JButton remButton = new JButton ( "-" );
        remButton.setFont ( font );
        remButton.setFocusable ( false );
        butPanel.add ( remButton );

        final JPanel labPanel = new JPanel ();
        final JScrollPane scrollPane = new JScrollPane ( labPanel );
        labPanel.setLayout ( new BoxLayout ( labPanel, BoxLayout.Y_AXIS ) );

        addButton.addActionListener ( new ActionListener()
        {
            public void actionPerformed ( ActionEvent e )
            {
                int number = labPanel.getComponentCount () + 1;
                JLabel label = new JLabel ( "Label " + number );
                label.setAlignmentX ( JLabel.CENTER_ALIGNMENT );
                label.setFont ( font );
                labPanel.add ( label );
                scrollPane.revalidate ();
            }
        } );

        remButton.addActionListener ( new ActionListener()
        {
            public void actionPerformed ( ActionEvent e )
            {
                if ( labPanel.getComponentCount () > 0 )
                {
                    int index = labPanel.getComponentCount () - 1;
                    JLabel label = ( JLabel ) labPanel.getComponent ( index );
                    labPanel.remove ( label );
                    labPanel.repaint ();
                    scrollPane.revalidate ();
                }
            }
        } );

        frame.getContentPane ().setLayout ( new BorderLayout () );
        frame.getContentPane ().add ( butPanel, BorderLayout.NORTH );
        frame.getContentPane ().add ( scrollPane, BorderLayout.CENTER );

        frame.setPreferredSize ( new Dimension ( 250, 200 ) );
        frame.pack ();
        frame.setLocationRelativeTo ( null );
        frame.setVisible ( true );
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
