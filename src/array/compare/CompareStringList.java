package array.compare;


import java.util.ArrayList;
import java.util.Collection;

/**
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 13.03.2018 16:45
 */
public class CompareStringList
{
    private boolean compareList ( Collection<String> deviceMacAddressList, Collection<String> dbMacAddresslist )
    {
        int deviceCode, dbCode;

        if ( deviceMacAddressList == null )  deviceMacAddressList = new ArrayList<String> ();
        if ( dbMacAddresslist     == null )  dbMacAddresslist     = new ArrayList<String> ();

        deviceCode  = deviceMacAddressList.hashCode ();
        dbCode      = dbMacAddresslist.hashCode ();

        System.out.println ( "- deviceCode = "+deviceCode );
        System.out.println ( "- dbCode = "+dbCode );

        return deviceCode == dbCode;
    }

    public static void main ( String[] args )
    {
        CompareStringList handler;
        Collection<String> deviceMacAddressList, dbMacAddresslist;

        deviceMacAddressList = new ArrayList<String>();
        deviceMacAddressList.add ( "00:11:22:33:44:55" );
        deviceMacAddressList.add ( "00:11:22:33:44:56" );
        deviceMacAddressList.add ( "00:11:22:33:44:57" );
        deviceMacAddressList.add ( "00:11:22:33:44:58" );
        deviceMacAddressList.add ( "00:11:22:33:44:59" );
        //deviceMacAddressList = null;

        dbMacAddresslist = new ArrayList<String>();
        dbMacAddresslist.add ( "00:11:22:33:44:55" );
        dbMacAddresslist.add ( "00:11:22:33:44:56" );
        dbMacAddresslist.add ( "00:11:22:33:44:57" );
        dbMacAddresslist.add ( "00:11:22:33:44:58" );
        dbMacAddresslist.add ( "00:11:22:33:44:59" );

        System.out.println ( "deviceMacAddressList = "+deviceMacAddressList );
        System.out.println ( "dbMacAddresslist = "+dbMacAddresslist );

        handler = new CompareStringList();

        boolean b = handler.compareList ( deviceMacAddressList, dbMacAddresslist );

        System.out.println ( "-- b = "+b );

    }
}
