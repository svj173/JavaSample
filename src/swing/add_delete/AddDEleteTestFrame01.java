package swing.add_delete;


import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 * Динамическое добавление и удаление компонентов с JPanel и JScrollPane
 * <p/>
 * Январь 25, 2010 от bondforever Комментарии (11)
 * <p/>
 * Java Swing - Динамическое добавление и удаление компонентов Временами при написании приложений на Java Swing, требуется реализовать динамическое добавление и удаление компонентов пользовательского интерфейса – то есть добавление и удаление компонентов должно происходить во время работы приложения. В недавних комментариях в одному посту проявлялся интерес к этому вопросу. Пример такого приложения будет рассмотрен далее – это достаточно простое приложение с двумя кнопками: добавить + и удалить -. При нажатии на кнопку добавления должно происходить создание нового компонента JLabel и добавление его на панель, при удалении – последний добавленный JLabel удаляется, панель перерисовывается и больше мы его не видим. Давайте посмотрим как это сделать.
 * <p/>
 * Если немного порассуждать по поводу того, как такое должно происходить, сразу приходит на ум что-то вроде: для добавления у JPanel (или куда там еще мы собираемся добавить компонент) есть метод add для добавления, значит для удаления должен быть remove. Всё верно, всё правильно – такие методы есть и они делают именно то, что надо. Однако, есть пара хитростей, которые нужно знать, если хочется сделать (или есть не хочется, но надо) динамическое добавление и удаление.
 * <p/>
 * Посмотрим код всего примера.
 * <p/>
 * <p/>
 * Обратимся сразу к тестовому примеру и посмотрим на заветный код кнопок «Добавить» и «Удалить». Что тут собственно делается. При добавлении нового JLabel делается вот, что:
 * view source
 * print?
 * 1	int number = labels.size() + 1;
 * 2	JLabel swing.label = new JLabel("Label " + number);
 * 3	labels.add(swing.label);
 * 4	swing.label.setAlignmentX(JLabel.CENTER_ALIGNMENT);
 * 5	swing.label.setFont(font);
 * 6	labPanel.add(swing.label);
 * 7	scrollPane.revalidate();
 * <p/>
 * Для начала высчитываем индекс, который будет у нового JLabel, чтобы нумерация шла по порядку. Далее создаем новый JLabel, устанавливаем ему шрифт, выравнивание и добавляем на панель. А теперь самое интерсное – вызываем метод revalidate у JScrollPane. Если не сделать этот вызов, то ничего мы не увидим.
 * <p/>
 * Посмотрим, что происходит при удалении ранее добавленного JLabel.
 * view source
 * print?
 * 1	if(labels.size() > 0) {
 * 2	    int index = labels.size() - 1;
 * 3	    JLabel swing.label = labels.remove(index);
 * 4	    labPanel.remove(swing.label);
 * 5	    labPanel.repaint();
 * 6	    scrollPane.revalidate();
 * 7	}
 * <p/>
 * Для начала мы проверяем, есть ли JLabel’ы, которые могут быть удалены или нет. Если такой JLabel имеется, то находим его индекс (последний в списке), удаляем его с панели с помощью вызова метода remove. Затем мы должны вызвать repaint у панели JPanel, на которой располагается JLabel. Вызов repaint говорит JPanel о том, что ему необходимо перерисоваться. И наконец, самый последний вызов – это revalidate у JScrollPane, на которой располагается JPanel.
 * <p/>
 * <p/>
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 11.06.2010 16:03:06
 */
public class AddDEleteTestFrame01 extends JFrame
{
    private static List<JLabel> labels = new ArrayList<JLabel> ();

    public static void createGUI ()
    {
        JFrame frame = new JFrame ( "Test frame" );
        frame.setDefaultCloseOperation ( JFrame.EXIT_ON_CLOSE );

        final Font font = new Font ( "Verdana", Font.PLAIN, 25 );

        JPanel butPanel = new JPanel ();

        JButton addButton = new JButton ( "+" );
        addButton.setFont ( font );
        addButton.setFocusable ( false );
        butPanel.add ( addButton );

        JButton remButton = new JButton ( "-" );
        remButton.setFont ( font );
        remButton.setFocusable ( false );
        butPanel.add ( remButton );

        final JPanel labPanel = new JPanel ();
        final JScrollPane scrollPane = new JScrollPane ( labPanel );
        labPanel.setLayout ( new BoxLayout ( labPanel, BoxLayout.Y_AXIS ) );

        addButton.addActionListener ( new ActionListener()
        {
            public void actionPerformed ( ActionEvent e )
            {
                int number = labels.size () + 1;
                JLabel label = new JLabel ( "Label " + number );
                labels.add ( label );
                label.setAlignmentX ( JLabel.CENTER_ALIGNMENT );
                label.setFont ( font );
                labPanel.add ( label );
                scrollPane.revalidate ();
            }
        } );

        remButton.addActionListener ( new ActionListener()
        {
            public void actionPerformed ( ActionEvent e )
            {
                if ( labels.size () > 0 )
                {
                    int index = labels.size () - 1;
                    JLabel label = labels.remove ( index );
                    labPanel.remove ( label );
                    labPanel.repaint ();
                    scrollPane.revalidate ();
                }
            }
        } );

        frame.getContentPane ().setLayout ( new BorderLayout () );
        frame.getContentPane ().add ( butPanel, BorderLayout.NORTH );
        frame.getContentPane ().add ( scrollPane, BorderLayout.CENTER );

        frame.setPreferredSize ( new Dimension ( 250, 200 ) );
        frame.pack ();
        frame.setLocationRelativeTo ( null );
        frame.setVisible ( true );
    }

    public static void main ( String[] args )
    {
        javax.swing.SwingUtilities.invokeLater ( new Runnable()
        {
            public void run ()
            {
                JFrame.setDefaultLookAndFeelDecorated ( true );
                createGUI ();
            }
        } );
    }
}
