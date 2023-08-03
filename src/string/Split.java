package string;


import tools.DumpTools;

import java.util.StringTokenizer;


/**
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 04.06.2012 13:11:04
 */
public class Split
{
    public static void main ( String[] args )
    {
        String      ss, s;
        String[]    buf, sb;
        
        //ss  = "GET_ONT_CONFIG,SET_ONT_CONFIG, GET_ONT_STATE,";
        //ss  = "GET_ONT_CONFIG";  // length = 1
        ss  = "";         // length = 1
        //ss  = null;
        buf = ss.split(",");
        System.out.println ( buf );
        if ( buf != null )
            System.out.println ( buf.length );
        else
            System.out.println ( "null" );
        System.out.println ( ss + " = " + DumpTools.printArray ( buf ) );

        sb = new String[] { "\"InternetGatewayDevice.Services.VoiceService.1.VoiceProfile.1.Line.1.CallingFeatures.CallerIDName\"\"420014\"",
                "\"InternetGatewayDevice.Services.VoiceService.1.VoiceProfile.1.Line.1.CallingFeatures.CallerIDName\" \"420014\"",
                "InternetGatewayDevice.Services.VoiceService.1.VoiceProfile.1.Line.1.CallingFeatures.CallerIDName 420014",
                "\"InternetGatewayDevice.Services.VoiceService.1.VoiceProfile.1.Line.1.CallingFeatures.CallerIDName\"420014",
                "InternetGatewayDevice.Services.VoiceService.1.VoiceProfile.1.Line.1.CallingFeatures.CallerIDName\"420014\"",
                "\"InternetGatewayDevice.Services.VoiceService.1.VoiceProfile.1.Line.1.CallingFeatures.CallerIDName \"420014",
                "InternetGatewayDevice.Services.VoiceService.1.VoiceProfile.1.Line.1.CallingFeatures.CallerIDName \"420014\"",
                "",
                "InternetGatewayDevice.Services.VoiceService.1.VoiceProfile.1.Line.1.CallingFeatures.CallerIDName\"\"",
                "\"InternetGatewayDevice.Services.VoiceService.1.VoiceProfile.1.Line.1.CallingFeatures.CallerIDName\"\"\"",
                "\"InternetGatewayDevice.Services.VoiceService.1.VoiceProfile.1.Line.1.CallingFeatures.CallerIDName\" \"\"" };

        /*
        for ( String str : sb )
        {
            System.out.println ( str );
            buf = str.split("\"");
            System.out.println ( "-- buf size = " + buf.length );
            System.out.println ( DumpTools.listArray ( buf, ';' ) );
            System.out.println ( "----------------" );
        }
        */

        System.out.println ( "================================================" );
        StringTokenizer st;
        for ( String str : sb )
        {
            System.out.println ( str );
            st = new StringTokenizer ( str, "\"", false );
            while ( st.hasMoreTokens() )
            {
                s = st.nextToken ();
                System.out.println ( "-- '" + s + "'" );
            }
            System.out.println ( "------------------------------------" );
        }
    }

}
