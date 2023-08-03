package string.orf;

import com.svj.utils.DateTools;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Vector;

/**
 * Проверка орфографии.
 * Поиск похожестей.
 *
 * Примеры работы и Время (для matchCount=3):
 *  1) В массиве из 128907 слов схожести для слова "проверка" были найдены за 1.2 сек.
 *      [взаимопроверка, перепроверка, проверка, самопроверка]
 *  2) В массиве из 322000 слов схожести для слова "проверкой" были найдены за 2.8 сек.
 *      [продразверсткой, проверкой, перепроверкой]
 *  3) В массиве из 322000 слов схожести для слова "пролеркой" были найдены за 2.8 сек.
 *      [пролеткой, проверкой]
 *
 * <BR> User: Zhiganov
 * <BR> Date: 31.01.2008
 * <BR> Time: 15:08:55
 */
public class Orf
{
    private Logger logger = LogManager.getFormatterLogger ( Orf.class );

    /* Минимальная длинна подстроки совпадения. Т.е. тот кусок из текста
     который считается совпавшим. Если совпавший подкусок меньше данного числа,
      то это не считается совпадением. */
    private int matchCount  = 3;


    /**
     * Возвращает массив записей, наиболее похожих на исходную строку.
     * На слова единичной длины не ищем похожести.
     *
     * @param source
     * @param dictFileName
     * @param codePage
     * @return    массив похожих записей
     */
    public Vector handle ( String source, String dictFileName, String codePage )
    {
        Vector    result;
        String    str, line;
        int       ic, icw;
        long      startTime, endTime;
        FileInputStream file;
        BufferedReader  br;

        startTime   = System.currentTimeMillis();
        ic          = 0;
        icw         = 0;   // счетчик найденных слов (чтобы не слишком много)
        result      = new Vector();

        try
        {
            file        = new FileInputStream ( dictFileName );
            br          = new BufferedReader(new InputStreamReader(file,codePage ));

            //String[]    src = new String[] {"проверка/J", "поверка", "проба", "гровер"};

            //System.out.println("Start. ish = " + source );
            while ( ( line = br.readLine () )   != null )
            {
                ic++;   // счетчик анализируемых из словаря слов
                str     = deleteService(line);
                if ( compareSpell ( source, str ) )
                {
                    result.addElement(str);
                    icw++;
                    if ( icw > 10 ) break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.addElement( "ERROR" );
        }

        endTime   = System.currentTimeMillis();
        String time = DateTools.intervalDate(startTime,endTime,"hh:mm:ss",true);
        System.out.println("Finish. Time = " + time + ". See words = " + ic );
        return result;
    }

    /**
     *
     *
     * @param source   Исходная запись.
     * @param dictStr  Строка из словаря (с которой сравниваем).
     * @return         TRUE - похожи.
     */
    public boolean compareSpell ( String source, String dictStr )
    {
        int       syncCount, nCount, nCount1, ic, size1, size2;
        boolean   b1, b2, result;
        int[]     cmp;

        result      = false;

        try
        {
            // Расчитываем мин кол-во совпадений
            size1   = source.length();
            size2   = dictStr.length();
            if ( size1 < size2 )
            {
                syncCount   = size1 - 1;
            }
            else
            {
                syncCount   = size1 - 2; // 70%
            }
            if ( syncCount <= 0 )   syncCount   = size1-1;

            //nCount1     = source.length();
            nCount1     = size2;
            //syncCount   = (nCount1 / 2) + 1;  // Мин кол-во совпавших букв - больше половины слова

                //System.out.println( i + ". str to = " + str );
                cmp     = compareStrings ( source, dictStr );
                //System.out.println( i + ". Compare result (sovpad/ne_sovpad) = " + cmp[0] + "/" + cmp[1] );
                nCount  = cmp[1]; // nCount - кол-во несовпавших символов.
            // Сравниваем по совпавшим
                b1      = cmp[0] > syncCount;
            // Сравниваем по НЕсовпавшим - на случай, если проблемы с совпавшими - ???
            b2  = false;
                //b2      = (cmp[0] == syncCount) && (nCount < nCount1);
                //System.out.println( i + ". syncCount = " + syncCount +", b1 = " + b1 + ", b2 = " + b2 );
                if ( b1 || b2 )
                {
                    //
                    //syncCount   = cmp[0];
                    //nCount1     = nCount;
                    result      = true;
                    //System.out.println( i + ". add = " + str +", syncCount = " + syncCount + ", nCount1 = " + nCount1 );
                }
        } catch (Exception e) {
            e.printStackTrace();
            //result.addElement( "ERROR" );
        }

        return result;
    }

    /**
     * Возвращает массив наиболее похожих на исходную записей.
     * Рабочий вариант.
     *
     * @return         TRUE - похожи.
     */
    /*
    public Vector getMaxMatch ( String source )
    {
        Vector    result;
        String    tempStr, str, dictFile, line;
        int       syncCount, nCount, nCount1, ic;
        boolean   b1, b2;
        int[]     cmp;
        long      startTime, endTime;
        FileInputStream file;

        startTime   = System.currentTimeMillis();
        ic          = 0;
        result      = new Vector();

        try
        {
            dictFile    = "D:/Projects/SVJ/JavaSample/test/orf/ru_RU_dic.txt";
            file    = new FileInputStream ( dictFile );
            BufferedReader br
                 = new BufferedReader(new InputStreamReader(file,"windows-1251"));


            //String[]    src = new String[] {"проверка/J", "поверка", "проба", "гровер"};

            tempStr     = source;
            nCount1     = tempStr.length();
            syncCount   = 0;

            //System.out.println("Start. ish = " + source );
            while ( ( line = br.readLine () )   != null )
            //for ( int i=0; i<src.length; i++ )
            {
                ic++;   // счетчик анализируемых из словаря слов
                str     = deleteService(line);
                //System.out.println( i + ". str to = " + str );
                //
                cmp     = compareStrings ( tempStr, str );
                //System.out.println( i + ". Compare result (sovpad/ne_sovpad) = " + cmp[0] + "/" + cmp[1] );
                nCount  = cmp[1]; // nCount - кол-во несовпавших символов.
                b1      = cmp[0] > syncCount;
                b2      = (cmp[0] == syncCount) && (nCount < nCount1);
                //System.out.println( i + ". syncCount = " + syncCount +", b1 = " + b1 + ", b2 = " + b2 );
                if ( b1 || b2 )
                {
                    //
                    syncCount   = cmp[0];
                    nCount1     = nCount;
                    result.addElement(str);
                    //System.out.println( i + ". add = " + str +", syncCount = " + syncCount + ", nCount1 = " + nCount1 );
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.addElement( "ERROR" );
        }

        endTime   = System.currentTimeMillis();
        String time = DateTools.intervalDate(startTime,endTime,"hh:mm:ss",true);
        System.out.println("Finish. Time = " + time + ". See words = " + ic );
        return result;
    }
    */

    private String deleteService(String str)
    {
        String  result;
        int ic;

        ic  = str.indexOf('/');
        if ( ic > 0 )
            result  = str.substring(0,ic);
        else
            result  = str;
        return result;
    }

    /**
     * Сравниваем две строки.
     * Выдаем кол-во совпавших и несовпавших символов.
     *
     * @param str1  Первая строка для сравнения.
     * @param str2  Вторая строка для сравнения.
     * @return  [0] - кол-во совпавших символов, [1] - кол-во несовпавших символов.
     */
    private int[] compareStrings ( String str1, String str2 )
    {
        int[]   result;
        int     i, j, count1, count;
        String  s1, s2;
        boolean flag;
        char    ch1, ch2;

        result  = new int[2];

        count       = 0;
        result[1]   = 0;
        s1          = str1.replaceAll(" ", "");
        s2          = str2.replaceAll(" ", "");
        //System.out.println("Start compare. s1 = " + s1 + ", s2 = " + s2 );

        if ( (s1.length() == 0) || (s2.length() == 0) )
        {
            result[0]  = 0;
            result[1]  = 255;
        }
        else
        {
            s1  = s1.toUpperCase();
            s2  = s2.toUpperCase();
            //
            i   = 0;
            do
            {
                j   = 0;
                do
                {
                    //System.out.println(" - i = " + i + ", j = " + j  );
                    if ( s1.charAt(i) == s2.charAt(j) )  // and(str1[i] <> ' ')
                    {
                        // Буквы разных слов равны
                        count1  = 1;  // счетчик совпадений?
                        flag    = true;
                        while ( flag )
                        {
                            flag    = false;
                            if ( (i + count1 < s1.length()) && (j+count1 < s2.length()))
                            {
                                //System.out.println("count1 = " + count1 + ", i+count1 = " + (i+count1) + ", j+count1 = " + (j+count1)  );
                                ch1 = s1.charAt(i+count1);
                                ch2 = s2.charAt(j+count1);
                                //System.out.println("ch1 = " + ch1 + ", ch2 = " + ch2  );
                                // Если следующие буквы равны - выход
                                if ( ch1 == ch2  )
                                {
                                    flag = true; // and(S1[j+Count_] <> ' ')
                                    count1++;
                                }

                                // test
                                //if ( count1 > 20 ) flag = true;
                            }
                        }
                        //System.out.println("Create count1 = " + count1 );

                        i   = i + count1 - 1;
                        j   = j + count1 - 1;
                        if ( count1 >= matchCount ) count   = count + count1;
                    }
                    j++;
                } while(j<s2.length());
                i++;
            } while(i<s1.length());

            if ( s1.length() < s2.length())   count1 = s1.length();
            else    count1  = s2.length();

            result[0]   = count;
            result[1]   = count1 - count;
        }

        return result;
    }


    public static void main(String[] args)
    {
        //
        Vector vec;
        String  str, dictFile, codePage;
        Orf orf;

        str         = "пролеркой";
        dictFile    = "D:/Projects/SVJ/JavaSample/test/orf/ru_RU_ie_dic.txt";
        codePage    = "windows-1251";
        
        orf         = new Orf();
        //vec = orf.getMaxMatch(args[0]);
        vec = orf.handle(str,dictFile,codePage);
        System.out.println("-- Ish = '" + str + "'\n-- Result = " + vec );
    }

}
