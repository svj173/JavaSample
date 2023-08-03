package swing.table.multiLineCell.pr03;


import javax.swing.table.TableCellRenderer;

import javax.swing.JTextArea;

import javax.swing.JTable;

import java.awt.Component;

/**
 * Рендерер больших текстов.
 * <BR/> Использует JTextArea - но увеличивает высоту ячейки, чтобы выводился весь текст.
 * <BR/> Учтен Пересчет высоты ячейки - если в таблице две таких ячейки, то может произойти зацикливание,
 * т.к. каждая ячейка строки стремиться установить свою высоту, а установка высоты снова вызывает рендереры обоих ячеек,
 * в результате получится бесконечный пинг-понг.
 * <BR/>
 * <BR/> Large text renderer with row height automatic expanding. author Andrey Brainin
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 20.01.2012 17:14:10
 */
public class JTextCellRenderer extends JTextArea implements TableCellRenderer
{
    public JTextCellRenderer()
    {
        setWrapStyleWord(true);
        setLineWrap(true);
    }

    public Component getTableCellRendererComponent ( JTable table, Object value,
                          boolean isSelected, boolean hasFocus, int row, int column )
    {
        if (isSelected)
        {
           setForeground(table.getSelectionForeground());
           setBackground(table.getSelectionBackground());
        }
        else
        {
            setForeground(table.getForeground());
            setBackground(table.getBackground());
        }

        setText((String) value);

        // Пересчет высоты ячейки.
        int height = (int) getPreferredSize().getHeight();

        if ( table != null && height > 0 && table.getRowHeight(row) < height )
            table.setRowHeight(row, height);

        setFont(table.getFont());

        return this;
    }

}
