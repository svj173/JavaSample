package awt.g.freechart.demo;


import awt.g.freechart.source.StandardPieItemLabelGenerator;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartRenderingInfo;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.entity.StandardEntityCollection;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.urls.StandardPieURLGenerator;
import org.jfree.data.general.DefaultPieDataset;

import java.io.*;

/**
 * A demo showing how to create an HTML image map for a pie chart.
 *
 */
public class ImageMapDemo2 {

    /**
     * Default constructor.
     */
    public ImageMapDemo2() {
        super();
    }

    /**
     * The starting point for the demo.
     *
     * @param args  ignored.
     */
    public static void main(final String[] args) {

        // create a chart
        final DefaultPieDataset data = new DefaultPieDataset();
        data.setValue("One", new Double(43.2));
        data.setValue("Two", new Double(10.0));
        data.setValue("Three", new Double(27.5));
        data.setValue("Four", new Double(17.5));
        data.setValue("Five", new Double(11.0));
        data.setValue("Six", new Double(19.4));

        JFreeChart chart = null;
        final boolean drilldown = true;

        // create the chart...
        if (drilldown) {
            final PiePlot plot = new PiePlot(data);
  //          plot.setInsets(new Insets(0, 5, 5, 5));
            plot.setToolTipGenerator(new StandardPieItemLabelGenerator ());
            plot.setURLGenerator(new StandardPieURLGenerator("pie_chart_detail.jsp"));
            chart = new JFreeChart("Pie Chart Demo 1", JFreeChart.DEFAULT_TITLE_FONT, plot, true);
        }
        else {
            chart = ChartFactory.createPieChart(
                "Pie Chart Demo 1",  // chart title
                data,                // data
                true,                // include legend
                true,
                false
            );
        }
        chart.setBackgroundPaint(java.awt.Color.white);

        // ****************************************************************************
        // * JFREECHART DEVELOPER GUIDE                                               *
        // * The JFreeChart Developer Guide, written by David Gilbert, is available   *
        // * to purchase from Object Refinery Limited:                                *
        // *                                                                          *
        // * http://www.object-refinery.com/jfreechart/guide.html                     *
        // *                                                                          *
        // * Sales are used to provide funding for the JFreeChart project - please    * 
        // * support us so that we can continue developing free software.             *
        // ****************************************************************************
        
        // save it to an image
        try {
            final ChartRenderingInfo info = new ChartRenderingInfo(new StandardEntityCollection());
            final File file1 = new File("piechart100.png");
            ChartUtilities.saveChartAsPNG(file1, chart, 600, 400, info);

            // write an HTML page incorporating the image with an image map
            final File file2 = new File("piechart100.html");
            final OutputStream out = new BufferedOutputStream(new FileOutputStream(file2));
            final PrintWriter writer = new PrintWriter(out);
            writer.println("<HTML>");
            writer.println("<HEAD><TITLE>JFreeChart Image Map Demo 2</TITLE></HEAD>");
            writer.println("<BODY>");
//            ChartUtilities.writeImageMap(writer, "chart", info);
            writer.println("<IMG SRC=\"piechart100.png\" "
                           + "WIDTH=\"600\" HEIGHT=\"400\" BORDER=\"0\" USEMAP=\"#chart\">");
            writer.println("</BODY>");
            writer.println("</HTML>");
            writer.close();

        }
        catch (IOException e) {
            System.out.println(e.toString());
        }

    }

}
