package time;


import java.util.*;

/**
 * Найти разницу между датами - в годах, месяцах, днях.
 *
 *  Подсчет временной разницы между двумя датами
 Возникла такая задача: подсчитать разницу, прошедшую между двумя датами и уметь выводить её в годах, месяцах, днях и т.д., а также в такой форме "1 день 2 месяца 3 года".

 При решении данной задачи нужно учитывать, что нельзя просто взять и вычесть количество миллисекунд одной даты из другой. Таким образом мы не можем в точности посчитать ничего, т.к. существует переход на летнее/зимнее время. Например, мы вычтем кол-во мс из даты, созданной на 30 марта 2009 года(установим также 0 часов, минут, секунд и миллисекунд) и 29 марта 2009 года(также обнулим лишние поля). Если перевести получившееся количество миллисекунд в часы, то мы получим 23 часа, а должно быть 24. Почему? Потому что именно в это время у нас переводят на зимнее время. Соответственно, необходимо учитывать данный факт.

 То есть для вычисления мс, прошедших между двумя датами нужно использовать следующий метод:

 public static long getDiffInMillis(Calendar c1, Calendar c2)
     {
         // учитываем перевод времени
         long time1 = c1.getTimeInMillis() + c1.getTimeZone().getOffset(c1.getTimeInMillis());
         long time2 = c2.getTimeInMillis() + c2.getTimeZone().getOffset(c2.getTimeInMillis());
         return Math.abs(time1 - time2);
     }


 Таким образом мы легко получим кол-во прошедших часов, минут и секунд. Но если мы хотим получить кол-во прошедших дней, не учитывая часы, минуты и прочее, то нам необходим следующий метод:

 public static int getDiffInDays(Calendar c1, Calendar c2)
     {
         Calendar gc1 = (Calendar)c1.clone();
         Calendar gc2 = (Calendar)c2.clone();

         // обнуляем часы, минуты и секунды
         gc1.set(Calendar.HOUR, 0);
         gc2.set(Calendar.HOUR, 0);
         gc1.set(Calendar.SECOND, 0);
         gc2.set(Calendar.SECOND, 0);
         gc1.set(Calendar.MILLISECOND, 0);
         gc2.set(Calendar.MILLISECOND, 0);

         return (int)(getDiffInMillis(c1, c2) / MILLISECONDS_IN_A_DAY);
     }


 Но используя данную методику нельзя сказать сколько прошло месяцев и лет. Для этого мы будем использовать следующий метод:

 **
 * Высчитывает разницу между двумя временами, в зависимости от значения field. * field - это константное значение из java.util.Calendar, обозначающее * конкретную временную метрику(год, месяц и т.д.).
 *
 * Устанавливает поля, заданные в массиве clearFields равными 0.
 *
      * @param field
      *            константное значение из java.util.Calendar(YEAR, MONTH...)
      * @param c1
      *            первая дата
      * @param c2
      *            вторая дата
      * @param clearFields
      *            поля даты, которые не учитываются при сравнении. Задаются массивом констант из из java.util.Calendar.
      *            Например, можно не учитывать миллисекунды, секунды и часы. Данные поля будут обнулены.
      * @return
      *         разницу между c1 и c2 в метрике, заданной field
      *
     public static int getTimeDifference(int field, Calendar c1, Calendar c2, int... clearFields)
     {
         Calendar gc1, gc2;

         if (c2.after(c1))
         {
             gc1 = (Calendar)c1.clone();
             gc2 = (Calendar)c2.clone();
         }
         else
         {
             gc1 = (Calendar)c2.clone();
             gc2 = (Calendar)c1.clone();
         }

         if (clearFields != null)
         {
             // очищаем поля, которые мы не будем учитывать при сравнении дат
             for (int clearField : clearFields)
             {
                 gc1.clear(clearField);
                 gc2.clear(clearField);
             }
         }

         int count = 0;
         for (gc1.add(field, 1); gc1.compareTo(gc2) <= 0; gc1.add(field, 1))
         {
             count++;
         }
         return count;
     }

 Как мы видим, данный метод просто пытается прибавить 1 к заданному временному промежутку. В clearFields мы задаем поля, которые необходимо обнулить. Таким образом мы можем найти разницу не только в годах и месяцах, но и в чем угодно, но это будет неэффективно. Следующей задачей является вывод нужного слова для временной единицы. Например, мы говорим прошло "5 лет", "2 месяца", "1 год", "11 секунд" и т.д. Оказывается, что все названия временных промежутков подчиняются единому закону. Достаточно всего лишь трех слов :). Этот закон выражен данным кодом:

 **
      * Возвращает слово, добавляющееся к временной единице, когда указывается сколько прошло
      * временных единиц.
      * В русском языке для временных единиц достаточно трех слов.
      * Значение time берется по модулю.
      *
      * @param time
      *            количество временной единицы
      * @param timeUnitName1
      *            Название временной единицы, которое используется с 1. Например, 1 "минута".
      * @param timeUnitName2
      *            Название временной единицы, которое используется с 2. Например, 2 "минуты".
      * @param timeUnitName5
      *            Название временной единицы, которое используется с 5. Например, 5 "минут".
      * @return
      *         слово для единицы времени
      *
     public static String getTimeUnitPeriodName(long time, String timeUnitName1, String timeUnitName2, String timeUnitName5)
     {
         String result;
         time = Math.abs(time); // для отрицательных чисел
         int small = (int)(time % 10);
         int middle = (int)(time % 100);

         // если заканчивается на 11, то всегда оформляется словом timeUnitName5
         if (small == 1 && middle != 11)
         {
             result = timeUnitName1;
         }
         // если оканчиваются на 2, 3, 4, за исключением 12, 13 и 14
         else if (small >= 2 && small <= 4 && (middle < 12 || middle > 14))
         {
             result = timeUnitName2;
         }
         else
         {
             result = timeUnitName5;
         }

         return result;
     }

 Оказалось, что так устроен наш язык, что особыми временными единицами являются 11, 12, 13 и 14(точнее оканчивающимся на эти числа). Остальное подчиняется общим правилам. То ли дело в английском языке. Теперь мы можем считать промежуток между двумя датами в любой временной единице и находить для данной единицы нужное слово. Осталась ещё одна нерешенная задача: как выводить временной промежуток в формате "X дней Y месяцев Z лет", но делать это так, чтобы у нас не было ситуации "12 дней 13 месяцев 1 год", а было например так "12 дней 1 месяц 2 года". Т.е. у нас не может быть больше 12 месяцев или больше 31 дня. Данную задачу мы решим следующим образом. Будем находить разницу в годах и затем вычитать данную разницу из конца промежутка. Дальше поступим также для месяца. Оставшиеся дни же просто выведем. Вот кусок кода(from и to - экземпляры Calendar):

 // если конечная дата больше, то меняем их местами
         if (from.after(to))
         {
             to = from;
         }

         int diffInYears = DateUtils.getDiffInYears(from, to);
         to.add(Calendar.YEAR, -diffInYears); // вычитаем учтенные года

         int diffInMonths = DateUtils.getDiffInMonths(from, to);
         to.add(Calendar.MONTH, -diffInMonths); // вычитаем учтенные месяцы

         int diffInDays = DateUtils.getDiffInDays(from, to);

 Не забудьте, что вы вычитаете из даты, поэтому нужно клонировать переданный экземпляр Calendar. Ну а далее форматируем полученные числа по своему желанию.

 p.s. также не забывайте указывать первый день недели на понедельник(в России) - calendar.setFirstDayOfWeek(Calendar.MONDAY)
 Автор: Михаил на 11/23/2009 09:05:00 после полудня

  2 комментария:
 1)
     leonidv30 декабря 2009 г., 2:06

     Так по простому, через миллисекунды делать нельзя. Не особо вникал - високосные года учитываются? Плюс, еще есть замечательной свойство когда раз в несколько лет то ли секунду, то ли миллисекунду добавляют.
     Лучше всего для этих целей использовать проверенные решения вроде joda times.

   2)
     Sergey Kiselev3 марта 2010 г., 9:42

     joda-time.sourceforge.net/faq.html#datediff

 *
 *
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 12.11.2013 10:28
 */
