package awt.g.freechart;


/**
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 28.06.2011 16:09:52
 */

import awt.g.freechart.dataset.SampleXYDataset;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * A simple demo showing mouse zooming.
 *
 * @author Viktor Rajewski
 */
public class MouseZoomDemo extends ApplicationFrame {

    /** The chart panel. */
    private ChartPanel chartPanel;

    /** X zoom. */
    private JCheckBox xzoom;

    /** Y zoom. */
    private JCheckBox yzoom;

    /**
     * A demonstration of mouse zooming.
     *
     * @param title  the frame title.
     */
    public MouseZoomDemo(String title) {

        super(title);
        SampleXYDataset data = new SampleXYDataset ();
        
        JFreeChart chart = ChartFactory.createXYLineChart(
            "Mouse Zoom Demo",
            "X",
            "Y",
            data,
            PlotOrientation.VERTICAL,
            true,
            true,
            false
        );

        this.chartPanel = new ChartPanel(chart);
        //this.chartPanel.setHorizontalZoom(false);
        //this.chartPanel.setVerticalZoom(false);
        this.chartPanel.setHorizontalAxisTrace(false);
        this.chartPanel.setVerticalAxisTrace(false);
        this.chartPanel.setFillZoomRectangle(true);
        this.chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));

        JPanel main = new JPanel(new BorderLayout());
        JPanel checkpanel = new JPanel();
        this.xzoom = new JCheckBox("Horizontal Mouse Zooming");
        this.xzoom.setSelected(false);
        this.yzoom = new JCheckBox("Vertical Mouse Zooming");
        this.yzoom.setSelected(false);
        CheckListener clisten = new CheckListener();
        this.xzoom.addItemListener(clisten);
        this.yzoom.addItemListener(clisten);
        checkpanel.add(this.xzoom);
        checkpanel.add(this.yzoom);
        main.add(checkpanel, BorderLayout.SOUTH);
        main.add(this.chartPanel);
        setContentPane(main);

    }

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

    /**
     * Starting point for the demonstration application.
     *
     * @param args  ignored.
     */
    public static void main(String[] args) {

        MouseZoomDemo demo = new MouseZoomDemo("Mouse Zoom Demo");
        demo.pack();
        RefineryUtilities.centerFrameOnScreen(demo);
        demo.setVisible(true);

    }

    /**
     * An item listener.
     *
     * @author VR
     */
    class CheckListener implements ItemListener {

        /**
         * Receives change events.
         *
         * @param e  the event.
         */
        public void itemStateChanged(ItemEvent e) {
            Object source = e.getItemSelectable();
            if (source == xzoom) {
                if (e.getStateChange() == ItemEvent.DESELECTED) {
                    //chartPanel.setHorizontalZoom(false);
                    chartPanel.setHorizontalAxisTrace(false);
                    chartPanel.repaint();
                }
                else {
                    //chartPanel.setHorizontalZoom(true);
                    chartPanel.setHorizontalAxisTrace(true);
                }
            }
            else if (source == yzoom) {
                if (e.getStateChange() == ItemEvent.DESELECTED) {
                    //chartPanel.setVerticalZoom(false);
                    chartPanel.setVerticalAxisTrace(false);
                    chartPanel.repaint();
                }
                else {
                    //chartPanel.setVerticalZoom(true);
                    chartPanel.setVerticalAxisTrace(true);
                }
            }
       }
    }

}

