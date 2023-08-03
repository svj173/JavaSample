package awt.g.freechart.source;


import org.jfree.chart.labels.AbstractPieItemLabelGenerator;
import org.jfree.chart.labels.PieSectionLabelGenerator;
import org.jfree.chart.labels.PieToolTipGenerator;
import org.jfree.data.general.PieDataset;
import org.jfree.util.PublicCloneable;

import java.io.Serializable;
import java.text.AttributedString;
import java.text.MessageFormat;
import java.text.NumberFormat;

/**
 * A standard item label generator for plots that use data from a 
 * {@link PieDataset}.
 * <p>
 * For the label format, use {0} where the pie section key should be inserted,
 * {1} for the absolute section value and {2} for the percent amount of the pie
 * section, e.g. <code>"{0} = {1} ({2})"</code> will display as  
 * <code>apple = 120 (5%)</code>.
 */
public class StandardPieItemLabelGenerator extends AbstractPieItemLabelGenerator
                                           implements PieToolTipGenerator,
                                                      PieSectionLabelGenerator,
                                                      Cloneable, 
                                                      PublicCloneable, 
                                                      Serializable
{

    /** For serialization. */
    private static final long serialVersionUID = 2995304200445733779L;
    
    /** The default tooltip format. */
    public static final String DEFAULT_TOOLTIP_FORMAT = "{0}: ({1}, {2})";

    /** The default section label format. */
    public static final String DEFAULT_SECTION_LABEL_FORMAT = "{0} = {1}";

    /**
     * Creates an item label generator using default number formatters.
     */
    public StandardPieItemLabelGenerator() {
        this(
            DEFAULT_SECTION_LABEL_FORMAT, 
            NumberFormat.getNumberInstance(), NumberFormat.getPercentInstance()
        );
    }

    /**
     * Creates an item label generator.
     * 
     * @param labelFormat  the label format.
     */
    public StandardPieItemLabelGenerator(String labelFormat) {
        this(
            labelFormat, NumberFormat.getNumberInstance(), 
            NumberFormat.getPercentInstance()
        );
    }
    
    /**
     * Creates an item label generator using the specified number formatters.
     *
     * @param labelFormat  the label format string (<code>null</code> not 
     *                     permitted).
     * @param numberFormat  the format object for the values (<code>null</code>
     *                      not permitted).
     * @param percentFormat  the format object for the percentages 
     *                       (<code>null</code> not permitted).
     */
    public StandardPieItemLabelGenerator(String labelFormat,
                                         NumberFormat numberFormat, 
                                         NumberFormat percentFormat) {

        super(labelFormat, numberFormat, percentFormat);

    }

    // мне пришлось создать, иначе - ошибка
    @Override
    public AttributedString generateAttributedSectionLabel ( PieDataset dataset, Comparable key )
    {
        return null;
    }


    /**
     * Generates a label for a pie section.
     * 
     * @param dataset  the dataset (<code>null</code> not permitted).
     * @param key  the section key (<code>null</code> not permitted).
     * 
     * @return The label (possibly <code>null</code>).
     */
    public String generateSectionLabel(PieDataset dataset, Comparable key) {
        String result = null;    
        if (dataset != null) {
            Object[] items = createItemArray(dataset, key);
            result = MessageFormat.format(getLabelFormat(), items);
        }
        return result;
    }

    /**
     * Generates a tool tip text item for one section in a pie chart.
     *
     * @param dataset  the dataset (<code>null</code> not permitted).
     * @param key  the section key (<code>null</code> not permitted).
     *
     * @return The tool tip text (possibly <code>null</code>).
     */
    public String generateToolTip(PieDataset dataset, Comparable key) {
        return generateSectionLabel(dataset, key);
    }

    /**
     * Returns an independent copy of the generator.
     * 
     * @return A clone.
     * 
     * @throws CloneNotSupportedException  should not happen.
     */
    public Object clone() throws CloneNotSupportedException {      
        return super.clone();
    }

}
