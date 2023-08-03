package enumUse;


import java.io.IOException;


/**
 * Пример получения значений для enum по рефлекшн.
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 01.03.2012 9:06:00
 */
public class ReflectionEnum01
{
    public static void main ( String[] args )
            throws IOException
    {
        int ic;
        String className;
        INameNumber nn;
        OnOffEnum ooEnum;

        ic  = 2;
        //className   = "enumUse.INameNumber";
        className   = "enumUse.OnOffEnum";

        try
        {
            // можно создавать только классы с конструкторами без сигнатур.
            //nn  = ( INameNumber ) Class.forName(className).newInstance();
            //System.out.println ( "nn = " + nn );

            // по рефлекшн enum не создаются !!!! (svj, 2012-03-01) - работать только на сравнение имен классов

            ooEnum  = ( OnOffEnum ) Class.forName(className).newInstance();
            System.out.println ( "ooEnum = " + ooEnum );
            System.out.println ( "value = " + ic + "; result = " + ooEnum.getStrByIdx ( ic ) );

        } catch ( Exception e )       {
            e.printStackTrace();
        }
    }

}
