package swing.list;


import javax.swing.*;
import java.util.*;

/**
 * Простейший способ создания списков
 * <BR/> По умолчанию - множественный выбор.
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 28.09.2010 9:35:34
 */

public class SimpleLists extends JFrame
{
    // данные для списков
    private String[] datal = { "Один", "Два", "Три", "Четыре", " П я т ь " };
    private String[] data2 = { "Просто", "Легко", "Элементарно", "Как дважды два" };

    public SimpleLists ()
    {
        super ( "SimpleLists" );
        setDefaultCloseOperation ( EXIT_ON_CLOSE );

        // создаем списки
        JPanel contents = new JPanel ();
        JList listl = new JList ( datal );

        // для второго списка используем вектор .
        Vector data = new Vector ();
        data.addAll ( Arrays.asList ( data2 ) );
        JList list2 = new JList ( data );

        // динамически наполняем вектор
        Vector big = new Vector ();
        for ( int i = 0; i < 50; i++ )
        {
            big.add ( "# " + i );
        }
        JList bigList = new JList ( big );
        bigList.setPrototypeCellValue ( "12345" );

        // добавляем списки в панель
        contents.add ( listl );
        contents.add ( list2 );
        contents.add ( new JScrollPane ( bigList ) );

        // выводим окно на экран
        setContentPane ( contents );
        setSize ( 300, 200 );
        setVisible ( true );
    }

    public static void main ( String[] args )
    {
        new SimpleLists ();
    }

}
