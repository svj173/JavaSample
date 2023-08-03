package net.northbound;


import exception.SvjException;
import thread.ThreadProcess;

import java.util.ArrayList;
import java.util.Collection;

/**
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 11.09.2013 14:22
 */
public class NbTest
{
    private final String    url;
    private final int       processSize;

    public NbTest ( String url, int processSize )
    {
        this.url         = url;
        this.processSize = processSize;
    }

    public static void main ( String[] args )
    {
        NbTest              dateTest;
        String              urlReq;
        Collection<Thread>  list;
        int                 processSize;

        // logger to console
        //LogWriter.initConsole();

        //urlReq   = "http://192.168.26.69:8080/northbound/getDeviceSnmpAttr?ip=192.168.16.223";
        urlReq      = "http://192.168.26.137:8080/northbound/getDeviceSnmpAttr?ip=192.168.16.223";

        //processSize = 5;        // OK
        //processSize = 10;       // много ошибок
        processSize = 30;       // много ошибок

        dateTest    = new NbTest ( urlReq, processSize );  // по 10 запусков на каждый процесс.

        try
        {
            list    = dateTest.init();
            dateTest.start ( list );

            // конец работы
            System.out.println ( "=============\n All  : " + NbTestTools.getAllCounter() + "\n Error: " + NbTestTools.getErrCounter() );

        } catch ( Exception e )         {
            //System.out.println ( "Error", e );
            e.printStackTrace ();
        }
    }

    private Collection<Thread> init ()
    {
        int                 count;
        NbHandler           nbHandler;
        thread.ThreadProcess       process;
        Collection<Thread>  list;
        Thread              thread;

        NbTestTools.resetCounters();

        count   = 10;
        list    = new ArrayList<Thread> ( processSize );

        try
        {
            // Создаем даты + потоки
            for ( int i=0; i<processSize; i++ )
            {
                nbHandler   = new NbHandler ( url );
                process     = new ThreadProcess ( nbHandler, count, Integer.toString(i) );
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
        try
        {
            // запуск
            for ( Thread tp : list )
            {
                tp.start();
            }

            // Ожидание завершения работы.
            for ( Thread tp : list )
            {
                tp.join();
            }
        } catch ( Exception e )         {
            //LogWriter.l.error ( "Start NbTest error",e );
            e.printStackTrace ();
        }
    }

}
