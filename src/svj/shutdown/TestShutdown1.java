package svj.shutdown;


/**
 * По Ctrl-C отрабатывает ShutdownHook.
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 21.09.2016 16:55
 */
public class TestShutdown1
{
    public static void main ( String[] args ) throws Exception
    {

        Runtime r = Runtime.getRuntime ();
        r.addShutdownHook ( new MyThread () );

        System.out.println ( "Now main sleeping... press ctrl+c to exit" );
        try
        {
            Thread.sleep ( 300000 );
        } catch ( Exception e )   {
            // При Ctrl-C сюда уже не приходит
            e.printStackTrace ();
        }
        // При Ctrl-C сюда уже не приходит
        System.out.println ( "Finish" );
    }

}
