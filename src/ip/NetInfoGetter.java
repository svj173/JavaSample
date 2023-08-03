package ip;


import java.io.IOException;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;

/**
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 16.09.2014 13:05
 */
public class NetInfoGetter
{
    public static void main(String[] args) throws IOException
    {
        NetInfoGetter       getter;
        Collection<NetInfo> infoList;

        getter      = new NetInfoGetter ();
        infoList    = getter.getInfo();

        for ( NetInfo ni : infoList )
        {
            System.out.println ( ni );
        }

    }

    private Collection<NetInfo> getInfo ()
    {
        Enumeration         interfaces, inetAddresses;
        Collection<NetInfo> result;
        NetworkInterface    nif;
        NetInfo             info;
        InetAddress         inetAddr;

        result      = new ArrayList<NetInfo> ();

        try
        {
            interfaces = NetworkInterface.getNetworkInterfaces ();
            while ( interfaces.hasMoreElements() )
            {
                info    = new NetInfo();
                result.add ( info );

                nif     = (NetworkInterface) interfaces.nextElement();
                info.setName ( nif.getDisplayName() );

                inetAddresses = nif.getInetAddresses();
                while ( inetAddresses.hasMoreElements() )
                {
                    inetAddr = (InetAddress) inetAddresses.nextElement();

                    if (inetAddr instanceof Inet4Address )
                    {
                        info.setIp ( inetAddr.getHostAddress() );
                        info.setLoopback ( inetAddr.isLoopbackAddress() );
                    }
                    else if (inetAddr instanceof Inet6Address )
                    {
                        // nothing
                    }
                }
            }
        } catch ( Exception e )  {
            e.printStackTrace();
        }
        return result;
    }

}
