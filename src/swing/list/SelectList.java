package swing.list;


import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Vector;

/**
 * Простейший способ создания списков
 * <BR/> По умолчанию - множественный выбор.
 * <BR/> Функция селекта - для отладки отрисовки списка при выборе обьекта, лежащего вне поля зрения (подтягивание списка).
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 28.09.2010 9:35:34
 */

public class SelectList extends JFrame
{
    private JList list;
    JTextField text;

    public SelectList ()
    {
        super ( "SelectList" );

        setDefaultCloseOperation ( EXIT_ON_CLOSE );

        // создаем списки
        JPanel contents = new JPanel ();

        // динамически наполняем вектор
        Vector big = new Vector ();
        for ( int i = 0; i < 50; i++ )
        {
            big.add ( "# " + i );
        }
        JList bigList = new JList ( big );
        bigList.setPrototypeCellValue ( "12345" );

        // добавляем списки в панель
        contents.add ( new JScrollPane ( bigList ) );

        // поле перехода
        text    = new JTextField();
        contents.add ( text );
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

        // выводим окно на экран
        setContentPane ( contents );
        setSize ( 300, 200 );
        setVisible ( true );
    }

    private void search ( String text )
    {
        //list.getModel ().
    }

    public static void main ( String[] args )
    {
        new SelectList ();
    }

}