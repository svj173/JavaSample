package svj.swing.cursor;


/**
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 12.05.2016 10:35
 */
import javax.swing.*;
import java.awt.*;

public class TestCursorFrame extends JFrame {

    private static void createButtons2 ( JPanel panel )
    {
        Cursor [] curs = new Cursor [] {
           Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR),
           Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR),
           Cursor.getPredefinedCursor(Cursor.E_RESIZE_CURSOR),
           Cursor.getPredefinedCursor(Cursor.HAND_CURSOR),
           Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR),
           Cursor.getPredefinedCursor(Cursor.N_RESIZE_CURSOR),
           Cursor.getPredefinedCursor(Cursor.NE_RESIZE_CURSOR),
           Cursor.getPredefinedCursor(Cursor.NW_RESIZE_CURSOR),
           Cursor.getPredefinedCursor(Cursor.S_RESIZE_CURSOR),
           Cursor.getPredefinedCursor(Cursor.SE_RESIZE_CURSOR),
           Cursor.getPredefinedCursor(Cursor.SW_RESIZE_CURSOR),
           Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR),
           Cursor.getPredefinedCursor(Cursor.W_RESIZE_CURSOR),
           Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR)
        };
        for (int i = 0; i < curs.length; i++)
        {
           Cursor cur = curs[i];
           JButton comp = new JButton(cur.getName());
           comp.setCursor(cur);
            panel.add(comp);
        }
    }

    public static void createGUI()
    {
        final JFrame frame = new JFrame("Test frame");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        //createButtons1 ( panel );
        createButtons2 ( panel );

        frame.getContentPane().setLayout(new BorderLayout());
        frame.getContentPane().add(panel, BorderLayout.CENTER);

        frame.setPreferredSize(new Dimension(260, 220));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private static void createButtons1 ( JPanel panel )
    {
        panel.add(Box.createVerticalGlue());

        JButton button1 = new JButton("Cursor.MOVE_CURSOR");
        button1.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        button1.setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
        panel.add(button1);

        panel.add(Box.createVerticalGlue());

        JButton button2 = new JButton("Cursor.HAND_CURSOR");
        button2.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        button2.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        panel.add(button2);


        panel.add(Box.createVerticalGlue());

        JButton button3 = new JButton("Cursor.CROSSHAIR_CURSOR");
        button3.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        button3.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
        panel.add(button3);

        panel.add(Box.createVerticalGlue());

        JButton button4 = new JButton("Cursor.TEXT_CURSOR");
        button4.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        button4.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
        panel.add(button4);

        panel.add(Box.createVerticalGlue());
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JFrame.setDefaultLookAndFeelDecorated(true);
                createGUI();
            }
        });
    }

}