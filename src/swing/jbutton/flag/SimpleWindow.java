package swing.jbutton.flag;


import javax.swing.*;
import javax.swing.border.TitledBorder;


/**
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 29.07.2010 9:53:34
 */
public class SimpleWindow extends JFrame
{
    public SimpleWindow ()
    {
        super ( "Пример с кнопками выбора, флажками и переключателями" );
        setDefaultCloseOperation ( EXIT_ON_CLOSE );
        ImageIcon icon = new ImageIcon ( "1.gif" );

        // будем использовать один значок на все случаи
        Box mainBox = Box.createVerticalBox ();
        Box box1 = Box.createVerticalBox ();
        JToggleButton tButton1 = new JToggleButton ( "Кнопка выбора 1" );
        JToggleButton tButton2 = new JToggleButton ( "Кнопка выбора 2", icon );
        ButtonGroup bg = new ButtonGroup ();

        // создаем группу взаимного исключения
        bg.add ( tButton1 );
        bg.add ( tButton2 );

        // сделали кнопки tButton1 и tButton2 взаимоисключающими
        box1.add ( tButton1 );
        box1.add ( tButton2 );

        // добавили кнопки tButton1 и tButton2 на панель box1
        box1.setBorder ( new TitledBorder ( "Кнопки выбора" ) );
        Box box2 = Box.createVerticalBox ();
        JCheckBox check1 = new JCheckBox ( "Флажок 1" );
        JCheckBox check2 = new JCheckBox ( "Флажок 2", icon );
        check2.setSelectedIcon(new ImageIcon("2.gif"));
        box2.add ( check1 );
        box2.add ( check2 );

        // добавили флажки на панель box2
        box2.setBorder ( new TitledBorder ( "Флажки" ) );
        Box box3 = Box.createVerticalBox ();
        JRadioButton rButton1 = new JRadioButton ( "Переключатель 1" );
        JRadioButton rButton2 = new JRadioButton ( "Переключатель 2", icon );
        rButton2.setSelectedIcon(new ImageIcon("2.gif"));
        bg = new ButtonGroup ();

        // создаем группу взаимного исключения
        bg.add ( rButton1 );
        bg.add ( rButton2 );

        // сделали радиокнопки взаимоисключающими
        box3.add ( rButton1 );
        box3.add ( rButton2 );

        // добавили радиокнопки на панель box3
        box3.setBorder ( new TitledBorder ( "Переключатели" ) );
        mainBox.add ( box1 );
        mainBox.add ( box2 );
        mainBox.add ( box3 );
        setContentPane ( mainBox );
        pack ();
        setVisible ( true );
    }

    public static void main ( String[] args )
    {
        new SimpleWindow ();
    }

}
