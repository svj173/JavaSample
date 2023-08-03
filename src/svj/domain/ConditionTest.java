package svj.domain;


import svj.obj.DomainObj;
import tools.DumpTools;

import java.util.ArrayList;
import java.util.Collection;
import java.util.StringTokenizer;

/**
 * Проверка условий.
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 18.10.2016 16:02
 */
public class ConditionTest
{
    DomainContainer dc;

    public static void main ( String[] args )
    {
        ConditionTest test = new ConditionTest();

        test.process ();

        System.out.println ( "Finish" );
    }

    public void process ()
    {
        String                  value;
        DomainObj               dRoot;

        // Создать домены
        dc      = new DomainContainer ();
        dRoot = createRootDomain ();
        dc.setRoot ( dRoot );

        value   = "CAN.NSK.RT";

        // 1) поиск в одном потоке
        System.out.println ( "------------------------- 1 ----------------------------" );
        for ( int i=0; i<10000; i++ )
        {
            checkDomain ( value );
        }

        // 2) поиск в параллельных потоках
        System.out.println ( "\n\n------------------------- 2 ----------------------------" );
        process2 ();

    }

    private DomainObj createRootDomain ()
    {
        DomainObj result, dNsk, dCan, dTomsk, dPlus;

        result  = new DomainObj ( "RT" );
        dNsk    = new DomainObj ( "NSK" );
        dCan    = new DomainObj ( "CAN" );
        dTomsk  = new DomainObj ( "TOMSK" );
        dPlus   = new DomainObj ( "PLUS" );

        result.addChild ( dTomsk );
        dTomsk.addChild ( dPlus );

        for ( int i=0; i<1000; i++ )
        {
            result.addChild ( new DomainObj ( "A_"+i ) );
        }

        result.addChild ( dNsk );
        dNsk.addChild ( dCan );

        return result;
    }

    private void checkDomain ( String value )
    {
        Collection<DomainObj>   domains;

        domains = getDomainsFromStr ( value, " " );
        if ( !value.trim().isEmpty() && domains.isEmpty() )
        {
            //System.out.println ( "Cannot create domain by string '"+ value+ "'." );
            System.out.println ( Thread.currentThread().getName()+": ERROR. value = '" + value + "'; domains = '" + domains + "'.\n== Tree:\n"+ DumpTools.printDomainAsTree ( dc.getRoot() ) );
        }
        else
        {
            //System.out.println ( "Is OK for string '"+ value+ "'; domains = '" + domains + "'." );
        }
    }

    private void process2 ()
    {
        final int  IMAX = 100;
        Thread t;
        Collection<Thread> threads;

        threads = new ArrayList<Thread> ();

        // Создать поток который бы переустанавливал (сбрасывал и заносил новое дерево) массив доменов
        t = new Thread ( new Runnable ()
        {
            @Override
            public void run ()
            {
                for ( int i = 0; i < IMAX*20; i++ )
                {
                    DomainObj dRoot = createRootDomain ();
                    dc.setRoot ( dRoot );
                    try
                    {
                        Thread.currentThread().sleep ( 5 );
                    } catch ( Exception e )    {
                        e.printStackTrace ();
                    }
                }
                System.out.println ( "Thread '"+Thread.currentThread().getName()+"' Finish." );
            }
        } );
        t.setName ( "Thread_Reset" );
        System.out.println ( "Create thread = " + t.getName() );
        threads.add ( t );

        // Создаем несколько потоков, которые в свою очередь несколкьо раз дергают Сендер

        for ( int ic = 0; ic < 2000; ic++ )
        {
            final int finalIc = ic;
            t = new Thread ( new Runnable ()
            {
                @Override
                public void run ()
                {
                    for ( int i = 0; i < IMAX; i++ )
                    {
                        checkDomain ( "CAN.NSK.RT" );
                        try
                        {
                            Thread.currentThread().sleep ( 100 );
                        } catch ( Exception e )    {
                            e.printStackTrace ();
                        }
                    }
                    System.out.println ( "Thread '"+Thread.currentThread ().getName()+"' Finish." );
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

        System.out.println ( "----------------------- Start all process. Wait join. ---------------------------" );

        // Ждем завершения всех поднятых процессов. -- Почему-то не работает. У всех процессов флаг isAlive = false;
        //*
        for ( Thread th : threads )
        {
            //System.out.println ( "-- Thread:"+th.getName()+"; isAlive = "+ th.isAlive() + "; isInterrupted = "+th.isInterrupted()+"; isDaemon = "+th.isDaemon () );
            try
            {
                th.join();
            } catch (  Exception e )     {
                e.printStackTrace();
            }
        }
        //*/

        /*
        try
        {
            Thread.currentThread ().sleep ( 120000 );
        } catch ( Exception e )         {
            e.printStackTrace ();
        }
        */
    }

    private Collection<DomainObj> getDomainsFromStr ( String domainsStr, String separator )
    {
        Collection<DomainObj>   result;
        StringTokenizer         st;
        DomainObj               dObj;

        result = new ArrayList<DomainObj> ();

        if ( domainsStr != null )
        {
            st = new StringTokenizer ( domainsStr, separator );
            while ( st.hasMoreElements() )
            {
                dObj = dc.getObjectByFullName ( st.nextToken().trim() );
                if ( dObj != null )  result.add ( dObj );
            }
        }

        return result;
    }

}
