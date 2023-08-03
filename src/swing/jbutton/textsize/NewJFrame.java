package swing.jbutton.textsize;


import javax.swing.*;
import java.awt.*;


/**
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 29.12.2011 15:09:07
 */
public class NewJFrame extends javax.swing.JFrame
{
    //private JButton jButton1;

    public NewJFrame ()
    {
        initComponents ();
    }

    private void initComponents ()
    {
        JButton jButton1, button;


        setDefaultCloseOperation ( WindowConstants.EXIT_ON_CLOSE );

        getContentPane().setLayout ( new FlowLayout() );

        button  = new JButton("text");
        button.setFont ( new Font ( "Dialog", 0, 8 ) );
        //button.setText ( "some text" );
        //button.setMargin ( new Insets ( 1, 1, 1, 1 ) );
        getContentPane().add ( button );

        jButton1 = new JButton ();
        jButton1.setFont ( new Font ( "Dialog", 0, 8 ) );
        jButton1.setText ( "some text" );
        jButton1.setMargin ( new Insets ( 1, 1, 1, 1 ) );
        getContentPane().add ( jButton1 );

        pack ();
    }

    public static void main ( String args[] )
    {
        java.awt.EventQueue.invokeLater ( new Runnable()
        {
            public void run ()
            {
                new NewJFrame().setVisible ( true );
            }
        } );
    }

}