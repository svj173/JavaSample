package swing.table.action;


import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.util.Date;
import java.util.Vector;


/**
 * Двойной клик на кнопке вызывает открытие внешнего диалогового окна с выборкой цвета.
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 03.03.2011 16:30:59
 */
public class Action02
{
    public static void main ( String[] args )
    {
        Vector vasyan_info = new Vector ();
        // создаем вектор, хранящий сведения (колонки) для каждой из строк-записей
        vasyan_info.add ( "Василий Пупкин" );
        vasyan_info.add ( new Integer ( 20 ) );
        vasyan_info.add ( new Date ( System.currentTimeMillis () ) );
        vasyan_info.add ( true );// тип данных Boolean
        vasyan_info.add ( Color.red );// объект-цвет

        Vector petyan_info = new Vector ();
        petyan_info.add ( "Петюан Козлов" );
        petyan_info.add ( new Integer ( 24 ) );
        petyan_info.add ( new Date ( System.currentTimeMillis () ) );
        petyan_info.add ( false );
        petyan_info.add ( Color.blue );

        // помещаем две созданные записи - вектора внутрь третьего - списка всех записей
        final Vector vec_data = new Vector ();
        vec_data.add ( vasyan_info );
        vec_data.add ( petyan_info );


        JFrame jf = new JFrame ();
        // создаем таблицу на основании собственной модели данных
        JTable jt = new JTable (
                new AbstractTableModel()
                {
                    public int getRowCount ()
                    {
                        return vec_data.size ();
                    }

                    public int getColumnCount ()
                    {
                        return 5;
                    }

                    public Object getValueAt ( int rowIndex, int columnIndex )
                    {
                        return ( ( Vector ) vec_data.get ( rowIndex ) ).get ( columnIndex );
                    }

                    public String getColumnName ( int column )
                    {
                        return new String[] { "ФИО", "Возраст", "Дата рождения", "Хитрость", "Цвет ушей" }[ column ];
                    }

                    public Class<?> getColumnClass ( int columnIndex )
                    {
                        return new Class[] { String.class, Integer.class, Date.class, Boolean.class, Color.class }[ columnIndex ];
                    }

                    public boolean isCellEditable ( int rowIndex, int columnIndex )
                    {
                        return true;
                    }

                    // самая главная часть поддержки редактора ячеек -
                    // функция сохранения значения которое было введено в данную ячейку
                    public void setValueAt ( Object aValue, int rowIndex, int columnIndex )
                    {
                        ( ( Vector ) vec_data.get ( rowIndex ) ).set ( columnIndex, aValue );
                    }
                }
        );
        jt.setDefaultEditor ( Color.class, new ColorEditor () );
        jt.setDefaultRenderer ( Color.class, new TableCellRenderer()
        {
            JPanel lab = new JPanel ();
            Border border_black = BorderFactory.createLineBorder ( Color.black, 3 );
            Border border_white = BorderFactory.createLineBorder ( Color.white, 3 );

            public Component getTableCellRendererComponent (
                    JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column )
            {
                if ( value != null )
                    lab.setBackground ( ( Color ) value );
                else
                    lab.setBackground ( Color.white );
                if ( hasFocus )
                    lab.setBorder ( border_black );
                else
                    lab.setBorder ( border_white );
                return lab;
            }
        } );

        jt.setRowHeight ( 50 );
        jf.getContentPane ().add ( new JScrollPane ( jt ), BorderLayout.CENTER );
        jf.setSize ( 640, 480 );
        jf.setVisible ( true );
    }
}
