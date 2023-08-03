package awt.g.freechart;


import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.chart.renderer.category.LayeredBarRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;
import org.jfree.util.SortOrder;
import tools.Convert;

import javax.swing.*;
import java.awt.*;
import java.util.Date;
import java.util.Random;

public class LayeredBarChartDemo_02 extends ApplicationFrame
{

    public LayeredBarChartDemo_02 (String s)
    {
        super(s);
        JPanel jpanel = createDemoPanel();
        jpanel.setPreferredSize(new Dimension(2500, 470));

        setContentPane( new JScrollPane (jpanel) );
    }

    private static CategoryDataset createDataset()
    {
        DefaultCategoryDataset dataset;
        String titleConfig, titleState, titleError, packet;
        Random randomGenerator;
        int configCount, stateCount, errorCount, size;
        Date date;

        randomGenerator = new Random();

        dataset     = new DefaultCategoryDataset();

        titleConfig = "Конфигурации";
        titleState  = "Активные";
        titleError  = "Ошибки";

        //size    = 200;
        //size    = 100;
        //size    = 50;
        size    = 30;

        for ( int i=0; i<size; i++ )
        {
            date        = new Date();
            packet      = Convert.getDateAsStr ( date, "dd.MM.yyyy HH:mm:ss.SSS" );
            // генерим значение для конфига
            configCount = randomGenerator.nextInt(200);
            stateCount  = randomGenerator.nextInt(150);
            errorCount  = randomGenerator.nextInt(11);
            //
            dataset.addValue ( configCount, titleConfig, packet );
            dataset.addValue ( stateCount,  titleState,  packet );
            dataset.addValue ( errorCount,  titleError,  packet );
            // pause
            try
            {
                Thread.sleep ( 10 );
            } catch ( InterruptedException e )            {
                e.printStackTrace();
            }
        }

        return dataset;
    }

