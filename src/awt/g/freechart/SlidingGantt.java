package awt.g.freechart;


/**
 * Диаграмму Ганта я выводить научился, пытаюсь теперь добавить в неё полосу прокрутки.
 * Для этого использую класс SlidingGanttCategoryDataset. Если я правильно понимаю, то в его конструкторе один из
 * параметров отвечает за количество выводимых на панель Task'ов.
 * Но проблема в том, что полоса прокрутки не появляется...
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 28.06.2011 15:33:30
 */

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.gantt.*;
import org.jfree.data.time.SimpleTimePeriod;
import org.jfree.ui.ApplicationFrame;

import java.util.Calendar;
import java.util.Date;


@SuppressWarnings ( "serial" )
public class SlidingGantt extends ApplicationFrame
{
    public static JFreeChart chart;

    public SlidingGantt ( String title ) {super ( title );}


    private static SlidingGanttCategoryDataset createDataset ()
    {
        TaskSeries s1 = new TaskSeries ( "Scheduled1" );

        s1.add ( new Task ( "one", new SimpleTimePeriod ( date ( 1, Calendar.JULY, 2008 ),
                                                          date ( 2, Calendar.JULY, 2008 ) ) ) );

        s1.add ( new Task ( "two", new SimpleTimePeriod ( date ( 3, Calendar.JULY, 2008 ),
                                                          date ( 4, Calendar.JULY, 2008 ) ) ) );

        s1.add ( new Task ( "three", new SimpleTimePeriod ( date ( 4, Calendar.JULY, 2008 ),
                                                            date ( 9, Calendar.JULY, 2008 ) ) ) );

        s1.add ( new Task ( "four", new SimpleTimePeriod ( date ( 11, Calendar.JULY, 2008 ),
                                                           date ( 12, Calendar.JULY, 2008 ) ) ) );

        s1.add ( new Task ( "five", new SimpleTimePeriod ( date ( 12, Calendar.JULY, 2008 ),
                                                           date ( 17, Calendar.JULY, 2008 ) ) ) );


        TaskSeries s2 = new TaskSeries ( "Scheduled2" );

        s2.add ( new Task ( "one", new SimpleTimePeriod ( date ( 1, Calendar.JULY, 2008 ),
                                                          date ( 2, Calendar.JULY, 2008 ) ) ) );

        s2.add ( new Task ( "two", new SimpleTimePeriod ( date ( 7, Calendar.JULY, 2008 ),
                                                          date ( 8, Calendar.JULY, 2008 ) ) ) );

        s2.add ( new Task ( "three", new SimpleTimePeriod ( date ( 9, Calendar.JULY, 2008 ),
                                                            date ( 13, Calendar.JULY, 2008 ) ) ) );

        s2.add ( new Task ( "four", new SimpleTimePeriod ( date ( 13, Calendar.JULY, 2008 ),
                                                           date ( 15, Calendar.JULY, 2008 ) ) ) );

        s2.add ( new Task ( "five", new SimpleTimePeriod ( date ( 16, Calendar.JULY, 2008 ),
                                                           date ( 17, Calendar.JULY, 2008 ) ) ) );


        TaskSeriesCollection taskcollect = new TaskSeriesCollection ();

        taskcollect.add ( s1 );
        taskcollect.add ( s2 );

        GanttCategoryDataset gantt = taskcollect;

        SlidingGanttCategoryDataset dataset = new SlidingGanttCategoryDataset ( gantt, 0, 3 );

        return dataset;
    }


    private static Date date ( final int day, final int month, final int year )
    {
        Calendar calendar = Calendar.getInstance ();
        calendar.set ( year, month, day );
        Date result = calendar.getTime ();
        return result;
    }


    public void createChart ()
    {
        SlidingGanttCategoryDataset dataset = createDataset ();

        chart = ChartFactory.createGanttChart ( "График", "Задача", "Дата", dataset, true, true, false  );
        ChartPanel chartPanel = new ChartPanel ( chart );
        chartPanel.setPreferredSize ( new java.awt.Dimension ( 500, 270 ) );
        setContentPane ( chartPanel );
        pack ();
        setVisible ( true );
    }

    public static void main ( String[] args )
    {
        SlidingGantt demo = new SlidingGantt ( "SlidingGantt" );
        demo.createChart ();
    }

}
