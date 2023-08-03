package bit;


import java.util.BitSet;

/**
 * Пример работы с битами целого числа.
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 21.12.2012 16:08
 */
public class BitIntSample
{
    public static void main ( String[] args )
    {
        BitSet bitSet;
        int flags;

        /*
        flags  = 2;
        System.out.println ( "- flags = " + flags );
        bitSet = new BitSet ( flags );

        System.out.println ( "- bitSet = " + bitSet );
        System.out.println ( "- bit 0 = " + bitSet.get ( 0 ) );
        System.out.println ( "- bit 1 = " + bitSet.get ( 1 ) );
        System.out.println ( "- bit 2 = " + bitSet.get ( 2 ) );
        System.out.println ( "- bit 3 = " + bitSet.get ( 3 ) );

        BitSet bits1 = new BitSet ();
        BitSet bits2 = new BitSet ();
        bits1.set(0); // set the 0th bit
        bits2.set(6); // set the 6th bit
        bits2.or ( bits1 );
        System.out.println ( bits2 );    // {0, 6}

        int bits02 = 0b1000001;
        int bits01 = 0b1111111;
        bits02 &= bits01;
        System.out.println(Integer.toBinaryString(bits02));
        */

        //int Number = int.MaxValue;
        //byte[] bytes = BitConverter.GetBytes(Number);

        flags = 3;
        System.out.println ( "- flags = " + flags );
        byte[] bb   = getBits ( flags );
        System.out.println ( "- bit 0 = " + bb[0] );
        System.out.println ( "- bit 1 = " + bb[1] );
        System.out.println ( "- bit 2 = " + bb[2] );
        System.out.println ( "- bit 3 = " + bb[3] );
        System.out.println ( "- bit 29 = " + bb[29] );
        System.out.println ( "- bit 30 = " + bb[30] );
        System.out.println ( "- bit 31 = " + bb[31] );
    }

    /*
    // from 11100101
    private static BitSet fromString(final String s)
    {
            return BitSet.valueOf(new long[] { Long.parseLong(s, 2) });
        }
    */

    /**
     * Преобразовать целое в набор битов как массив байт, в котором только значения 0 или 1 - 100101010
     * Здесь самый младший бит располагается в 31 месте.
     * @param value Исходное значение.
     * @return      Массив из значений битов 0/1.
     */
    private static byte[] getBits ( int value )
    {
        byte[] result;

        result = new byte[32];
       int displayMask = 1 << 31;

       for ( int c = 1; c <= 32; c++ )
       {
          result [ c - 1 ] =  (byte) ( ( value & displayMask ) == 0 ? 0 : 1 );
          value <<= 1;

          //if ( c % 8 == 0 )  buf.append( ' ' );
       }

       return result;
    }


}
