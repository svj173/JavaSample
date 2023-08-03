package tools;


import exception.SvjException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;


/**
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 08.07.2011 10:33:31
 */
public class FileTools
{
    /**
      * Прочитать файл с текстом. Выдать результат.
      * Если ошибка - Exception.
      * @param fileName    имя файла
      * @param codePage    кодировка файла
      * @return            Текстовое содержимое файла
      * @throws exception.SvjException ошибка чтения файла
      */
     public static String loadFile ( String fileName, String codePage ) throws SvjException
     {
         //Log.l.debug ( "Start loadFile: Name = ", fileName );
         String result = "";

         try
         {
             // Загрузить файл
             File f = new File ( fileName );
             FileInputStream fis = new FileInputStream ( f );
             byte[] buf = new byte[( int ) f.length ()];
             fis.read ( buf );
             //
             if ( codePage == null )
                 result = new String ( buf );
             else
                 result = new String ( buf, codePage );

         } catch ( Exception e )        {
             //LogWriter.file.error ( e, "Load file ERROR. codePage = '", codePage, "', fileName = '", fileName, "'" );
             throw new SvjException ( e, "Ошибка чтения файла '", fileName, "' с кодировкой '", codePage, "' :\n", e );
         }
         //LogWriter.file.debug ( "load: Finish" );

         return result;
     }


    public static byte[] readFile ( String fileName )  throws Exception
    {
        File file;
        long size;
        byte[] bb, signText;
        FileInputStream fis;

        // читаем текст
        file = new File ( fileName );
        size = file.length();
        bb   = new byte[(int)size];
        fis  = new FileInputStream ( file );
        fis.read ( bb );
        fis.close();

        return bb;
    }

    public static void save ( String fileName, byte[] text ) throws Exception
    {
        FileOutputStream fw;

        try
        {
            fw = new FileOutputStream ( fileName );
            // Записать в основной
            fw.write ( text );
            fw.flush ();
            fw.close ();
        } catch ( Exception e )         {
            throw new Exception ( "Ошибка сохранения данных в файл '" + fileName + "'.", e );
        }
    }

    /**
     * Создать полное имя файла - с абсолютным путем.
     *
     * @param fileName Имя файла
     * @return Полное имя файла
     */
    public static String createFileName ( String fileName )
    {
        if ( fileName == null ) return null;

        if ( fileName.startsWith ( "/" ) || ( fileName.indexOf ( ':' ) > 0 ) )
        {
            // Это абсолютный путь - взять как есть
            return fileName;
        }
        else
        {
            // Добавить абс путь
            String path = "/home/svj";

            // добавить разделитель - если необходимо
            String sep = File.separator;
            if ( path.endsWith ( "/" ) || path.endsWith ( "\\" ) ) sep = "";
            return path + sep + fileName;
        }
    }

    public static String createFileName ( String path, String fileName )
    {
        if ( fileName == null ) return null;

        if ( fileName.startsWith ( "/" ) || ( fileName.indexOf ( ':' ) > 0 ) )
        {
            // Это абсолютный путь - взять как есть
            return fileName;
        }
        else
        {
            // Добавить абс путь

            // добавить разделитель - если необходимо
            String sep = File.separator;
            if ( path.endsWith ( "/" ) || path.endsWith ( "\\" ) ) sep = "";
            return path + sep + fileName;
        }
    }

}
