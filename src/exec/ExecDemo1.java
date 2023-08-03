package exec;


import java.io.*;

/**
 * Запуск внешней команды ОС. И работа получение результата.
 * <BR>
 * <BR>
 * <BR> User: svj
 * <BR> Date: 29.05.2006
 * <BR> Time: 13:01:09
 */
public class ExecDemo1
{
    static void listDirContents1 ( String dir )     throws IOException
    {
        InputStream inputstream;
        InputStreamReader inputstreamreader;
        BufferedReader bufferedreader;
        String line;
        Runtime runtime;
        Process proc;

        // start the ls command running

        runtime = Runtime.getRuntime ();
        proc    = runtime.exec ( "ls" + " " + dir );      // cmd

        // put a BufferedReader on the ls output

        inputstream         = proc.getInputStream();
        inputstreamreader   = new InputStreamReader ( inputstream );
        bufferedreader      = new BufferedReader ( inputstreamreader );

        // read the ls output

        while ( ( line = bufferedreader.readLine () )   != null )
        {
            System.out.println ( line );
        }

        // check for ls failure

        try
        {
            if ( proc.waitFor () != 0 )
            {
                System.err.println ( "exit value = " + proc.exitValue () );
            }
        }
        catch ( InterruptedException e )
        {
            System.err.println ( e );
        }
    }

    static void listDirContents2 ( String dir )
    {
        // create File object for directory and then list directory contents

        String[] list = new File ( dir ).list ();
        int len = ( list == null ? 0 : list.length );
        for ( int i = 0; i < len; i++ )
        {
            System.out.println ( list[i] );
        }
    }

    public static void main ( String[] args )
            throws IOException
    {
        if ( args.length != 1 )
        {
            System.out.println ( "missing directory" );
            System.exit ( 1 );
        }
        listDirContents1 ( args[0] );
        listDirContents2 ( args[0] );
    }

}
