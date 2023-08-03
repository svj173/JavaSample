package array.sort.verybig2;


import java.util.Comparator;

/**
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 14.09.2015 9:50
 */
public interface SortComparator<T>  extends Comparator<T>
{
    boolean compareIsOk ( T obj1, T obj2 );
}
