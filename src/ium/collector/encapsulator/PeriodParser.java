package ium.collector.encapsulator;

import org.apache.logging.log4j.*;

import javax.naming.ConfigurationException;
import java.util.Calendar;

/**
 * Парсинг для Репроцессинга.
 * User: Zhiganov
 * Date: 25.04.2007
 * Time: 12:04:18
 */
public class PeriodParser
{
    private static final Logger log = LogManager.getFormatterLogger(PeriodParser.class);

    /* Указатель на текущий временной период обработки записей. */
    private int currentPeriod   = 0;

    private int year;

    /* Массив временных периодов - начало-конец. */
    private int[]  month;
    private int[]  monthEndDate;


    public void testPeriod ( String attr )
    {
        try
        {
            log.debug("TestPeriod: Start");
            createPeriodDate( attr );

            // Проверка на дату - вдруг превосходит текущую.
            if ( ! isDataValid() )
            {
                log.debug("Current data less then (<) working data. Finished.");
                return;
            }

            String sqlRequest   = createSqlRequest();
            log.debug("SQL = \n" + sqlRequest );
            log.debug("TestPeriod: Finish");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Парсинг заданных временных периодов - год, месяцы.
     * Пример:
     * <br> current_year,mar,apr,jun
     * <br> 2007,1,5,10
     * <br> Соглашение по годам: current_year, last_year, lastlast_year
     * @param str  Строка с атрибутами через запятую.
     */
    private void createPeriodDate ( String str ) throws ConfigurationException
    {
        String[] attr;

        try
        {
            log.debug ( "createPeriodDate. Start. params = " + str );
            attr    = str.split(",");
            log.debug("Params size = " + attr );

            // Выделяем год
            year    = createYear ( attr[0] );

            // Выделяем массив месяцев - как числа
            month   = createMonth ( attr );

            // Формируем массив дат окончания месяца
            monthEndDate    = new int[month.length];
            createEndDate ( year, month );

            //
            log.debug( "Year    = " + year );
            for ( int i=0; i<month.length; i++ )
            {
                log.debug( i + ". - month = " + month[i] + ", max day = "
                        + monthEndDate[i] );
            }

        } catch (ConfigurationException ce) {
            throw ce;
        } catch (Exception e) {
            throw new ConfigurationException("Parse 'Period' error. Params = "
                    + str + ". Error = " + e.getMessage() );
        }
    }

    private void createEndDate ( int year, int[] month )
    {
        Calendar calendar;

        calendar    = Calendar.getInstance();
        //log.debug("Current date = " + calendar );
        calendar.set ( Calendar.YEAR, year );
        //log.debug("Work year = " + calendar );
        for ( int i=0; i<month.length; i++)
        {
            calendar.set ( Calendar.MONTH, month[i] );  //TODO
            //log.debug("Work month = " + calendar );
            monthEndDate[i] = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        }
    }

    /**
     * Выделить массив месяцев из конфиг-параметров
     * @param attr
     * @return
     */
    private int[] createMonth ( String[] attr )
    {
        int[]   result;
        int     ic;
        String  str;

        ic  = attr.length - 1;
        result  = new int[ic];
        for ( int i=0; i<ic; i++)
        {
            str         = attr[i+1].trim();
            // -1 -- переводим нумерацию месяцев, начинающуюся с 1 на java-нумерацию,
            //  начинающуюся с 0.
            result[i]   = Integer.parseInt(str) - 1;
        }
        return result;
    }

    /**
     * Парсим заданный год.
     * @param year
     * @return
     */
    private int createYear ( String year ) throws ConfigurationException {
        int result  = -1;
        Calendar calendar;
        calendar    = Calendar.getInstance();

        year    = year.trim();
        if ( year.equalsIgnoreCase("current_year"))
        {
            // текущий год
            result  = calendar.get(Calendar.YEAR);
            return result;
        }
        if ( year.equalsIgnoreCase("last_year"))
        {
            // прошлый год
            calendar.add(Calendar.YEAR, -1);
            result  = calendar.get(Calendar.YEAR);
            return result;
        }
        if ( year.equalsIgnoreCase("lastLast_year"))
        {
            // позапрошлый год
            calendar.add(Calendar.YEAR, -2);
            result  = calendar.get(Calendar.YEAR);
            return result;
        }
        throw new ConfigurationException("Parse 'Period' error. Year problem = "
                + year );
    }

    private String createSqlRequest()
    {
        StringBuffer stringbuffer = new StringBuffer();

        //  Собираем требуемые имена стобцов
		stringbuffer.append("SELECT * ");
        stringbuffer.append(" FROM screening WHERE start_time between ");

        // Дата начала
        //stringbuffer.append("to_date('01/03/2007 00:00:00','dd/mm/yyyy hh24:mi:ss')");
        stringbuffer.append("to_date('01/");
        // -- вставляем рабочий месяц
        stringbuffer.append(setFirstZero(month[currentPeriod]+1));
        stringbuffer.append('/');
        // -- вставляем рабочий год
        stringbuffer.append(year);
        stringbuffer.append(" 00:00:00','dd/mm/yyyy hh24:mi:ss')");

        // Дата конца
        stringbuffer.append(" and ");
        //stringbuffer.append("to_date('31/03/2007 23:59:59','dd/mm/yyyy hh24:mi:ss') ");
        stringbuffer.append("to_date('");
        // -- вставляем рабочую дату окончания
        stringbuffer.append(setFirstZero(monthEndDate[currentPeriod]));
        stringbuffer.append('/');
        // -- вставляем рабочий месяц
        stringbuffer.append(setFirstZero(month[currentPeriod]+1));
        stringbuffer.append('/');
        // -- вставляем рабочий год
        stringbuffer.append(year);
        stringbuffer.append(" 23:59:59','dd/mm/yyyy hh24:mi:ss') ");

        // Добавляем Условие
        stringbuffer.append("and IS_FOR_UPLOAD = 1 and IS_UPLOADED = 0 and ROWNUM < 1000");

        return stringbuffer.toString();
    }

    private boolean isDataValid()
    {
        Calendar curr;
        int curr_year, curr_month;

        log.debug ("Start");
        // Берем текущую дату
        curr    = Calendar.getInstance();
        // Берем текущий год
        curr_year   = curr.get (Calendar.YEAR);
        log.debug ("Current year = " + curr_year + ", work = " + year );
        // Сравниваем текущий год и рабочий.
        if ( year > curr_year )
        {
            log.debug("Current year '" + curr_year + "' less then work year '" + year );
            return false;
        }

        if ( year == curr_year )
        {
            // Года равны. Берем текущий месяц
            curr_month   = curr.get (Calendar.MONTH); //TODO
            log.debug ("Current month = " + curr_month + ", work = " + month[currentPeriod] );
            // month - считается от 0
            if ( month[currentPeriod] > curr_month )
            {
                log.debug("Current month '" + (curr_month+1)
                        + "' less then work month '" + (month[currentPeriod]+1) );
                return false;
            }
        }

        log.debug ("Finish");
        return true;
    }

    private String setFirstZero ( int iprv )
    {
       String result;
       if ( iprv < 10 )
          result = "0" + iprv;
       else
          result = "" + iprv;
       return result;
    }

    public static void main (String args[])
        throws Exception
    {
        PeriodParser src  = new PeriodParser();
        src.testPeriod(args[0]);
    }

}
