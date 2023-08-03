package grafics.grafics2;


//----------------------------------------------------------------------------
//	��������� �������� ���������� ������ �� ������ ���� 1998
//			������� ������		(3832) 18-81-22
//----------------------------------------------------------------------------
import java.applet.*;
import java.awt.*;
import java.awt.Toolkit;
import java.awt.image.*;
import java.util.Date;
import java.util.Hashtable;
import java.util.*;
import java.lang.Integer;

//----------------------------------------------------------------------------
public class d1 extends Applet {
//    MediaTracker tracker;
//    Image art;
    int bb;
    int xmax, ymax;

//--------------------------------------------------------------------
  public void init () {
    xmax = size().width;
    ymax = size().height;

  }

//--------------------------------------------------------------------

  public void run()  {
/*
    timer.setPriority (Thread.MIN_PRIORITY);
    while (true) {
      try {
        timer.sleep(1000);
      }
      catch (InterruptedException e) {
        ;	// break
      }
      dlr = dlr - 1;
      repaint();
    }
*/
    repaint();
  }

//--------------------------------------------------------------------
  public void paint (Graphics g) {
    update (g);
  }

//--------------------------------------------------------------------
/*
  public void start() {
    try {
      Date dnach = new Date();		// ����� ������� ����
      long dln = dnach.getTime();		// � ��
//      Date dnewy = new Date(99, 0, 1, 0, 0, 0);	//	����� ��� - 1998 (98)
      Date dnewy = god;			// ����, �� ������� ���� ������
      long dlnew = dnewy.getTime();	// � ��
      dlr = (dlnew - dln) / 1000;	// ����� � ��� - 6 ����� (������-�� ����� ��� ���� �� ��������)
    } catch (Exception e) {;}

    if (timer == null) {
      timer=new Thread(this);
      timer.start();
    }
  }

//--------------------------------------------------------------------

  public void stop()  {
    if (timer != null)  {
      timer.stop();
      timer=null;
    }
  }
*/
//--------------------------------------------------------------------

  public void update(Graphics g)  {
      g.setColor (new Color(0x2B2E73));		// 0070FF 0x66CCFF
      g.fillRect (0, 0, xmax, ymax);

      //beep();
  }

//--------------------------------------------------------------------

}
