package swing.table.multiLineCell.pr03;


import javax.swing.*;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


/**
 * Много текста в ячейке.
 * <BR/> Попытка выводить его в несколько строк. Чтобы менялась высота строки, чтоыб был виден весь текст.
 * <BR/>
 * <BR/> Текст отображается не весь !!!
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 20.01.2012 16:58:27
 */
public class BigTextInCell extends JFrame
{
    private boolean DEBUG = true;


    public BigTextInCell ()
    {
        super ( "MainFrame" );

        Object[][] data = {
                { "Mary", "asdasd asdasd asdfasd asfasdf vxcv gfhfgh ertert tyutyu yiuiyui yuiyui yuiyui yuiyui" },
                { "Pit", "Учтен Пересчет высоты ячейки - если в таблице две таких ячейки, то может произойти зацикливание,\n" +
                        " * т.к. каждая ячейка строки стремиться установить свою высоту, а установка высоты снова вызывает рендереры обоих ячеек,\n" +
                        " * в результате получится бесконечный пинг-понг." }
            };
        String[] columnNames = { "First Name", "Text" };

        final JTable table = new JTable ( data, columnNames );
        table.setPreferredScrollableViewportSize ( new Dimension ( 500, 70 ) );

        //table.setDefaultRenderer ( String.class, new JTextCellRenderer() );

        TableColumnModel columnModel = table.getColumnModel();

        // column Text
        TableColumn column = columnModel.getColumn ( 1 );
        //column.setCellRenderer ( new JTextCellRenderer() );
        column.setCellRenderer ( new TextAreaRenderer() );

        //column.setCellEditor ( new LocationTableCellEditor ( "First Name" ) );
        column.setPreferredWidth ( 150 );

        //Create the scroll pane and add the table to it.
        JScrollPane scrollPane = new JScrollPane ( table );

        //Add the scroll pane to this window.
        getContentPane ().add ( scrollPane, BorderLayout.CENTER );

        addWindowListener ( new WindowAdapter()
        {
            public void windowClosing ( WindowEvent e )
            {
                System.exit ( 0 );
            }
        } );
    }


    public static void main ( String[] args )
    {
        BigTextInCell frame = new BigTextInCell();
        frame.pack ();
        frame.setVisible ( true );
    }

}
