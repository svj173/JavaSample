package string;


import tools.Convert;

/**
 * Проверка, что быстрее складывает строк - плюс или StringBuilder.
 * <BR/> Проверяем исходя из того что компилятор сам оптимизирует конкатенацию. Т.е. использум строки с несколькими плючами
 * <BR/>
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 30.05.2016 9:25
 */
public class ConcatTest2
{
    public static void main ( String[] args )
    {
        StringBuilder sb;
        String S0 = "00";
        String S1 = "111";
        String S2 = "2222";
        String S3 = "33333";
        String S4 = "444444";
        String str;
        int  max;
        long start, delta1, delta2;

        max     = 10000;
        str     = "";
        sb      = new StringBuilder();

        System.out.println ( "Size = " + max );

        start   = System.currentTimeMillis();
        for ( int i=0; i<max; i++ )  str = S0 + S1 + S2 + S3 + S4;
        delta1   = System.currentTimeMillis () - start;
        System.out.println ( "Plus: time = " + delta1 );

        start   = System.currentTimeMillis();
        for ( int i=0; i<max; i++ )  str = Convert.concatObj ( S0, S1, S2, S3, S4 );
        delta2   = System.currentTimeMillis() - start;
        System.out.println ( "StringBuilder: time = " + delta2 );

    }

}
