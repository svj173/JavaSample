package awt.g.freechart.dynamic.demo2;

import org.jfree.chart.JFreeChart;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class DemoPanel extends JPanel
{
    List charts;

    public DemoPanel ( java.awt.LayoutManager layoutManager )
    {
        super ( layoutManager );
        charts = new ArrayList();
    }

    public void addChart ( JFreeChart jfreechart )
    {
        charts.add ( jfreechart );
    }

    public JFreeChart[] getCharts()
    {
        int i = charts.size();
        JFreeChart ajfreechart[] = new JFreeChart[i];
        for(int j = 0; j < i; j++)
            ajfreechart[j] = (JFreeChart)charts.get(j);

        return ajfreechart;
    }

}
