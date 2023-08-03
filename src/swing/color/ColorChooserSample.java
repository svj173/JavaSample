package swing.color;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFrame;

public class ColorChooserSample 
{
  public static void main(String args[]) {
    final JFrame f = new JFrame("JColorChooser Sample");
    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    Container content = f.getContentPane();
    final JButton button = new JButton("Pick to Change Background");

    ActionListener actionListener = new ActionListener() {
      public void actionPerformed(ActionEvent actionEvent) {
        Color initialBackground = button.getBackground();
        Color background = JColorChooser.showDialog(f,
            "JColorChooser Sample", initialBackground);
        if (background != null) {
          button.setBackground(background);
        }
      }
    };
    button.addActionListener(actionListener);
    content.add(button, BorderLayout.CENTER);
    f.setSize(300, 200);
    f.setVisible(true);
  }
}
