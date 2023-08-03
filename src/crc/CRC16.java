package crc;

/**
 * <BR>
 * <BR>
 * <BR> User: svj
 * <BR> Date: 30.06.2006
 * <BR> Time: 12:16:57
 */
public class CRC16
{
    private static int CRCPOLY_CCTALK = 0x8408;

    private CRC16() {
    }

    //public static short[] CRC16_CCTALK(short[] stream)
    public static short[] CRC16_CCTALK(short[] stream)
    {
        int     Len = stream.length;
        int     CRC, i, cur;
        boolean condition;

        CRC = 0;

        for ( cur = 0; cur < Len; cur++)
        {
            CRC ^= stream[cur];
            for (i = 0; i < 8; i++)
            {
                condition = (CRC & 0x0001) != 0;
                if ( condition )
                {
                    CRC >>= 1;
                    CRC ^= CRCPOLY_CCTALK;
                }
                else
                {
                    CRC >>= 1;
                }
            }
        }
        return new short[]{(short) ((CRC & 65280) >> 8), (short) (CRC & 255)};
        //return CRC;
    }

    public static void main ( String[] args )
    {
        int crc, r1, r2;
        //short crc, result, r1, r2;
        //char crc, result, r1, r2;
        /*
        byte[] data;

        data =  new byte[] { (byte) 0xFC, 0x05, 0x40 };//, 0x2B, 0x15 };
        result  = CRC16.CRC16_CCTALK ( data );
        System.out.println ( "CRC16 (0x2B, 0x15) = " + Integer.toHexString ( result ) );

        data =  new byte[] { (byte) 0xFC, 0x05, 0x50 };//, AA 05 };
        result  = CRC16.CRC16_CCTALK ( data );
        System.out.println ( "CRC16 (AA 05) = " + Integer.toHexString ( result ) );

        data =  new byte[] { (byte) 0xFC, 0x05, 0x11 };//, 27 56 };    FC 05 11
        result  = CRC16.CRC16_CCTALK ( data );
        System.out.println ( "CRC16 (27 56) = " + Integer.toHexString ( result ) );
        */

        short[] data;
        short[] result;

        //data =  new short[] { (byte) 0xFC, 0x05, 0x40 };//, 0x2B, 0x15 };
        data =  new short[] { 0xFC, 0x05, 0x40 };//, 0x2B, 0x15 };
        result  = CRC16.CRC16_CCTALK ( data );
        System.out.println ( "CRC16 (0x2B, 0x15) = " + Integer.toHexString ( result[0] )
              + " "  + Integer.toHexString ( result[1] ) );

        data =  new short[] { 0xFC, 0x05, 0x50 };//, AA 05 };
        result  = CRC16.CRC16_CCTALK ( data );
        System.out.println ( "CRC16 (AA 05) = " + Integer.toHexString ( result[0] )
        + " "  + Integer.toHexString ( result[1] ) );

        data =  new short[] { 0xFC, 0x05, 0x11 };//, 27 56 };    FC 05 11
        result  = CRC16.CRC16_CCTALK ( data );
        System.out.println ( "CRC16 (27 56) = " + Integer.toHexString ( result[0] )
        + " "  + Integer.toHexString ( result[1] ) );

        data =  new short[] { 0xFC, 0x05, 0x88 };//, 27 56 };    FC 05 11
        result  = CRC16.CRC16_CCTALK ( data );
        System.out.println ( "CRC16 (88) = " + Integer.toHexString ( result[0] )
        + " "  + Integer.toHexString ( result[1] ) );

        data =  new short[] { 0xFC, 0x05, 0x89 };//, 27 56 };    FC 05 11
        result  = CRC16.CRC16_CCTALK ( data );
        System.out.println ( "CRC16 (89) = " + Integer.toHexString ( result[0] )
        + " "  + Integer.toHexString ( result[1] ) );

        System.exit ( 10 );
    }

}
