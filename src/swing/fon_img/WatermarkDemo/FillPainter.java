package swing.fon_img.WatermarkDemo;



import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * An extension of <code>WatermarkPainter</code> that
 * tiles an image to fill the entire component.
 *
 * @version 1.1 06/20/2002
 * @author Shannon Hickey
 */
public class FillPainter extends WatermarkPainter {

    /** The image to paint in the background */
    private Image bgImage;

    public FillPainter() {
        bgImage = getImage(getClass().getResource("beach.jpg"));
    }

    public String[] getCommands() {
        return new String[] {"beach.jpg",
                             "rocks_waves.jpg",
                             "green_swirl.jpg",
                             "fire_ring.jpg"};
    }

    public void paint(Graphics g) {
        // if a background image exists, paint it
        if (bgImage != null) {
            int width = getComponent().getWidth();
            int height = getComponent().getHeight();
            int imageW = bgImage.getWidth(null);
            int imageH = bgImage.getHeight(null);

            // we'll tile the image to fill our area
            for (int x = 0; x < width; x += imageW) {
                for (int y = 0; y < height; y += imageH) {
                    g.drawImage(bgImage, x, y, getComponent());
                }
            }
        }
    }

    public void actionPerformed(ActionEvent ae) {
        bgImage = getImage(getClass().getResource(ae.getActionCommand()));
        getComponent().repaint();
    }

}
