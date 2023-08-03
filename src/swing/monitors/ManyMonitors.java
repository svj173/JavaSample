package swing.monitors;


/**
 * Работа с несколькими мониторами.
 * <BR/> Задача - определить, в каком мониторе находится отображаемая панель, его разрешение,
 * и исходя из разрешения перетсорить компоненты - на маленьком экране, уменьшить текстовые поля.
 * <BR/>
 *
 Поддержка нескольких мониторов и Java
 вторник, 25 января 2011 г.

Некоторое время назад была у меня задача развернуть форму на весь экран на дополнительном мониторе. Все достаточно просто, но как же я тогда ее решил. Я сделал следующим образом:
        // Определим форму на монитор
        setLocation(x, y);
        addWindowListener(new WindowAdapter() {

                @Override
                public void windowOpened(WindowEvent e) {
                    setExtendedState(JFrame.MAXIMIZED_BOTH);
                }
        });
Этот код можно поместить в конструктор формы и та распахнется на весь экран. Нюанс в том что тут x и y это координаты точки,
которая попадает на требуемый монитор, на котором и требуется развернуть эту самую форму.
Всё бы хорошо, но такая настройка не особо интуитивно понятная для большинства администраторов.
Все же хочется как-то работать именно с мониторами а не с их пикселями.
И вот недавно попалась на глаза интересная информация.
Можно работать с графическими устройствами получая все необходимые параметры.
Используем для этого библиотеку AWT. В качестве демонстрации приведу код.

        import java.awt.GraphicsDevice;
        import java.awt.GraphicsEnvironment;
        ...
        GraphicsDevice[] screenDevices = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices();
        for (GraphicsDevice graphicsDevice : screenDevices)
        {
            System.out.println("graphicsDevice = " + graphicsDevice.getIDstring() + " " + graphicsDevice.toString()
                    + "\nРазрешение экрана " + graphicsDevice.getDefaultConfiguration().getBounds().height + "x" + graphicsDevice.getDefaultConfiguration().getBounds().width
                    + "\nГлубина цвета " + graphicsDevice.getDisplayMode().getBitDepth()
                    + "\nЧастота " + graphicsDevice.getDisplayMode().getRefreshRate()
                    + "\nНачало координат " + graphicsDevice.getDefaultConfiguration().getBounds().x
                    + "-" + graphicsDevice.getDefaultConfiguration().getBounds().y);
        }

далее возможно развернуть форму на весь выбранный монитор таким способом

         graphicsDevice.setFullScreenWindow(form);

Есть конечно нюансы при использовании такого кода в Win и в Linux. К примеру в Win Vista с одним монитором будет

    graphicsDevice = \Display0 D3DGraphicsDevice[screen=0]
    Разрешение экрана 1080x1920
    Глубина цвета 32
    Частота 60
    Начало координат 0-0

в Ubuntu10.04 с двумя мониторами будет так

    graphicsDevice = :0.0 X11GraphicsDevice[screen=0]
    Разрешение экрана 1080x1920
    Глубина цвета-1
    Частота 0
    Начало координат 1680-0
    graphicsDevice = :0.1 X11GraphicsDevice[screen=1]
    Разрешение экрана 1050x1680
    Глубина цвета -1
    Частота 0
    Начало координат 0-0

Что там случилось с параметрами я разбираться не стал. Ясно одно, определение мониторов, их разрешения и начала координат вполне нормально определяются.
А значит это можно использовать для вывода формы на требуемый монитор и распахнуть форму на весь экран.
При этом настройки будут вполне понятны как админам так и простым пользователям вашей программы.


А как получить разрешение экрана без учёта панели инструментов?

GraphicsDevice graphicsDevice = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
int wi = graphicsDevice.getDisplayMode().getWidth();
int he = graphicsDevice.getDisplayMode().getHeight();


 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 19.08.2014 11:09
 */
public class ManyMonitors
{
}
