package file.find;


   import java.io.File;
   import java.util.List;

   /**
    * Программа поиска файлов
    * Параметры поиска:
    * 1 – начальная директория (папка) поиска
    * 2 – регулярное выражение (необязательный)
    * Например:
    * java -jar FileSearchProgram.jar C:junit3.8.1
    * java -jar FileSearchProgram.jar C:junit3.8.1 .*.html
    *
    * @author Стаценко Владимир
    * http://www.vova-prog.narod.ru
    */
   public class FileFinderTest {
       /** Создает экземпляры Main */
       public FileFinderTest() {
       }
       /**
        *  args[0] начальная директория (папка) поиска
        *  args[1] регулярное выражение
        */
       public static void main(String[] args) {
           //проверяем, заданы ли параметры поиска
           if(args != null && args.length >= 1) {
               FileFinder finder = new FileFinder();
               try {
                   List searchRes = null;
                   //если задано регулярное выражение…
                   if(args.length == 2) {
                       //…ищем только соответствующие объекты
                       searchRes = finder.findAll(args[0], args[1]);
                   }
                   //если нет…
                   else {
                       //…ищем все подряд
                       searchRes = finder.findAll(args[0]);
                   }
                   //выводим результаты
                   for(int i = 0; i < searchRes.size(); i++) {
                       File curObject = (File)searchRes.get(i);
                       if(curObject.isDirectory()) {
                           System.out.println(
                                   curObject.getName() + " (папка)");
                       }
                       else {
                           System.out.println(curObject.getName()
                                   + " (" + curObject.length() + " байт)");
                       }
                   }
                   System.out.println("Найдено " + finder.getFilesNumber() +
                           " файлов и " + finder.getDirectoriesNumber() +
                           " папок.");
               }
               catch(Exception err) {
                   System.out.println(err.getMessage());
               }
           }
           else {
               System.out.println("Не заданы параметры поиска.n" +
                       "начальная_папка <регулярное_выражение>n");
           }
       }
   }


