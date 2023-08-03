package crc;

/*************************************************************************
 *  Compilation:  javac CRC16.java
 *  Execution:    java CRC16 < data.txt
 *  Dependencies: StdIn.java
 *
 *  Reads in a sequence of bytes and prints out its 16 bit
 *  Cylcic Redundancy Check (CRC).
 *
 *  1 + x + x^5 + x^12 + x^16 is irreducible polynomial.
 *
 *************************************************************************/

/**
 * <BR>
 * <BR>
 * <BR> User: svj
 * <BR> Date: 22.06.2006
 * <BR> Time: 18:54:43
 */
public class crc16v1
{
    //private int CRCPOLY_CCTALK = 0x1021;
    private int CRCPOLY_CCTALK = 0x8408;

    public short process ( byte[] data, int firstCrc )
    {
        byte    c;
        short   crc;
        crc = (short) firstCrc;
        for ( int k=0; k<data.length; k++ )
        {
            c = data[k];
            for ( int i = 0; i < 8; i++ )
            {
                boolean c15 = ( ( crc >> 15 & 1 ) == 1 );
                boolean bit = ( ( c >> ( 7 - i ) & 1 ) == 1 );
                crc <<= 1;
                if ( c15 ^ bit ) crc ^= CRCPOLY_CCTALK;   // 0001 0000 0010 0001  (0, 5, 12)
                //crc ^= 0x1021;   // 0001 0000 0010 0001  (0, 5, 12)
                //if ( c15 ^ bit ) crc ^= 0x1020;   // 0001 0000 0010 0001  (0, 5, 12)
                //if ( c15 ^ bit ) crc ^= 0x8408;   // 1000 0100 0000 1000  (0, 5, 12)
            }
        }
        return crc;
    }

    public short process ( byte[] data )
    {
        byte    c;
        short   crc;
        crc = (short) 0xFFFF;
        for ( int k=0; k<data.length; k++ )
        {
            c = data[k];
            for ( int i = 0; i < 8; i++ )
            {
                boolean c15 = ( ( crc >> 15 & 1 ) == 1 );
                boolean bit = ( ( c >> ( 7 - i ) & 1 ) == 1 );
                crc <<= 1;
                if ( c15 ^ bit ) crc ^= CRCPOLY_CCTALK;   // 0001 0000 0010 0001  (0, 5, 12)
                //crc ^= 0x1021;   // 0001 0000 0010 0001  (0, 5, 12)
                //if ( c15 ^ bit ) crc ^= 0x1020;   // 0001 0000 0010 0001  (0, 5, 12)
                //if ( c15 ^ bit ) crc ^= 0x8408;   // 1000 0100 0000 1000  (0, 5, 12)
            }
        }
        return crc;
    }

    public static void main ( String[] args )
    {
        int crc, result, r1, r2;
        //short crc, result, r1, r2;
        //char crc, result, r1, r2;
        byte[] data;
        crc16v1 crc16;

        crc16   = new crc16v1 ();

        data =  new byte[] { (byte) 0xFC, 0x05, 0x40 };//, 0x2B, 0x15 };
        result  = crc16.process ( data );
        System.out.println ( "CRC16 (0x2B, 0x15) = " + Integer.toHexString ( result ) );

        data =  new byte[] { (byte) 0xFC, 0x05, 0x50 };//, AA 05 };
        result  = crc16.process ( data );
        System.out.println ( "CRC16 (AA 05) = " + Integer.toHexString ( result ) );

        data =  new byte[] { (byte) 0xFC, 0x05, 0x11 };//, 27 56 };    FC 05 11
        result  = crc16.process ( data );
        System.out.println ( "CRC16 (27 56) = " + Integer.toHexString ( result ) );


        //crc = ( short ) 0x0;    // 0xFFFF;       // initial contents of LFSR
        //data =  new byte[] { (byte) 'A'};

        /*
        data =  new byte[] { (byte) 0xFC, 0x05, 0x40 };//, 0x2B, 0x15 };
        //data =  new byte[] { (byte) 0xCF, 0x50, 0x04 };//, 0x2B, 0x15 };
        r1  = 0x2B15;
        r2  = 0x152B;
        // 9902 -> 2B15
        // 32410 -> 152B
        //*/

        /*
        data =  new byte[] { (byte) 0xFC, 0x05, 0x50 };//, AA 05 };
        r1  = 0xAA05;
        r2  = 0x05AA;
        // 1450 -> 05AA
        // -23678 -> AA05
        //*/

        /*
        data =  new byte[] { (byte) 0xFC, 0x05, 0x11 };//, 27 56 };    FC 05 11
        r1  = 0x2756;
        r2  = 0x5627;
        // 1450 -> 05AA
        // -23678 -> AA05
        //

        for ( crc = 0; crc<=0xFFFF; crc++ )     //   0xFFFF  32767  65535
        {
            result  = crc16.process ( data, crc );
            if ( (result == r1) || (result == r2) )
            {
                System.out.println ( "CRC16 = " + (int)result + ", first = " + (int)crc );
                System.out.println ( "CRC16 = " + Integer.toHexString ( result ) );
            }
        }
        */
        System.exit ( 10 );
    }

}
