package tools;


import java.io.BufferedReader;
import java.io.StringReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * Конвертация объектов (приведение типов).
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 10.11.2010 10:46:13
 */
public class Convert
{
    /* Набор форматтеров для парсинга введенной даты (чтобы создать форматтер только один раз) */
    private static final Map<String,SimpleDateFormat> formatters = new HashMap<String,SimpleDateFormat> ();

    public static final     String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";


    /**
     * Вывести чистое время, которое задано в параметре dateMsec (без учета Таймзоны).
     * @param dateMsec  Время в мсек.
     * @param tz  Таймзона в часах (например, 6).
     * @return   Дату в строковом формате "dd.MM.yyyy HH:mm:ss"
     */
    public static String getRussianDateTZ ( long dateMsec, int tz )
    {
        GregorianCalendar gc;
        StringBuilder result;

        gc = new GregorianCalendar ( new SimpleTimeZone ( tz * SCons.ONE_HOUR, "EMS_SERVER_"+tz ) );
        gc.setTimeInMillis ( dateMsec );

        result  = new StringBuilder(64);
        result.append ( String.format("%02d.",gc.get(Calendar.DAY_OF_MONTH)));
        result.append ( String.format("%02d.",gc.get(Calendar.MONTH)+1));
        result.append ( String.format("%04d ",gc.get(Calendar.YEAR)));
        result.append ( String.format("%02d:",gc.get(Calendar.HOUR_OF_DAY)));
        result.append ( String.format("%02d:",gc.get(Calendar.MINUTE)));
        result.append ( String.format("%02d",gc.get(Calendar.SECOND)));

        return result.toString();
    }

    /**
     * Вывести чистое время, которое задано в параметре dateMsec (без учета Таймзоны).
     * @param gc  Время в мсек.
     * @return   Дату в строковом формате "dd.MM.yyyy HH:mm:ss"
     */
    public static String getRussianDateTZ ( Calendar gc )
    {
        StringBuilder result;

        result  = new StringBuilder(64);
        result.append ( String.format("%02d.",gc.get(Calendar.DAY_OF_MONTH)));
        result.append ( String.format("%02d.",gc.get(Calendar.MONTH)+1));
        result.append ( String.format("%04d ",gc.get(Calendar.YEAR)));
        result.append ( String.format("%02d:",gc.get(Calendar.HOUR_OF_DAY)));
        result.append ( String.format("%02d:",gc.get(Calendar.MINUTE)));
        result.append ( String.format("%02d",gc.get(Calendar.SECOND)));

        return result.toString();
    }

    public static int getInt ( Object strObj, int defaultValue )
    {
        if ( strObj == null ) return defaultValue;

        String strInt   = strObj.toString();
        return getInt ( strInt, defaultValue );
    }

    public static int getInt ( String strInt, int defaultValue )
    {
        int     result;

        result   = defaultValue;
        if ( (strInt == null) || (strInt.length() == 0) )  return result;

        try
        {
            result = Integer.parseInt ( strInt );
        } catch ( Exception e )            {
            result  = defaultValue;
        }
        return result;
    }

    public static Double getDouble ( String strValue, double defaultValue )
    {
        Double     result;

        result   = defaultValue;
        if ( (strValue == null) || (strValue.length() == 0) )  return result;

        try
        {
            result = Double.parseDouble ( strValue );
        } catch ( Exception e )            {
            result  = defaultValue;
        }
        return result;
    }

    public static Date str2date ( String strDate, String outTemplate ) throws ParseException
    {
        Date                result;
        SimpleDateFormat    formatter;

        formatter   = getFormatter ( outTemplate );
        result      = formatter.parse ( strDate );

        return result;
    }

    public static String getEuroDateAsStr ( Date date )
    {
        return getDateAsStr ( date, YYYY_MM_DD_HH_MM_SS );
    }

    public static String getDateAsStr ( Date date, String outTemplate )
    {
        SimpleDateFormat    formatter;
        String              dateStr;

        dateStr     = "";
        if ( date == null ) return dateStr;
        if ( outTemplate == null ) return date.toString();

        formatter   = getFormatter (outTemplate);
        dateStr     = formatter.format(date);

        return dateStr;
    }

    private static SimpleDateFormat getFormatter ( String outTemplate )
    {
        SimpleDateFormat    formatter;

        formatter   = formatters.get(outTemplate);
        if ( formatter == null )
        {
            formatter = new SimpleDateFormat ( outTemplate );
            formatters.put ( outTemplate, formatter );
        }
        return formatter;
    }

