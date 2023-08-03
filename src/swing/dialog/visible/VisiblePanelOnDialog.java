package swing.dialog.visible;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * Проверка показывания и закрытия панели в диалоговом окне.
 * <BR/> Жмем кнопку - Показать - открывает панель, Скрыть - закрывает панель.
 * <BR/> Особенность - панель со скроллингом.
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 11.01.2012 16:44:57
 */
public class VisiblePanelOnDialog  extends JFrame
{
    public VisiblePanelOnDialog () throws HeadlessException
    {
        super ( "Проверка скрытия панели" );

        JButton button;
        JPanel  panel;
        JLabel  label;

        setDefaultCloseOperation ( WindowConstants.EXIT_ON_CLOSE );

        panel = new JPanel();

        
        button = new JButton();
        button.setText ( "Start" );
        button.addActionListener ( new ActionListener () {
            @Override
            public void actionPerformed ( ActionEvent e )
            {
                PanelDialog dialog;
                dialog  = new PanelDialog(VisiblePanelOnDialog.this, "Проверка");
                dialog.setVisible ( true );
            }
        });

        label = new JLabel (" -- text 1 -- ");
        panel.add ( label );

        panel.add ( button );

        label = new JLabel ("  text 2  ");
        panel.add ( label );

        getContentPane().add ( panel );

        setPreferredSize ( new Dimension ( 400, 400) );
        pack();
    }

    public static void main ( String[] args )
    {
        java.awt.EventQueue.invokeLater ( new Runnable()
        {
            public void run ()
            {
                JFrame frame = new VisiblePanelOnDialog ();
                frame.setVisible ( true );
            }
        } );

    }

}