public class Date1MinusDate2
{
    public static void main ( String[] args )
    {
        //устанавливаем текущую дату
        Date date = new Date ();
        //формируем календарь на основе текущей даты
        Calendar today = Calendar.getInstance ();
        //формируем календарь показывающий дату рождения
        Calendar dayB = Calendar.getInstance ();
        //dayB.setTime(date);
        //устанавливаем дату и время рождения
        dayB.set ( Calendar.YEAR, 2013 );
        dayB.set ( Calendar.MONTH, 9 );
        dayB.set ( Calendar.DAY_OF_MONTH, 8 );
        dayB.set ( Calendar.HOUR_OF_DAY, 0 );
        dayB.set ( Calendar.MINUTE, 20 );
        dayB.set ( Calendar.SECOND, 0 );
        //создаем календарь который выводит количество прожитых лет, месяцев и т.д.
        Calendar calendar3 = Calendar.getInstance ();

        /*количество лет: в поле YEAR устанавливается разница между
        * Сегодня(today.get(Calendar.YEAR)) и ДеньРожд(dayB.get(Calendar.YEAR))
        */
        calendar3.set ( Calendar.YEAR, today.get ( Calendar.YEAR ) - dayB.get ( Calendar.YEAR ) );
        //количество месяцев в году
        calendar3.set ( Calendar.MONTH, today.get ( Calendar.MONTH ) - dayB.get ( Calendar.MONTH ) );
        //количество дней в месяце
        calendar3.set ( Calendar.DAY_OF_MONTH, today.get ( Calendar.DAY_OF_MONTH ) - dayB.get ( Calendar.DAY_OF_MONTH ) );
        //количество часов в сутках
        calendar3.set ( Calendar.HOUR_OF_DAY, today.get ( Calendar.HOUR_OF_DAY ) - dayB.get ( Calendar.HOUR_OF_DAY ) );
        //количество минут в часе
        calendar3.set ( Calendar.MINUTE, today.get ( Calendar.MINUTE ) - dayB.get ( Calendar.MINUTE ) );
        System.out.println ( today.getTime() );
        System.out.println ( dayB.getTime() );
        System.out.println ( "Вы прожили: " + calendar3.get ( Calendar.YEAR ) + " лет, " +
                                     +calendar3.get ( Calendar.MONTH ) + " месяцев," +
                                     +calendar3.get ( Calendar.DAY_OF_MONTH ) + " дней," +
                                     +calendar3.get ( Calendar.HOUR_OF_DAY ) + " часов," +
                                     +calendar3.get ( Calendar.MINUTE ) + " минут." );
        //количество миллисекунд между датой рождения и текущей датой
        long dif = today.getTimeInMillis() - dayB.getTimeInMillis ();
        //System.out.println(dif);
        long second = dif / 1000;
        long min = second / 60;
        long hour = min / 60;
        long day = hour / 24;
        long year = day / 365;
        long month = year % 12;
        //проверка
        System.out.println ( month );
    }
}
