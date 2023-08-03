package awt.g.freechart.sos;


import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.time.*;
import org.jfree.data.xy.XYDataset;
import sos.reports.*;

import javax.swing.*;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.util.Calendar;
import java.util.Random;



/**
 * A sample application that creates and displays a report that uses
 * line graphs, bar charts, and pie charts. The chart are created
 * using JFreeChart, available at www.jfree.org/jfreechart/.
 *
 * import sos.reports.*;
 * import sos.reports.Element;

 В одной панели рисует 6 графиков.
 *
 * @author  Side of Software
 */
public class ChartExample
{
  // a random number generator to generator the data
  private static final Random random = new Random( 0 );

  /**
   * Creates and returns the theme used to display
   * the nested report body.
   */
  private static Theme createBodyTheme()
  {
    // store the styles in a style context
    StyleContext styleContext = new StyleContext();

    // cells have a 12-point padding, and their contents
    // are rendered using a custom chart renderer
    Style cellStyle = styleContext.addStyle( "Cell", null );
    ReportStyleConstants.setPadding( cellStyle, 12.0f );
    ReportStyleConstants.setRenderer( cellStyle, new ChartRenderer () );
    styleContext.addStyle( "Cell0,0", cellStyle );
    styleContext.addStyle( "Cell0,1", cellStyle );
    styleContext.addStyle( "Cell1,0", cellStyle );
    styleContext.addStyle( "Cell1,1", cellStyle );
    styleContext.addStyle( "Cell2,0", cellStyle );
    styleContext.addStyle( "Cell2,1", cellStyle );

    Theme bodyTheme = new DefaultTheme ( "Some theme", styleContext );
    return bodyTheme;
  }

  /**
   * Creates and returns a pie chart showing the breakdown
   * of visitors by country. The data is arbitrary.
   */
  private static JFreeChart createCountryChart()
  {
    DefaultPieDataset pieDataset = new DefaultPieDataset();
    pieDataset.setValue( "US", 44.3 );
    pieDataset.setValue( "France", 12.1 );
    pieDataset.setValue( "Japan", 10.0 );
    pieDataset.setValue( "UK", 8.4 );
    pieDataset.setValue( "Spain", 3.4 );
    pieDataset.setValue( "Germany", 8.6 );
    pieDataset.setValue( "Australia", 3.2 );
    pieDataset.setValue( "China", 5.4 );
    pieDataset.setValue( "Other", 1.4 );
    JFreeChart countryChart = ChartFactory.createPieChart3D( "Visitors by Country", pieDataset, false, true, true );
    countryChart.setBackgroundPaint( Color.white );
    return countryChart;
  }

  /**
   * Creates and returns a bar chart showing the breakdown
   * of visits by folder. The data is arbitrary.
   */
  private static JFreeChart createHitByFolderChart()
  {
    DefaultCategoryDataset dataset = new DefaultCategoryDataset ();
    dataset.addValue( 30.0, "", "/" );
    dataset.addValue( 8.0, "", "/evaluation/" );
    dataset.addValue( 35.0, "", "/products/" );
    dataset.addValue( 27.0, "", "/api/" );
    dataset.addValue( 4.0, "", "Not Found" );
    JFreeChart hitsByFolderChart = ChartFactory.createBarChart( "Hits By Folder", "Folder", "Percentage", dataset, PlotOrientation.HORIZONTAL, false, false, false );
    hitsByFolderChart.setBackgroundPaint( Color.white );
    CategoryPlot plot = hitsByFolderChart.getCategoryPlot();
    BarRenderer renderer = (BarRenderer)plot.getRenderer();
    renderer.setDrawBarOutline(false);
    renderer.setSeriesPaint(0, new GradientPaint( 0.0f, 0.0f, Color.green, 0.0f, 0.0f, Color.lightGray ));
    return hitsByFolderChart;
  }

  /**
   * Creates and returns an x,y bar chart showing the average hits
   * by hour of the day. The data is arbitrary.
   */
  private static JFreeChart createHitsByHourChart()
  {
    TimeSeries hitsByHourData = new TimeSeries( "Hits", Hour.class );
    Day day = new Day( 1, 1, 2004 );
    for( int i = 0; i < 24; i++ )
      hitsByHourData.add( new Hour( i, day ), random.nextInt( 20 ));
    TimeSeriesCollection hitsByHourDataset = new TimeSeriesCollection( hitsByHourData );
    hitsByHourDataset.setDomainIsPointsInTime( false );
    JFreeChart hitsByHourChart = ChartFactory.createXYBarChart( "Average Hits by Hour", "Hour", true, "Avg Hits", hitsByHourDataset, PlotOrientation.VERTICAL, false, false, false );
    hitsByHourChart.setBackgroundPaint( Color.white );
    return hitsByHourChart;
  }

  /**
   * Creates and returns a line chart showing the number of hits
   * per day. The data is arbitrary.
   */
  private static JFreeChart createHitsPerDayChart()
  {
    TimeSeries hitsPerDayData = new TimeSeries( "Hits", Day.class );
    Calendar calendar = Calendar.getInstance();
    calendar.set( 2003, 0, 0 );
    for( int i = 0; i < 365; i++ )
    {
      hitsPerDayData.add( new Day( calendar.getTime() ), random.nextInt( 200 ));
      calendar.add( Calendar.DATE, 1 );
    }
    XYDataset hitsPerDayDataset = new TimeSeriesCollection( hitsPerDayData );
    JFreeChart hitsPerDayChart = ChartFactory.createTimeSeriesChart( "Hits Per Day", "Date", "Hits", hitsPerDayDataset, false, false, false );
    hitsPerDayChart.setBackgroundPaint( Color.white );
    return hitsPerDayChart;
  }

