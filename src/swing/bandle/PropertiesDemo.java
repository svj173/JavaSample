package swing.bandle;


import java.util.Enumeration;
import java.util.Locale;
import java.util.ResourceBundle;


/**
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 29.04.2012 20:17:00
 */
public class PropertiesDemo
{
    private static final String BUNDLE_PATH = "swing.bandle.resource.LabelsBundle";

    static void displayValue ( Locale currentLocale, String key )
    {

        ResourceBundle labels =
                ResourceBundle.getBundle ( BUNDLE_PATH, currentLocale );
        String value = labels.getString ( key );
        System.out.println (
                "Locale = " + currentLocale.toString () + ", " +
                        "key = " + key + ", " +
                        "value = " + value );

    } // displayValue


    static void iterateKeys ( Locale currentLocale )
    {

        ResourceBundle labels =
                ResourceBundle.getBundle ( BUNDLE_PATH, currentLocale );

        Enumeration bundleKeys = labels.getKeys ();

        while ( bundleKeys.hasMoreElements () )
        {
            String key = ( String ) bundleKeys.nextElement ();
            String value = labels.getString ( key );
            System.out.println ( "key = " + key + ", " +
                    "value = " + value );
        }

    } // iterateKeys


    static public void main ( String[] args )
    {

        Locale[] supportedLocales = {
                Locale.FRENCH,
                Locale.GERMAN,
                Locale.ENGLISH
        };

        for ( Locale supportedLocale : supportedLocales )
        {
            displayValue ( supportedLocale, "s2" );
        }

        System.out.println ();

        iterateKeys ( supportedLocales[ 0 ] );

    } // main

}
