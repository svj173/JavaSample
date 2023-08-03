package convert;

import java.math.RoundingMode;
import java.nio.ByteBuffer;
import java.text.DecimalFormat;
import java.text.ParsePosition;

/**
 * Датчик вычисления кол-ва теплоты.
 * <BR/> Хранит данные в виде - 8 байт - целая часть, 8 байт - дробная в формате float IEEE.
 * <BR/> Необходимо из байт сформивроать ноимальное дробное число.
 * <BR/>
 * <BR/> Дано:
 * <BR/> - целая часть - 00 00 00 77    -- 119
 * <BR/> - др часть    - 3F 3E DC 00    -- 0.745
 * <BR/> Итого: 119.745
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 16.09.2020 17:21
 */
public class Vkt9Value {
    public static void main(String[] args)
    {
        //byte[] fb = new byte[] { (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x77};
        byte[] fb = new byte[] { (byte) 0x00, (byte) 0x12, (byte) 0x5D, (byte) 0xEB};     // 00 12 5D EB

        //byte[] fd = new byte[] { (byte) 0x3F, (byte) 0x3E, (byte) 0xDC, (byte) 0x00};
        //byte[] fd = new byte[] { (byte) 0x3F, (byte) 0xB2, (byte) 0xBC, (byte) 0x96};    // 3F B2 BC 96
        byte[] fd = new byte[] { (byte) 0x40, (byte) 0x0A, (byte) 0x40, (byte) 0x30};    // 40 0A 40 30

        Vkt9Value handler = new Vkt9Value();

        float result = handler.getValue(fb, fd);
        System.out.println( "\nresult = " + result);


        byte[] value;

        //value = new byte[] { (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x77, (byte) 0x3F,
        //        (byte) 0x3E, (byte) 0xDC, (byte) 0x00};

        // 00 00 00 B7 3E B3 FA 00
        value = new byte[] { (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0xB7, (byte) 0x3E,
                (byte) 0xB3, (byte) 0xFA, (byte) 0x00};

        result = handler.getValue(value);
        System.out.println( "\nresult 2 = " + result);

        float f1;
        //f1 = 119.746f;       // bit = 1122991604 --  42 EF 7D F4
        f1 = 183.352f;         // bit = 1127701021 --  43 37 5A 1D

        int ic = Float.floatToIntBits (f1);

        System.out.println( "\n\nic = " + ic);

        byte[] bb;
        bb = new byte[] { (byte) 0x42, (byte) 0xEF, (byte) 0x7D, (byte) 0xB8};    //

        f1 = handler.getFloat(bb);

        System.out.println( "\n\nFloat: bytes = " + handler.listArray(bb, ' ') + "; float = " + f1);


    }

    private float getFloat(byte[] doublePart) {

        int dubl = ByteBuffer.wrap(doublePart).getInt();

        float myFloat = Float.intBitsToFloat(dubl);

        return myFloat;
    }

    private float getValue(byte[] firstPart, byte[] doublePart) {

        int first = ByteBuffer.wrap(firstPart).getInt();
        int dubl = ByteBuffer.wrap(doublePart).getInt();

        float myFloat = Float.intBitsToFloat(dubl);

        float result = first + myFloat;

        // округляем до 3-х знаков
        float result2 = roundThreeCharacters(result);


        System.out.println( "\nFirst = " + first);
        System.out.println( "\nDouble = " + myFloat);
        System.out.println( "\nresult 1 = " + result);
        System.out.println( "\nresult 2 = " + result2);

        return result2;
    }

    public static float roundThreeCharacters(float value) {
        DecimalFormat decimalFormat = new DecimalFormat("#.###");
        decimalFormat.setRoundingMode(RoundingMode.HALF_UP);
        String result = decimalFormat.format(value);
        return decimalFormat.parse(result, new ParsePosition(0)).floatValue();
    }

    private float getValue(byte[] value) {

        if (value.length != 8) throw new RuntimeException("Error");

        byte[] firstPart = new byte[4];
        firstPart[0] = value[0];
        firstPart[1] = value[1];
        firstPart[2] = value[2];
        firstPart[3] = value[3];

        byte[] doublePart = new byte[4];
        doublePart[0] = value[4];
        doublePart[1] = value[5];
        doublePart[2] = value[6];
        doublePart[3] = value[7];

        // Преобразуем массивы байт в числа
        int first = ByteBuffer.wrap(firstPart).getInt();
        int doubl = ByteBuffer.wrap(doublePart).getInt();

        float fl = Float.intBitsToFloat(doubl);

        float result = first + fl;

        // округляем до 3-х знаков
        float result2 = roundThreeCharacters(result);

        return result2;
    }

    public String listArray(byte[] array, char ch) {
        String result = "";
        StringBuilder sb = new StringBuilder(128);
        if ((array == null) || (array.length == 0)) {
            return result;
        }

        int ic = array.length;
        for (int i = 0; i < ic; i++) {
            sb.append(array[i]);
            sb.append(ch);
            sb.append(" ");
        }
        // удалить последнюю запятую (символ CH)
        result = sb.toString();
        result = result.substring(0, result.length() - 2);
        return result;
    }


}
