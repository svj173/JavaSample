package statics;

/**
 * <BR>  Тестовый класс, содержит статические методы.
 * <BR>
 * <BR> User: svj
 * <BR> Date: 17.03.2006
 * <BR> Time: 13:30:11
 */
public class Statics
{
    public static int   get ( int ic )
    {
        int result, i;
        result  = 0;
        for ( i=0; i<ic; i++ )   {
            result++;
        }
        return result;
    }

}
