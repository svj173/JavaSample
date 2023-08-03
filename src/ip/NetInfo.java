package ip;


/**
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 16.09.2014 13:06
 */
public class NetInfo
{
    private String  name, ip;
    private boolean loopback;

    public String getName ()
    {
        return name;
    }

    public void setName ( String name )
    {
        this.name = name;
    }

    public String getIp ()
    {
        return ip;
    }

    public void setIp ( String ip )
    {
        this.ip = ip;
    }

    public boolean isLoopback ()
    {
        return loopback;
    }

    public void setLoopback ( boolean loopback )
    {
        this.loopback = loopback;
    }

    public String toString()
    {
        StringBuilder result;

        result = new StringBuilder();

        result.append ( "[ NetInfo : name = " );
        result.append ( getName() );
        result.append ( "; ip = " );
        result.append ( getIp () );
        result.append ( "; loopback = " );
        result.append ( isLoopback () );

        result.append ( " ]" );

        return result.toString();
    }

}
