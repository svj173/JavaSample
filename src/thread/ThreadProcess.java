package thread;


/**
 * <BR> Обработчик в собственном потоке..
 * <BR>
 * <BR> User: svj
 * <BR> Date: 11.09.2013
 * <BR> Time: 13:45:46
 */
public class ThreadProcess implements Runnable
{
    private final IHandler handler;

    /* Имя процесса. */
    private String title = "Process";

    /* счетчик обработанных задач. */
    private final long handleCounter;


    public ThreadProcess ( IHandler handler, long handleCounter, String name )
    {
        this.handler = handler;
        this.handleCounter = handleCounter;
        this.title = name;
    }

    public StringBuilder getInfo ()
    {
        StringBuilder result;

        result  = new StringBuilder(128);
        result.append ( "[ ThreadProcess: " );
        result.append ( " name = " );
        result.append ( getName() );
        result.append ( "; handleCounter = " );
        result.append ( handleCounter );
        result.append ( " ]" );

        return result;
    }

    public void run ()
    {
        Object obj;

        Thread.currentThread().setName ( getName() );

        //LogWriter.l.debug ( "Start. Handler Name = ", name );
        
        for ( int i=0; i<handleCounter; i++ )
        {
            obj = null;
            try
            {
                obj = handler.handle ( null );
                Thread.sleep ( 4 );

            } catch ( Exception ex )                        {
                //LogWriter.l.error ( "Error : ", ex );
                ex.printStackTrace ();
            }
            //System.out.println ( "--- "+ getName()+ " ("+i+ ") : "+ obj );
        }
        //LogWriter.l.info ( "Finish. TIME: thread '", this, "' terminated. Work time = ", ( (new Date().getTime () - startDate.getTime() )),
        //         ", handleCounter = ", handleCounter );
    }

    public String getName ()
    {
        return title;
    }

}
