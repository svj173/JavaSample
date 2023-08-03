package swing.table.renderer;


import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;


/**
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 14.06.2011 16:56:35
 */
public class AlternateRowRenderer implements TableCellRenderer {
    private final TableCellRenderer wrappedRenderer;

    public AlternateRowRenderer( TableCellRenderer wrappedRenderer, Color highlightColour) {
       this.wrappedRenderer = wrappedRenderer;
    }

    public Component getTableCellRendererComponent(JTable table, Object value, boolean     isSelected, boolean hasFocus, int row, int column) {
        Component ret = wrappedRenderer.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        ret.setBackground(getTableBackgroundColour(table, value, isSelected, hasFocus, row, column));

        return ret;
    }

    @SuppressWarnings({"UnusedDeclaration"})
    public static Color getTableBackgroundColour( JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Color ret;

        /*
        if (row % 2 != 0) {
            ret = isSelected ? ColorUtil.mergeColors ( Color.LIGHT_GRAY,     table.getSelectionBackground(), 0.75) : Color.LIGHT_GRAY;
        } else {
            ret = isSelected ? table.getSelectionBackground() : table.getBackground();
        }
        */
        ret = isSelected ? table.getSelectionBackground() : table.getBackground();

        return ret;
    }
}

