package swing.table.multiLineCell.pr02;


import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

/**
 * Рендерер показа больших текстов в ячейке.
 * <BR/> Отображает JTextArea в однй строку со скроллингом. Т.е. видна только первая строчка.
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 20.01.2012 17:00:19
 */
public class ScrollCellRend implements TableCellRenderer
{
    private JTextArea ta;
    private JScrollPane scrollPane;


    public ScrollCellRend ()
    {
        ta = new JTextArea ();
        scrollPane = new JScrollPane ( ta );
        scrollPane.setBorder ( BorderFactory.createEmptyBorder () );
        ta.setLineWrap ( true );
        ta.setWrapStyleWord ( true );
        ta.setOpaque ( true );
    }

    public Component getTableCellRendererComponent ( JTable table, Object value,
                                                     boolean isSelected, boolean hasFocus, int row, int column )
    {
        if ( isSelected )
        {
            ta.setForeground ( table.getSelectionForeground () );
            ta.setBackground ( table.getSelectionBackground () );
        }
        else
        {
            ta.setForeground ( table.getForeground () );
            ta.setBackground ( table.getBackground () );
        }

        ta.setFont ( table.getFont() );

        if ( hasFocus )
        {
            if ( isSelected )
                ta.setBorder ( BorderFactory.createLineBorder ( Color.blue ) );

            if ( table.isCellEditable ( row, column ) )
            {
                ta.setForeground ( UIManager.getColor ( "Table.focusCellForeground" ) );
                ta.setBackground ( UIManager.getColor ( "Table.focusCellBackground" ) );
            }
        }
        else
        {
            ta.setBorder ( new EmptyBorder ( 1, 2, 1, 2 ) );
        }

        ta.setText ( ( value == null ) ? "" : value.toString () );

        return scrollPane;
    }

}
