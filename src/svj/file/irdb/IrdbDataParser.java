package svj.file.irdb;

import tools.DumpTools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;

/**
 * Парсер библиотеки IR сигналов https://github.com/probonopd/irdb.
 * <BR/> Задача: Вытащить из файлов данные, с разбивкой их по типам устройств (ТВ, Кондиционер).
 * <BR/> Если тип устройства не указан - определить его по набору функций.
 *
 * Типы устройсмтв:
 * A/C , Аудио , Селекторы аудио/видео , Кабель , Автомобильные стереосистемы , CD , Комбинированные аудиосистемы ,
 * Цифровые камеры , Цифровые фоторамки , Цифровые приставки , DVD (стандартный) , DVD (Blu-Ray) ,
 * DVD / VCR комбинированные устройства , DVR/PVR , Вентиляторы , Домашняя автоматизация ,
 * HTPC - Медиацентры , Клавиатуры , Лазерный диск, Медиаплееры , Разное , MP3-плееры ,
 * Проекторы , Спутник , Спутниковое радио , Системы безопасности , Лента , Игрушки , ТВ , ТВ комбо ,
 * Видеомагнитофон , UEI Official , WAV Files ,
 *
 *
 * Есть: Unknown_TV, Unknown_DVD, ...
 *
 * Типы устройств, как описаны в IRDB - всего 106 штук
 * 1) Projector
 * 2) Video Projector
 * 3) DVD_WINDVD
 * 4)
 *
 *
 * Статистика
 * +1) Всего Производителей  - директории первого уровня
 * 2) Поддиректории - всего: типы - остальное
 * 3) Есть ли вложенные поддиректории
 * 4) Файлы: колво - в типах устройств - остальные
 */
public class IrdbDataParser {

    private final static String Unknown = "Unknown";
    //private final static String[] TYPES = new String[] {"Projector", "Video Projector", "DVD_WINDVD", "Set Top Box"};
    private final static String[] TYPES = new String[] {"Air Conditioner", "Amplifier", "Blu-Ray", "CD Changer",
            "CD Jukebox", "CD Player", "CD-R", "Cable Box", "Camera Switcher", "Cassette Tape", "Control System",
            "D-VHS", "DAT", "DLP Projector", "DMX", "DSP", "DSS", "DVD", "DVD Changer", "DVD Player", "DVD Receiver",
            "DVD Recorder", "DVR", "Digital Cable", "Digital Jukebox", "Digital Recorder", "Distributed Audio",
            "Drapery Controller", "Game Console", "HDMI Switcher", "HDTV Tuner", "Integrated Amplifier",
            "Karaoke", "LCD", "Laser Disc", "Lighting Controller", "Line Doubler", "MP3 Player", "Matrix Switcher",
            "Media Center", "Media PC", "Media Server", "Mini System", "Mini-Disc", "Monitor", "Multi-Zone Receiver",
            "Music Server", "PLR-IR1", "Plasma", "Pre-Amplifier", "Processor", "Projector", "Receiver",
            "Sat", "Satelite DVR", "Satellite", "Satellite Radio", "Satellite Receiver", "Scaler", "Screen",
            "Set Top Box", "Subwoofer", "Surround Processor", "Surround Receiver", "Switcher", "TV", "TiVo", "Tuner",
            "Unknown", "Unknown_006", "Unknown_101", "Unknown_AS-218", "Unknown_Audio", "Unknown_CD", "Unknown_Cable",
            "Unknown_DI4001N", "Unknown_DTV", "Unknown_DVB", "Unknown_DVB-S", "Unknown_DVB-T", "Unknown_DVD",
            "Unknown_Digital", "Unknown_MD-5410", "Unknown_PC", "Unknown_RC", "Unknown_RM-RK50", "Unknown_RM-V211T",
            "Unknown_SAT", "Unknown_SIR", "Unknown_Sirius", "Unknown_TV", "Unknown_URC-6012w", "Unknown_VCR", "Unknown_cd",
            "Unknown_dvd", "Unknown_lircd.conf", "Unknown_rc5", "Unknown_remote", "Unknown_test", "Unknown_tv", "Unknown_xsat",
            "VCR", "Video Controller", "Video Processor", "Video Projector", "Video Scaler", "Video Switcher", "iPod", "iPod Dock",
    };

