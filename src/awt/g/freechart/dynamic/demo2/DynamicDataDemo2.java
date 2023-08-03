package awt.g.freechart.dynamic.demo2;


import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

import javax.swing.*;

public class DynamicDataDemo2 extends ApplicationFrame
{

    public DynamicDataDemo2 ( String title )
    {
        super ( title );
        setContentPane ( createDemoPanel() );
    }

    public JPanel createDemoPanel()
    {
        return new MyDemoPanel();
    }

    public static void main ( String args[] )
    {
        DynamicDataDemo2 demo2;

        demo2 = new DynamicDataDemo2 ( "JFreeChart: Два графика в одном окне" );
        demo2.pack();
        RefineryUtilities.centerFrameOnScreen ( demo2 );
        demo2.setVisible(true);
    }
    
}
