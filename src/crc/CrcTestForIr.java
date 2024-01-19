package crc;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;
import java.util.zip.Adler32;
import java.util.zip.CRC32;

/**
 * Вычислдение конктроьной суммы для ИК пакета кондиционера корпуса 1.
 * <BR/> Имеем - A=0x55600600c4022a00,B=0x1c6
 * <BR/> - где 0x1c6 - это контрольная сумма для  55600600c4022a00
 * <BR/> Необходимо олпределть алгоритм вычисления.
 * <BR/>
 * <BR/>
 * - A=0x55600600c4012a00,B=0x1c0      -- 1C0 = 448 =  1 1100 0000
 * - A=0x55600600c4013a00,B=0x1e0
 * - A=0x55a00600c4012a00,B=0xc0
 * - A=0x550006000a812a00, B=0x1e8
 *
 * 227 = E3 = 1110 0011
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 28.09.23 19:49
 */
public class CrcTestForIr {

    private static File file;


    public static void main(String[] args) {
        CrcTestForIr handler = new CrcTestForIr();

        //handler.create(args);

        // 55600600c4022a00 - 55 60 06 00 c4 02 2a 00   --- 1c6     -- 454   -- 1 198
        //byte[] buffer = new byte[] {0x55, 0x60, 0x06, 0x00, (byte) 0xC4, 0x02, 0x2a, 0x00 };  // 196
        //short[] data = new short[] {0x55, 0x60, 0x06, 0x00, 0xC4, 0x02, 0x2a, 0x00 };  // 196
        // 0xC4 = 196
        // в байти С4 - это отрицательное число
        byte[] dataByte = new byte[] {0x55, 0x60, 0x06, 0x00, (byte)0xC4, 0x01, 0x2a, 0x00 };  // C4 - 196
        int[] dataInt = new int[] {0x55, 0x60, 0x06, 0x00, 0xC4, 0x02, 0x2a, 0x00 };  // C4 - 196
        // long - точно знак пропадет

        // 55 00 06 00 0a 81 2a 00
        byte[] dataByte4 = new byte[] {0x55, 0x00, 0x06, 0x00, 0x0a, (byte)0x81, 0x2a, 0x00 };  // 81 -


        Map<String,long[]> dataList = new HashMap<>();
        long[] dataLong = new long[] {0x55, 0x60, 0x06, 0x00, 0xC4, 0x01, 0x2a, 0x00 };  // 1C0 = 448
        dataList.put("448", dataLong);
        // 55 60 06 00 c4 01 3a 00,B=0x1e0   = 480
        dataLong = new long[] {0x55, 0x60, 0x06, 0x00, 0xC4, 0x01, 0x3a, 0x00 };  // 1E0 = 480
        dataList.put("480", dataLong);
        // 55 a0 06 00 c4 01 2a 00,  B=0xc0   = 192
        dataLong = new long[] {0x55, 0xA0, 0x06, 0x00, 0xC4, 0x01, 0x2a, 0x00 };  // C0 = 192
        dataList.put("192", dataLong);
        // 55 00 06 00 0a 81 2a 00, B=0x1e8  = 488
        dataLong = new long[] {0x55, 0x00, 0x06, 0x00, 0x0A, 0x81, 0x2a, 0x00 };  // 1E8 = 488
        dataList.put("488", dataLong);

        // 55 60 06 00 c4 02 2a 00, B=0x1c6  = 454
        dataLong = new long[] {0x55, 0x60, 0x06, 0x00, 0xC4, 0x02, 0x2a, 0x00 };  // 1C6 = 454
        dataList.put("454", dataLong);
        // 55 20 06 00 c4 01 2a 00, B=0x140  = 320
        dataLong = new long[] {0x55, 0x20, 0x06, 0x00, 0xC4, 0x01, 0x2a, 0x00 };  // 140 = 320
        dataList.put("320", dataLong);
        // 55 e0 06 00 c4 01 2a 00, B=0x20  = 32
        dataLong = new long[] {0x55, 0xE0, 0x06, 0x00, 0xC4, 0x01, 0x2a, 0x00 };
        dataList.put("32", dataLong);
        // 55 10 06 00 c4 01 2a 00, B=0x120  = 288
        dataLong = new long[] {0x55, 0x10, 0x06, 0x00, 0xC4, 0x01, 0x2a, 0x00 };
        dataList.put("288", dataLong);
        // 55 90 06 00 c4 01 2a 00, B=0xa0  = 160
        dataLong = new long[] {0x55, 0x90, 0x06, 0x00, 0xC4, 0x01, 0x2a, 0x00 };
        dataList.put("160", dataLong);
        // 55 10 06 00 c4 01 3a 00, B=0x110  = 272
        dataLong = new long[] {0x55, 0x10, 0x06, 0x00, 0xC4, 0x01, 0x3a, 0x00 };
        dataList.put("272", dataLong);

        long result;
        int ic = 1;
        for (Map.Entry<String,long[]> entry : dataList.entrySet()) {
            result = handler.createSumNibe(entry.getValue());
            System.out.println(ic + ") result = " + result + "; crc = " + entry.getKey());
            ic++;
        }


    //    checkByte();

        //short[] result = handler.create16(data);
        //short[] result = handler.create32(data);
        //short result = handler.createSum4(dataInt);
        //long result = handler.createSumLong1(dataLong);
    //    long result = handler.createSumNibe(dataLong);   // 227 = E3

        // исходник
        //printSourceByte(dataByte);
    //    printSource(dataLong);

        //System.out.println("result = " + DumpTools.printArray(result));
        //System.out.println("result = " + result);

    }


