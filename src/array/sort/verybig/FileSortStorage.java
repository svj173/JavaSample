package array.sort.verybig;


import java.io.IOException;
import java.util.List;

/**
 * Интерфейс для сохранения на диске:
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 11.09.2015 12:26
 */
public interface FileSortStorage<T> extends Iterable<T>
{
    public void setObjects(List<T> objects) throws IOException;
}
