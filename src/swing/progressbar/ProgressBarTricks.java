package swing.progressbar;


import javax.swing.*;


/**
 * Здесь изменяем GUI вид движка прогресс-линии. Делаем ее ячеистой и т.д.
 * <BR/> Свойство с названием cellLength управляет тем, какой длины будут
ячейки полосы индикатора (полоса индикатора не обязательно прорисовывается
сплошной, она может состоять из набора ячеек одинакового размера), а свойство
cellSpacing задает расстояние между ячейками (если это расстояние приравнять
нулю, то полоса будет сплошной).
 * <BR/>  Правда, стоит иметь в виду, что данные свойства действуют,
только если в индикаторе процесса не прорисовывается информационная строка.
Если строка есть, то полоса индикатора в любом случае будет сплошной.
 * <BR/>
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 28.12.2010 9:49:38
 */
public class ProgressBarTricks extends JFrame
{
    public ProgressBarTricks ()
    {
        super ( "ProgressBarTricks" );

        setDefaultCloseOperation ( EXIT_ON_CLOSE );

        // настраиваем параметры для UI-представителей
        UIManager.put ( "ProgressBar.cellSpacing", new Integer ( 2 ) );
        UIManager.put ( "ProgressBar.cellLength", new Integer ( 6 ) );

        // стандартная модель
        final DefaultBoundedRangeModel model = new DefaultBoundedRangeModel ( 0, 0, 0, 100 );

        //  создаем простой индикатор процесса на основе полученной модели
        JProgressBar progress = new JProgressBar ( model );
        // добавляем его в окно
        JPanel contents = new JPanel();
        contents.add ( progress );
        setContentPane ( contents );

        // выводим окно на экран
        setSize ( 1000, 200 );
        setVisible ( true );

        // создаем   "процесс"
        Thread process = new Thread ( new Runnable()
        {
            public void run ()
            {
                // увеличиваем текущее значение модели до достижения максимального значения
                while ( model.getValue() < model.getMaximum() )
                {
                    model.setValue ( model.getValue() + 1 );
                    try
                    {
                        Thread.currentThread().sleep ( 1000 );
                    } catch ( Exception ex )
                    {
                    }
                }
            }
        } );
        // запускаем поток
        process.start();
    }

    public static void main ( String[] args )
    {
        new ProgressBarTricks();
    }

}
