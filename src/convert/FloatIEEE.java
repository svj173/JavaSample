package convert;

/**
 * Программа для преобразования 32 битов Single Precision IEEE 754 с плавающей запятой
 * <BR/> Из массива байт в число и наоборот.
 * <BR/>
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 16.09.2020 16:53
 */
public class FloatIEEE {
    // Convert the 32-bit binary into the decimal
    private static float GetFloat32( String Binary )
    {
        int intBits = Integer.parseInt(Binary, 2);
        float myFloat = Float.intBitsToFloat(intBits);
        return myFloat;
    }

    // Get 32-bit IEEE 754 format of the decimal value
    private static String GetBinary32( float value )
    {
        int intBits = Float.floatToIntBits(value);
        String binary = Integer.toBinaryString(intBits);
        return binary;
    }

    /**
     * @param args
     */
    public static void main(String[] args)
    {
        float value;

        //value = 19.5f;
        value = 119.746f;

        // Convert 19.5 into IEEE 754 binary format..
        String str = GetBinary32( value );
        System.out.println( "Binary equivalent of "+value+":" );
        System.out.println( str );

        //str = "00111111001111101101110000000000";        // 0.74554443
        //str = "00000000011101110011111100111110";        //
        str = "11111100111110";        //
        // .. and back again
        float f = GetFloat32( str );
        System.out.println( "\nDecimal equivalent of " + str + ":");
        System.out.println( f );
    }
}