  /**
   * Creates and returns an x,y bar chart showing the number of hits
   * per month. The data is arbitrary.
   */
  private static JFreeChart createHitsPerMonthChart()
  {
    TimeSeries hitsPerMonthData = new TimeSeries( "Hits", Month.class );
    for( int i = 1; i <= 12; i++ )
      hitsPerMonthData.add( new Month( i, 2003 ), random.nextInt( 1000 ) + 3000 );
    TimeSeriesCollection hitsPerMonthDataset = new TimeSeriesCollection( hitsPerMonthData );
    hitsPerMonthDataset.setDomainIsPointsInTime( false );
    JFreeChart hitsPerMonthChart = ChartFactory.createXYBarChart( "Hits by Month", "Month", true, "Hits", hitsPerMonthDataset, PlotOrientation.VERTICAL, false, false, false );
    hitsPerMonthChart.setBackgroundPaint( Color.white );
    XYPlot plot1 = hitsPerMonthChart.getXYPlot();
    XYItemRenderer renderer1 = (XYItemRenderer)plot1.getRenderer();
    renderer1.setSeriesPaint(0, Color.blue );
    return hitsPerMonthChart;
  }

  public static Theme createMainTheme()
  {
    // the charts will be displayed as a table
    TableReportTemplate bodyTemplate = new TableReportTemplate();

    // the table report will use the following theme
    Theme bodyTheme = createBodyTheme();

    StyleContext mainStyleContext = new StyleContext();

    // the title resides in cell 0,0
    //
    // font size 16; Arial; bold; centered; 6 spaces below
    //
    Style titleStyle = mainStyleContext.addStyle( "Cell0,0Object", null );
    StyleConstants.setFontSize( titleStyle, 24 );
    StyleConstants.setFontFamily( titleStyle, "Arial" );
    StyleConstants.setBold( titleStyle, true );
    StyleConstants.setAlignment( titleStyle, StyleConstants.ALIGN_CENTER );
    StyleConstants.setUnderline( titleStyle, true );

    // the content resides in cell 1,0
    //
    // use a nested report template and theme; default font Arial
    //
    Style bodyStyle = mainStyleContext.addStyle( "Cell1,0Object", null );
    ReportStyleConstants.setReportTemplate( bodyStyle, bodyTemplate );
    ReportStyleConstants.setTheme( bodyStyle, bodyTheme );
    StyleConstants.setFontFamily( bodyStyle, "Arial" );

    Theme theme = new DefaultTheme( mainStyleContext );
    return theme;
  }

  /**
   * Creates and returns a pie chart showing the breakdown
   * of referral sites. The data is arbitrary.
   */
  private static JFreeChart createReferrerChart()
  {
    DefaultPieDataset referrerDataset = new DefaultPieDataset();
    referrerDataset.setValue( "google.com", 82 );
    referrerDataset.setValue( "yahoo.com", 8 );
    referrerDataset.setValue( "somesite.com", 7 );
    referrerDataset.setValue( "other", 3 );
    JFreeChart referrerChart = ChartFactory.createPieChart3D( "Referrers", referrerDataset, false, false, false );
    referrerChart.setBackgroundPaint( Color.white );
    return referrerChart;
  }

  /**
   * Entry point into the application.
   */
  public static void main(String[] args)
  {
    // initialize the gui on Swing's event dispatch thread
    SwingUtilities.invokeLater( new Runnable() {
      public void run()
      {
        showReport();
      }
    } );
  }

  private static void showReport()
  {
    // create a chart showing the total hits per month
    JFreeChart hitsPerMonthChart = createHitsPerMonthChart();

    // create a chart showing the visitor's countries
    JFreeChart countryChart = createCountryChart();

    // create a chart showing the referral urls
    JFreeChart referrerChart = createReferrerChart();

    // create a chart showing average hits for each hour of the day
    JFreeChart hitsByHourChart = createHitsByHourChart();

    // create a chart showing the total hits per day
    JFreeChart hitsPerDayChart = createHitsPerDayChart();

    // create a chart showing the breakdown of folders visited
    JFreeChart hitsByFolderChart = createHitByFolderChart();

    // the body of the report consists of a 3-by-2 table of charts
    Object[][] bodyData = new Object[][] {
      { hitsPerDayChart, hitsPerMonthChart },
      { countryChart, hitsByFolderChart },
      { hitsByHourChart, referrerChart }
    };

    // the main report consists of a title and the body
    Object[] mainReportData = new Object[] { "2003 Website Statistics", bodyData };

    // the table report template converts the array into a report
    ReportTemplate template = new TableReportTemplate();

    // create a theme for this report
    Theme mainTheme = createMainTheme();

    // generate the report
    Report report = template.createReport( mainReportData, mainTheme );

    // display the report in a report pane
    JReportPane reportPane = new JReportPane( report );

    // place the report pane in a scroll pane
    JScrollPane scrollPane = new JScrollPane( reportPane );

    // display the scroll pane in a frame
    JFrame frame = new JFrame( "Chart Example by Side of Software" );
    frame.getContentPane().add( scrollPane );

    frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
    frame.setSize( 800, 500 );
    frame.show();
  }
}

/**
 * A custom renderer that displays charts in a ChartPanel.
 */
class ChartRenderer implements ElementRenderer
{
  private ChartPanel chartPanel;

  public Component getElementRendererComponent(JReportPane reportPane, sos.reports.Element element)
  {
    JFreeChart chart = (JFreeChart)element.getObject();

    if( chartPanel == null )
      chartPanel = new ChartPanel( chart );
    else
      chartPanel.setChart( chart );

    chartPanel.setPreferredSize( new Dimension( 300, 250 ));
    return chartPanel;
  }
}