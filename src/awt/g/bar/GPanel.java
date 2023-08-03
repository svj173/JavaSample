package awt.g.bar;


import javax.swing.*;
import java.awt.*;
import java.util.Collection;


/**
 * Рисовальщик графиков.
 * <BR/> Получает - модель данных, тип графика, кол-во пакетов данных с описанием - цвет, название; единицы измерения - по X, Y.
 * <BR/> В цикле, получая данные от модели (данные - пакетами), рисует график - расчет длины (с учетом макс допустимой - например, 10 экранов),
 * расчет сетки - вертикаль (градация по 10 и 5), горизонталь - учет отрисовки текста, чтобы не налазил друг на друга..
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 28.06.2011 9:01:26
 */
public class GPanel  extends JPanel
{
    private TableModel<Integer> model;
    //private BarType barType;
    private GBarWriter barWriter;
    private String xInfo, yInfo;           // шт., time
    private Collection<Packet> packets;

    public GPanel ( TableModel<Integer> model, BarType barType, String xInfo, String yInfo, Collection<Packet> packets )
    {
        Dimension screenSize;

        this.model = model;
        this.xInfo = xInfo;
        this.yInfo = yInfo;
        this.packets = packets;

        // вычисляем все исходные размеры
        // Берем рисовальщик графики
        barWriter   = GBarWriterFactory.create ( barType );

        // берем размер монитора
        screenSize     = Toolkit.getDefaultToolkit().getScreenSize();

        // вычисляем мин размер панели

    }

    /* Метод paintComponent() вызывается при прорисовке компонента первым, и именно он рисует сам компонент. */
    @Override
    public void paintComponent ( Graphics g )
    {
        int  step;


        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        // Rectangle2D

    }
    
}
