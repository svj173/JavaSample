package string;

/**
 * <BR> User: svj
 * <BR> Date: 11.07.2008
 * <BR> Time: 12:19:47
 */
public class Translit2
{
  public static String translit ( String ruText )
  {
    StringBuffer result;
    int ic;
    char ch;


    ic      = ruText.length();
    result  = new StringBuffer();

    for (int i=0; i<ic; i++)
    {
      ch  = ruText.charAt ( i );
      switch ( ch )
      {
        case 1040:  // б
          result.append ( 'A' );
          break;
        case 1041:  // в
          result.append ( 'B' );
          break;
        case 1042:  // ч
          result.append ( 'V' );
          break;
        case 1043:  // з
          result.append ( 'G' );
          break;
        case 1044:  // д
          result.append ( 'D' );
          break;
        case 1045:  // E
          result.append ( 'E' );
          break;
        case 1025:  // і
          result.append ( 'E' );
          break;
        case 1046:  // ц
          result.append ( "ZH" );
          break;
        case 1047:  // ъ
          result.append ( 'Z' );
          break;
        case 1048:  // й
          result.append ( 'I' );
          break;
        case 1049:  // к
          result.append ( 'I' );
          break;
        case 1050:  // K
          result.append ( 'K' );
          break;
        case 1051:  // м
          result.append ( 'L' );
          break;
        case 1052:  // M
          result.append ( 'M' );
          break;
        case 1053:  // о
          result.append ( 'N' );
          break;
        case 1054:  // O
          result.append ( 'O' );
          break;
        case 1055:  // р
          result.append ( 'P' );
          break;
        case 1056:  // P
          result.append ( 'R' );
          break;
        case 1057:  // у
          result.append ( 'S' );
          break;
        case 1058:  // T
          result.append ( 'T' );
          break;
        case 1059:  // х
          result.append ( 'U' );
          break;
        case 1060:  // ж
          result.append ( 'F' );
          break;
        case 1061:  // и
          result.append ( 'H' );
          break;
        case 1062:  // г
          result.append ( 'C' );
          break;
        case 1063:  // ю
          result.append ( "CH" );
          break;
        case 1064:  // ы
          result.append ( "SH" );
          break;
        case 1065:  // э
          result.append ( "SH" );
          break;
        case 1066:  // я
          result.append ( '\'' );
          break;
        case 1067:  // щ
          result.append ( 'Y' );
          break;
        case 1068:  // ш
          result.append ( '\'' );
          break;
        case 1069:  // ь
          result.append ( 'E' );
          break;
        case 1070:  // а
          result.append ( 'U' );
          break;
        case 1071:  // с
          result.append ( "YA" );
          break;

        case 1072:  // Б
          result.append ( 'a' );
          break;
        case 1073:  // В
          result.append ( 'b' );
          break;
        case 1074:  // Ч
          result.append ( 'v' );
          break;
        case 1075:  // З
          result.append ( 'g' );
          break;
        case 1076:  // Д
          result.append ( 'd' );
          break;
        case 1077:  // Е
          result.append ( 'e' );
          break;
        case 1105:  // Ј
          result.append ( 'e' );
          break;
        case 1078:  // Ц
          result.append ( "zh" );
          break;
        case 1079:  // Ъ
          result.append ( 'z' );
          break;
        case 1080:  // Й
          result.append ( 'i' );
          break;
        case 1081:  // К
          result.append ( 'i' );
          break;
        case 1082:  // Л
          result.append ( 'k' );
          break;
        case 1083:  // М
          result.append ( 'l' );
          break;
        case 1084:  // Н
          result.append ( 'm' );
          break;
        case 1085:  // О
          result.append ( 'n' );
          break;
        case 1086:  // П
          result.append ( 'o' );
          break;
        case 1087:  // Р
          result.append ( 'p' );
          break;
        case 1088:  // Т
          result.append ( 'r' );
          break;
        case 1089:  // У
          result.append ( 's' );
          break;
        case 1090:  // Ф
          result.append ( 't' );
          break;
        case 1091:  // Х
          result.append ( 'u' );
          break;
        case 1092:  // Ж
          result.append ( 'f' );
          break;
        case 1093:  // И
          result.append ( 'h' );
          break;
        case 1094:  // Г
          result.append ( 'c' );
          break;
        case 1095:  // Ю
          result.append ( "ch" );
          break;
        case 1096:  // Ы
          result.append ( "sh" );
          break;
        case 1097:  // Э
          result.append ( "sh" );
          break;
        case 1098:  // Я
          result.append ( '\'' );
          break;
        case 1099:  // Щ
          result.append ( 'y' );
          break;
        case 1100:  // Ш
          result.append ( '\'' );
          break;
        case 1101:  // Ь
          result.append ( 'e' );
          break;
        case 1102:  // А
          result.append ( 'u' );
          break;
        case 1103:  // С
          result.append ( "ya" );
          break;

        default:
          result.append ( ch );

      }
    }

    return result.toString ();
  }


  public static void main(String[] args)
  {
      final String test = "рТЙЧЕФ, нЙТ. ьФП дМЙООБС уФТПЛБ У тБЪОЩНЙ УЙНЧПМБНЙ ТХУУЛПЗП БМЖБЧЙФБ.";
      System.out.println("toTranslit(test) = \n" + test);

      System.out.println("Test speed");

      long time = System.currentTimeMillis();
    System.out.println("toTranslit(test) = \n" + Translit2.translit(test));
      System.out.println((System.currentTimeMillis() - time));

    /*
    System.out.println();
    char ch;
    String test2  = "бвчздеіцъйклмнопртуфхжигюыэящшьасБВЧЗДЕЈЦЪЙКЛМНОПРТУФХЖИГЮЫЭЯЩШЬАС";
    for ( int i=0; i<test2.length (); i++ )
    {
      ch  = test2.charAt ( i );
      System.out.println( ch + " -- " + (int)ch );

    }
    */
  }

}
