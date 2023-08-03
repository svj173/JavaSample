package swing.table.action;


import javax.swing.*;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.EventObject;


/**
 * Редактор на поле таблицы, вызывает внешнее диалоговое окно.
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 03.03.2011 16:28:27
 */
public class ColorEditor extends AbstractCellEditor implements TableCellEditor
{
    Color currentColor;
    JButton button;
    JColorChooser colorChooser;
    JDialog dialog;

    public ColorEditor ()
    {
        button = new JButton ();
        button.addActionListener ( new ActionListener()
        {
            // обработчик события нажатие на кнопку
            // для отображение диалогового окна выбора цвета
            public void actionPerformed ( ActionEvent e )
            {
                button.setBackground ( currentColor );
                colorChooser.setColor ( currentColor );
                dialog.setVisible ( true );
            }
        } );
        button.setBorderPainted ( false );

        colorChooser = new JColorChooser ();
        dialog = JColorChooser.createDialog ( button,
                                              "Pick a Color",
                                              true,  //окно должно быть модальным
                                              colorChooser,
                                              // обработчик события  "выбор нового цвета в редакторе" успешно завершен
                                              new ActionListener()
                                              {
                                                  public void actionPerformed ( ActionEvent e )
                                                  {
                                                      currentColor = colorChooser.getColor ();
                                                      fireEditingStopped ();
                                                      // извещаем всех о том, что редактирование было завершено успешно
                                                  }
                                              },
                                              //обработчик события при нажатии на диалоге выбора цвета OK
                                              new ActionListener()
                                              {
                                                  public void actionPerformed ( ActionEvent e )
                                                  {
                                                      fireEditingCanceled ();// извещаем всех о том, что редактирование было отменено
                                                  }
                                              } ); //обработка нажатия кнопки CANCEL на диалоге выбора цвета
    }
    // данный метод является дополнительным относительно базового для нас класса TableCellEditor –
    // и вводится в составе наследника AbstractCellEditor  -
    // в нем мы должны вернуть текущее значение поля редактирования

    public Object getCellEditorValue ()
    {
        return currentColor;
    }

    public Component getTableCellEditorComponent ( JTable table, Object value, boolean isSelected, int row, int column )
    {
        // мы должны вернуть компонент используемый для редактирования данной ячейки.
        // Компонент может быть различным в зависимости от того,
        // какое значение этой ячейки (value), выбрана ли данная ячейка (isSelected),
        // а также координат (row, column) этой ячейки
        currentColor = ( Color ) value;
        return button;
    }
    // для того чтобы активировать редактор необходимо предварительное выделение ячейки

    public boolean shouldSelectCell ( EventObject anEvent )
    {
        return true;
    }

    public boolean isCellEditable ( EventObject e )
    {
        if ( e instanceof MouseEvent )
            // редактор должен быть активирован только в том случае,
            // если был выполнен двойной клик по ячейке и ячейка выделена
            return ( ( MouseEvent ) e ).getClickCount () == 2;
        return false;
    }

}
