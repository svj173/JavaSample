package svj.date;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;

/**
 * Вычитаем из даты месяц.
 * <BR/> Здесь даты должны быть в юникс-формате - время в секундах.
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 31.08.2021 14:24
 */
public class MinusMonthDate {

    public static void main ( String[] args )
    {
        long timestamp = 1640243294;       // sec -  Thu Dec 23 14:08:14 NOVT 2021

        Date date = new Date(timestamp * 1000);

        String zone = "Z";

        // Tue Nov 23 14:08:14 NOVT 2021 - sec
        long newTimestamp = LocalDateTime.ofEpochSecond(timestamp, 0, ZoneOffset.of(zone))
                        .minusMonths(1)
                        .toEpochSecond(ZoneOffset.of(zone));

        Date newDate = new Date(newTimestamp * 1000);

        System.out.println("time = " + timestamp + "; date = " + date);
        System.out.println("newTimestamp = " + newTimestamp + "; newDate = " + newDate);

    }


}
