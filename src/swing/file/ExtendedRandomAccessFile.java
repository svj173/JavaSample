package swing.file;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;


/**
 * Не давно, я начал писать на Java, у меня возникла необходимос в написании простенькой программки для учёта звонков.
 * Учтенные звонки я решил хранить в файлах, в названии которых будет дата + последний звонок должен быть сверху, первый снизу.
 * Для такой структуры файла мне потребовалось вставлять записи вначало, отодвигая все предыдущие записи.
 * Для этого было написано расширение класса RandomAccessFile.
 * Конкретно в фонде Apache Commons такой же штуки мне найти не удалось.
 * И ещё сразу оговорюсь, сам класс я нашел на просторах интернета(ссылка в конце статьи), немного модифицировав его.
 * @author http://www.sql.ru/forum/actualutils.aspx?action=gotomsg&tid=86071&msg=625007
 *
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 09.12.2010 15:44:27
 */
public class ExtendedRandomAccessFile extends RandomAccessFile
{
   /**
    * Буффер для для чтения и записи в файл
    */
   private byte[] buffer = new byte[8192];

   public ExtendedRandomAccessFile(String name, String mode) throws FileNotFoundException {
      super(name, mode);
   }

   public ExtendedRandomAccessFile(File file, String mode) throws FileNotFoundException {
      super(file, mode);
   }

   public void insert(byte[] buf, long off) throws IOException {
      if (buf == null) {
         throw new NullPointerException();
      }
      if (off < 0) {
         throw new IndexOutOfBoundsException();
      }
      if (buf.length == 0) {
         return;
      }
      /**
       * Запоминаем старый размер файла
       */
      long oldLength = length();

      if (off < oldLength) {
         /**
          * Если смещение меньше длины файла, т.е. вставляем как
          * бы в середину файла...
          * Увеличиваем длину файла на длину вставляемого массива
          */
         setLength(oldLength + buf.length);
         /**
          * Точка начиная с которой будем двигать куски файла в конец
          * buffer.length -размер передвигаемого куска
          */
         long startChunk = oldLength - buffer.length;
         while (true) {
            /**
             * Сначала передвигаем куски равные buffer.length
             */
            if (startChunk >= off) {
               seek(startChunk);
               readFully(buffer);
               seek(startChunk + buf.length);
               write(buffer);
            } else {
                /**
                 * В конце обычно остается кусок меньше buffer.length
                 * соответственно читаем не весь буфер, а только часть
                 */
               seek(off);
               int chunkLength = (int) (buffer.length + startChunk - off);
               readFully(buffer, 0, chunkLength);
               seek(off + buf.length);
               write(buffer, 0, chunkLength);
               break;
            }
            startChunk -= buffer.length;
         }
      } else {
         /**
          * Если смещение больше длины файла,
          * ничего сохранять и перемещать ненадо, просто увеличиваем файл:
          */
         setLength(off + buf.length);
      }
      /**
       * Все данные перемещены(если требовалось), размер файла увеличен
       * перемещаемся на нужную позицию
       */
      seek(off);
      /**
       * и записываем
       */
      write(buf);
   }
    
}