    // по вхождению
    //private final static String[] AIR_CONDITIONER = new String[] {"TEMP -", "TEMP +", "TEMP/TIME +", "TEMP/TIME -"};
    private final static String[] AIR_CONDITIONER = new String[] {"TEMP"};

    public static void main (String[] args ) {

        String irdbDirName = "/home/svj/projects/Eltex/IOT/IR/irdb/codes";
        IrdbDataParser parser = new IrdbDataParser();

        // сбор общей статистики. собираем инфу о директориях.
        // которые часто встречаются - считаем названиями типов устройств
        // подсчитываем общее кол-во файло и директорий
    //    parser.processCommonStatistic(irdbDirName);


        // сбор статистики уже только по диреткориям - типы устройств
        // - запоминаем пути до этих файлов
    //    parser.processTypeStatistic(irdbDirName);

        parser.findByFunctions(irdbDirName, AIR_CONDITIONER);

        //parser.parseIrdb(irdbDirName);
    }

    private void parseIrdb(String irdbDirName) {

        Collection<String> typeNames;
        typeNames = new ArrayList<>();
        Collections.addAll(typeNames, TYPES);

        // Открыть директорию
        File dirFile = new File(irdbDirName);

        File[] files;
        String name;
        int icType = 0;
        int icDevice = 0;

        for (File brandDir : dirFile.listFiles()) {
            files = brandDir.listFiles();
            if (files == null) {
                continue;
            }
            for (File file: files) {
                if (file.isDirectory()) {
                    // Анализируем поддиректории на предмет названий Типов устройств
                    name = file.getName();
                    if (typeNames.contains(name))  {
                        icType++;
                        // диерктория - тип устройства
                        // - берем файлы, парсим, заносим в БД прлатформы
                        File[] fList = file.listFiles();
                        if (fList == null) {
                            continue;
                        }
                        for (File f: fList) {
                            if (f.isDirectory()) {
                                System.out.println("Wrong directory = '" + f.getAbsolutePath() + "'");
                            }
                            // парсим файл
                            //System.out.println("icType = " + icType);
                            icDevice++;
                            processFile(f, brandDir.getName(), name);
                        }
                    }
                }
            }
        }

        System.out.println("icType = " + icType);       // 742
        System.out.println("icDevice = " + icDevice);   // 1701
        System.out.println("------------------ \n");
    }

    private void processFile(File file, String vendor, String deviceType) {

        //System.out.println("\n---- file = " + file.getAbsolutePath());

        // смотрим - это CSV файл ?
        String fileName = file.getName();
        String fileName2 = fileName.toLowerCase();
        try {
            if (fileName2.endsWith("csv")) {
                // парсим
                String row, str;
                BufferedReader csvReader = new BufferedReader(new FileReader(file));
                while ((row = csvReader.readLine()) != null) {
                    // KEY_POWER | NEC | 134 | 114 | 9
                    String[] data = row.split(",");
                    if (data.length != 5) {
                        System.out.println("Wrong function for file '" + file.getAbsolutePath() + "'");
                        System.out.println(" - " + DumpTools.printArray(data, " | "));
                    }

                    // сгенерить ИД
                    String id = null;

                    IrTemplateObj irObj = new IrTemplateObj(id, vendor, deviceType, data[0], data[1], data[2], data[3], data[4]);
                    /*
                    str = data[0].toLowerCase();
                    if (str.contains("sett"))  {
                        System.out.println("Setting function for file '" + file.getAbsolutePath() + "'");
                        System.out.println(DumpTools.printArray(data, " | "));

                    }
                    */
                }
                csvReader.close();
            } else {
                // нет такого
                System.out.println("processFile: Wrong CSV file = " + file.getAbsolutePath());
            }
        } catch (Exception e) {
            System.out.println("File error: " + file.getAbsolutePath());
            e.printStackTrace();
        }
    }

