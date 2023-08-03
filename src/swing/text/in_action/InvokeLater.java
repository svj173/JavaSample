package swing.text.in_action;


/**
 * Пример как в одной акции можно несколько раз менять содержимое компонент
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 19.07.2010 13:19:22
 */
// InvokeLater.java  II Метод invokeLaterO и работа с потоком рассылки событий

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class InvokeLater extends JFrame
{
    private JButton button;

    public InvokeLater ()
    {
        super ( "InvokeLater" );

        // при закрытии окна - выход
        setDefaultCloseOperation ( JFrame.EXIT_ON_CLOSE );
        // добавим кнопку со слушателем

        button = new JButton ( "Выполнить сложную работу" );
        button.addActionListener ( new ActionListener()
        {
            public void actionPerformed ( ActionEvent e )
            {
                // запустим отдельный поток
                new ComplexJobThread().start();
                button.setText ( "Подождите..." );
            }
        } );

        // настроим панель содержимого и выведем окно на экран
        getContentPane().setLayout ( new FlowLayout() );
        getContentPane().add ( new JTextField(20) );
        getContentPane().add ( button );
        
        setSize ( 400, 200 );
        setVisible ( true );
    }

    // поток, выполняющий "сложную работу"

    class ComplexJobThread extends Thread
    {
        public void run ()
        {
            try
            {
                // изобразим задержку
                sleep ( 2000 );
                // работа закончена, нужно изменить интерфейс
                EventQueue.invokeLater ( new Runnable()
                {
                    public void run ()
                    {
                        button.setText ( "Работа - шаг 1" );
                    }
                } );
                // изобразим задержку
                sleep ( 3000 );
                // работа закончена, нужно изменить интерфейс
                EventQueue.invokeLater ( new Runnable()
                {
                    public void run ()
                    {
                        button.setText ( "Работа - шаг 2" );    // не отображает
                        try
                        {
                            sleep ( 2000 );
                        } catch ( InterruptedException e )                        {
                            e.printStackTrace ();
                        }
                        button.setText ( "Работа завершена" );
                    }
                } );
            } catch ( Exception ex )            {
                ex.printStackTrace ();
            }
        }
    }

    public static void main ( String[] args )
    {
        new InvokeLater ();
    }

}
