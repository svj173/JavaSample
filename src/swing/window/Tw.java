package swing.window;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;


/**
 * Окно произвольной формы
 * <BR/> Выводится яблоко. Но все равно в квадрате, просто все остальное - прозрачное.
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 08.07.2010 11:18:06
 */
public class Tw extends JWindow implements MouseMotionListener, FocusListener 
{
    Image img, tim;
    Graphics tig;
    Point mp;

    public Tw() {
        addMouseMotionListener(this);
        setBounds(170, 170, 100, 100);
        capture();
        setVisible(true);
        addFocusListener(this);
    }
    public void focusGained(FocusEvent fe) {
        Point p = getLocation();
        setLocation(11111, 0);
        capture();
        setLocation(p);
    }
    public void focusLost(FocusEvent fe) {
    }
    public void capture() {
        Dimension d = Toolkit.getDefaultToolkit().
            getScreenSize();
        try {
            Robot r = new Robot();
            Rectangle rect = new Rectangle(0, 0,
                d.width, d.height);
            img = r.createScreenCapture(rect);
        } catch (AWTException awe) {
            System.out.println("robot excepton occurred");
        }
    }
    public void mouseDragged(MouseEvent m) {
        Point p = m.getPoint();
        int x = getX() + p.x - mp.x;
        int y = getY() + p.y - mp.y;
        setLocation(x, y);
        Graphics g = getGraphics();
        paint(g);
    }
    public void mouseMoved(MouseEvent m) {
        mp = m.getPoint();
    }
    public void paint(Graphics g) {
        if (tim == null) {
            tim = createImage(getWidth(), getHeight());
            tig = tim.getGraphics();
        }
        tig.drawImage(img, 0, 0, getWidth(), getHeight(),
                getX(), getY(), getX() +
                getWidth(), getY() + getHeight(), null);
        tig.setColor(Color.orange);
        tig.fillOval(10, 20, 70, 80);
        tig.setColor(Color.green);
        tig.fillOval(21, 16, 20, 10);
        tig.fillOval(40, 02, 11, 21);
        g.drawImage(tim, 0, 0, null);
    }
    public void update(Graphics g) {
        this.paint(g);
    }
    public static void main(String[] args) {
        new Tw();
    }
}
