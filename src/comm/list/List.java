package comm.list;


import javax.comm.CommPortIdentifier;
import java.util.Enumeration;

/**
 * <BR>
 * <BR> User: svj
 * <BR> Date: 01.03.2006
 * <BR> Time: 16:04:06
 */
public class List
{
    public static void main ( String[] args )
    {
        CommPortIdentifier  portId;
        Enumeration         portList;

        System.out.println ( "Start Port List" );
        try
        {
            portList = CommPortIdentifier.getPortIdentifiers ();
            while ( portList.hasMoreElements () )
            {
                portId = ( CommPortIdentifier ) portList.nextElement ();
                System.out.println ( "Port: Name = " + portId.getName () + ", Type = " + portId.getPortType () );
            }
        } catch ( Exception e ) {
            System.out.println ( "ERROR" );
            e.printStackTrace ();
        }
        System.out.println ( "Finish Port List" );
    }
}
