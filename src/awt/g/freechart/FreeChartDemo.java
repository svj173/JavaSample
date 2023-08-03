package awt.g.freechart;


/**
 * Рисует круглый (секционный) график.
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 28.06.2011 15:25:13
 */

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

import javax.swing.*;
import java.awt.*;
import java.util.Vector;

public class FreeChartDemo
{
    public FreeChartDemo ( JPanel root_pnl, Vector<Vector> data, String title )
    {
        root_pnl.add ( createDemoPanel ( data, title ) );
    }

    private static PieDataset createDataset ( Vector<Vector> data )
    {
        DefaultPieDataset dataset = new DefaultPieDataset ();

        for ( int i = 0; i < data.size (); i++ )
        {
            dataset.setValue (
                    data.get ( i ).get ( 0 ).toString (),
                    Double.parseDouble ( data.get ( i ).get ( 1 ).toString () )
            );
        }
        return dataset;
    }

    private static JFreeChart createChart ( PieDataset dataset, String title )
    {

        JFreeChart chart = ChartFactory.createPieChart3D (
                title,  // chart title
                dataset,             // data
                true,               // include legend
                true,                //tool tips
                false                //urls
        );
        PiePlot plot = ( PiePlot ) chart.getPlot ();
        plot.setSectionOutlinesVisible ( false );
        plot.setLabelFont ( new Font ( "SansSerif", Font.PLAIN, 12 ) );
        plot.setNoDataMessage ( "No data available" );
        plot.setCircular ( false );
        plot.setLabelGap ( 0.02 );
        return chart;

    }

    public static JPanel createDemoPanel ( Vector<Vector> data,
                                           String title, Dimension dim )
    {
        JFreeChart chart = createChart ( createDataset ( data ), title );
        ChartPanel pnl = new ChartPanel ( chart );
        pnl.setSize ( dim );
        return pnl;
    }

    public static JPanel createDemoPanel ( Vector<Vector> data,
                                           String title )
    {
        JFreeChart chart = createChart ( createDataset ( data ), title );
        ChartPanel pnl = new ChartPanel ( chart );
        return pnl;
    }

    public static void main ( String[] args ) throws Exception
    {
        Vector<Vector> v = new Vector<Vector> ();
        Vector vv;

        vv = new Vector ();
        vv.add ( "Value1" );
        vv.add ( 5 );
        v.add ( vv );
        vv = new Vector ();
        vv.add ( "Value2" );
        vv.add ( 15 );
        v.add ( vv );

        vv = new Vector ();
        vv.add ( "Value4" );
        vv.add ( 3 );
        v.add ( vv );


        JFrame frm = new JFrame ();
        frm.setLocationRelativeTo ( null );
        frm.setSize ( 600, 600 );
        JPanel p = new JPanel ();
        frm.setDefaultCloseOperation ( JFrame.DISPOSE_ON_CLOSE );
        new FreeChartDemo ( p, v, "Free Chart Test" );
        frm.setVisible ( true );
        frm.add ( p );
    }
}

