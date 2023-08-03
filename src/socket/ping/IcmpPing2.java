package socket.ping;


import org.shortpasta.icmp.IcmpPingRequest;
import org.shortpasta.icmp.IcmpUtil;
import org.shortpasta.icmp.IcmpPingResponse;

/**
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 23.07.2010 9:40:57
 */
public class IcmpPing2
{
    public static void main ( String[] args )
    {
        String host;
        try
        {
            //host = "web.mit.edu";   // 18.9.22.69
            //host = "192.168.1.55";        // ok
            //host = "192.168.1.51";
            //host = "192.168.1.101";        // ok
            //host = "192.168.0.215";
            host = "192.168.0.215";
            System.out.println ( "host: " + host );
            final IcmpPingRequest icmpPingRequest = new IcmpPingRequest ();
            icmpPingRequest.setIpAddress ( host );           // required: always set the ip address to ping
            icmpPingRequest.setPacketSize (32);                    // optional: set the packet size if you want a size other than 32
            icmpPingRequest.setTimeout (1000);                     // required: set a timeout, or your thread might wait forever!
            icmpPingRequest.setIdentifier (5000);                  // optional: set identifier/sequenceNumber only if you don't want shortpasta-icmp to do it=
            icmpPingRequest.setSequenceNumber (1);                 // optional: shortpasta-icmp sets these to the process id and an incremental number that starts with 1
            icmpPingRequest.setThrowExceptionOnTimeoutFlag (true); // optional: set this if you want to receive a TimeoutIcmpException to signal a timeout
            icmpPingRequest.setUseDedicatedSocketFlag (true);      // optional: set this if you want this ping to be processed entirely on the native side and with a dedicated socket

            final IcmpPingResponse icmpPingResponse = IcmpUtil.executeIcmpPingRequest (icmpPingRequest);
            System.out.println ( "icmpPingResponse: " + icmpPingResponse );

        } catch ( Exception e )        {
            e.printStackTrace ();
        }
    }
    
}
