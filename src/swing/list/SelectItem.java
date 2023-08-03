package swing.list;


import tools.GuiTools;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Vector;

/**
 * Для отладки изменения выбранного обьекта списка извне (не по клику на него).
 * <BR/> Работает нормально. По нажатию кнокпи сразу переходит на третий элемент.
 * <BR/> Если он вне зоны видимости - подтягивает скролл.
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 02.12.2011 9:05:34
 */

public class SelectItem extends JFrame
{
    private JList list;
    JTextField text;

    public SelectItem ()
    {
        super ( "SelectList" );

        setDefaultCloseOperation ( EXIT_ON_CLOSE );

        // создаем списки
        JPanel contents = new JPanel ( new BorderLayout ( 5,5));

        // динамически наполняем вектор
        Vector big = new Vector ();
        for ( int i = 0; i < 30; i++ )
        {
            big.add ( "# " + i );
        }

        list = new JList ( big );
        list.setPrototypeCellValue ( "12345" );

        // добавляем списки в панель
        contents.add ( new JScrollPane ( list ), BorderLayout.CENTER );

        // поле перехода
        text    = new JTextField();
        text.setColumns ( 20 );
        text.setText ( "(поиск)" );
        contents.add ( text, BorderLayout.NORTH );
        text.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed( KeyEvent e){
                if ( e.getKeyCode() == KeyEvent.VK_ENTER ) {
                        search ( text.getText() );
                }
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                        text.setText ( "" );
                }
            }
        });

        //
        JButton button = new JButton ( "Перейти на третий элемент" );
        button.addActionListener ( new ActionListener() {
            @Override
            public void actionPerformed ( ActionEvent e )
            {
                // Номера индексов отображаются правильно.
                System.out.println ( "-- old index = " + list.getSelectedIndex() );
                // релоад панели здесь не нужен.
                //list.setSelectedIndex ( 2 );
                // true - чтобы и скролл подтянул, если элемент - вне зоны видимости.
                list.setSelectedValue ( "# 3", true );
                // Если два селекта подряд - то актуален последний
                //list.setSelectedValue ( "# 5", true );
                System.out.println ( "---- new index = " + list.getSelectedIndex() );
            }
        });
        contents.add ( button, BorderLayout.SOUTH );

        // выводим окно на экран
        setContentPane ( contents );
        setSize ( 500, 400 );
        GuiTools.setDialogScreenCenterPosition ( this );
        
        setVisible ( true );
    }

    private void search ( String text )
    {
        System.out.println ( "search text = '" + text + "'." );
        //list.getModel ().
    }

    public static void main ( String[] args )
    {
        new SelectItem ();
    }

}