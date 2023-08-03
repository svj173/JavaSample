package exception;


import tools.Convert;

/**
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 08.07.2011 10:31:42
 */
public class SvjException  extends Exception
{
    public SvjException ( Throwable th, Object ... msg )
    {
        super ( Convert.concatObj ( msg ), th );
    }
    
}
