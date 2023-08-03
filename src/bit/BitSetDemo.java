package bit;


import java.util.BitSet;

/**
 * Вывод результата:
 *
 Initial pattern in bits1:
 {0, 2, 4, 6, 8, 10, 12, 14}

 Initial pattern in bits2:
 {1, 2, 3, 4, 6, 7, 8, 9, 11, 12, 13, 14}

 bits2 AND bits1:
 {2, 4, 6, 8, 12, 14}

 bits2 OR bits1:
 {0, 2, 4, 6, 8, 10, 12, 14}

 bits2 XOR bits1:
 {}
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 21.12.2012 16:35
 */
public class BitSetDemo
{
    public static void main(String args[]) {
       BitSet bits1 = new BitSet(16);
       BitSet bits2 = new BitSet (16);

 	  // set some bits
       for(int i=0; i<16; i++)
       {
          if((i%2) == 0) bits1.set(i);
          if((i%5) != 0) bits2.set(i);
       }
       System.out.println("Initial pattern in bits1: ");
       System.out.println(bits1);
       System.out.println("\nInitial pattern in bits2: ");
       System.out.println(bits2);

       // AND bits
       bits2.and(bits1);
       System.out.println("\nbits2 AND bits1: ");
       System.out.println(bits2);

       // OR bits
       bits2.or(bits1);
       System.out.println("\nbits2 OR bits1: ");
       System.out.println(bits2);

       // XOR bits
       bits2.xor(bits1);
       System.out.println("\nbits2 XOR bits1: ");
       System.out.println(bits2);
    }

    /**
     * Преобразовать целое в набор битов как строка - 100101010
     * @param value
     * @return
     */
    private String getBits( int value )
    {
       int displayMask = 1 << 31;
       StringBuffer buf = new StringBuffer( 35 );

       for ( int c = 1; c <= 32; c++ )
       {
          buf.append(
             ( value & displayMask ) == 0 ? '0' : '1' );
          value <<= 1;

          if ( c % 8 == 0 )
             buf.append( ' ' );
       }

       return buf.toString();
    }

}
