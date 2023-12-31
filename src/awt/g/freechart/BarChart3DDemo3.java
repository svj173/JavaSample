package awt.g.freechart;


import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

import javax.swing.*;
import java.awt.*;

public class BarChart3DDemo3 extends ApplicationFrame
{

    public BarChart3DDemo3(String s)
    {
        super(s);
        JPanel jpanel = createDemoPanel();
        jpanel.setPreferredSize(new Dimension(500, 270));
        setContentPane(jpanel);
    }

    private static CategoryDataset createDataset()
    {
        DefaultCategoryDataset defaultcategorydataset = new DefaultCategoryDataset();
        defaultcategorydataset.addValue(25D, "Series 1", "Category 1");
        defaultcategorydataset.addValue(34D, "Series 1", "Category 2");
        defaultcategorydataset.addValue(19D, "Series 2", "Category 1");
        defaultcategorydataset.addValue(29D, "Series 2", "Category 2");
        defaultcategorydataset.addValue(41D, "Series 3", "Category 1");
        defaultcategorydataset.addValue(33D, "Series 3", "Category 2");
        return defaultcategorydataset;
    }

    private static JFreeChart createChart(CategoryDataset categorydataset)
    {
        JFreeChart              jfreechart;
        CategoryPlot            categoryplot;
        CategoryAxis            categoryaxis;
        CategoryItemRenderer    categoryitemrenderer;

        jfreechart = ChartFactory.createBarChart3D("3D Bar Chart Demo", "Category", "Value", categorydataset, PlotOrientation.VERTICAL, true, true, false);

        categoryplot = (CategoryPlot)jfreechart.getPlot();

        categoryaxis = categoryplot.getDomainAxis();

        // Наклоняем подпись под горизонтальнйо осью.
        categoryaxis.setCategoryLabelPositions( CategoryLabelPositions.createUpRotationLabelPositions(0.39269908169872414D));

        categoryitemrenderer = categoryplot.getRenderer();
        categoryitemrenderer.setBaseItemLabelsVisible(true);

        BarRenderer barrenderer = (BarRenderer)categoryitemrenderer;
        barrenderer.setItemMargin(0.20000000000000001D);

        return jfreechart;
    }

    public static JPanel createDemoPanel()
    {
        JFreeChart jfreechart = createChart(createDataset());
        return new ChartPanel(jfreechart);
    }

    public static void main(String args[])
    {
        BarChart3DDemo3 barchart3ddemo3 = new BarChart3DDemo3("JFreeChart: BarChart3DDemo3.java");
        barchart3ddemo3.pack();
        RefineryUtilities.centerFrameOnScreen(barchart3ddemo3);
        barchart3ddemo3.setVisible(true);
    }
}
