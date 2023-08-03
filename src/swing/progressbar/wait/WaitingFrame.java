package swing.progressbar.wait;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 03.02.2011 11:38:21
 */
public class WaitingFrame  extends JFrame implements ActionListener
{
    private JButton startButton;

    public WaitingFrame () throws HeadlessException
    {
        super ( "Test" );

        setDefaultCloseOperation ( JFrame.EXIT_ON_CLOSE );

        startButton = new JButton("Start");
        startButton.setActionCommand("start");
        startButton.addActionListener(this);

        getContentPane ().add ( startButton, BorderLayout.CENTER );

        setSize ( 300, 100 );
    }

    @Override
    public void actionPerformed ( ActionEvent e )
    {
        startButton.setEnabled(false);
        WaitingDialog wd    = new WaitingDialog(this, "Проверка крестика на диалоге");
        wd.start();
    }

    public static void main ( String args[] )
    {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                WaitingFrame          f;
                f       = new WaitingFrame ();
                f.setVisible ( true );
            }
        });
    }

}
