package swing.jbutton;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Arc2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.RoundRectangle2D;

/**
 * Есть в Java2D интерфейс Shape который описывает любые геометрические примитивы.
 * А вот и готовый пример как можно сделать самую "извращенную кнопку".
 * <BR/> Полуовальная кнопка с изменяемым фоном и пунктирным разноцветным бордюром.
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 08.07.2010 11:21:35
 */

public class JSwingButton extends JButton
{
    private Paint rolloverHighlight, armedHighlight, borderHighlight, borderShadow;
    private Shape shape, upperBorder, lowerBorder;
    private Stroke borderStroke;

    public JSwingButton ( String label, Paint rolloverHighlight, Paint armedHighlight, Paint borderHighlight, Paint borderShadow, Stroke borderStroke )
    {
        super ( label );
        setContentAreaFilled ( false );
        setRolloverEnabled ( true );
        setFocusable ( false );
        setRolloverHighlight ( rolloverHighlight );
        setArmedHighlight ( armedHighlight );
        setBorderHighlight ( borderHighlight );
        setBorderShadow ( borderShadow );
        setBorderStroke ( borderStroke );
        setShape ( new Ellipse2D.Double ( 0.0, 0.0, 100.0, 100.0 ) );
        setUpperBorder ( new Arc2D.Double ( 0.0, 0.0, 100.0, 100.0, 000.0, 180.0, Arc2D.OPEN ) );
        setLowerBorder ( new Arc2D.Double ( 0.0, 0.0, 100.0, 100.0, 180.0, 180.0, Arc2D.OPEN ) );
        addComponentListener ( new ResizeListener () );
    }

    public JSwingButton ( String label )
    {
        this ( label, null, null, null, null, null );
    }

    public JSwingButton ()
    {
        this ( "", null, null, null, null, null );
    }

    public void setRolloverHighlight ( Paint rolloverHighlight )
    {
        Paint oldHighLight = this.rolloverHighlight;
        this.rolloverHighlight = rolloverHighlight;
        if ( this.rolloverHighlight == null )
            this.rolloverHighlight = new Color ( 255, 255, 255, 64 );
        firePropertyChange ( "rolloverHighlight", oldHighLight, this.rolloverHighlight );
    }

    public Paint getRolloverHighlight ()
    {
        return rolloverHighlight;
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
        Dimension size = getSize ( new Dimension () );
        System.out.println ( "- size  = " + size );
        Rectangle shapeBounds = getBorderStroke ().createStrokedShape ( shape ).getBounds ();
        System.out.println ( "- shapeBounds  = " + shapeBounds );

        if ( shapeBounds.x != 0 || shapeBounds.y != 0 )
        {
            AffineTransform transform = AffineTransform.getTranslateInstance ( -shapeBounds.x, -shapeBounds.y );
            shape       = transform.createTransformedShape ( shape );
            upperBorder = transform.createTransformedShape ( upperBorder );
            lowerBorder = transform.createTransformedShape ( lowerBorder );
        }
        if ( shapeBounds.width != size.width || shapeBounds.height != size.height )
        {
            double sx = ( double ) size.width / ( double ) shapeBounds.width;
            double sy = ( double ) size.height / ( double ) shapeBounds.height;
            AffineTransform transform = AffineTransform.getScaleInstance ( sx, sy );
            shape       = transform.createTransformedShape ( shape );
            upperBorder = transform.createTransformedShape ( upperBorder );
            lowerBorder = transform.createTransformedShape ( lowerBorder );
        }
    }

    public Shape getUpperBorder ()
    {
        return upperBorder;
    }

    public void setUpperBorder ( Shape upperBorder )
    {
        Shape oldUpperBorder = this.lowerBorder;
        this.upperBorder = upperBorder;
        firePropertyChange ( "upperBorder", oldUpperBorder, upperBorder );
    }

    public Shape getLowerBorder ()
    {
        return lowerBorder;
    }

