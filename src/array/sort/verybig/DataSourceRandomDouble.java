package array.sort.verybig;


import java.util.Iterator;
import java.util.Random;

/**
 * Источник данных дял сортирвоки - генератор случайных чисел (до 1000 шт).
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 11.09.2015 12:58
 */
public class DataSourceRandomDouble  implements Iterator<Double>
{
    private int i = 0;
    private Random rand = new Random ();

    public boolean hasNext ()
    {
        if ( i >= 1000 )
        {
            System.out.println ( "generator finish" );
        }
        return i < 1000;
    }

    public Double next ()
    {
        i++;
        return rand.nextDouble ();
    }

    public void remove ()
    {
        throw new UnsupportedOperationException ();
    }

}
