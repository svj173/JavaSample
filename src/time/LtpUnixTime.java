package time;


import tools.Convert;
import tools.SCons;

import java.util.*;


/**
 * Время на устройстве LTP.
 * <BR/>
 *     1) Хранит в двух целых переменных.
      - время в unix (в секундах)
      - таймзона - в часах.
    2) Локальное время устройства вычисляется как unix-время с учетом таймзоны.
      Если ТЗ - положительное, то происходит вычитание времени, причем при ТЗ=3 - вычитается 4 часа.

    Т.е. unix-время - это время UTC в 0 меридиане.

    1) unix-time   - 1317604061 - 03.10.2011 08:11:41
    2) tz          - 3
    3) show time   - Mon Oct  3 04:11:41 LOCAL 2011

 * <BR/> Задача - привести к обьекту java и уметь менять время на устройстве (синхронизировать).
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 03.10.2011 11:16:56
 */
public class LtpUnixTime
{
    private long unixTime;
    private int  tz;

    public static void main ( String[] args )
    {
        LtpUnixTime handler;
        int   time;

        handler = new LtpUnixTime();

        //time    = 1317604061;
        //time    = 1317605130;
        //time    = 1317605769; // 1317605769 (03.10.2011 08:36:09)
        time    = 1317721100; // TZ=1 04.10.2011 10:38:20)

        handler.initTime ( time, 1 );
        System.out.println ( handler.showTime() );

    }

    private String showTime ()
    {
        StringBuilder result;
        Calendar        calendar;
        SimpleTimeZone timeZone;
        int    offsetMsec;
        long    unixTimeMsec;

        result = new StringBuilder(512);

        unixTimeMsec    = unixTime * 1000;

        result.append ( "\n - unix-time\t: " );
        result.append ( unixTime );
        result.append ( "\n - tz\t\t\t: " );
        result.append ( tz );
        result.append ( "\n - str time\t\t: " );
        result.append ( Convert.getRussianDateTime ( new Date ( unixTimeMsec ) ) );

        // Заносим в календарь
        // -смещение для ТЗ в мсек
        offsetMsec  = tz * SCons.ONE_HOUR;
        result.append ( "\nNew calendar:" );
        result.append ( "\n - offsetMsec\t: " );
        result.append ( offsetMsec );
        timeZone    = new SimpleTimeZone ( offsetMsec, "EMS_Server" );
        calendar    = new GregorianCalendar ( timeZone, Locale.getDefault() );
        calendar.setTimeInMillis ( unixTimeMsec );
        result.append ( "\n - str time\t\t: " );
        result.append ( Convert.getRussianDateTime ( calendar.getTime() ) );

        // UTC
        result.append ( "\nUTC:" );
        calendar    = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        //calendar    = Calendar.getInstance ( new SimpleTimeZone(0,"UTC"));
        calendar.setTimeInMillis ( unixTimeMsec );
        result.append ( "\n - str time\t\t: " );
        result.append ( Convert.getRussianDateTZ ( calendar ) );
     
        return result.toString();
    }

    private void initTime ( int unixTime, int tz )
    {
        this.unixTime   = unixTime;
        this.tz         = tz;
    }

}