    private void findByFunctions(String irdbDirName, String[] airConditioner) {

        Collection<String> functionNames;
        //typeNames = Arrays.asList(TYPES);
        functionNames = new ArrayList<>();
        Collections.addAll(functionNames, airConditioner);
        System.out.println("functionNames = " + functionNames);

        // Пробегаем по всем, ищем вхождения функций

        // Открыть директорию
        File dirFile = new File(irdbDirName);

        File[] files, files2;
        int ic = 0;
        // цикл по производителя
        for (File brandDir : dirFile.listFiles()) {
            // цикл по типам устройств
            files = brandDir.listFiles();
            if (files != null) {
                for (File f: files) {
                    if (f.isDirectory()) {
                        // пробег по файлам диреткории
                        files2 = f.listFiles();
                        if (files2 != null) {
                            for (File f2: files2) {
                                if (f2.isDirectory()) {
                                    // нет такого
                                    System.out.println("findByFunctions: Wrong file = " + f2.getAbsolutePath());
                                } else {
                                    processFile(f2, functionNames);
                                }
                            }

                        }
                    } else {
                        // это файл - анализ
                        processFile(f, functionNames);
                    }
                }
            }
            ic++;
            //if (ic > 3) break;
        }

        System.out.println("ic = " + ic);   // 625
        //System.out.println("------------------ \n");
    }

    private void processFile(File file, Collection<String> functionNames) {

        //System.out.println("\n---- file = " + file.getAbsolutePath());

        // смотрим - это CSV файл ?
        String fileName = file.getName();
        String fileName2 = fileName.toLowerCase();
        try {
            if (fileName2.endsWith("csv")) {
                // парсим
                String row, str;
                BufferedReader csvReader = new BufferedReader(new FileReader(file));
                while ((row = csvReader.readLine()) != null) {
                    // KEY_POWER | NEC | 134 | 114 | 9
                    String[] data = row.split(",");
                    //System.out.println(DumpTools.printArray(data, " | "));
                    /*
                    str = data[0].trim();
                    if (str.length() > 1) {
                        for (String name : functionNames) {
                            if (str.contains(name)) {
                                System.out.println("\n------- file = " + file.getAbsolutePath());
                                System.out.println("  Has function: '" + data[0] + "' (" + name + ")");
                            }
                        }
                    }
                    */
                    /*
                    if (functionNames.contains(data[0])) {
                        System.out.println("\n------- file = " + file.getAbsolutePath());
                        System.out.println("  Has function: '" + data[0] + "'");
                    }
                    //*/

                    // NECx-f16: {D=7,S=7,F=2}, beg=0, end=67
                    //if (data[1].equals("NECx-f16") && data[2].equals("7")) {
                    if (data[2].equals("7") && data[3].equals("7") && data[4].equals("2")) {
                        System.out.println("\n------- file = " + file.getAbsolutePath());
                        System.out.println("  Has function: " + DumpTools.printArray(data, " | "));
                    }
                }
                csvReader.close();
            } else {
                // нет такого
                System.out.println("processFile: Wrong file = " + file.getAbsolutePath());
            }
        } catch (Exception e) {
            System.out.println("File error: " + file.getAbsolutePath());
            e.printStackTrace();
        }
    }


    private void processTypeStatistic(String irdbDirName) {
        Map<String, StatisticInfo> typesInfo = new TreeMap<>();
        Collection<String> typeNames;
        //typeNames = Arrays.asList(TYPES);
        typeNames = new ArrayList<>();
        Collections.addAll(typeNames, TYPES);
        System.out.println("typeNames = " + typeNames);


        // Открыть директорию
        File dirFile = new File(irdbDirName);

        int ic = 0;
        // цикл по производителя
        for (File brandDir : dirFile.listFiles()) {
            createTypeStatistic(brandDir, typesInfo, typeNames);
        }

        System.out.println("ic = " + ic);   // 625
        //System.out.println("------------------ \n");

        int typeCount = 0;
        int fileCounts = 0;
        for (Map.Entry<String, StatisticInfo> entry: typesInfo.entrySet()) {
                typeCount++;
                System.out.println(typeCount + ") " + entry.getKey() + " : " + entry.getValue());
            fileCounts = fileCounts + entry.getValue().getFileCount();
        }
        System.out.println("typeCount = " + typeCount);   // 109
        System.out.println("fileCounts = " + fileCounts);   // 
    }

