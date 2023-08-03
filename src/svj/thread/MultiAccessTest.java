package svj.thread;


import exception.SvjException;
import net.UrlManager;
import net.northbound.NbTestTools;
import thread.ThreadProcess;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Тест проверки обращения из потоков к одному функицоналу - будут ли сбои.
 * <BR/>  Несколько потоков обращаются к одному статик методу (не synchronized !) - commonProcess.
 * В нем задержки работы - Thread.sleep либо чтение URL.
 * <BR/>
 * <BR/> Результат: Все работает нормально.
 * <BR/>
 * <BR/>
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 01.12.2017 11:41
 */
public class MultiAccessTest
{
    private final String    name;
    private final int       processSize;

    public MultiAccessTest ( String name, int processSize )
    {
        this.name        = name;
        this.processSize = processSize;
    }

    public static void main ( String[] args )
    {
        MultiAccessTest     dateTest;
        String              name;
        Collection<Thread>  list;
        int                 processSize;

        name = "Proc";


        //processSize = 5;        // OK
        //processSize = 10;       // много ошибок
        processSize = 30;       // много ошибок

        dateTest    = new MultiAccessTest ( name, processSize );

        try
        {
            list    = dateTest.init();
            dateTest.start ( list );

            //Thread.sleep ( 30000 );

            // конец работы
            System.out.println ( "Finish" );

        } catch ( Exception e )         {
            //System.out.println ( "Error", e );
            e.printStackTrace ();
        }
    }

    private Collection<Thread> init ()
    {
        int                     count;
        MultiAccessHandler      nbHandler;
        thread.ThreadProcess    process;
        Collection<Thread>      list;
        Thread                  thread;

        NbTestTools.resetCounters();

        //count   = 10;
        count   = 3;
        list    = new ArrayList<Thread> ( processSize );

        try
        {
            // Создаем даты + потоки
            for ( int i=0; i<processSize; i++ )
            {
                nbHandler   = new MultiAccessHandler ( name+"_"+i );
                process     = new ThreadProcess ( nbHandler, count, Integer.toString( i) );
                thread      = new Thread ( process );
                list.add ( thread );
            }

        } catch ( Exception e )         {
            //LogWriter.l.error ( "Init error",e );
            e.printStackTrace ();
        }
        return list;
    }

    private void start ( Collection<Thread> list ) throws SvjException
    {
        int ic = 50;
        try
        {
            // запуск
            for ( Thread tp : list )
            {
                tp.start();
                //Thread.sleep ( ic );
                ic++;
                // Ожидание завершения работы - сразу же, а то можем не успеть запустить Join.
                //tp.join();
            }

            /*
            // Ожидание завершения работы - сразу же, а то можем не успеть запустить Join.
            for ( Thread tp : list )
            {
                tp.join();
            }
            */
        } catch ( Exception e )         {
            //LogWriter.l.error ( "Start NbTest error",e );
            e.printStackTrace ();
        }
    }

    public static String commonProcess ( String str )
    {
        UrlManager urlManager;
        String result, urlStr;

        result = null;

        // http://192.168.26.91:8080/northbound/getDeviceSnmpAttr?ip=192.168.40.40
        urlStr = "http://192.168.42.220:8080/northbound/getDeviceSnmpAttr?ip=192.168.15.119";
        //urlStr = "http://lib.ru/ANEKDOTY/parachute.txt";
        //urlStr = "http://lib.ru/PARACHUTE/";
        
        urlManager = new UrlManager();

        for ( int i=0; i<100; i++ )
        {
            try
            {
                // лучше внешнее обращение - БД, УРЛ...
                //Thread.sleep ( 8 );
                urlManager.loadDataByUrl ( urlStr );
                result = str;
            } catch ( Exception e )             {
                System.err.println ( "Name = "+str + "; result = "+result+"; i = "+ i );
                e.printStackTrace();
            }
        }

        return result;
    }

}
