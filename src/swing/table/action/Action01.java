package swing.table.action;


import javax.swing.*;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EventObject;
import java.util.Iterator;


/**
 * Таблица. Клик на одной ячейке (имя файла) вызывает редактор - своя панель (в этой же ячейке) с текстовым полем и двумя кнопками - Да, Нет.
 * <BR/> Можно вводить текст - редактирование имени файла.
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 03.03.2011 16:09:17
 */
public class Action01
{
    public static void main ( String[] args )
    {
        // сначала получим список всех файлов внутри некоторого каталога - эти файлы и будут содержимым таблицы
        final File[] files = new File ( "/home/svj/projects/SVJ/JavaSample/test/table/action01" ).listFiles();
        JFrame jf = new JFrame ();
        JTable jt = new JTable (
                // первое отличие от всех примеров ранее – мы  создаем таблицу на базе модели данных
                new AbstractTableModel()
                {
                    // в состав модели входят многожество методов но только три нижеследующий обязательны

                    public int getRowCount ()
                    { // обязательно – количество строк в таблице
                        return files.length;
                    }

                    public int getColumnCount ()
                    {// количество колонок в таблице
                        return 3;
                    }

                    // и последний жестко обязаталельный метод – возвращающий значение элемента,
                    //  который должнен находиться в указанных координатах
                    public Object getValueAt ( int rowIndex, int columnIndex )
                    {
                        File f = files[ rowIndex ];
                        if ( columnIndex == 0 ) return f.getName ();
                        if ( columnIndex == 1 ) return f.length ();
                        if ( columnIndex == 2 ) return f;
                        return null;
                    }

                    // необязательный метод – возвращает класс – или тип данных который хранится в данной колонке
                    public Class<?> getColumnClass ( int columnIndex )
                    {
                        if ( columnIndex == 0 ) return String.class;
                        if ( columnIndex == 1 ) return Long.class;
                        if ( columnIndex == 2 ) return File.class;
                        return null;
                    }

                    // важно данный метод должен вернуть название колонки – если вы не перекроете данный метод,
                    //  то колонки получат имена по правилу A, B, C , D, …
                    public String getColumnName ( int column )
                    {
                        if ( column == 0 ) return "Имя файла";
                        if ( column == 1 ) return "Размер";
                        if ( column == 2 ) return "Preview";
                        return null;
                    }

                    //метод должен вернуть признак того можно ли редактировать значение в колонке с указанным номером
                    public boolean isCellEditable ( int rowIndex, int columnIndex )
                    {
                        return columnIndex == 0;
                    }

                    // и метод который вызывается когда значение в ячейке изменяется
                    // в этом примере выполняется переименование файла
                    public void setValueAt ( Object aValue, int rowIndex, int columnIndex )
                    {
                        super.setValueAt ( aValue, rowIndex, columnIndex );
                        File f = files[ rowIndex ];
                        File dest = new File ( f.getParentFile (), aValue.toString () );
                        if ( !f.renameTo ( dest ) )
                            JOptionPane.showMessageDialog ( null, "Ошибка, не могу переименовать файл" );
                        else
                            files[ rowIndex ] = dest;
                    }
                }//end of --  AbstractTableModel --
        );
        // создается класс редактора – редактор привязывается не к конкретному столбцу а типу данных,
        // который задается первым параметром при вызове setDefaultEditor
        jt.setDefaultEditor (
                String.class,// тип данных для которого создается редактор
                new TableCellEditor()
                {// код самого редактора он представляет собой панель с текстовым полем,
                    //  где находится имя файла и кнопками 'принять изменение' и 'отменить изменение'
                    JTextField fld_txt = new JTextField ();
                    JPanel btns_zone = new JPanel ( new GridLayout ( 1, 2 ) );
                    JPanel editor = new JPanel ( new BorderLayout () );

                    {// здесь я создаю внешний вид класса редатора свойства - имени файла
                        // для этого создается панель с двумя кнопками - принять введенное значние и отказаться
                        editor.add ( btns_zone, BorderLayout.NORTH );
                        editor.add ( fld_txt, BorderLayout.CENTER );
                        fld_txt.setPreferredSize ( new Dimension ( 0, 25 ) );
                        JButton b_OK = new JButton ( "Принять" );
                        JButton b_CANCEL = new JButton ( "Отказаться" );
                        b_OK.addActionListener (
                                new ActionListener()
                                {
                                    public void actionPerformed ( ActionEvent e )
                                    {
                                        fireValueWasChanged ( true );
                                    }
                                }
                        );
                        b_CANCEL.addActionListener (
                                new ActionListener()
                                {
                                    public void actionPerformed ( ActionEvent e )
                                    {
                                        fireValueWasChanged ( true );
                                    }
                                }
                        );
                        btns_zone.add ( b_OK );
                        btns_zone.add ( b_CANCEL );
                    }

                    //данный метод вызывается когда таблица нуждается в редаторе для выбранного пользователем поля
                    public Component getTableCellEditorComponent
                            ( JTable table, Object value, boolean isSelected, int row, int column )
                    {
                        fld_txt.setText ( value.toString () );
                        // я выполняю инициализацию текстового поля тем значением,
                        // которое находится в таблице и возвращаю панель редактирования
                        return editor;
                    }

                    public Object getCellEditorValue ()
                    {// получить отредактированное изменение
                        return fld_txt.getText ();
                    }

                    // проверка того можно ли редактировать ячейку,
                    // но в отличии от ранее вам встречавшегося метода isCellEditable
                    // в модели таблицы, здесь выполнятся проверка того,
                    // может ли событие anEvent вызвать открытие редатора.
                    // В моем примере любое событие приводит к появлению редактора,
                    // а можно только при нажатии кнопки F2
                    public boolean isCellEditable ( EventObject anEvent )
                    {
                        return true;
                    }

                    public boolean shouldSelectCell ( EventObject anEvent ) {return false;}

                    //метод вызывается когда таблица спрашивает завершено ли редатирование поля
                    public boolean stopCellEditing ()
                    {return false;}

                    //метод вызывается когда редактирование должно быть прекращено
                    public void cancelCellEditing ()
                    {}

                    // коллекция в которой хранится множество ссылок на слушателей событий с редактором.
                    // Далее я реализую методы добавления и удаления слушателей из очереди
                    List vListeners = Collections.synchronizedList ( new ArrayList () );

                    public void addCellEditorListener ( CellEditorListener l )
                    {
                        vListeners.add ( l );
                    }

                    public void removeCellEditorListener ( CellEditorListener l )
                    {
                        vListeners.remove ( l );
                    }// этот метод я буду вызывать при  нажатии на кнопки ПРИНЯТЬ и ОТКАЗАТЬСЯ

                    protected void fireValueWasChanged ( boolean is_ok )
                    {
                        ArrayList anList = new ArrayList ( vListeners );
                        for ( Iterator iterator = anList.iterator (); iterator.hasNext (); )
                        {
                            CellEditorListener cellEditorListener = ( CellEditorListener ) iterator.next ();
                            if ( is_ok )
                                cellEditorListener.editingStopped ( new ChangeEvent ( this ) );
                            else
                                cellEditorListener.editingCanceled ( new ChangeEvent ( this ) );
                        }
                    }
                }
        );
        // теперь создаем класс – рендерера
        jt.setDefaultRenderer (
                // рендерер также как редактор назначается не для конкретного столбца
                //таблицы а именно для типа данных
                File.class,
                new TableCellRenderer()
                {
                    public Component getTableCellRendererComponent
                            ( JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column )
                    {
                        // обратите внимание какие данные передаются как
                        //параметры метода, здесь не только значение ячейки для которой необходимо
                        // выполнить создание представления-рендерера,
                        // но и координаты ячейки, и признак того входит ли данная
                        //ячейка в выделение и имеет ли фокус ввода.
                        try
                        {// возвращаем Jlabel на котором находится изображение картинки в строке
                            return new JLabel ( new ImageIcon ( ( ( File ) value ).getCanonicalPath () ) );
                        } catch ( IOException e )
                        {
                            e.printStackTrace ();
                        }
                        return new JLabel ( "Ошибка: " + value );
                    }
                }
        );
        jt.setRowHeight ( 100 );// устанавливаем высоту строк таблицы
        jf.getContentPane ().add ( new JScrollPane ( jt ), BorderLayout.CENTER );
        jf.pack ();
        jf.setVisible ( true );
    }
}
