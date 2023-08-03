package socket.send;

import java.io.Serializable;

/**
 * <BR>
 * <BR>
 * <BR> User: svj
 * <BR> Date: 16.06.2006
 * <BR> Time: 11:01:48
 */
public class TestObject  implements Serializable
{
    public int      code;
    public String   msg;

    public TestObject ( int code, String msg )
    {
        this.code   = code;
        this.msg    = msg;
    }

    public String toString()
    {
        StringBuffer    result  = new StringBuffer ( 128 );
        result.append ( "TestObject. Code = " );
        result.append ( code );
        result.append ( ", MSG = " );
        result.append ( msg );
        return result.toString ();
    }

}
