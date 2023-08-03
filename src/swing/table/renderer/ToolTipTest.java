package swing.table.renderer;


import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;

/**
 * Отображение toolTip на строке или ячейках таблицы.
 *
 * Варианты:
 *
 1) Создаем свой обще-табличный рендерер
// This table displays a tool tip text based on the string
 // representation of the cell value
 JTable table = new JTable()
 {
    public Component prepareRenderer (TableCellRenderer renderer, int rowIndex, int vColIndex)
    {
        Component c = super.prepareRenderer(renderer, rowIndex, vColIndex);
        if (c instanceof JComponent)
        {
            JComponent jc = (JComponent)c;
            jc.setToolTipText((String)getValueAt(rowIndex, vColIndex));
        }
        return c;
    }
 };

 2) Исп наведение мышки. Где мышь проходит у таблицы там и появляется надпись.

 JTable table - new JTable();
 table.addMouseMotionListener ( new MouseMotionAdapter()
 {
    public void mouseMoved(MouseEvent e)
    {
        Point p = e.getPoint();
        int row = table.rowAtPoint(p);
        int column = table.columnAtPoint(p);
        table.setToolTipText ( String.valueOf(table.getValue(row,column)));
    } //end MouseMoved
 }; // end MouseMotionAdapter 

 3) переопределить у JTable метод getToolTipText(MouseEvent):

public String getToolTipText(MouseEvent event)
{
  String toolTip = "";
  int row = rowAtPoint(event.getPoint());
  int column = columnAtPoint(event.getPoint());
  //....
  return toolTip;
}
ToolTipManager.sharedInstance().registerComponent(table);

 
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 29.09.2010 12:47:22
 */
public class ToolTipTest
{
    private JScrollPane getContent()
    {
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(new Object[] { "Name", "Age", "State" });
        model.addRow(new Object[] { "Peter",   29, "Florida" });
        model.addRow(new Object[] { "Gabriel", 32, "Oregon"  });
        model.addRow(new Object[] { "Hans",    27, "Texas"   });
        model.addRow(new Object[] { "Serge",   30, "Ohio"    });
        JTable table = new JTable(model);
        TableColumnModel colModel = table.getColumnModel();

        for ( int j = 0; j < colModel.getColumnCount(); j++ )
            colModel.getColumn(j).setCellRenderer(new RowRenderer());
        
        return new JScrollPane(table);
    }

    public static void main(String[] args)
    {
        JFrame f = new JFrame();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setContentPane(new ToolTipTest().getContent());
        f.setSize(400,140);
        f.setLocation(300,300);
        f.setVisible(true);
    }
}

class RowRenderer extends DefaultTableCellRenderer
{
    public Component getTableCellRendererComponent(JTable table,
                                                   Object value,
                                                   boolean isSelected,
                                                   boolean hasFocus,
                                                   int row, int column)
    {
        String toolTipText;

        super.getTableCellRendererComponent (table, value, isSelected, hasFocus, row, column);

        // на ячейках
        toolTipText  = (value == null) ? "": value.toString();

        // на строке
        //toolTipText  = (String)table.getValueAt(row, 0);
        // если необходимо чтобы на всех ячейках строки по toolTip отображалось содержимое только первой ячейки
        //toolTipText = (String)table.getModel().getValueAt(row, 0);

        setToolTipText ( toolTipText );

        return this;
    }
    
}
