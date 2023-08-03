package svj.calendar;


import java.util.Calendar;
import java.util.Date;

/**
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 29.04.2016 11:24
 */
public class Calendar1
{
    public static void main ( String[] args )
    {
        Calendar calendar;
        Date startPeriod, endPeriod;

        // Вычисляем дату начала периода и дату окончания.

        // 1) исходный
        calendar = Calendar.getInstance ();
        calendar.set ( Calendar.DAY_OF_MONTH, 1 );
        calendar.add ( Calendar.MONTH, 1 );        // следующий месяц (номер месяца увеличиваем на 1)
        endPeriod = calendar.getTime();
        calendar.add(Calendar.MONTH, -12 * 3);
        startPeriod = calendar.getTime();

        System.out.println ( "1) startPeriod = "+startPeriod+"; endPeriod = "+ endPeriod );

        endPeriod   = createEndPeriod ();
        startPeriod = createStartPeriod ( endPeriod );
        System.out.println ( "2) startPeriod = "+startPeriod+"; endPeriod = "+ endPeriod );
    }

    private static Date createStartPeriod ( Date endPeriod )
    {
        Date  startPeriod;
        Calendar calendar;

        calendar = Calendar.getInstance ();
        calendar.setTime ( endPeriod );
        calendar.add(Calendar.MONTH, -12 * 3);
        startPeriod = calendar.getTime();
        return startPeriod;
    }

    private static Date createEndPeriod ()
    {
        Calendar calendar;
        Date  endPeriod;
        calendar = Calendar.getInstance ();
        calendar.set ( Calendar.DAY_OF_MONTH, 1 );
        calendar.add ( Calendar.MONTH, 1 );        // следующий месяц (номер месяца увеличиваем на 1)
        endPeriod = calendar.getTime();
        return endPeriod;
    }

}
