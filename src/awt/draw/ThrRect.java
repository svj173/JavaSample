package awt.draw;


/**
 * Трехмерный прямоугольник
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 02.12.2010 10:12:22
 */

import java.awt.*;
import javax.swing.*;

public class ThrRect extends JApplet
{
    public void draw3D ( Graphics g, int x, int y, int width, int height, boolean isRaised, boolean isFilled )
    {
        // (int x, int y, int width, int height,  boolean raised)
        g.draw3DRect ( x, y, width-1, height-1, isRaised );

        g.draw3DRect ( x + 1, y + 1, width-3, height-3, isRaised );

        g.draw3DRect ( x + 2, y + 2, width-5, height-5, isRaised );

        if ( isFilled )
            g.fillRect ( x + 3, y + 3, width-6, height-6 );
    }

    public void paint ( Graphics g )
    {
        g.setColor ( Color.GRAY );
        draw3D ( g, 10, 5, 80, 40, true, false );
        draw3D ( g, 130, 5, 80, 40, false, false );
        draw3D ( g, 10, 55, 80, 40, true, true );
        draw3D ( g, 130, 55, 80, 40, false, true );
    }

}
