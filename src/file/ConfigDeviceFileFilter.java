package file;


import java.io.File;
import java.io.FileFilter;


/**
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 24.03.2011 12:12:56
 */
public class ConfigDeviceFileFilter implements FileFilter
{
    private String prefix;


    public ConfigDeviceFileFilter ( String prefix )
    {
        this.prefix = prefix;
    }

    public ConfigDeviceFileFilter ()
    {
        this ( null );
    }

    public boolean accept ( File file )
    {
        if ( prefix != null )
        {
            if ( file.getName().startsWith ( prefix ) )    return true;
        }
        return false;
    }

    public void setPrefix ( String prefix )
    {
        this.prefix = prefix;
    }

}
