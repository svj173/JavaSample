package swing.progressbar;


import javax.swing.*;


/**
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 03.02.2011 10:05:39
 */
public class VerticalProgressBar
{
    public static void main(String[] argv) throws Exception {
      int minimum = 0;
      int maximum = 100;
      JProgressBar progress = new JProgressBar( JProgressBar.VERTICAL, minimum, maximum);
    }
}
