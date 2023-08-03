package string;

/**
 * <BR> User: Zhiganov
 * <BR> Date: 19.10.2007
 * <BR> Time: 11:36:11
 */
public class HashCode 
{
    public static void main(String[] args)
    {
        String str, title;
        int hashCode;

        str         = "value";
        title       = str.toUpperCase();
        hashCode    = str.hashCode();

        //     private final int XMLBOOK = -1946313824;  // xmlBook
        System.out.println( "private final int\t" + title + "\t= " + hashCode + "; // " + str );
    }

}
