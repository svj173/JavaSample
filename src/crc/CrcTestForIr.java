package crc;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.zip.Adler32;
import java.util.zip.CRC32;

/**
 * Вычислдение конктроьной суммы для ИК пакета кондиционера корпуса 1.
 * <BR/> Имеем - A=0x55600600c4022a00,B=0x1c6
 * <BR/> - где 0x1c6 - это контрольная сумма для  55600600c4022a00
 * <BR/> Необходимо олпределть алгоритм вычисления.
 * <BR/>
 * <BR/>
 * - A=0x55600600c4012a00,B=0x1c0
 * - A=0x55600600c4013a00,B=0x1e0
 * - A=0x55a00600c4012a00,B=0xc0
 * - A=0x550006000a812a00, B=0x1e8
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
        byte[] data = new byte[] {0x55, 0x60, 0x06, 0x00, (byte)0xC4, 0x02, 0x2a, 0x00 };  // C4 - 196

        // 55 00 06 00 0a 81 2a 00
        byte[] data4 = new byte[] {0x55, 0x00, 0x06, 0x00, 0x0a, (byte)0x81, 0x2a, 0x00 };  // 81 -

        //short[] result = handler.create16(data);
        //short[] result = handler.create32(data);
        short result = handler.createSum(data);

        //System.out.println("result = " + DumpTools.printArray(result));
        System.out.println("result = " + result);

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
