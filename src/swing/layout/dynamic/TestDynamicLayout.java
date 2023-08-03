package swing.layout.dynamic;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Создаем один менеджер компоновки. Наполняем панель обьектами. Потом переустанавливаем компоновщик и смотрим что получится.
 * <BR/>
 * <BR/> Задача: изменять влет компоновку уже созданных обьектов.
 * <BR/>
 * <BR/> Результат:
 * <BR/> 1) Если смена компоновщика - тут же при наполнении, но до pack - все ОК.
 * <BR/> 2) Если по кнопке менять - то надо говорить pane.validate();
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 06.02.2013 13:33
 */
public class TestDynamicLayout
{
    private static void createAndShowGUI()
    {
        //Make sure we have nice window decorations.
        JFrame.setDefaultLookAndFeelDecorated ( true );

        //Create and set up the window.
        JFrame frame = new JFrame("TestDynamicLayout");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Set up the content pane.
        addComponentsToPane ( frame.getContentPane() );

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    private static void addComponentsToPane ( final Container pane )
    {
        JLabel label;

        pane.setLayout ( new FlowLayout() );

        label   = new JLabel("Panel 1");
        label.setPreferredSize ( new Dimension ( 250, 40 ) );
        pane.add ( label );
        pane.add ( new JLabel("Super Panel 2"));
        pane.add ( new JLabel("Super Super Panel 3"));

        JButton button;
        button  = new JButton ( "Вертикально" );
        button.addActionListener ( new ActionListener ()
        {
            @Override
            public void actionPerformed ( ActionEvent e )
            {
                pane.setLayout(new BoxLayout ( pane, BoxLayout.PAGE_AXIS ));
                //pane.repaint ();
                pane.validate();
                //this.pack();
            }
        }
        );
        pane.add ( button );

        //pane.setLayout(new BoxLayout ( pane, BoxLayout.PAGE_AXIS ));

    }

    public static void main(String[] args)
    {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}
