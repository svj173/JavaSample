package swing.focus;


/**
 * Циклим внутри панели FocusCycleConstrainedJPanel.
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 28.06.2011 9:20:52
 */
import javax.swing.*;
import java.awt.*;

public class FocusCycleSample {
  public static void main(String args[]) {
    JFrame frame = new JFrame("Focus Cycle Sample");

    Container contentPane = frame.getContentPane();
    contentPane.setLayout(new GridLayout(3,3));
    for (int i = 0; i < 8; i++) {
      JButton button = new JButton("" + i);
      contentPane.add(button);
    }

    JPanel panel = new FocusCycleConstrainedJPanel();
    panel.setLayout(new GridLayout(1, 3));
    for (int i = 0; i < 3; i++) {
      JButton button = new JButton("" + (i + 3));
      panel.add(button);
    }
    contentPane.add(panel);

    frame.setSize(300, 200);
    frame.setVisible(true);
  }
}

class FocusCycleConstrainedJPanel extends JPanel {
  public boolean isFocusCycleRoot() {
    return true;
  }
}