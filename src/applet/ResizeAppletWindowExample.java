package applet;


/**
 *         Resize Applet Window Example
         This java example shows how to resize an aplet window using resize method
         of an applet class.

index.html
 <applet code="ResizeAppletWindowExample" width=200 height=200>
 </applet>

 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 02.04.2013 16:23
 */
import java.applet.Applet;
import java.awt.Graphics;

public class ResizeAppletWindowExample extends Applet
{

        public void paint(Graphics g)
        {
                /*
                 * To resize an applet window use,
                 * void resize(int x, int y) method of an applet class.
                 */
                resize(300,300);
                g.drawString("Window has been resized to 300,300", 50, 50);
        }

}