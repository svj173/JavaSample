package awt.animation;

/**
 * <BR>
 * <BR>
 * <BR> User: svj
 * <BR> Date: 22.05.2006
 * <BR> Time: 18:03:00
 */
public class Force
{
    private final static double g = 9.8; // not convention
       double b = 0;   // used in drag force

       public void setb(double b)
       {
          this.b = b;
       }

       public double getfx(double x, double y, double vx, double vy, Particle p)
       {
          return -b*vx;
       }

       public double getfy(double x, double y, double vx, double vy, Particle p)
       {
          return -b*vy - g*p.getMass();
       }
}
