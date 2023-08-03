package string.regexc;


import java.util.Stack;

/**
 * Строковые регулярные выражения.
 * Для цифровых шаблонов. Показывает периоды чисел для true и для false.
 *
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 16.09.2016 13:10:31
 */
public class RegexpIntPeriod
{
    private Stack<Period> stack = new Stack<Period> ();

    public static void main ( String[] args )
    {
        boolean  b, oldB;
        String   regexp, str;
        RegexpIntPeriod manager;
        int max, start;

        manager = new RegexpIntPeriod();
        manager.clear();

        //regexp  = "^([2-9]\\d{6,31}|1[1-9]\\d{5,30}|10[5-9]\\d{4,29}|1049\\d{3,28}|1048[6-9]\\d{2,27}|10485[8-9]\\d{1,26}|104857[6-9]\\d{0,25}|[1-9]\\d{7,31})$";  // от 1048576 и выше
        //regexp  = "^\\d{3,4}$";   // от 100 до 9999
        regexp  = "^\\d{4,4}$";     // от 1000 до 9999

        System.out.println ( "regexp = "+regexp);

        //start   = 0;
        //start   = 1000000;
        start   = 10;

        str     = Integer.toString ( start );
        oldB    = str.matches ( regexp );
        // занести в результат
        manager.startPeriod ( start, oldB );

        //max = Integer.MAX_VALUE;
        max = start + 1000000;
        System.out.println ( "max = "+max);
        for ( int i=start; i<max; i++ )
        {
            str = Integer.toString ( i );
            b   = str.matches ( regexp );
            if ( b == oldB )
            {
                //manager.add ( i, b );
            }
            else
            {
                manager.endPeriod ( i );
                manager.startPeriod ( i, b );
                oldB = b;
            }
            //System.out.println ( "-- '"+str+"' : "+b);
        }
        manager.endPeriod ( max );
        System.out.println ( '\n' );
        System.out.println ( manager.toString() );
    }

    private void endPeriod ( int number )
    {
        // Взять из стека текущий период. Занести конечное значение
        Period period = stack.peek ();
        period.setEndNumber ( number-1 );
    }

    private void clear ()
    {
        stack.clear ();
    }

    private void startPeriod ( int number, boolean result )
    {
        Period period;

        // создать новый период - результат и начало
        period = new Period ();
        period.setResult ( result );
        period.setNumber ( number );
        // вложить в стек
        stack.push ( period );
    }

    public String toString()
    {
        StringBuilder sb = new StringBuilder ( 128 );

        int ic = 1;
        for ( Period period : stack )
        {
            sb.append ( ic );
            sb.append ( "). " );
            sb.append ( period.toString() );
            sb.append ( '\n' );
            ic++;
        }

        return sb.toString ();
    }

    private class Period
    {
        private boolean result = false;
        private int number = -1;
        private int endNumber = -1;

        public String toString()
        {
            StringBuilder sb = new StringBuilder ( 128 );

            sb.append ( result );
            sb.append ( " : " );
            sb.append ( number );
            sb.append ( " - " );
            sb.append ( endNumber );

            return sb.toString();
        }

        public void setResult ( boolean result )
        {
            this.result = result;
        }

        public boolean isResult ()
        {
            return result;
        }

        public void setNumber ( int number )
        {
            this.number = number;
        }

        public int getNumber ()
        {
            return number;
        }

        public void setEndNumber ( int endNumber )
        {
            this.endNumber = endNumber;
        }

        public int getEndNumber ()
        {
            return endNumber;
        }
    }

}