    private static void checkByte() {
        /*
        // 0xC4    - 196
        //byte b0 = 196;
        int i1 = 0xC4;
        byte b1 = (byte)0xC4;
        System.out.println("0xC4: int = " + i1 + "; byte = " + b1);
        // 0xC4: int = 196; byte = -60

        // int myInt = myByte & 0xff;
        //short myShort = myByte & 0xff;
        int myInt = i1 & 0xff;
        short myInt2 = i1 & 0xff;

        Byte.
        */
    }


    private static void printSourceByte(byte[] data) {
        for (int i=0; i < data.length; i++) {
            System.out.print(Byte.toString(data[i])  + " ");
        }
    }

    private static void printSource(long[] data) {
        for (int i=0; i < data.length; i++) {
            System.out.print(data[i] + " ");
            System.out.println();
        }
    }


    private short[] create32(byte[] data) {
        CRC32 crc32 = new CRC32();
        /*Создаем объект класса Adler32*/
        Adler32 adler32 = new Adler32();

        int countBytes = data.length;


        /*
        try (FileInputStream in = new FileInputStream(file)) {
            //читаем байты из файла
            while ((countBytes = in.read(buffer)) != -1) {
                //обнавляем контрольную сумму у объектов
                crc32.update(buffer, 0, countBytes);
                adler32.update(buffer, 0, countBytes);
            }
        } catch (IOException ignore) {}
        */

        crc32.update(data, 0, countBytes);
        adler32.update(data, 0, countBytes);

        /*Выводим контрольные суммы на экран*/
        System.out.println("CheckSum: " + crc32.getValue() + "(CRC32)");
        System.out.println("CheckSum: " + adler32.getValue() + "(Adler32)");

        return new short[0];
    }

    private short[] create16(short[] buffer) {
        try {
            //CRC16 crc16 = new CRC16();
            short[] crc = CRC16.CRC16_CCTALK(buffer);
            return crc;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("error") ;
        }
    }

    // old
    private void create(String[] args) {
        /*Проверяем аргументы командной строки*/
        if (checkArgs(args)) {
            /*Создаем объект класса CRC32*/
            CRC32 crc32 = new CRC32();
            /*Создаем объект класса Adler32*/
            Adler32 adler32 = new Adler32();

            byte[] buffer = new byte[1024];
            int countBytes = 0;

            try (FileInputStream in = new FileInputStream(file)) {
                /*читаем байты из файла*/
                while ((countBytes = in.read(buffer)) != -1) {
                    /*обнавляем контрольную сумму у объектов*/
                    crc32.update(buffer, 0, countBytes);
                    adler32.update(buffer, 0, countBytes);
                }
            } catch (IOException ignore) {}

            /*Выводим контрольные суммы на экран*/
            System.out.println("CheckSum: " + crc32.getValue() + "(CRC32)");
            System.out.println("CheckSum: " + adler32.getValue() + "(Adler32)");
        }
    }

    private short createSum(byte[] data) {
        short checksum = 0x00;
        for (int i=0; i < (data.length-1); i++) {
          checksum += data[i];
        }
        return checksum;
    }

    private byte createSum3(byte[] data) {
        byte checksum = 0x00;
        for (int i=0; i < (data.length-1); i++) {
          checksum += data[i];
        }
        return checksum;
    }

    private short createSum2(byte[] data) {
        short checksum = 0x00;
        for (int i=0; i < (data.length-1); i++) {
          checksum ^= data[i];
        }
        return checksum;
        /*
 int add(int a, int b) {
   while (b != 0) {
     int sum = a ^ b; // суммирование без переноса
     int carry = (a & b) << 1; // перенос без суммирования
     a = sum;
     b = carry;
   }
   return a;
 }

         */
    }

    private int createSum4(int[] data) {
        int checksum = 0x00;
        for (int i=0; i < (data.length-1); i++) {
          checksum += data[i];
        }
        return checksum;
    }

    private int createSum5(int[] data) {
        int checksum = 0x00;
        for (int i=0; i < (data.length-1); i++) {
          checksum ^= data[i];
        }
        return checksum;
    }

    private long createSumLong1(long[] data) {
        long checksum = 0x00;
        for (int i=0; i < data.length; i++) {
          checksum ^= data[i];
        }
        return checksum;
    }

    private long createSumNibe(long[] data) {

        long checksum = 0;
        for (int i = 0; i < 8; i++) {
            if (i == 7)
                checksum += reverseBits8(data[i], 2);  // Byte 7 only has 2 bits
            else
                checksum += reverseBits8(data[i], 8);
        }
        long result = reverseBits8(checksum, 8);
        result = result << 1;

        return result;
    }

    // Reverses bit order for a uint8_t.
    // Can modify the bitlength that needs to be reversed (needed 8, 5 bit or 2 bit reverse)
    private long reverseBits8(long value, int bitLength) {
        long reversedValue = 0;

        for (int i = 0; i < bitLength; i++) {
            // Extract the i-th bit from the original value
            long bit = (value >> i) & 1;

            // Set the (bitLength - 1 - i)-th bit in the reversed value
            reversedValue |= bit << (bitLength - 1 - i);
        }

        return reversedValue;
    }

    /**
     * Функция для проверки аргументов командной строки
     * @param args аргументы командной строки
     * @return true, если мы корректно передали неообходимые аргументы,
     *                                                    и false в противном случае
     */
    private static boolean checkArgs(String[] args) {
        //Проверяем что программе передан путь к файлу
        if (args.length == 0) {
            System.out.println("Введите путь до файла");
            return false;
        }

        String fileName = args[0];
        file = new File(fileName);

        //Проверяем наличие файла на диске
        if (!file.exists()) {
            System.out.println("Данного файла не существует");
            return false;
        }

        return true;
    }

}
