package format.decimal;

import java.util.*;
import java.text.*;

public class DecimalFormatDemo
{

   static public void customFormat ( String pattern, double value ) {
      DecimalFormat myFormatter = new DecimalFormat(pattern);
      String output = myFormatter.format(value);
      System.out.println(value + "  " + pattern + "  " + output);
   }

   static public void customFormat ( String pattern, double value, int maxSize ) {
      DecimalFormat myFormatter = new DecimalFormat(pattern);
     myFormatter.setMaximumIntegerDigits ( maxSize );
      String output = myFormatter.format(value);
      System.out.println(value + "  " + pattern + "  " + output);
   }

   static public void customNumberFormat (String pattern, int value ) {
     NumberFormat nf = NumberFormat.getIntegerInstance();

      String output = nf.format(value);
      System.out.println ( value + "  " + pattern + "  " + output);
   }

   static public void customNumberFormat (String pattern, int value, Locale loc ) {
     NumberFormat nf = NumberFormat.getNumberInstance(loc);

      String output = nf.format(value);
      System.out.println ( value + "  " + pattern + "  " + output);
   }

   static public void localizedFormat  (String pattern, double value, Locale loc )
   {
      NumberFormat nf = NumberFormat.getNumberInstance(loc);
      DecimalFormat df = (DecimalFormat)nf;
      df.applyPattern(pattern);
      String output = df.format(value);
      System.out.println(pattern + "  " + output + "  " + loc.toString());
   }


  public static String format ( final double number, final int maxPrecision, final int minPrecision, final char decimalSeparator )
  {
    final DecimalFormat dfmt = new DecimalFormat();
    dfmt.setMinimumFractionDigits(minPrecision);
    dfmt.setMaximumFractionDigits(maxPrecision);
    dfmt.setMinimumIntegerDigits(1);
    dfmt.setMaximumIntegerDigits(25);
    dfmt.setGroupingUsed (false);
    dfmt.setParseIntegerOnly (false);
    dfmt.setDecimalSeparatorAlwaysShown(false);
    final DecimalFormatSymbols dfs = dfmt.getDecimalFormatSymbols();
    dfs.setDecimalSeparator(decimalSeparator);
    dfmt.setDecimalFormatSymbols(dfs);
    System.out.println(" format =  " + dfmt.toPattern () );
    String s = dfmt.format ( number );
    return s;
  }


   static public void main(String[] args)
   {
     String str;
     /*
     Locale ru = new Locale("ru", "RU");
      //customNumberFormat("000000", 1234567.89);
      //customNumberFormat("0#####", 1234,ru);
      //customNumberFormat("000000", 12345678,ru);
      //customNumberFormat("000000", 123456,ru);

      customFormat("###,###.###", 123456.789);
      customFormat("###.##", 123456.789);
      customFormat("###.##", 123456.70);
      customFormat("###.00", 123456.789);
      customFormat("##0.00", .7);
      customFormat("000000.000", 123.78);
      customFormat("$###,###.###", 12345.67);
      customFormat("\u00a5###,###.###", 12345.67);

     customFormat("000000", 123.78);
     customFormat("000000", 1237);
     customFormat("000000", 12345678);
     customFormat("000000", 12345678, 6);
     //customFormat("0#####", 12345678);   // �������� ������
     customFormat("000000", 123456);

      Locale currentLocale = new Locale("en", "US");

      DecimalFormatSymbols unusualSymbols =   new DecimalFormatSymbols(currentLocale);
      unusualSymbols.setDecimalSeparator('|');
      unusualSymbols.setGroupingSeparator('^');
      String strange = "#,##0.###";
      DecimalFormat weirdFormatter = new DecimalFormat(strange, unusualSymbols);
      weirdFormatter.setGroupingSize(4);
      String bizarre = weirdFormatter.format(12345.678);
      System.out.println(bizarre);

      Locale[] locales = {
         new Locale("en", "US"),
         new Locale("de", "DE"),
         new Locale("ru", "RU"),
         new Locale("fr", "FR")
      };

      for (int i = 0; i < locales.length; i++) {
         localizedFormat("###,###.###", 123456.789, locales[i]);
      }
     */
     str  = format ( 9.600000000000001, 2, 2, '.' );
     System.out.println(" str =  " + str );
     str  = format ( 9.6, 2, 2, '.' );
     System.out.println(" str =  " + str );
   }
}