    public void setLowerBorder ( Shape lowerBorder )
    {
        Shape oldLowerBorder = this.lowerBorder;
        this.lowerBorder = lowerBorder;
        firePropertyChange ( "lowerBorder", oldLowerBorder, lowerBorder );
    }

    public void setBorderHighlight ( Paint borderHighlight )
    {
        Paint oldBorderHighlight = this.borderHighlight;
        this.borderHighlight = borderHighlight;
        if ( this.borderHighlight == null )
            this.borderHighlight = UIManager.getColor ( "Button.light" );
        firePropertyChange ( "borderHighlight", oldBorderHighlight, this.borderHighlight );
    }

    public Paint getBorderHighlight ()
    {
        return borderHighlight;
    }

    public void setBorderShadow ( Paint borderShadow )
    {
        Paint oldBorderShadow = this.borderShadow;
        this.borderShadow = borderShadow;
        if ( this.borderShadow == null )
            this.borderShadow = UIManager.getColor ( "Button.shadow" );
        firePropertyChange ( "borderShadow", oldBorderShadow, this.borderShadow );
    }

    public Paint getBorderShadow ()
    {
        return borderShadow;
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

    protected void paintComponent ( Graphics g )
    {
        Graphics2D g2 = ( Graphics2D ) g;
        if ( getModel ().isArmed () )
        {
            g2.setPaint ( getArmedHighlight () );
            g2.fill ( shape );
        }
        else if ( getModel ().isRollover () )
        {
            g2.setPaint ( getRolloverHighlight () );
            g2.fill ( shape );
        }
        super.paintComponent ( g );
    }

    protected void paintBorder ( Graphics g )
    {
        Graphics2D g2 = ( Graphics2D ) g;
        g2.setStroke ( getBorderStroke () );
        if ( getModel ().isRollover () )
        {
            if ( getUpperBorder () != null && getLowerBorder () != null )
            {
                g2.setPaint ( getBorderHighlight () );
                g2.draw ( getUpperBorder () );
                g2.setPaint ( getBorderShadow () );
                g2.draw ( getLowerBorder () );
            }
            else
            {
                g2.setPaint ( getBorderShadow () );
                g2.draw ( getShape () );
            }
        }
    }

    public boolean contains ( int x, int y )
    {
        return shape.contains ( x, y );
    }

    public static void main ( String[] args )
    {
        try
        {
            UIManager.setLookAndFeel ( UIManager.getInstalledLookAndFeels ()[ 0 ].getClassName () );
        } catch ( Exception ex )         {
        }
        JFrame frame = new JFrame ();
        frame.setDefaultCloseOperation ( JFrame.DISPOSE_ON_CLOSE );
        Container cont = frame.getContentPane ();
        cont.setLayout ( new GridLayout ( 1, 0 ) );
        JSwingButton button = new JSwingButton ( "Button 1" );
        cont.add ( button );
        button = new JSwingButton ( "Button 2" );
        Dimension d = new Dimension ( 250, 150 );
        button.setPreferredSize ( d );
        button.setShape ( new RoundRectangle2D.Double ( 0, 0, d.width, d.height, 100, 100 ) );
        button.setUpperBorder ( null );
        button.setLowerBorder ( null );
        button.setArmedHighlight ( new GradientPaint ( 0, 0, Color.RED.darker (), d.width, d.height, Color.YELLOW.darker () ) );
        button.setRolloverHighlight ( new GradientPaint ( 0, 0, Color.RED, d.width, d.height, Color.YELLOW ) );
        button.setBorderShadow ( new GradientPaint ( 0, 0, Color.GREEN, d.width, d.height, Color.BLUE ) );
        //button.setBorderStroke(new BasicStroke(5.0f));
        button.setBorderStroke ( new BasicStroke ( 4.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 1.0f, new float[] { 30.0f, 10.0f, 10.0f, 10.0f }, 0.0f ) );
        cont.add ( button );
        frame.pack ();
        frame.setLocationRelativeTo ( null );
        frame.show ();
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