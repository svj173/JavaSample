package swing.opaque;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;


/**
 * Псевдо-прозрачность (2007).
 * <BR/> Делаем принт-скрин фона, навешивает его на фрейм, совмещает с исходным при перемещении фрейма.
 * <BR/>
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 26.04.2012 17:28:56
 */
public class TransparentBackground extends JComponent
{
    private JFrame frame;
    private Image background;

    public TransparentBackground ( JFrame frame )
    {
        this.frame = frame;
        updateBackground ();
    }

    public void updateBackground ()
    {
        try
        {
            Robot rbt = new Robot ();
            Toolkit tk = Toolkit.getDefaultToolkit ();
            Dimension dim = tk.getScreenSize ();
            background = rbt.createScreenCapture (
                    new Rectangle ( 0, 0, ( int ) dim.getWidth (),
                                    ( int ) dim.getHeight () ) );
        } catch ( Exception ex )      {
            ex.printStackTrace ();
        }
    }

    public void paintComponent ( Graphics g )
    {
        Point pos = this.getLocationOnScreen ();
        Point offset = new Point ( -pos.x, -pos.y );
        g.drawImage ( background, offset.x, offset.y, null );
    }

    private static class TransparentComponentListener
            implements ComponentListener
    {

        public void componentResized ( ComponentEvent e )
        {
            Component[] components = ( ( JFrame ) e.getComponent () )
                    .getContentPane ().getComponents ();
            components[ 0 ].repaint ();
        }

        public void componentMoved ( ComponentEvent e )
        {
            componentResized ( e );
        }

        public void componentShown ( ComponentEvent e )
        {
            componentResized ( e );
        }

        public void componentHidden ( ComponentEvent e )
        {
            componentResized ( e );
        }
    }

    public static void main ( String[] args )
    {
        JFrame frame = new JFrame ( "Transparent Window" );
        frame.addComponentListener ( new TransparentComponentListener () );
        TransparentBackground bg = new TransparentBackground ( frame );
        bg.setLayout ( new BorderLayout () );
        JButton button = new JButton ( "This is a button" );
        bg.add ( "North", button );
        JLabel label = new JLabel ( "This is a label" );
        bg.add ( "South", label );
        frame.getContentPane ().add ( "Center", bg );
        frame.pack ();
        frame.setSize ( 200, 200 );
        //frame.show ();
        frame.setVisible (true);
    }

}
