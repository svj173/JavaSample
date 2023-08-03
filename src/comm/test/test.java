package comm.test;

import javax.comm.*;
import java.util.*;


/**
 * <BR> Выводит список всех портов компьютера.
 *
 * <BR> User: svj
 * <BR> Date: 01.03.2006
 * <BR> Time: 11:32:38
 */
public class test
{

    public static void main ( String[] args )
    {
        CommPortIdentifier  portId;
        Enumeration         portList;
        Terminal            terminal;

        portList = CommPortIdentifier.getPortIdentifiers ();
        while ( portList.hasMoreElements () )
        {
            portId = ( CommPortIdentifier ) portList.nextElement ();
            System.out.println ( "Port: Name = " + portId.getName () + ", Type = " + portId.getPortType () );
            //*
            if ( portId.getPortType () == CommPortIdentifier.PORT_SERIAL )
            {
                if ( portId.getName ().equals ( "COM2" ) ) {
                    terminal = new Terminal ( portId );
                }
            }
            //*/
        }
    }

}