    public static String getRussianDateTime ( Date date )
    {
        return getDateAsStr ( date, "dd.MM.yyyy HH:mm:ss" );
    }


    public static String concatObj ( Object ... mess )
    {
        StringBuilder result;

        result = new StringBuilder (512);
        for ( Object mes : mess )
        {
            result.append ( mes );
        }

        return result.toString();
    }

    public static String concatObj2 ( String[] mess )
    {
        StringBuilder result;

        result = new StringBuilder (512);
        for ( String mes : mess )
        {
            result.append ( mes );
        }

        return result.toString();
    }

    /* Преобразовать строку вида 'par1=v1\npar2=v2' в обьект пропертей. Перевод строки в качестве разделителя параметрoв обязателен. */
    public static Properties str2props ( String text ) throws Exception
    {
        Properties      result;
        StringReader sr;
        BufferedReader br;

        try
        {
            sr      = new StringReader ( text );
            br      = new BufferedReader ( sr );
            result  = new Properties();
            result.load ( br );
        } catch ( Exception e )        {
            throw new Exception ( "Ошибка конвертации строки в набор параметров :\n" + e );
        }

        return result;
    }

    /**
     * Перечислить содержимое  массива через символ CH.
     * В конце строки символа CH - нет
     * @param array Коллекция обьектов для преобразования
     * @param ch    Разделитель между обьектами
     * @return      Строковое представление коллекции.
     */
    public static String collectionToString ( Collection array, char ch )
    {
        String result = "";
        StringBuffer sb = new StringBuffer ( 128 );
        if ( array == null ) return result;

        for ( Object obj : array )
        {
            //result = result + array[i] + ch + " ";
            sb.append ( obj );
            sb.append ( ch );
            sb.append ( " " );
        }
        // удалить последнюю запятую (символ CH)
        result = sb.toString ();
        result = result.substring ( 0, result.length() - 2 );
        return result;
    }

    public static byte[] getAddress ( int address )
    {
        byte[] addr = new byte[4];

        addr[ 0 ] = ( byte ) ( ( address >>> 24 ) & 0xFF );
        addr[ 1 ] = ( byte ) ( ( address >>> 16 ) & 0xFF );
        addr[ 2 ] = ( byte ) ( ( address >>> 8 ) & 0xFF );
        addr[ 3 ] = ( byte ) ( address & 0xFF );

        return addr;
    }