    private static JFreeChart createChart ( CategoryDataset categorydataset )
    {
        JFreeChart          jfreechart;
        CategoryPlot        categoryplot;
        NumberAxis          numberaxis;
        LayeredBarRenderer  layeredbarrenderer;
        CategoryAxis        categoryaxis;
        CategoryItemRenderer categoryitemrenderer;
        int type;
        GradientPaint gradientpaint, gradientpaint1, gradientpaint2;
        Paint paint;

        type    = 1;

        switch ( type )
        {
            case 1: // 3D
                jfreechart      = ChartFactory.createBarChart3D("Статистика состава ОНТ для ...", "Время", "шт.", categorydataset, PlotOrientation.VERTICAL, true, true, false);
                categoryplot = (CategoryPlot)jfreechart.getPlot();

                categoryaxis = categoryplot.getDomainAxis();

                // Наклоняем подпись под горизонтальнйо осью.
                //categoryaxis.setCategoryLabelPositions( CategoryLabelPositions.createUpRotationLabelPositions(0.39269908169872414D));
                categoryaxis.setCategoryLabelPositions( CategoryLabelPositions.UP_90 );  // вправо вниз на 45

                categoryitemrenderer = categoryplot.getRenderer();
                categoryitemrenderer.setBaseItemLabelsVisible(true);

                BarRenderer barrenderer = (BarRenderer)categoryitemrenderer;
                // расстояние между пакетами данных. Чем это значение больше, тем элементы в пакете - уже.
                barrenderer.setItemMargin(0.1D);
                //barrenderer.setItemMargin(0.2D);
                //barrenderer.setItemMargin(0.4D);

                /*
                gradientpaint  = new GradientPaint(0.0F, 0.0F, Color.blue, 0.0F, 0.0F, new Color(0, 0, 16));
                gradientpaint1 = new GradientPaint(0.0F, 0.0F, Color.green, 0.0F, 0.0F, new Color(0, 64, 0));
                gradientpaint2 = new GradientPaint(0.0F, 0.0F, Color.red, 0.0F, 0.0F, new Color(64, 0, 0));

                barrenderer.setSeriesPaint(0, gradientpaint);
                barrenderer.setSeriesPaint(1, gradientpaint1);
                barrenderer.setSeriesPaint(2, gradientpaint2);
                //*/
                paint   = barrenderer.getSeriesPaint ( 0 );
                System.out.println ( "paint = " + paint );
                break;

            default:
            case 2:
                jfreechart      = ChartFactory.createBarChart("Статистика состава ОНТ для ...", "Время", "шт.", categorydataset, PlotOrientation.VERTICAL, true, true, false);

                categoryplot    = (CategoryPlot) jfreechart.getPlot();
                categoryplot.setDomainGridlinesVisible(true);
                categoryplot.setRangePannable(true);
                categoryplot.setRangeZeroBaselineVisible(true);

                numberaxis = (NumberAxis)categoryplot.getRangeAxis();
                numberaxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

                /*
                // Устанавливаем границы отображения - попытка своего локального скроллинга
                // Set chart boundaries (rangeMinX, rangeMaxX, rangeMinY, rangeMaxY).
                NumberAxis numberaxisX = (NumberAxis)plot.getDomainAxis();
                numberaxisX.setRange(rangeMinX, rangeMaxX);
                NumberAxis numberaxisY = (NumberAxis)plot.getRangeAxis();
                numberaxisY.setRange(rangeMinY, rangeMaxY);
                 */

                // Устанавливаем отрисовщик - располагать прямоугольники друг на друге.
                layeredbarrenderer = new LayeredBarRenderer();
                layeredbarrenderer.setDrawBarOutline(false);

                categoryplot.setRenderer(layeredbarrenderer);
                categoryplot.setRowRenderingOrder(SortOrder.DESCENDING);

                categoryaxis = categoryplot.getDomainAxis();

                // Наклоняем подпись под горизонтальной осью.
                //categoryaxis.setCategoryLabelPositions( CategoryLabelPositions.createUpRotationLabelPositions(0.39269908169872414D));  // влево вниз на 30
                //categoryaxis.setCategoryLabelPositions( CategoryLabelPositions.createUpRotationLabelPositions(0.5D));  // влево вниз на 35
                //categoryaxis.setCategoryLabelPositions( CategoryLabelPositions.createUpRotationLabelPositions(1D));  // влево вниз на 45
                //categoryaxis.setCategoryLabelPositions( CategoryLabelPositions.createUpRotationLabelPositions(2D));  // вправо вниз на 45
                //categoryaxis.setCategoryLabelPositions( CategoryLabelPositions.createUpRotationLabelPositions(1.5D));  // вправо вниз на 45
                categoryaxis.setCategoryLabelPositions( CategoryLabelPositions.DOWN_90 );  // вправо вниз на 45
                /*
                gradientpaint  = new GradientPaint(0.0F, 0.0F, Color.blue, 0.0F, 0.0F,  new Color(0, 0, 64));
                gradientpaint1 = new GradientPaint(0.0F, 0.0F, Color.green, 0.0F, 0.0F, new Color(0, 64, 0));
                gradientpaint2 = new GradientPaint(0.0F, 0.0F, Color.red, 0.0F, 0.0F,   new Color(64, 0, 0));

                layeredbarrenderer.setSeriesPaint(0, gradientpaint);
                layeredbarrenderer.setSeriesPaint(1, gradientpaint1);
                layeredbarrenderer.setSeriesPaint(2, gradientpaint2);
                */
                break;
        }

        return jfreechart;
    }

    public static JPanel createDemoPanel()
    {
        JFreeChart jfreechart;
        ChartPanel chartpanel;

        jfreechart = createChart ( createDataset() );

        chartpanel = new ChartPanel(jfreechart);
        chartpanel.setMouseWheelEnabled(true);

        return chartpanel;
    }

    public static void main(String args[])
    {
        LayeredBarChartDemo_02 layeredbarchartdemo1;

        layeredbarchartdemo1 = new LayeredBarChartDemo_02 ("JFreeChart: LayeredBarChartDemo_02.java");
        layeredbarchartdemo1.pack();
        RefineryUtilities.centerFrameOnScreen ( layeredbarchartdemo1 );

        layeredbarchartdemo1.setVisible(true);
    }

}