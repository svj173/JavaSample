package file.filename;


import java.io.*;


/**
 * Фильтр, применяемый исключительно к имени файла
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 24.03.2011 13:15:27
 */
public class OnlyExt implements FilenameFilter
{
    String ext;

    public OnlyExt ( String ext )
    {
        this.ext = "." + ext;
    }

    public boolean accept ( File dir, String name )
    {
        return name.endsWith ( ext );
    }
    
}