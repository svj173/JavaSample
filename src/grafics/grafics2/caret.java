package grafics.grafics2;


    import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultCaret;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Указатель курсора - в виде подчеркивания. В текстовом окне.
 */
    class MyCaret extends DefaultCaret {
        // draw the caret
        public void paint(Graphics g) {
            if (!isVisible())
                return;
            try {
                JTextComponent c = getComponent();
                int dot = getDot();
                Rectangle r = c.modelToView(dot);
                g.setColor(c.getCaretColor());
                g.drawLine(r.x, r.y + r.height - 1,
                    r.x + 3, r.y + r.height - 1);
            }
            catch (BadLocationException e) {
                System.err.println(e);
            }
        }

        // specify the size of the caret for redrawing
        // and do repaint() -- this is called when the
        // caret moves
        protected synchronized void damage(Rectangle r) {
            if (r == null)
                return;
            x = r.x;
            y = r.y + r.height - 1;
            width = 4;
            height = 1;
            repaint();
        }
    }

    public class caret {
        public static void main(String args[]) {
            JFrame frame = new JFrame("Caret demo");
            frame.addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent e) {
                    System.exit(0);
                }
            });
            JTextArea area = new JTextArea(10, 40);
            area.setCaret(new MyCaret());
            JPanel panel = new JPanel();
            panel.add(area);
            frame.getContentPane().add("Center", panel);
            frame.pack();
            frame.setVisible(true);
        }
    }

