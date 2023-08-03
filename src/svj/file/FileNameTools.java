package svj.file;

import java.io.File;

/**
 * Работа с именами файлов и директорий
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 15.04.2022 13:45
 */
public class FileNameTools {

    private String PROJECT_DIR = "/home/svj/Serg/stories/SvjStory";
    private String PREFIX = null;

    public static void main ( String[] args )
    {
        String fileName;

        FileNameTools manager = new FileNameTools ();

        /*
        fileName = "/home/svj/Serg/stories/release_books/fb2/zs7.fb2";
        manager.save(fileName);

        fileName = "/home/svj/Serg/stories/sb1/fb2/zs7.fb2";
        manager.save(fileName);
        */

        fileName = "sb1/fb2/zs7.fb2";
        String r1 = manager.get(fileName);


    }

    private String get(String fileName) {
        System.out.println("\nGET: fileName: " + fileName);
        String result;
        if (fileName.startsWith("/")) {
            result = fileName;
        }
        else {
            String prefix = getPrefix(PROJECT_DIR);
            result = prefix + fileName;
        }
        System.out.println("GET: result: " + result);
        return result;
    }

    private String getPrefix(String dir)
    {
        // - выделяем префикс - на директорию выше - /home/svj/Serg/stories
        // -- сохраняем Префикс (только в памяти) и в след разы используем его - без всякого выделения.
        if (PREFIX == null) {
            // выделяем Префикс - из чего? Из сборника
            File file = new File(PROJECT_DIR);
            PREFIX = file.getParent() + "/";
            System.out.println("parent: " + PREFIX);
        }
        return PREFIX;
    }

    /*
a) занесение имени файла в ConvertParameter
- получаем имя файла ковнертации:       - /home/svj/Serg/stories/release_books/fb2/zs7.fb2
- получаем диреткорию текущего сборника - /home/svj/Serg/stories/SvjStory
- выделяем префикс - на директорию выше - /home/svj/Serg/stories  -- сохраняем Префикс (только в памяти) и в след разы используем его - без всякого выделения.
- смотрим, если имя файла начинается с этого префикса - удаляем его. --
б) получение имени файла из ConvertParameter

     */
    private void save(String fileName) {

        System.out.println("\nSAVE: fileName: " + fileName);
        System.out.println("PROJECT_DIR: " + PROJECT_DIR);
        System.out.println("PREFIX: " + PREFIX);

        String prefix = getPrefix(PROJECT_DIR);


        if (fileName.startsWith(prefix))  {
            System.out.println("-- режем имя");
            String result = fileName.substring(prefix.length());
            System.out.println("new name: '" + result + "'");
        }
    }


}
