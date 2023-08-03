package file.sorting;


import file.ConfigDeviceFileFilter;

import java.io.File;
import java.util.List;


/**
 * Получаем список отфильтрованных файлов. Этот список сортируем по дате.
 */
public class FileSorterTest2
{
    public FileSorterTest2 ( String fileDir )
    {
        File dir;
        File[] files;
        String filePrefix;
        ConfigDeviceFileFilter deviceFileFilter;
        List<File> searchRes;

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

        System.out.println ( " ----------------- by date ----------------- " );

        FileSorter sorter = new FileSorter ( FileSorter.SortType.SORT_BY_DATE );
        searchRes = sorter.sort ( files );
        for ( File f : searchRes )
        {
            System.out.println ( " -- file: " + f.getName() );
        }

    }

    public static void main ( String[] args )
    {
        String fileDir;
        fileDir = "/tftpboot/svj_test";
        new FileSorterTest2 ( fileDir );
    }

}
