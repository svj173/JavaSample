package svj.file.irdb;

import java.io.File;
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

    public static void main (String[] args ) {

        String irdbDirName = "/home/svj/projects/Eltex/IOT/IR/irdb/codes";
        IrdbDataParser parser = new IrdbDataParser();

        // сбор общей статистики. собираем инфу о директориях.
        // которые чатсо встречаются - считаем названиями типов устройств
        // подсчитываем общее кол-во файло и директорий
    //    parser.processCommonStatistic(irdbDirName);


        // сбор статистики уже только по диреткориям - типы устройств
        // - запоминаем пути до этих файлов
        parser.processTypeStatistic(irdbDirName);
    }

    public IrdbDataParser() {
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
