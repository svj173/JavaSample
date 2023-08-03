package swing.border;


import javax.swing.border.AbstractBorder;
import java.awt.*;


/**
 * Бордер в виде разьема на 8 штырьков, лежащий на левом боку.
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 19.12.2011 17:50:25
 */
public class PortBorder extends AbstractBorder
{
    private int thickness;
    private Color lineColor;

    public PortBorder ( Color color, int thickness )
    {
        lineColor = color;
        this.thickness = thickness;
    }

    /**
     * Рисовать границу согласно заданным размерам и толщине линий.
     * <BR/> Начальная координата - левый верхний угол.
     * <BR/>
     * @param c the component for which this border is being painted
     * @param g the paint graphics
     * @param x the x position of the painted border
     * @param y the y position of the painted border
     * @param width the width of the painted border
     * @param height the height of the painted border
     */
    public void paintBorder ( Component c, Graphics g, int x, int y, int width, int height )
    {
        Color   oldColor;
        int     i, ic, x1, y1, h, w;

        // x=0, y=0, width=40, height=40
        //Logger.getInstance().debug ( "--- PortBorder.paintBorder: Start. x=", x, ", y=", y, ", width=", width, ", height=", height );
        // JLabel
        //Logger.getInstance().debug ( "--- PortBorder.paintBorder: Component = ", c );

        oldColor = g.getColor();

        g.setColor ( getLineColor() );

        // рисуем общую рамку
        for ( i = 0; i < getThickness(); i++ )
        {
            g.drawRect ( x + i, y + i, width - i - i - 1, height - i - i - 1 );
        }

        // --------------- рисуем ярлычок разьема (слева) ---------------------
        // - одна пятая размера.
        ic  = height / 5;
        // - толщина - 1 /20 часть размера
        w   = width / 10;
        h   = ic + ic;
        // - начальные координаты
        //g.drawRect ( x + getThickness(), y + getThickness(), w, h );
        g.fillRect ( x + getThickness(), y + getThickness(), w, h );
        // 2)
        y1  = y + (ic*3);
        h   = height - y1;
        g.fillRect ( x + getThickness(), y1, w, h );

        // ------------------ рисуем 8 штырьков (справа)   -----------------------------
        // - всего 17 отрезков. Вычисляем их размер исходя из размера компоненты.
        ic  = height / 17;
        // - толщина  - такая же - w
        x1  = width - w;
        for ( i=2; i<17; i=i+2 )
        {
            g.fillRect ( x1, y + ( ic*i), w, ic );
        }

        g.setColor ( oldColor );
    }

    public Insets getBorderInsets ( Component c )
    {
        int ic;
        ic  = getThickness() + 2;
        return new Insets ( ic, ic, ic, ic );
    }

    public Insets getBorderInsets ( Component c, Insets insets )
    {
        insets.left = insets.top = insets.right = insets.bottom = thickness;
        return insets;
    }

    public Color getLineColor ()
    {
        return lineColor;
    }

    public int getThickness ()
    {
        return thickness;
    }

    public boolean isBorderOpaque ()
    {
        return true;
    }

}
