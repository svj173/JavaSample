package file;


import java.io.File;


/**
 * Применение фильтра
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 24.03.2011 11:40:06
 */
public class FileFilter01
{
    public static void main ( String[] args )
    {
        String fileDir;
        fileDir = "/tftpboot/svj_test";
        new FileFilter01 ( fileDir );
    }

    public FileFilter01 ( String fileDir )
    {
        File dir;
        File[] files;
        String filePrefix;
        ConfigDeviceFileFilter deviceFileFilter;

        System.out.println ( "dir : " + fileDir );

        //filePrefix  = "6_";
        filePrefix  = "7_";
        System.out.println ( "filePrefix : " + filePrefix );

        dir = new File ( fileDir );

        deviceFileFilter = new ConfigDeviceFileFilter ();
        deviceFileFilter.setPrefix (  filePrefix );

        files = dir.listFiles ( deviceFileFilter );
        for ( File f : files )
        {
            System.out.println ( " -- file: " + f.getName() );
        }
    }

}
