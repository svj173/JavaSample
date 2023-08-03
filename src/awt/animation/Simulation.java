package awt.animation;

/**
 * <BR>
 * <BR>
 * <BR> User: svj
 * <BR> Date: 22.05.2006
 * <BR> Time: 18:01:16
 */
import java.awt.*;

public class Simulation extends Frame
{
   Image offscreen;

   public static void main(String[] args)
   {
      Simulation sim = new Simulation();   // set up window for application
      sim.calculateTrajectory();
   }

   public Simulation()
   {  // constructor
      setSize(512, 342);
      setVisible(true);
      offscreen = createImage(getSize().width, getSize().height);
   }

   public void paint(Graphics g)
   {
      setBackground(Color.white);
      g.drawImage(offscreen,0,0,this);
   }

   public void calculateTrajectory()
   {
      final double tmax = 10.0;
      final double dt = 0.005;
      Graphics b = offscreen.getGraphics();
      Particle p = new Particle(0.0, 200.0, 40.0, 25.0);
      Force f = new Force();
      double time = 0.0;
      while (time < tmax)
      {
         Rectangle oldRect = new Rectangle((int)p.getX(),getSize().height -(int)p.getY(),11,11);
         p.step(dt,f);
         time += dt;
         Rectangle newRect = new Rectangle((int)p.getX(),getSize().height -(int)p.getY(),11,11);
         // new region of ball
         Rectangle r = newRect.union(oldRect); // new plus old region
         // b is buffer. key to avoid flicker is not to call repaint
         b.clearRect(r.x,r.y,r.width,r.height);    // clear new plus old region on buffer
         b.fillOval((int)p.getX(), getSize().height - (int)p.getY(), 10, 10);
         Graphics g = getGraphics();
         g.drawImage(offscreen,0,0,this);
      }
   }
}
