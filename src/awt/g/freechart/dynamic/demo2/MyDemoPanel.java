package awt.g.freechart.dynamic.demo2;


import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.DefaultXYItemRenderer;
import org.jfree.data.time.Millisecond;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 23.09.2011 10:12:56
 */
public class MyDemoPanel extends DemoPanel
    implements ActionListener
{
    private TimeSeries  series1, series2;
    private double      lastValue1, lastValue2;


    public void actionPerformed ( ActionEvent actionevent )
    {
        boolean     flag1, flag2;
        Millisecond mSec;
        double      d;

        flag1 = false;
        flag2 = false;

        // Вычисляем, какой буфер данных необходимо наполнить (по random)
        if ( actionevent.getActionCommand().equals("ADD_DATA_1"))
            flag1 = true;
        else if(actionevent.getActionCommand().equals("ADD_DATA_2"))
            flag2 = true;
        else if(actionevent.getActionCommand().equals("ADD_BOTH"))
        {
            flag1  = true;
            flag2 = true;
        }

        // Наполняем буфера данными
        if ( flag1 )
        {
            d           = 0.90000000000000002D + 0.20000000000000001D * Math.random();
            lastValue1  = lastValue1 * d;
            mSec        = new Millisecond();
            System.out.println ( "Now G-1 = " + mSec.toString() );
            series1.add ( mSec, lastValue1 );
        }

        if ( flag2 )
        {
            d           = 0.90000000000000002D + 0.20000000000000001D * Math.random();
            lastValue2  = lastValue2 * d;
            mSec        = new Millisecond();
            System.out.println ( "Now G-2 = " + mSec.toString() );
            series2.add ( mSec, lastValue2 );
        }
    }

    public MyDemoPanel()
    {
        super ( new BorderLayout() );

        TimeSeriesCollection    tsc1, tsc2;
        JFreeChart              jfreechart;
        XYPlot                  xyPlot;
        ValueAxis               vAxis;
        NumberAxis              nAxis;
        ChartPanel              chartPanel;
        JButton                 jbutton;
        JPanel                  buttonPanel;

        lastValue1  = 100D;
        lastValue2  = 500D;
        series1     = new TimeSeries("Random 1");
        series2     = new TimeSeries("Random 2");

        tsc1        = new TimeSeriesCollection ( series1 );
        tsc2        = new TimeSeriesCollection ( series2 );

        jfreechart  = ChartFactory.createTimeSeriesChart ( "Dynamic Data Demo 2", "Time", "Value", tsc1, true, true, false );
        addChart ( jfreechart );

        xyPlot  = (XYPlot) jfreechart.getPlot();

        vAxis   = xyPlot.getDomainAxis();
        vAxis.setAutoRange ( true );
        vAxis.setFixedAutoRange ( 10000D );

        xyPlot.setDataset ( 1, tsc2 );

        nAxis = new NumberAxis ( "Range Axis 2" );
        nAxis.setAutoRangeIncludesZero ( false );

        xyPlot.setRenderer ( 1, new DefaultXYItemRenderer() );
        xyPlot.setRangeAxis ( 1, nAxis );
        xyPlot.mapDatasetToRangeAxis ( 1, 1 );

        ChartUtilities.applyCurrentTheme ( jfreechart );

        chartPanel = new ChartPanel ( jfreechart );
        add ( chartPanel );

        buttonPanel = new JPanel ( new FlowLayout() );
        buttonPanel.setBackground ( Color.white );
        add ( buttonPanel, "South" );

        // Кнопки
        jbutton = new JButton("Add To Series 1");
        jbutton.setActionCommand("ADD_DATA_1");
        jbutton.addActionListener(this);
        buttonPanel.add ( jbutton );

        jbutton = new JButton("Add To Series 2");
        jbutton.setActionCommand("ADD_DATA_2");
        jbutton.addActionListener(this);
        buttonPanel.add ( jbutton );

        jbutton = new JButton("Add To Both");
        jbutton.setActionCommand("ADD_BOTH");
        jbutton.addActionListener(this);
        buttonPanel.add ( jbutton );

        chartPanel.setPreferredSize ( new Dimension(500, 270) );
    }

}
