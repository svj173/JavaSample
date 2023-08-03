package logger;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;

import java.util.Date;

/**
 * Наолняем лог чтобы логгер создал необхзодимые файлы с переполнением инфы - узнать что из инфы он отрежет.
 * <BR/> 20Кб. 3 файла.  - 60000 символов.
 * <BR/>
 * <BR/> Файл log4j2.xml складывать в /home/svj/projects/SVJ/JavaSample/deploy/classes
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 22.07.2016 11:47
 */
public class TestSaveToLog
{
    private static Logger log = LogManager.getFormatterLogger ( "Kernel" );

    public static void main ( String[] args )
    {
        String msg;
        TestSaveToLog tester;
        int max, ic;

        msg = "11111222223333344444555556666677777888889999900000"; // 50 символов

        // 60000 : 50 = 12000
        //max = 20000;
        max = 2000;

        System.out.println ( "-- start = " + new Date() );

        ThreadContext.push ( "start" );  // метка Х

        ic = 0;
        for ( int i=0; i<max; i++ )
        {
            //log.info ( msg );
            log.error ( msg );
            ic++;
            if ( ic >= 100 )
            {
                ic = 0;
                ThreadContext.pop();
                ThreadContext.push ( ""+(i+1) );
            }
        }

        System.out.println ( "-- finish = " + new Date() );

        //log.info ( "----------------- TESt -------------" );


        /*

        try
        {

        } catch ( Exception e )  {
            e.printStackTrace ();
        }
        */
    }

}
