package swing.scrollStep.exm1;


import javax.swing.*;
import java.awt.*;


/**
 * пример компонента играющего роль "линейки" расположенной слева и сверху основной области прокрутки.
 * В качестве параметра конструктору класса Rule передается флаг ориентации линейки (горизонтальная или вертикальная),
 * также указываем единицы измерения (ну что с буржуев с их дюймами и ярдами возьмешь ...) 
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 26.12.2011 12:03:43
 */
public class Rule extends JComponent
{
    public static final int INCH = Toolkit.getDefaultToolkit ().getScreenResolution ();
    // получаем сведения о разрешении экрана - сколько точек на дюйм
    public static final int HORIZONTAL = 0;// константы указывающие на режим создаваемой области линейки
    public static final int VERTICAL = 1;
    public static final int SIZE = 35;// размер по умолчанию для линейки
    public int orientation;
    public boolean isMetric;
    private int increment;
    private int units;

    // при создании линейки прокрутки необходимо указать ее ориентацию и используемые единицы измерения
    // будут ли использованы дюймы или сантиметры

    public Rule ( int o, boolean m )
    {
        orientation = o; // ориентация линейки - горизонтальная или вертикальная
        isMetric = m;// единицы измерения
        setIncrementAndUnits ();// метод изменения размеров штриха, растояния между ними
    }

    // установить режим метрической системы

    public void setIsMetric ( boolean isMetric )
    {
        this.isMetric = isMetric;
        setIncrementAndUnits ();
        repaint ();
    }

    // функция выполняющая расчет величины приращения линейки прокрутки на основе текущих единиц измерения

    private void setIncrementAndUnits ()
    {
        if ( isMetric )
        {
            // получаем сколько точек должно прийтись на один сантиметр
            units = ( int ) ( ( double ) INCH / ( double ) 2.54 );
            increment = units;
        }
        else
        {
            units = INCH;
            increment = units / 2;
        }
    }

    public boolean isMetric ()
    {
        return this.isMetric;
    }

    public int getIncrement ()
    {
        return increment;
    }
    // установка размеров линейки - линейка будет занимать размер
    // равный одному дюйму

    // все методы изменения размеров линейки сводятся к перевызову базового для всех компонентов метода setPreferedSize

    public void setPreferredHeight ( int ph )
    {
        setPreferredSize ( new Dimension ( SIZE, ph ) );
    }

    public void setPreferredWidth ( int pw )
    {
        setPreferredSize ( new Dimension ( pw, SIZE ) );
    }

    protected void paintComponent ( Graphics g )
    {
        // Метод, который выполняет рисование линейки для области прокрутки.
        // Обратите внимание на то, что здесь перекрыт не вызов paint, а, именно, paintComponent.
        // Дело в том, что paint выполняет рисование в следующей последовательности:
        // фон, САМ элемент, все дочерние элементы.
        // А метод paintComponent выполняет рисование именно САМОГО компонента.
        Rectangle drawHere = g.getClipBounds ();
        g.setColor ( new Color ( 230, 253, 4 ) );
        g.fillRect ( drawHere.x, drawHere.y, drawHere.width, drawHere.height );
        // первым шагом всю область линейки закрасили фоновым цветом
        g.setFont ( new Font ( "SansSerif", Font.PLAIN, 10 ) );
        g.setColor ( Color.black );
        int end = 0;
        int start = 0;
        int tickLength = 0;
        String text = null;
        if ( orientation == HORIZONTAL )
        {
            start = ( drawHere.x / increment ) * increment;
            end = ( ( ( drawHere.x + drawHere.width ) / increment ) + 1 )
                    * increment;
        }
        else
        {
            start = ( drawHere.y / increment ) * increment;
            end = ( ( ( drawHere.y + drawHere.height ) / increment ) + 1 )
                    * increment;
        }
        if ( start == 0 )
        {
            text = Integer.toString ( 0 ) + ( isMetric ? " cm" : " in" );
            tickLength = 10;
            if ( orientation == HORIZONTAL )
            {
                g.drawLine ( 0, SIZE - 1, 0, SIZE - tickLength - 1 );
                g.drawString ( text, 2, 21 );
            }
            else
            {
                g.drawLine ( SIZE - 1, 0, SIZE - tickLength - 1, 0 );
                g.drawString ( text, 9, 10 );
            }
            text = null;
            start = increment;
        }
        for ( int i = start; i < end; i += increment )
        {
            if ( i % units == 0 )
            {
                tickLength = 10;
                text = Integer.toString ( i / units );
            }
            else
            {
                tickLength = 7;
                text = null;
            }
            if ( tickLength != 0 )
            {
                if ( orientation == HORIZONTAL )
                {
                    g.drawLine ( i, SIZE - 1, i, SIZE - tickLength - 1 );
                    if ( text != null )
                        g.drawString ( text, i - 3, 21 );
                }
                else
                {
                    g.drawLine ( SIZE - 1, i, SIZE - tickLength - 1, i );
                    if ( text != null )
                        g.drawString ( text, 9, i + 3 );
                }
            }
        }
    }
}