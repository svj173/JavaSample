package date;


import java.util.Calendar;
import java.util.TimeZone;

/**
 * Не так давно было замечено странное поведение java.util.Calendar при использовании метода set(Field, value)
 * для дат близких к моменту перевода времени с летнего на зимнее.
 * <BR/> В примере - перевод времени был 25-10-2009.
 * <BR/> Задаем дату с 1 мсек. Потом принудительно заносим в мсек 0. Дата существенно изменилась - увеличилась на 3599 сек (1 час).
 * <BR/> Занимательно почитать, что пишут по этому поводу сановцы:
 * http://bugs.sun.com/view_bug.do?bug_id=6609340
 * <BR/>
 * <BR/> Результат:
 * <BR/> t1 = 1256412600001
 * <BR/> t2 = 1256416200000
 * <BR/> t2 - t1 = 3599999 (1 expected)
 * <BR/>
 * <BR/> Sun
 * <BR/> Использовать
 * <BR/> calendar.set(Calendar.DST_OFFSET, calendar.get(Calendar.DST_OFFSET));
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 09.11.2009
 * <BR/> Time: 16:02:14
 */
public class DateTest
{
  public static void main ( String[] args )
  {
    Calendar calendar = Calendar.getInstance ( TimeZone.getTimeZone ( "Asia/Novosibirsk" ) );
    calendar.setTimeInMillis ( 1256412600001L ); // 2009-10-25T02:30:00.001+07:00
    long t1 = calendar.getTimeInMillis ();
    System.out.println ( "t1 = " + t1 );
    //calendar.set(Calendar.DST_OFFSET, calendar.get(Calendar.DST_OFFSET)); // Added - рекомендация Сановцев
    calendar.set ( Calendar.MILLISECOND, 0 );
    long t2 = calendar.getTimeInMillis ();
    System.out.println ( "t2 = " + t2 );
    System.out.println ( "t2 - t1 = " + (t2 - t1) + " (1 expected)" );
  }
  
}
