package test.as;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: svj
 * Date: 16.02.2007
 * Time: 17:12:10
 * To change this template use File | Settings | File Templates.
 */
public class Response implements Serializable
{
    private int id;
    private Object value;

    public Response ( int id, Object object )
    {
        this.id = id;
        this.value = object;
    }

    public String toString()
    {
        StringBuffer result = new StringBuffer(64);
        result.append ( "Response: id = " );
        result.append ( id );
        result.append ( ", value = " );
        result.append ( value );
        return result.toString();
    }

}
