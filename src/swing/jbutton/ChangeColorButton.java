package swing.jbutton;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.geom.AffineTransform;


/**
 * Кнопка, которая меняет цвет при наведении на нее мышью.
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 20.12.2011 14:11:04
 */
public class ChangeColorButton extends JButton
{
    private Paint rolloverHighlight, armedHighlight;
    private Shape shape;
    /* Используется исключительно при изменениях размера кнопки. Для пересчета координат. */
    private Stroke borderStroke;

    public ChangeColorButton ( String name, Paint rolloverHighlight, Paint armedHighlight, Shape shape )
    {
        super ( name );

        //setContentAreaFilled ( false );   // отключили стандартную зарисовку кнопки (фон и т.д.)
        setContentAreaFilled ( true );
        
        setRolloverEnabled ( true );
        //setRolloverEnabled ( false );   // отключили реакцию на наведение мышью

        setFocusable ( false );
        //setFocusable ( true );

        setRolloverHighlight ( rolloverHighlight );
        setArmedHighlight ( armedHighlight );

        setShape ( shape );

        addComponentListener ( new ResizeListener() );
    }

    protected void paintComponent ( Graphics g )
    {
        Graphics2D g2 = ( Graphics2D ) g;
        boolean armed, roll;

        armed   = getModel().isArmed();
        roll    = getModel().isRollover();

        System.out.println ( "armed  = " + armed + "; roll  = " + roll );

        if ( armed )
        {
            //g2.setPaint ( getArmedHighlight() );
            //g2.fill ( getShape() );
            setBackground ( Color.RED );
        }
        else if ( roll )
        {
            //g2.setPaint ( getRolloverHighlight() );
            //g2.fill ( getShape() );
            setBackground ( Color.GREEN );
        }
        else
        {
            //g2.setPaint ( getBackground() );
            //g2.fill ( getShape() );
            setBackground ( Color.YELLOW );
        }
        super.paintComponent ( g );
    }

    public void setArmedHighlight ( Paint armedHighlight )
    {
        Paint oldHighLight = this.armedHighlight;
        this.armedHighlight = armedHighlight;
        if ( this.armedHighlight == null )
            this.armedHighlight = new Color ( 0, 0, 0, 64 );
        firePropertyChange ( "armedHighlight", oldHighLight, this.armedHighlight );
    }

    public Paint getArmedHighlight ()
    {
        return armedHighlight;
    }

    public Paint getRolloverHighlight ()
    {
        return rolloverHighlight;
    }

    public void setRolloverHighlight ( Paint rolloverHighlight )
    {
        this.rolloverHighlight = rolloverHighlight;
    }

    public Shape getShape ()
    {
        return shape;
    }

    public void setShape ( Shape shape )
    {
        if ( shape != null && !shape.equals ( this.shape ) )
        {
            Shape oldShape = this.shape;
            this.shape = shape;
            firePropertyChange ( "shape", oldShape, shape );
        }
    }

    protected void resizeShapes ()
    {
        Dimension size;
        AffineTransform transform;
        Rectangle shapeBounds;

        // Получить текущий размер
        size = getSize ( new Dimension() );
        System.out.println ( "- size  = " + size );

        //shapeBounds = getVisibleRect();
        //transform = AffineTransform.getTranslateInstance ( -shapeBounds.x, -shapeBounds.y );
        //transform = new AffineTransform ();
        //shape = transform.createTransformedShape ( shape );

        shapeBounds = getBorderStroke ().createStrokedShape ( shape ).getBounds ();
        System.out.println ( "- shapeBounds  = " + shapeBounds );
        
        if ( shapeBounds.x != 0 || shapeBounds.y != 0 )
        {
            transform   = AffineTransform.getTranslateInstance ( -shapeBounds.x, -shapeBounds.y );
            shape       = transform.createTransformedShape ( shape );
        }
        if ( shapeBounds.width != size.width || shapeBounds.height != size.height )
        {
            double sx = ( double ) size.width / ( double ) shapeBounds.width;
            double sy = ( double ) size.height / ( double ) shapeBounds.height;
            transform   = AffineTransform.getScaleInstance ( sx, sy );
            shape       = transform.createTransformedShape ( shape );
        }
    }

    public Stroke getBorderStroke ()
    {
        return borderStroke;
    }

    public void setBorderStroke ( Stroke borderStroke )
    {
        Stroke oldBorderStroke = this.borderStroke;
        this.borderStroke = borderStroke;
        if ( this.borderStroke == null )
            this.borderStroke = new BasicStroke ();
        firePropertyChange ( "lowerBorder", oldBorderStroke, this.borderStroke );
    }



    public static void main ( String[] args )
    {
        Paint roll, arm;
        Shape shape;

        /*
        try
        {
            UIManager.setLookAndFeel ( UIManager.getInstalledLookAndFeels ()[ 0 ].getClassName () );
        } catch ( Exception ex )         {
        }
        */
        final JFrame frame = new JFrame ();
        frame.setDefaultCloseOperation ( JFrame.DISPOSE_ON_CLOSE );
        Container cont = frame.getContentPane ();
        cont.setLayout ( new GridLayout ( 1, 0 ) );

        ChangeColorButton button;

        Dimension d = new Dimension ( 250, 150 );

        //roll    = new GradientPaint ( 0, 0, Color.RED.darker (), d.width, d.height, Color.YELLOW.darker () );
        roll    = Color.YELLOW.darker();
        arm     = new GradientPaint ( 0, 0, Color.RED, d.width, d.height, Color.YELLOW );

        // shape
        //shape   = new Ellipse2D.Double ( 0.0, 0.0, 100.0, 100.0 );
        shape   = new Rectangle ( 0, 0, d.width, d.height );

        button = new ChangeColorButton ( "Button 2", roll, arm, shape );
        button.setBackground ( Color.YELLOW );
        button.setPreferredSize ( d );

        /*
        button.setShape ( new RoundRectangle2D.Double ( 0, 0, d.width, d.height, 100, 100 ) );
        button.setUpperBorder ( null );
        button.setLowerBorder ( null );
        button.setArmedHighlight ( new GradientPaint ( 0, 0, Color.RED.darker (), d.width, d.height, Color.YELLOW.darker () ) );
        button.setRolloverHighlight ( new GradientPaint ( 0, 0, Color.RED, d.width, d.height, Color.YELLOW ) );
        button.setBorderShadow ( new GradientPaint ( 0, 0, Color.GREEN, d.width, d.height, Color.BLUE ) );
        */

        button.setBorderStroke(new BasicStroke(5.0f));
        //button.setBorderStroke ( new BasicStroke ( 4.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 1.0f, new float[] { 30.0f, 10.0f, 10.0f, 10.0f }, 0.0f ) );

        cont.add ( button );
        frame.pack ();
        frame.setLocationRelativeTo ( null );

        //frame.setVisible ( true );
        SwingUtilities.invokeLater (
                new Runnable()
                {
                    public void run ()
                    {
                        frame.setVisible ( true );
                    }
                }
        );
    }

    private class ResizeListener implements ComponentListener
    {
        public void componentResized ( ComponentEvent e )
        {
            resizeShapes ();
        }

        public void componentMoved ( ComponentEvent e )
        {}

        public void componentShown ( ComponentEvent e )
        {}

        public void componentHidden ( ComponentEvent e )
        {}
    }

}
