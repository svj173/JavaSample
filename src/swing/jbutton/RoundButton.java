package swing.jbutton;


import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;

/**
 * <BR> Кнопка круглой формы.
 * 
 This tip is about round Swing buttons. Actually, the information in
 this tip applies to a button of any arbitrary shape but to keep the
 discussion simple, we're just going to use a round shape.

 There are two things you need to do when creating a round button.
 The first is to override the appropriate painting methods in order
 to paint the round shape. The second is to set things up so that
 the button responds only when you click within the round button
 (not just within the rectangle that contains the round button).

 The RoundButton class extends JButton because we want to keep most
 of functionality of a JButton. In the RoundButton constructor, the
 method setContentAreaFilled() is called. This causes the button to
 paint the focus rectangle, but not to paint the background.

 Now we need to paint the circular background. That's done by
 overriding the paintComponent() method. That method uses
 Graphics.fillOval() to paint a solid circle. Then paintComponent()
 calls super.paintComponent() to paint the swing.label on top of the solid
 circle.

 This example also overrides paintBorder() in order to paint a border
 around the round button. If you don't want a border, you would not
 override this method. This method calls Graphics.drawOval() to
 paint a thin border around the circle.

 Finally, we want the button to respond only when the user clicks on
 it, but not respond to clicks outside of it. By default, a JButton
 responds to mouse clicks in a rectangular area around the
 button. A JButton has no idea about the real shape of the button,
 and so it just assumes that the button is rectangular. To tell
 JButton the real shape of a button you need to override the
 contains() method. The contains() method takes a coordinate and
 returns true if the coordinate is inside the button and false
 otherwise. The method uses a Shape object (Ellipse2D) to determine
 whether the coordinate is inside the circle.

 Note: In JDK 1.2.2, there's a small bug in how JButton behaves when
 you drag the mouse in and out of its perimeter. Ideally, if you
 clickon the circular button and then drag the mouse outside of the
 button's perimeter, the button should change its appearance. When
 you drag the mouse back into the circular button's perimeter, the
 button should restore its appearance. Unfortunately, the code that
 implements this behavior does not call the contains() method.
 Instead it just uses the "bounds" of the button (this is the
 smallest rectangular area containing the button). Notice that if
 you drag the mouse slightly beyond the circular perimeter, that is,
 outside of the circle but not outside the bounds, the button won't
 change it's appearance. There is no workaround for this minor bug
 except to implement all of the functionality yourself.

 * <BR>
 * <BR> User: svj
 * <BR> Date: 20.04.2006
 * <BR> Time: 17:09:47
 */
public class RoundButton extends JButton
{
    // Hit detection.
    Shape shape;


    public RoundButton ( String label )
    {
        super ( label );

        // These statements enlarge the button so that it
        // becomes a circle rather than an oval.
        Dimension size = getPreferredSize ();
        size.width = size.height = Math.max ( size.width, size.height );
        setPreferredSize ( size );

        // This call causes the JButton not to paint the background.
        // This allows us to paint a round background.
        setContentAreaFilled ( false );
    }

    // Paint the round background and swing.label.
    protected void paintComponent ( Graphics g )
    {
        if ( getModel().isArmed () ) {
            // You might want to make the highlight color
            // a property of the RoundButton class.
            g.setColor ( Color.lightGray );
        }
        else {
            g.setColor ( getBackground () );
        }
        g.fillOval ( 0, 0, getSize ().width - 1, getSize ().height - 1 );

        // This call will paint the swing.label and the focus rectangle.
        super.paintComponent ( g );
    }

    // Paint the border of the button using a simple stroke.
    protected void paintBorder ( Graphics g )
    {
        g.setColor ( getForeground () );
        g.drawOval ( 0, 0, getSize ().width - 1, getSize ().height - 1 );
    }

    public boolean contains ( int x, int y )
    {
        // If the button has changed size, make a new shape object.
        if ( shape == null || !shape.getBounds().equals ( getBounds () ) ) {
            shape = new Ellipse2D.Float ( 0, 0, getWidth (), getHeight () );
        }
        return shape.contains ( x, y );
    }

    // Test routine.
    public static void main ( String[] args )
    {
        // Create a button with the swing.label "Jackpot".
        JButton button = new RoundButton ( "Jackpot" );
        button.setBackground ( Color.green );

        // Create a frame in which to show the button.
        JFrame frame = new JFrame ();
        frame.getContentPane ().setBackground ( Color.yellow );
        frame.getContentPane ().add ( button );
        frame.getContentPane ().setLayout ( new FlowLayout () );
        frame.setSize ( 150, 150 );
        // Взять размер экрана чтобы разместить окно в середине экрана
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize ();
        //frame.setSize ( d.width, d.height );
        frame.setLocation ( ( d.width - frame.getSize().width ) / 2,
                ( d.height - frame.getSize ().height ) / 2 );

        frame.setVisible ( true );

        System.exit ( 10 );
    }

}
