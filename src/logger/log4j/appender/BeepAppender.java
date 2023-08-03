package logger.log4j.appender;


/**
 * Создать новый аппендер очень легко.
 * Вообще чтобы написать Аппендер нужно написать класс реазизующий интерфейс org.apache.log4j.Appender который содержит довольно много методов.

 Специально чтобы упростить написание новых аппендеров разработчики log4j написали абстрактный класс org.apache.log4j.AppenderSkeleton .
 Этот аппендер представляет из себя "скелет" для новых аппендеров. Чтобы наростить мясо нужно реализовать всего 3 метода метод:

 abstract protected void append(LoggingEvent event);

 public boolean requiresLayout()

 public void close();

 В методе append() как раз и нужно логировать сообщение. event содержит всю необходимую информацию.
 Методе requiresLayout() возвращает falseв случае если layout не используется.
 В методе close() - нужно закрывть открытые ресурсы (если они открывались в аппендере).

 Давайте напишем свой логер который сигналит по pc-спикеру, и выводит сообщение на консоль
 *
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 29.03.2013 14:39
 */

//import org.apache.log4j.AppenderSkeleton;
//import org.apache.log4j.spi.LoggingEvent;

/**
 * Этот аппендер пикает PC-спикером и выводит на консоль сообщение.
 */
public class BeepAppender //extends AppenderSkeleton
{
    /**
     * Пикаем и выводим сообщение.
     *
     * @param event отсюда берётся сообщение.
     */
    /*
    protected void append ( LoggingEvent event )
    {
        Toolkit.getDefaultToolkit ().beep ();
        System.out.println ( event.getMessage () );
    }
    */

    /**
     * ресурсы не выделялись - закрывать ничего не надо.
     */
    public void close ()
    {

    }

    /**
     * Layout не используется.
     */
    public boolean requiresLayout ()
    {
        return false;
    }

}
