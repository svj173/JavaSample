package test.as.server;

import org.apache.logging.log4j.*;

/**
 * Класс корректного завершения работы сервера.
 * User: svj
 * Date: 16.02.2007
 * Time: 15:07:43
 */
public class ServerShutdownHook  extends Thread
{
    private static Logger logger = LogManager.getFormatterLogger ( ServerShutdownHook.class );

    public static boolean shutdownActive;


    /**
     */
    public ServerShutdownHook ()
    {
        logger.info ( "Start Shutdown" );
        setName ( "Shutdown" );
    }

    /**
     * Запуск процесса останова MServer
     */
    public void run ()
    {
        logger.info ( "Start shutdown. Waiting for real work is finished." );

        shutdownActive = true;

        Server.getInstance().close();

        logger.info ( "Finish" );
    }

    /**
     * Остановить обработку запросов.
     * <BR> Если shutdown инициирован, то из этого вызова не возвращаться.
     */
    public static void checkShutdown ()
    {
       if ( shutdownActive )
       {
          logger.info ( "Shutdown active. Thread " +
                  Thread.currentThread().hashCode () + "/" +
                  Thread.currentThread().getName () + " stopped" );
          while ( true )
          {
             Thread.yield ();
          }
       }
    }

}
