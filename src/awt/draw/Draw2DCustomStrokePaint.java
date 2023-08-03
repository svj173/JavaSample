package awt.draw;


/**
 * Pen Fill
 * <BR/> Рисует фиксированные квадратики крестики круги, причем - с пунктирными краями.
 *
 * <BR/> User: svj
 * <BR/> Date: 02.12.2010 9:41:50
 */
import java.awt.BasicStroke;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.TexturePaint;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;
public class Draw2DCustomStrokePaint extends JFrame {
  Shape shapes[] = new Shape[5];
  public static void main(String args[]) {
    Draw2DCustomStrokePaint app = new Draw2DCustomStrokePaint();
  }
  public Draw2DCustomStrokePaint() {
    add("Center", new MyCanvas());
    for (int i = 0; i < shapes.length; ++i)
      shapes[i] = null;
    shapes[0] = new Line2D.Double(0.0, 0.0, 100.0, 100.0);
    shapes[1] = new Rectangle2D.Double(10.0, 100.0, 200.0, 200.0);
    shapes[2] = new Ellipse2D.Double(20.0, 200.0, 100.0, 100.0);
    GeneralPath path = new GeneralPath(new Line2D.Double(300.0, 100.0, 400.0, 150.0));
    path.append(new Line2D.Double(25.0, 175.0, 300.0, 100.0), true);
    shapes[3] = path;
    shapes[4] = new RoundRectangle2D.Double(350.0, 250, 200.0, 100.0, 50.0, 25.0);
    setSize(400, 400);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setVisible(true);
  }
  class MyCanvas extends Canvas {
    public void paint(Graphics graphics) {
      Graphics2D g = (Graphics2D) graphics;
      float[] dashPattern = { 10.0f, 10.0f, 5.0f, 5.0f };
      g.setStroke(new BasicStroke(5, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER, 10.0f,
          dashPattern, 0.0f));
      g.draw(shapes[0]);
      g.setPaint(Color.blue);
      g.draw(shapes[1]);
      g.fill(shapes[1]);
      g.setPaint(new GradientPaint(350.0f, 200.0f, Color.red, 325.0f, 175.0f, Color.green));
      g.draw(shapes[2]);
      g.fill(shapes[2]);
      g.setColor(Color.black);
      g.draw(shapes[3]);
      TexturePaint texture = new TexturePaint(createBufferedImage(), new Rectangle2D.Double(350.0,
          250, 200.0, 100.0));
      g.setPaint(texture);
      g.draw(shapes[4]);
      g.fill(shapes[4]);
    }
    BufferedImage createBufferedImage() {
      BufferedImage image = new BufferedImage(20, 20, BufferedImage.TYPE_INT_RGB);
      Graphics2D g = image.createGraphics();
      g.draw(new Line2D.Double(0.0, 0.0, 10.0, 10.0));
      g.draw(new Line2D.Double(0.0, 10.0, 10.0, 0.0));
      return image;
    }
  }
}



