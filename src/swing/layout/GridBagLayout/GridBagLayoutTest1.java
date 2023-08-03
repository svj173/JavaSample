package swing.layout.GridBagLayout;


import javax.swing.*;
import java.awt.*;


/**
 * Три компоненты. Распологаются вертикально. При ресайзе - изменяется только центральный.
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 24.01.2011 14:50:14
 */
public class GridBagLayoutTest1
{
    public static void main ( String[] args )
    {
        JTextField tf = new JTextField();
        JTextArea ta = new JTextArea();
        JButton btn = new JButton("Press me");
        GridBagLayout gbl = new GridBagLayout();
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.weightx = 1;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbl.setConstraints(tf, gbc);
        gbc.gridy = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbl.setConstraints(ta, gbc);
        gbc.gridy = 2;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbl.setConstraints(btn, gbc);
        JPanel container = new JPanel();
        container.setLayout(gbl);
        container.add(tf);
        container.add(ta);
        container.add(btn);
        JFrame f = new JFrame();
        f.setContentPane(container);
        f.setSize(400,300);
        f.setLocationRelativeTo(null);
        f.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
    }
}
