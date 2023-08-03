package swing.label;


/**
 * Проблема правильного определения длины текста в пикселях, согласно фонам или применения HTML.
 * <BR/>  I want to create a dialog that contains some kind of text element (JLabel/JTextArea etc) that
 * is multi lined and wrap the words. I want the dialog to be of a fixed width but adapt the height depending on how big the text is.
 * I have this code:
 * <BR/>
 * <BR/> Код где ничего не решено. Ответы - в след классах.
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 30.03.2011 14:27:22
 */
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static javax.swing.GroupLayout.DEFAULT_SIZE;

public class TextSizeProblem extends JFrame {
  public TextSizeProblem() {

    String dummyString = "";
    for (int i = 0; i < 100; i++) {
      dummyString += " word" + i;  //Create a long text
    }
    JLabel text = new JLabel();
    text.setText("<html>" + dummyString + "</html>");

    JButton packMeButton = new JButton("pack");
    packMeButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        pack();
      }
    });

    GroupLayout layout = new GroupLayout(this.getContentPane());
    getContentPane().setLayout(layout);
    layout.setVerticalGroup(layout.createParallelGroup()
        .addComponent(packMeButton)
        .addComponent(text)
    );
    layout.setHorizontalGroup(layout.createSequentialGroup()
        .addComponent(packMeButton)
        .addComponent(text, DEFAULT_SIZE, 400, 400) //Lock the width to 400
    );

    pack();
  }

  public static void main(String args[]) {
    SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        JFrame frame = new TextSizeProblem();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
      }
    });
  }
}
