package swing.table.multicolumn;


import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.Vector;


/**
 * Рендерер возвращает обьект JList в котором данные раскладываются по горизонтали.
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 24.09.2010 12:48:42
 */
public class MultiLineHeaderRenderer extends JList implements TableCellRenderer
{
  public MultiLineHeaderRenderer()
  {
    setOpaque(true);

    setForeground(UIManager.getColor("TableHeader.foreground"));
    setBackground(UIManager.getColor("TableHeader.background"));
    setBorder(UIManager.getBorder("TableHeader.cellBorder"));

    ListCellRenderer renderer = getCellRenderer();
    ((JLabel)renderer).setHorizontalAlignment(JLabel.CENTER);
    setCellRenderer(renderer);
  }

  public Component getTableCellRendererComponent(JTable table, Object value,
                   boolean isSelected, boolean hasFocus, int row, int column)
  {
    setFont(table.getFont());
    String str = (value == null) ? "" : value.toString();
    BufferedReader br = new BufferedReader(new StringReader (str));
    String line;
    Vector<String> v = new Vector<String>();
    // читать строковое значение ячейки, с разбивкой по строке, и формировать из них отдельную колонку
    try {
      while ((line = br.readLine()) != null) {
        v.addElement(line);
      }
    } catch ( IOException ex) {
      ex.printStackTrace();
    }
    setListData(v);
    return this;
  }

}
