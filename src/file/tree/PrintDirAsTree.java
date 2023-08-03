package file.tree;


import java.io.File;
import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;

/**
 * Вывести директории в виде дерева.
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 28.11.2014 17:54
 */
public class PrintDirAsTree
{
    public PrintDirAsTree ()
    {
    }

    private void handle ( File dirname, int margin )
    {
        String space;
        File[] files;
        Set<File> list;

        files   = dirname.listFiles();
        // Упорядочиваем по алфавиту
        list    = new TreeSet<File> ();
        if ( files != null )  Collections.addAll ( list, files );

        for ( File file : list )
        {
            if ( file.isDirectory() )
            {
                space = createMargin ( margin );
                System.out.println ( space + file.getName() );
                handle ( file, margin + 1 );
            }
        }
    }

    private String createMargin ( int margin )
    {
        StringBuilder result;

        result = new StringBuilder ();

        if ( margin > 0 )
        {
            for ( int i=0; i<margin; i++ ) result.append ( "  " );
        }

        return result.toString();
    }

    public static void main ( String args[] )
    {
        PrintDirAsTree handler;
        String dirname = "/home/svj/Serg/Raznoe";

        handler = new PrintDirAsTree ();
        handler.handle ( new File(dirname), 0 );
    }

}
