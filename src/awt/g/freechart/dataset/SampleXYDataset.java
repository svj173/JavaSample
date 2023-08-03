package awt.g.freechart.dataset;


/**
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 28.06.2011 16:12:16
 */

import org.jfree.data.DomainOrder;
import org.jfree.data.general.AbstractSeriesDataset;
import org.jfree.data.general.DatasetChangeEvent;
import org.jfree.data.xy.XYDataset;

/**
 * A dummy dataset for an XY plot.
 * <P>
 * Note that the aim of this class is to create a self-contained data source for demo purposes -
 * it is NOT intended to show how you should go about writing your own datasets.
 *
 * @author David Gilbert
 */
public class SampleXYDataset extends AbstractSeriesDataset implements XYDataset {

    /** Use the translate to change the data and demonstrate dynamic data changes. */
    private double translate;

    /**
     * Default constructor.
     */
    public SampleXYDataset() {
        this.translate = 0.0;
    }

    /**
     * Returns the translation factor.
     *
     * @return  the translation factor.
     */
    public double getTranslate() {
        return this.translate;
    }

    /**
     * Sets the translation constant for the x-axis.
     *
     * @param translate  the translation factor.
     */
    public void setTranslate(double translate) {
        this.translate = translate;
        notifyListeners(new DatasetChangeEvent(this, this));
    }

    /**
     * Returns the x-value for the specified series and item.  Series are numbered 0, 1, ...
     *
     * @param series  the index (zero-based) of the series.
     * @param item  the index (zero-based) of the required item.
     *
     * @return the x-value for the specified series and item.
     */
    public double getXValue ( int series, int item )
    {
        Double result = new Double ( -10.0 + this.translate + ( item / 10.0 ) );

        return result;
    }

    @Override
    public Number getY ( int i, int i1 )
    {
        return getYValue(i,i1);
    }

    /**
     * Returns the y-value for the specified series and item.  Series are numbered 0, 1, ...
     *
     * @param series  the index (zero-based) of the series.
     * @param item  the index (zero-based) of the required item.
     *
     * @return the y-value for the specified series and item.
     */
    public double getYValue(int series, int item) {
        if (series == 0) {
            return new Double(Math.cos(-10.0 + this.translate + (item / 10.0)));
        }
        else {
            return new Double(2 * (Math.sin(-10.0 + this.translate + (item / 10.0))));
        }
    }

    /**
     * Returns the number of series in the dataset.
     *
     * @return the number of series in the dataset.
     */
    public int getSeriesCount() {
        return 2;
    }

    @Override
    public Comparable getSeriesKey ( int i )
    {
        return null;
    }

    /**
     * Returns the name of the series.
     *
     * @param series  the index (zero-based) of the series.
     *
     * @return the name of the series.
     */
    public String getSeriesName(int series) {
        if (series == 0) {
            return "y = cosine(x)";
        }
        else if (series == 1) {
            return "y = 2*sine(x)";
        }
        else {
            return "Error";
        }
    }

    @Override
    public DomainOrder getDomainOrder ()
    {
        return null;
    }

    /**
     * Returns the number of items in the specified series.
     *
     * @param series  the index (zero-based) of the series.
     * @return the number of items in the specified series.
     *
     */
    public int getItemCount(int series) {
        return 200;
    }

    @Override
    public Number getX ( int i, int i1 )
    {
        return getXValue(i,i1);
    }

}

