package swing.table.renderer;


import javax.swing.table.TableCellRenderer;
import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.text.DateFormat;
import java.util.Date;

/**
 * Implementation of a <code>TableCellRenderer</code> for <code>java.util.Date</code> class
 * Отрисовка даты. использование цветовой гаммы  взависимости от заданного - фиксированные цветы, системные, L&F (наиболее предпочтительные)
 * Вывод - как JLabel.
 *
 * @author Eugene Matyushkin
 */
public class DateCellRenderer extends JLabel implements TableCellRenderer
{
    /** date format */
    private final DateFormat format;

    /** calendar icon */
    private final Icon icon;

    /** Cell border thickness */
    public static final int BT = 2;

    /** Selected cell border thickness */
    private static final int SBT = 1;

    /* Вид цветового отображения ячейки с датой */
    private ColorMode colorMode = ColorMode.LAF;

    private enum ColorMode { FIXED, SYSTEM, LAF }


    /**
     * Constructs renderer
     *
     * @param format date format to use. See <code>java.text.SimpleDateFormat</code> for description
     */
    public DateCellRenderer ( String format )
    {
        this.format = new SimpleDateFormat(format);
        setBorder(BorderFactory.createEmptyBorder(BT, BT, BT, BT));
        setOpaque(true); // сделать непрозрачным
        // иконка с часиками
        icon = new ImageIcon(getClass().getResource("/calendar.png"));
    }

    /**
     * Returns renderer component
     *
     * @param table      renderable table
     * @param value      value to render
     * @param isSelected flag that indicates wether the cell (row) is selected
     * @param hasFocus   flag that indicates wether the cell has focus
     * @param row        cell's row
     * @param column     cell's column
     *
     * @return cell renderer component
     */
    public Component getTableCellRendererComponent ( JTable table, Object value, boolean isSelected,
                                                     boolean hasFocus, int row, int column )
    {
        if ( ! (value instanceof Date) )
        {
            setForeground (Color.red);
            setBackground (Color.white);
            setText ("Table element is not a java.util.Date!");
            setIcon (null);
            return this;
        }

        Date date = (Date) value;
        setText (format.format(date));
        setIcon (icon);

        // color
        switch ( colorMode )
        {
            case FIXED:
                // using fixed colors
                setForeground(isSelected ? Color.white : Color.black);
                setBackground(isSelected ? Color.blue : Color.white);
                setBorder(hasFocus ?
                    BorderFactory.createLineBorder(Color.white, SBT) :
                    BorderFactory.createEmptyBorder(BT, BT, BT, BT));
                break;

            case SYSTEM:
                // using system colors
                setForeground(isSelected ? SystemColor.textHighlightText : SystemColor.textText);
                setBackground(isSelected ? SystemColor.textHighlight : SystemColor.text);
                setBorder(hasFocus ?
                    BorderFactory.createLineBorder(SystemColor.textHighlightText, SBT) :
                    BorderFactory.createEmptyBorder(BT, BT, BT, BT));
                break;

            default:
            case LAF:
                // using L&F colors
                setForeground(isSelected ?
                    UIManager.getColor("Table.selectionForeground") :
                    UIManager.getColor("Table.foreground"));
                setBackground(isSelected ?
                    UIManager.getColor("Table.selectionBackground") :
                    UIManager.getColor("Table.background"));
                setBorder(hasFocus ?
                    BorderFactory.createLineBorder(UIManager.getColor("Table.selectionForeground"), SBT) :
                    BorderFactory.createEmptyBorder(BT, BT, BT, BT));
                break;
        }
        
        return this;
    }

}
