package swing.table.multicolumn;



import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.geom.AffineTransform;

/**
 * Текст в ячейке таблицы можно выводить в несколько строк и даже под разными углами.
 * <BR/> Здесь все ячейки будут одной высоты - по самой макс.
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 20.01.2012 17:30:08
 */
public class TransformText extends JFrame
{
    JTable table;
    String[] columnNames = { "one", "two", "three" };
    String[][] data = {
            { "111", "222", "333" },
            { "aaa", "bbb", "ccc" },
            { "444", "55555555555", "666" },
            { "777", "888", "999" }
        };

    public TransformText ()
    {
        table = new JTable ( data, columnNames );
        // имея все данные для таблицы заранее пробегаем по ним и вычисляем макс высоту строки (по макс данным).
        table.setRowHeight ( getRowHeight () );
        // здесь вводим угол поворота текста ячеек в градусах
        TableRenderer renderer = new TableRenderer ( -30 );
        // а можно задать угол конкретной ячейке
        //  TableRenderer renderer = new TableRenderer(-30, 2, 1);
        for ( int i = 0; i < columnNames.length; i++ )
            table.getColumnModel ().getColumn ( i ).setCellRenderer ( renderer );
        add ( new JScrollPane ( table ) );
        table.getModel ().addTableModelListener ( new TableModelListener()
        {
            public void tableChanged ( TableModelEvent e )
            {
                table.setRowHeight ( getRowHeight () );
            }
        } );
    }

    // в этом методе подсчитывается предпочтительная высота строк
    // в таблице, при угле поворота текста ячейки 90 градусов.
    // мне лень было сделать расчет поточнее ;)
    public int getRowHeight ()
    {
        FontMetrics fm = table.getFontMetrics ( table.getFont () );
        int string_width = 0;
        for ( int i = 0; i < data.length; i++ )
            for ( int j = 0; j < data[ i ].length; j++ )
            {
                int width = fm.stringWidth ( data[ i ][ j ] );
                if ( string_width < width )
                    string_width = width;
            }
        return string_width + 8;
    }

    public static void main ( String[] args )
    {
        JFrame f = new TransformText ();
        f.setBounds ( 100, 100, 400, 300 );
        f.setDefaultCloseOperation ( 3 );
        f.setVisible ( true );
    }
}

class TableRenderer extends JComponent
        implements TableCellRenderer
{
    int angle, r = -1, c = -1;

    TableRenderer ( int angle )
    {
        this ( angle, -1, -1 );
    }

    TableRenderer ( int angle, int r, int c )
    {
        this.angle = angle;
        this.r = r;
        this.c = c;
    }

    public Component getTableCellRendererComponent ( JTable table, Object value,
                                                     boolean isSelected, boolean hasFocus, int row, int column )
    {
        if ( r < 0 && c < 0 )
            return new MyLabel ( value == null ? "" : value.toString (), angle );
        if ( r == row && c == column )
            return new MyLabel ( value == null ? "" : value.toString (), angle );
        else
            return new JLabel ( value == null ? "" : value.toString (), JLabel.CENTER );
    }
}

/**
 * Имеем возможность наклонять текст под разными углами.
 */
class MyLabel extends JLabel
{
    String s;
    int angle;

    MyLabel ( String s, int angle )
    {
        this.s = s;
        this.angle = angle;
    }

    public void paintComponent ( Graphics g )
    {
        super.paintComponent ( g );
        AffineTransform at = new AffineTransform ();
        at.rotate ( Math.toRadians ( angle ), getWidth () / 2, getHeight () / 2 );
        ( ( Graphics2D ) g ).transform ( at );
        FontMetrics fm = getFontMetrics ( getFont () );
        int string_width = fm.stringWidth ( s );
        g.drawString ( s, ( getWidth () - string_width ) / 2, getHeight () / 2 );
    }

}
