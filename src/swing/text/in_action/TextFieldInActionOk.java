package swing.text.in_action;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * Проблема - запустился ActionListener, который в процессе своей работы несколько раз
 * меенял содержимое поля JTextField. Но в результате отобразились только самые последние данные, несмотря на 10 сек задержки.
 * Проверяем на простом примере.
 * <BR/> Кнопка и еткстовое поле.
 * <BR/> Жмем кнопку по которйо активируестя акция, изменяющая текстовое поле с задержками.
 * <BR/>
 * <BR/>  Здесь решили эту проблему
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 19.07.2010 11:21:47
 */
public class TextFieldInActionOk extends JPanel
{
    private static JFrame frame;

    public TextFieldInActionOk ()
    {
        super ( new BorderLayout ( ) );

        final JTextField nameTextField = new JTextField();
        add (nameTextField, BorderLayout.NORTH );

        JButton button = new JButton ("Start Action");
        add (button, BorderLayout.SOUTH );
        /*
        button.addActionListener ( new ActionListener {
        @Override
        public void actionPerformed ( ActionEvent e )
        {
        }

    });
    */

        ActionListener actionListener = new ActionListener()
        {
          public void actionPerformed(ActionEvent actionEvent)
          {
              int i = 0;
              System.out.println("Command: " + actionEvent.getActionCommand());
              try
              {
                  nameTextField.setText ( " -1) text"  );
                  EventQueue.invokeLater ( new DynamicTextSetter ( nameTextField, " -1) text" ) );
                  System.out.println("- 1");
                  Thread.sleep(1000);

                  nameTextField.setText ( " -2) text 2"  );
                  System.out.println("- 2");
                  EventQueue.invokeLater ( new DynamicTextSetter ( nameTextField, " -2) text 2" ) );
                  Thread.sleep(1000);

                  nameTextField.setText ( " -3) text 33"  );
                  System.out.println("- 3");
                  EventQueue.invokeLater ( new DynamicTextSetter ( nameTextField, " -3) text 33" ) );
                  Thread.sleep(1000);

                  nameTextField.setText ( " -4) text 444"  );
                  System.out.println("- 4");

              } catch ( Exception e )              {
                  e.printStackTrace();
              }
          }
        };
        button.addActionListener ( actionListener );

    }

    public static void main(String[] args) {
        //Schedule a job for the event dispatch thread:
        //creating and showing this application's GUI.
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                //Turn off metal's use of bold fonts
	            //UIManager.put("swing.boldMetal", Boolean.FALSE);
                createAndShowGUI();
            }
        });
    }

    private static void createAndShowGUI() {
        //Create and set up the window.
        frame = new JFrame("TextFieldInAction");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Add contents to the window.
        frame.add(new TextFieldInActionOk ());

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }


}