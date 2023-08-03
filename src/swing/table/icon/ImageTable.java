package swing.table.icon;


/**
 * Во второй колонке выводит рисунок - зеленый треугольник-елочка.
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 02.12.2010 10:35:55
 */

import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;

public class ImageTable extends JFrame
{
    private ImageIcon myPicture = new ImageIcon ( getImage () );
    private Object[][] data = new Object[][] {
            { "blah", myPicture }, { "blah2", myPicture } };
    private Object[] columnNames = new Object[] { "Column1", "Column2" };
    private TableModel myModel = new MyModel ( data, columnNames );

    public ImageTable ()
    {
        super ( "Image JTable" );
        JTable myTable = new JTable ();
        myTable.setModel ( myModel );
        myTable.setDefaultRenderer ( Icon.class, new IconCellRenderer() );
        JScrollPane scrollPane = new JScrollPane ( myTable );
        getContentPane ().add ( scrollPane );
        setSize ( 200, 100 );
        setDefaultCloseOperation ( JFrame.EXIT_ON_CLOSE );

        try
        {
            // Меняем
            UIManager.setLookAndFeel ( "com.sun.java.swing.plaf.gtk.GTKLookAndFeel" );
            // Обновляем
            SwingUtilities.updateComponentTreeUI ( this );
        }  catch ( Exception e )        {
            e.printStackTrace();
        }

        setVisible ( true );
    }

    class IconCellRenderer extends DefaultTableCellRenderer
    {
        protected void setValue ( Object value )
        {
            if ( value instanceof Icon )
            {
                System.out.println ( "It works!" );
                setIcon ( ( Icon ) value );
                super.setValue ( null );
            }
            else
            {
                setIcon ( null );
                super.setValue ( value );
            }
        }
    }

    class MyModel extends DefaultTableModel
    {
        public MyModel ( Object[][] data, Object[] columnNames )
        {
            super ( data, columnNames );
        }

        public Class getColumnClass ( int col )
        {
            return col == 1 ? Icon.class : Object.class;
        }
    }

    public static void main ( String[] args )
    {
        new ImageTable ();
    }

    private static byte[] imageBytes = {
            71, 73, 70, 56, 57, 97, 16, 0, 16, 0, -94, 4, 0, 0, -103, -103, 0,
            102, 102, -103,
            -52, -52, 0, 101, 99, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 33, -7, 4,
            1, 0, 0, 4, 0, 44, 0, 0, 0, 0, 16, 0, 16, 0, 64, 3, 55, 72, -70, 52,
            -62, -80, 13, 64, -85, 120, 81, -46, 27, 67, -88, -96, -123,
            65, -125, 51, 102, 37, -56, -111,
            95, -72, 97, 94, 44, -49, 43, 83, -98, -103, 6, -32, -28,
            100, -27, -87, 16, -49, -29, -38,
            48, -120, 69, 35, 1, -103, 124, -51, -98, 50, 65, 2, 0, 59
    };

    public Image getImage ()
    {
        return Toolkit.getDefaultToolkit().createImage ( imageBytes );
    }
}