package file.img;


import java.io.File;
import java.io.FileFilter;


/**
 * Фильтруем файлы по расширению - здесь получаем список только image файлов.
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 24.03.2011 12:08:17
 */
public class ImageFileFilter implements FileFilter
{
    private final String[] okFileExtensions = new String[] { "jpg", "png", "gif" };

    public boolean accept ( File file )
    {
        for ( String extension : okFileExtensions )
        {
            if ( file.getName().toLowerCase().endsWith ( extension ) )
            {
                return true;
            }
        }
        return false;
    }

}
