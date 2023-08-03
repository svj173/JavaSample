package awt.g.freechart.source;


import org.jfree.data.xy.XYDataset;


/**
 * An interface that adds signal information to an {@link XYDataset}.
 *
 * @author Sylvain Vieujot
 */
public interface SignalsDataset extends XYDataset
{

    /** Useful constant indicating trade recommendation. */
    public static final int ENTER_LONG = 1;

    /** Useful constant indicating trade recommendation. */
    public static final int ENTER_SHORT = -1;

    /** Useful constant indicating trade recommendation. */
    public static final int EXIT_LONG = 2;

    /** Useful constant indicating trade recommendation. */
    public static final int EXIT_SHORT = -2;

    /**
     * Returns the type.
     *
     * @param series  the series (zero-based index).
     * @param item  the item (zero-based index).
     *
     * @return The type.
     */
    public int getType(int series, int item);

    /**
     * Returns the level.
     *
     * @param series  the series (zero-based index).
     * @param item  the item (zero-based index).
     *
     * @return The level.
     */
    public double getLevel(int series, int item);

}