    private void processCommonStatistic(String irdbDirName) {
        Map<String, Integer> typesName = new TreeMap<>();
        // Открыть директорию
        File dirFile = new File(irdbDirName);

        int ic = 0;
        StatisticObj stat = new StatisticObj();
        for (File brandDir : dirFile.listFiles()) {
            ic++;

            // Анализируем поддиректории на предмет названий Типов устройств
            // - скидываем имена в мап. выводим имена со счетчиком больше 1
            // Сбор статистики
            stat.addTotalBrand();
            createStatistic(stat, brandDir, typesName);
        }

        System.out.println("ic = " + ic);   // 625
        System.out.println("stat = " + stat);
        System.out.println("------------------ \n");

        StringBuilder sb = new StringBuilder(512);
        int typeCount = 0;
        int k = 0;
        for (Map.Entry<String, Integer> entry: typesName.entrySet()) {
            if (entry.getValue() > 1) {
                typeCount++;
                System.out.println(" - " + typeCount + ") " + entry.getKey() + " : " + entry.getValue());
                k = k + entry.getValue();
                /*
                // создать строковый массив, который бы легко перенесся в код как массив строк
                sb.append("\"");
                sb.append(entry.getKey());
                sb.append("\", ");
                */
            }
        }
        System.out.println("typeCount = " + typeCount);   // 109
        System.out.println("typeCount all = " + k);   // 742
        System.out.println("sb = " + sb);
    }

    private void createStatistic(StatisticObj stat, File brandDir, Map<String, Integer> typesName) {

        File[] files = brandDir.listFiles();
        if (files == null) return;

        for (File file : files) {
            if (file.isDirectory()) {
                Integer count;
                String fileName = file.getName();
                // Отбрасываем имена, начинающиеся на  Unknown
            //    if (! fileName.startsWith(Unknown)) {

                    // Это может быть типом устройств - занести в мап
                    count = typesName.get(fileName);
                    if (count == null) count = new Integer(0);
                    count++;
                    typesName.put(fileName, count);
            //    }
                // Подсчет поддиректорий
                stat.addSubdirTotal();
                // Анализ поддиректорий
                checkSubSubDir(stat, file);
            } else {
                stat.addTotalFiles();
            }
        }
    }

    private void createTypeStatistic(File brandDir, Map<String, StatisticInfo> typesInfo,
                                     Collection<String> typeNames) {

        File[] files = brandDir.listFiles();
        if (files == null) return;

        for (File file : files) {
            if (file.isDirectory()) {
                // возможно это имя типа
                String typeName = file.getName();
                if (typeNames.contains(typeName)) {
                    StatisticInfo info = typesInfo.get(typeName);
                    if (info == null) {
                        info = new StatisticInfo(typeName);
                        typesInfo.put(typeName, info);
                    }

                    info.addCount();
                    info.addUseFiles(brandDir.getName());

                    File[] fileList = file.listFiles();
                    if (fileList != null) {
                        info.addFileCount(fileList.length);
                    }
                }
            }
        }
    }

    private void checkSubSubDir(StatisticObj stat, File fileCheck) {
        File[] files = fileCheck.listFiles();
        if (files == null) return;

        for (File file : files) {
            if (file.isDirectory()) {
                // Подсчет поддиректорий
                stat.addSubSubDir();
                stat.addSubDirPath(file.getAbsolutePath());
                // Анализ поддиректорий
                checkSub3Dir(stat, file);
            } else {
                stat.addTotalFiles();
            }
        }
    }

    private void checkSub3Dir(StatisticObj stat, File fileCheck) {

        File[] files = fileCheck.listFiles();
        if (files == null) return;

        for (File file : files) {
            if (file.isDirectory()) {
                stat.addSub3DirPath(file.getAbsolutePath());
            } else {
                stat.addTotalFiles();
            }
        }
    }


}
