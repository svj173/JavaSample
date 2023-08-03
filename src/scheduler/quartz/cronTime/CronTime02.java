package scheduler.quartz.cronTime;


import org.quartz.CronExpression;
import tools.Convert;

import java.text.ParseException;
import java.util.Date;


/**
 * Попытка использовать CronExpression сам по себе (автономно), чтобы получать даты.
 * <BR/> Обьект инсталлируем каждый раз, и у него спрашиваем дату след старта.
 * <BR/> Работает.
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 17.03.2011 10:55:51
 */
public class CronTime02
{
    private CronExpression cronExpression;

    public CronTime02 ( String cronTime ) throws ParseException
    {
        cronExpression = new CronExpression ( cronTime );
    }

    public long getNextIncludedTime ( long timeInMillis, int ic )
    {
        long    nextIncludedTime;
        Date    date, nextDate;

        nextIncludedTime = timeInMillis + 1; //plus on millisecond
        //System.out.println("Start. nextIncludedTime = " + nextIncludedTime );

        date    = new Date ( nextIncludedTime );
        //System.out.println("date = " + Convert.getRussianDateTime ( date ));

        //nextDate            = cronExpression.getNextInvalidTimeAfter ( date );
        nextDate            = cronExpression.getNextValidTimeAfter ( date );
        nextIncludedTime    = nextDate.getTime();

        System.out.print ( "- " + ic + "). " );
        System.out.print ( Convert.getRussianDateTime ( date ) );
        System.out.print ( "  -->  ");
        System.out.println( Convert.getRussianDateTime ( nextDate ) );

        return nextIncludedTime;
    }


    public static void main(String[] args)
    {
        CronTime02 cronObject;
        String      cronTime;
        //Date date;

        /*
    * Секунды
    * Минуты
    * Часы
    * День месяца
    * Месяц
    * День недели
    * Год (необязательное поле)
         */
        cronTime    = "0,30 * * * * ?";
        //cronTime    = "0 0/3 * * * ?";
        //cronTime    = "* 3 * * * ?";
        try
        {
            for ( int i=0; i<10; i++ )
            {
                cronObject = new CronTime02 (cronTime);

                //System.out.println("-------------------- " + i + "----------------------");
                cronObject.getNextIncludedTime ( System.currentTimeMillis(), i );
                Thread.sleep ( 10000 );
            }

        } catch ( Exception e )        {
            e.printStackTrace();
        }
    }

}
