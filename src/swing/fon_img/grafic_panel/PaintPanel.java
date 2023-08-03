package swing.fon_img.grafic_panel;

import javax.swing.*;
import java.awt.*;

/**
 * <BR>
 * <BR>
 * <BR> User: svj
 * <BR> Date: 24.04.2006
 * <BR> Time: 12:06:43
 */
public class PaintPanel extends JPanel
{

    /**
     * Since we're always going to fill our entire bounds, allow Swing to optimize painting of us.
     */
    public boolean isOpaque() {
        return true;
    }

    /**
     * We'll do our own painting, so leave out a call to the superclass behavior.
     * We're telling Swing that we're opaque, and we'll honor this by always
     * filling our our entire bounds with solid colors.
     * @param g
     */
    protected void paintComponent( Graphics g)
    {
        int w, h, leftW, topH, xsp, ysp, xsize, ysize, xrad, yrad, xm, ym;

        w = getWidth();
        h = getHeight();

        /*
        leftW = w / 2;
        topH = h / 2;

        // Paint the top left and bottom right in red.
        g.setColor(Color.RED);
        g.fillRect(0, 0, leftW, topH);
        //g.fillRect(leftW, topH, w - leftW, h - topH);
        g.fillRoundRect(leftW, topH, w - leftW, h - topH, 30, 30);

        // Paint the bottom left and top right in white.
        g.setColor(Color.WHITE);
        g.fillRect(0, topH, leftW, h - topH);
        g.fillRect(leftW, 0, w - leftW, topH);
        */

        // Common Fon
        //g.setColor(Color.GREEN);
        //g.setColor(Color.getColor ( "00988F" ));
        g.setColor(Color.decode ( "0x00988F" ));
        g.fillRect(0, 0, w, h);


        g.setColor(Color.decode ( "0x73BABA" ));

        xsp     = ysp   = 30;
        xrad    = yrad  = 30;
        xsize   = w - xsp * 2;
        ysize   = h - ysp * 2;
        g.fillRoundRect(xsp, ysp, xsize, ysize, xrad, yrad);

        g.setColor(Color.WHITE);
        xm = 30;
        ym  = 20;
        g.fillRoundRect(xsp+xm, ysp+ym, xsize-xm*2, ysize-ym*2, xrad, yrad);

    }

}
