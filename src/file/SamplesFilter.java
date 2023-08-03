package file;


import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;


/**
 * Разные примеры применения фильтров.
 * <BR/> - Брать все
 * <BR/> - Исключить файлы, начинающиеся с точки
 * <BR/> - Брать только директории
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 24.03.2011 13:17:34
 */
public class SamplesFilter
{
    public static void main ( String[] args )
    {
        File dir = new File("directoryName");

        String[] children = dir.list();
        if (children == null) {
            // Either dir does not exist or is not a directory
        } else {
            for (int i=0; i<children.length; i++) {
                // Get filename of file or directory
                String filename = children[i];
                System.out.println ( "filename: " + filename );
            }
        }

        // It is also possible to filter the list of returned files.
        // This example does not return any files that start with `.'.
        FilenameFilter filter = new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return !name.startsWith(".");
            }
        };
        children = dir.list(filter);
        System.out.println ( "children: " + children );


        // The list of files can also be retrieved as File objects
        File[] files = dir.listFiles();

        // This filter only returns directories
        FileFilter fileFilter = new FileFilter() {
            public boolean accept( File file) {
                return file.isDirectory();
            }
        };
        files = dir.listFiles(fileFilter);
        System.out.println ( "files: " + files );

    }

}
