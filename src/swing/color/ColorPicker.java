package swing.color;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFrame;

public class ColorPicker extends JFrame {

  public ColorPicker() {
    super("JColorChooser Test Frame");
    setSize(200, 100);
    final Container contentPane = getContentPane();
    final JButton go = new JButton("Show JColorChooser");
    go.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        Color c;
        c = JColorChooser.showDialog(((Component) e.getSource())
            .getParent(), "Demo", Color.blue);
        contentPane.setBackground(c);
      }
    });
    contentPane.add(go, BorderLayout.SOUTH);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
  }

  public static void main(String args[]) {
    ColorPicker cp = new ColorPicker();
    cp.setVisible(true);
  }
}