    /**
     * Перевод русских букв в корявицу.
     * <BR/> В яве руские символы всегда хранятся в Unicode (UTF-8 -- ?) - к ним и надо привязаться.
     *
     * @param   ruText  Текст с русскими буквами.
     * @return  Текст в корявице (транслит).
     */
    public static String translit ( String ruText )
    {
      StringBuffer  result;
      int           ic;
      char          ch;


      ic      = ruText.length();
      result  = new StringBuffer();

      for ( int i=0; i<ic; i++ )
      {
        ch  = ruText.charAt ( i );
        switch ( ch )
        {
          case 1040:  // А
            result.append ( 'A' );
            break;
          case 1041:  // Б
            result.append ( 'B' );
            break;
          case 1042:  // В
            result.append ( 'V' );
            break;
          case 1043:  // Г
            result.append ( 'G' );
            break;
          case 1044:  // Д
            result.append ( 'D' );
            break;
          case 1045:  // E
            result.append ( 'E' );
            break;
          case 1025:  // Ё
            result.append ( 'E' );
            break;
          case 1046:  // Ж
            result.append ( "ZH" );
            break;
          case 1047:  // З
            result.append ( 'Z' );
            break;
          case 1048:  // И
            result.append ( 'I' );
            break;
          case 1049:  // Й
            result.append ( 'I' );
            break;
          case 1050:  // K
            result.append ( 'K' );
            break;
          case 1051:  // Л
            result.append ( 'L' );
            break;
          case 1052:  // M
            result.append ( 'M' );
            break;
          case 1053:  // Н
            result.append ( 'N' );
            break;
          case 1054:  // O
            result.append ( 'O' );
            break;
          case 1055:  // П
            result.append ( 'P' );
            break;
          case 1056:  // P
            result.append ( 'R' );
            break;
          case 1057:  // С
            result.append ( 'S' );
            break;
          case 1058:  // T
            result.append ( 'T' );
            break;
          case 1059:  // У
            result.append ( 'U' );
            break;
          case 1060:  // Ф
            result.append ( 'F' );
            break;
          case 1061:  // Х
            result.append ( 'H' );
            break;
          case 1062:  // Ц
            result.append ( 'C' );
            break;
          case 1063:  // Ч
            result.append ( "CH" );
            break;
          case 1064:  // Ш
            result.append ( "SH" );
            break;
          case 1065:  // Щ
            result.append ( "SH" );
            break;
          case 1066:  // Ъ
            result.append ( '\'' );
            break;
          case 1067:  // Ы
            result.append ( 'Y' );
            break;
          case 1068:  // Ь
            result.append ( '\'' );
            break;
          case 1069:  // Э
            result.append ( 'E' );
            break;
          case 1070:  // Ю
            result.append ( 'U' );
            break;
          case 1071:  // Я
            result.append ( "YA" );
            break;

          case 1072:  // а
            result.append ( 'a' );
            break;
          case 1073:  // б
            result.append ( 'b' );
            break;
          case 1074:  // в
            result.append ( 'v' );
            break;
          case 1075:  // г
            result.append ( 'g' );
            break;
          case 1076:  // д
            result.append ( 'd' );
            break;
          case 1077:  // е
            result.append ( 'e' );
            break;
          case 1105:  // ё
            result.append ( 'e' );
            break;
          case 1078:  // ж
            result.append ( "zh" );
            break;
          case 1079:  // з
            result.append ( 'z' );
            break;
          case 1080:  // и
            result.append ( 'i' );
            break;
          case 1081:  // й
            result.append ( 'i' );
            break;
          case 1082:  // к
            result.append ( 'k' );
            break;
          case 1083:  // л
            result.append ( 'l' );
            break;
          case 1084:  // м
            result.append ( 'm' );
            break;
          case 1085:  // н
            result.append ( 'n' );
            break;
          case 1086:  // о
            result.append ( 'o' );
            break;
          case 1087:  // п
            result.append ( 'p' );
            break;
          case 1088:  // р
            result.append ( 'r' );
            break;
          case 1089:  // с
            result.append ( 's' );
            break;
          case 1090:  // т
            result.append ( 't' );
            break;
          case 1091:  // у
            result.append ( 'u' );
            break;
          case 1092:  // ф
            result.append ( 'f' );
            break;
          case 1093:  // х
            result.append ( 'h' );
            break;
          case 1094:  // ц
            result.append ( 'c' );
            break;
          case 1095:  // ч
            result.append ( "ch" );
            break;
          case 1096:  // ш
            result.append ( "sh" );
            break;
          case 1097:  // щ
            result.append ( "sh" );
            break;
          case 1098:  // ъ
            result.append ( '\'' );
            break;
          case 1099:  // ы
            result.append ( 'y' );
            break;
          case 1100:  // ь
            result.append ( '\'' );
            break;
          case 1101:  // э
            result.append ( 'e' );
            break;
          case 1102:  // ю
            result.append ( 'u' );
            break;
          case 1103:  // я
            result.append ( "ya" );
            break;

          default:
            result.append ( ch );

        }
      }

      return result.toString ();
    }

    /**
     * Перечислить содержимое  массива через символ CH.
     * В конце строки символа CH - нет
     */
    public static String listArray ( Object[] array, char ch )
    {
      String result = "";
      StringBuffer sb = new StringBuffer ( 128 );
      if ( (array == null) || (array.length == 0) ) return result;  // .toString()

      int ic = array.length;
      for ( int i = 0; i < ic; i++ )
      {
        //result = result + array[i] + ch + " ";
        sb.append ( array[i] );
        sb.append ( ch );
        sb.append ( " " );
      }
      // удалить последнюю запятую (символ CH)
      result = sb.toString ();
      result = result.substring ( 0, result.length () - 2 );
      return result;
    }

    public static void main ( String[] args )
    {
        String str;
        Double d;

        // Double
        str = "1987";
        d   = Convert.getDouble ( str, -1d );
        System.out.println ( "str = '" + str + "', result = " + d );

        str = "1987.34";
        d   = Convert.getDouble ( str, -1d );
        System.out.println ( "str = '" + str + "', result = " + d );

        str = "1987,22";
        d   = Convert.getDouble ( str, -1d );
        System.out.println ( "str = '" + str + "', result = " + d );

        str = "-1987";
        d   = Convert.getDouble ( str, -1d );
        System.out.println ( "str = '" + str + "', result = " + d );

    }
}
