package swing.layout.SpringLayout;


/**
 * TextArea, Label, TextArea. При рнесайзе - Label не ищзменяется. Текстовое поле - в ширину и длину.
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 24.01.2011 15:08:20
 */

import javax.swing.*;
import java.awt.*;

public class SpringDemo5
{
    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private static void createAndShowGUI()
    {
        JTextField tfLeft, tfRight;

        //Create and set up the window.
        JFrame frame = new JFrame("SpringDemo3");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Set up the content pane.
        Container contentPane = frame.getContentPane();
        SpringLayout layout = new SpringLayout();
        contentPane.setLayout(layout);

        //Create and add the components.
        JLabel label = new JLabel("Label: ");
        tfLeft  = new JTextField("Text field Left", 15);
        tfRight = new JTextField("Text field Right", 15);

        contentPane.add(tfLeft);
        //contentPane.add(label);
        contentPane.add(tfRight);

        //Adjust constraints for the label so it's at (5,5).
        // Сторона компоненты 1, Компонента 1, пропуск между компонентами, Сторона комп 2, комп 2

        /*
        // цепляем слева направо
        layout.putConstraint( SpringLayout.WEST, contentPane , 5, SpringLayout.WEST, tfLeft );  // tfLeft цепляем слева к границе
        layout.putConstraint( SpringLayout.EAST, tfLeft, 5, SpringLayout.WEST, label );   // label слева цепл к панели
        layout.putConstraint( SpringLayout.EAST, label, 5, SpringLayout.WEST, tfRight );
        layout.putConstraint( SpringLayout.EAST, contentPane, 5, SpringLayout.EAST, tfRight );

        // цепляем сверху вниз
        layout.putConstraint ( SpringLayout.NORTH, label, 5, SpringLayout.NORTH, contentPane );
        layout.putConstraint ( SpringLayout.SOUTH, label, 5, SpringLayout.SOUTH, contentPane );
        layout.putConstraint ( SpringLayout.NORTH, tfLeft, 5, SpringLayout.NORTH, contentPane );
        layout.putConstraint( SpringLayout.SOUTH, contentPane, 5, SpringLayout.SOUTH, tfLeft );
        layout.putConstraint ( SpringLayout.NORTH, tfRight, 5, SpringLayout.NORTH, contentPane );
        layout.putConstraint( SpringLayout.SOUTH, contentPane, 5, SpringLayout.SOUTH, tfRight );
        */

        /*
        layout.putConstraint( SpringLayout.WEST, contentPane, 5, SpringLayout.WEST, tfLeft );
        layout.putConstraint( SpringLayout.NORTH, contentPane, 5, SpringLayout.NORTH, tfLeft );
        layout.putConstraint( SpringLayout.SOUTH, contentPane, 5, SpringLayout.SOUTH, tfLeft );

        layout.putConstraint( SpringLayout.EAST, tfLeft, 5, SpringLayout.WEST, tfRight );

        layout.putConstraint( SpringLayout.EAST, contentPane, 5, SpringLayout.EAST, tfRight );
        layout.putConstraint( SpringLayout.NORTH, contentPane, 5, SpringLayout.NORTH, tfRight );
        layout.putConstraint( SpringLayout.SOUTH, contentPane, 5, SpringLayout.SOUTH, tfRight );
        */

        layout.putConstraint(SpringLayout.WEST, tfLeft, 5, SpringLayout.WEST, contentPane);
        layout.putConstraint(SpringLayout.NORTH, tfLeft, 5, SpringLayout.NORTH, contentPane);

        layout.putConstraint(SpringLayout.WEST, tfRight, 5, SpringLayout.EAST, tfLeft);
        layout.putConstraint(SpringLayout.SOUTH, contentPane, 5, SpringLayout.SOUTH, tfLeft);
        
        layout.putConstraint(SpringLayout.NORTH, tfRight, 5, SpringLayout.NORTH, contentPane);
        layout.putConstraint(SpringLayout.EAST, contentPane, 5, SpringLayout.EAST, tfRight);
        layout.putConstraint(SpringLayout.SOUTH, contentPane, 5, SpringLayout.SOUTH, tfRight);

        // как Box - равной ширины. 2, 2, 6, 6 - отступы
        //SpringUtilities.makeGrid ( contentPane, 1, contentPane.getComponentCount(), 2, 2, 6, 6 );



        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}