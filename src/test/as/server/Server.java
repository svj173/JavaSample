package test.as.server;


import com.svj.utils.FileTools;
import com.svj.utils.NumberTools;
import com.svj.utils.socket.Listener;
import com.svj.utils.socket.SocketHandlersPool;
import com.svj.xml.TreeObject;
import org.apache.logging.log4j.*;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Properties;


/**
 * Тестовый сервер.
 * User: svj
 * Date: 16.02.2007
 * Time: 15:06:06
 */
public class Server
{

    private static Logger logger = LogManager.getFormatterLogger ( Server.class );

    private static Server ourInstance = new Server ();

    private Listener listener;


    public static Server getInstance ()
    {
        return ourInstance;
    }

    private Server ()
    {
    }

    public void init ( String configFile ) throws Exception
    {
        String      str;
        Properties  config;
        ServerShutdownHook shutdownHook;

        logger.info ( "Start. fileName = " + configFile );

        // Здесь, в самом начале, чтобы при ошибке ниже все нормально бы закрылось, что успело уже подняться.
        shutdownHook = new ServerShutdownHook ();
        try
        {
           Runtime.getRuntime().addShutdownHook ( shutdownHook );
           logger.info ( "Add ShutdownHook." );
        } catch ( Exception he )
        {
           logger.error ( "Can't run ShutdownHook. Reason: " + he.getMessage(), he );
        }

        // -------------  Взять и распарсить файл  начальных параметров. -------------
        //  Если его нет - ошибка
        config  = FileTools.loadProperties(configFile);
        //logger.debug ( "Config = \n" + config );

        // Инсталляция функций
        createService ( config );

        // Инсталляция слушателя
        createListener ( config );

        Par.working = true;

        logger.info ( "Finish" );
    }

    private void createService(Properties config)
    {
        Enumeration en;
        String  str, name;
        Object  cl;
        en  = config.keys();
        while ( en.hasMoreElements() )
        {
            name    = (String)en.nextElement();
            if ( name.equals("port")) continue;
            if ( name.equals("timeout")) continue;
            str     = config.getProperty(name);
            // Инсталлирвоать класс
            try
            {
                cl  = Class.forName ( str ).newInstance ();
            } catch (Exception e) {
                logger.error("Install service error. Service = " + name + ", Class = " + str, e );
                continue;
            }
            // Добавить в массив сервисов
            ServiceManager.getInstance().addService ( name, cl );
        }

    }

    private void createListener ( Properties config ) throws Exception
    {
        String      str;
        TreeObject  to;
        Hashtable ssl;
        int         poolSize, connectionTimeout, port;
        SocketHandlersPool pool;
        HandlerFactory  factory;

        str         = config.getProperty("port");
        port        = NumberTools.getInt ( str, "Invalid listen Port" );
        str                 = config.getProperty("timeout");
        connectionTimeout   = NumberTools.getInt ( str, "Invalid time out" );
        connectionTimeout   = connectionTimeout * 1000;

        // Создать фабрику обработчиков
        factory    = new HandlerFactory();

        poolSize    = 10;
        pool        = new SocketHandlersPool ( "Pool", factory, poolSize );
        listener    = new Listener ( "Listener" );
        // ( HandlersPool pool, int timeout, int port, Hashtable ssl )
        listener.init ( pool, connectionTimeout, port, null );

    }


    /**
     * Завершить работу менеджера - корректно закрыть все поднятые процессы.
     */
    public void close ()
    {
        logger.info ( "Start" );
        Par.working = false;
        if (listener != null) listener.close ();
        logger.info ( "Finish" );
    }


    public Listener getListener()
    {
        return listener;
    }


    public static void main ( String[] args )
    {
        String      str;
        Listener    listener;

        try
        {
            /*
            GMPar.MODULE_HOME         = System.getProperty ( "module.home" );

            // ----------- Инициализируем логгер   ----------------------
            str = System.getProperty ( "log4j" );
            str = FileTools.createFileName ( str, GMPar.MODULE_HOME );
            LoggerTools.createLogger (str);
            */
            logger.info ( "\n----------------------------------------------------" );

            Server.getInstance().init ( args[0] );

            // Поднять отдельный поток
            listener  = Server.getInstance().getListener();
            listener.start();

            // Ждем завершения потоков
            listener.join();

            System.out.println ( "Finish" );

        } catch ( Exception e ) {
            e.printStackTrace ();
        }
        System.exit ( 0 );
    }

}
