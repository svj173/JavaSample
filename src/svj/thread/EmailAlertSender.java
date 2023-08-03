package svj.thread;


import annotation.s1.User;
import exception.SvjException;

import javax.mail.internet.InternetAddress;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <BR/> Режимы тестирования:
 * <BR/> +1) Когда процесс обработки поднят.
 * <BR/> 2) Когда процесс обработки НЕ поднят.
 * <BR/> 3) Проблемы с отправкой почты - ошибочный отчет / запись / адрес.  -- см время обработки.
 * <BR/>
 * <BR/> Смотреть в VisualVM - Как ведут себя эти обьекты - сколько их (EmailAlertSender).
 * <BR/>
 * <BR/> Единственный вызов - в EmailAlertHandler. Но этого обьекта - много. И он клонируется. Может как-то из-за этого?
 * <BR/>
 * <BR/> Боевой сервер
 * <BR/> 1) рассылает 1.5 миллиона почтовых сообщений в день.
 * <BR/>
 * <BR/> Возможные выводы
 * <BR/> 1) Переполнение очереди до 7Гб - не справляется один-единсвтенный поток с таким обьемом данных.
 * <BR/>
 * <BR/>
 * <BR/>
 * <BR/>
 * <BR/>  shallow heap  - мелкая "куча"
 * <BR/>  retained heap - сохраненная "куча"
 * <BR/>
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 30.03.2016 11:40
 */
public class EmailAlertSender extends ObjectHandler<Object>
{
    private static final int MAX_QUEUE_PACKET_SIZE = 50;

    private volatile String subject, body;
    private volatile InternetAddress[] from;

    // На всякий случай, чтоб не было эксцессов при редактировании пользователей
    private ConcurrentHashMap<String,User> userHashMap = new ConcurrentHashMap<String,User>();

    private static EmailAlertSender ourInstance = new EmailAlertSender();


    public static EmailAlertSender getInstance ()
    {
        return ourInstance;
    }

    public EmailAlertSender ()
    {
        setTitle ( "Test" );
        setSubTitle ( "Send alerts to users via e-mail" );
        setMaxPickSize ( MAX_QUEUE_PACKET_SIZE );
    }

    /*
    @Override
    public void startHandler ()
    {
        if ( loadEmailParameters () )
        {
            loadUsers ();
            super.startHandler ();
        }
    }
    */

    protected void handle(List<Object> objects) throws SvjException
    {
        int pauseMsec;

        printMsg ( "Object size =", objects.size() );
        //pauseMsec = 2000;
        pauseMsec = 500;
        for ( Object alert : objects )
        {
            try
            {
                //msg = createMsg ( alert );
                //System.out.println ( "-- "+ Convert.getRussianDateTime (new Date())+" Object : " + alert + "; Sender = " + this.getClass ().toString () );
                printMsg ( "-- Object=", alert );
                Thread.currentThread().sleep ( pauseMsec );
            } catch ( Exception e )            {
                e.printStackTrace ();
            }
        }
    }

    @Override
    protected void handleEmpty() {
    }

    @Override
    protected void waitRunning() {
    }

    @Override
    protected void handleError(Exception exc) {
    }

    @Override
    public void setProperties(Properties props) throws SvjException {
    }

    public static void main ( String[] args )
    {
        final int  IMAX = 10;
        Thread t;
        Collection<Thread> threads;

        threads = new ArrayList<Thread> ();

        //EmailAlertSender.getInstance().startHandler();

        // Создаем несколько потоков, которые в свою очередь несколкьо раз дергают Сендер

        for ( int ic = 0; ic < 20; ic++ )
        {
            final int finalIc = ic;
            t = new Thread ( new Runnable ()
            {
                @Override
                public void run ()
                {
                    String alert;
                    for ( int i = 0; i < IMAX; i++ )
                    {
                        alert = "Alert:"+ finalIc +"_"+i;
                        EmailAlertSender.getInstance().publish ( alert );
                        try
                        {
                            Thread.currentThread ().sleep ( 10 );
                        } catch ( Exception e )    {
                            e.printStackTrace ();
                        }
                    }
                }
            } );
            t.setName ( "Thread_" + ic );
            System.out.println ( "Create thread = " + t.getName () );
            threads.add ( t );
        }

        System.out.println ( "threads = "+threads.size() );

        for ( Thread th : threads )
        {
            th.start();
        }

        System.out.println ( "Start all process. Wait join." );

        // Ждем завершения всех поднятых процессов. -- Почему-то не работает. У всех процессов флаг isAlive = false;
        /*
        for ( Thread th : threads )
        {
            System.out.println ( "-- Thread:"+th.getName()+"; isAlive = "+ th.isAlive() + "; isInterrupted = "+th.isInterrupted()+"; isDaemon = "+th.isDaemon () );
            try
            {
                th.join();
            } catch (  Exception e )     {
                e.printStackTrace();
            }
        }
        */
        try
        {
            Thread.currentThread ().sleep ( 120000 );
        } catch ( Exception e )         {
            e.printStackTrace ();
        }

        System.out.println ( "Finish sender: " + EmailAlertSender.getInstance().getInfo() );
        EmailAlertSender.getInstance().stopHandler ();
    }

}
