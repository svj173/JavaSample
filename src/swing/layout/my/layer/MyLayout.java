package swing.layout.my.layer;


import java.awt.*;


/**
 * Возникло желание написать свой менеджер разметки и в дополнение к необходимому функционалу(собственно размещение объектов) захотелось добавить дополнительный функционал в виде визуализации элементов ориентации: линий, окружностей, да чего угодно, относительно чего строится размещение компонент.
 * <p/>
 * Вопрос собственно в реализации:
 * <p/>
 * 1) о линиях знает только менеджер разметки,
 * 2) отрисовывать линии надо на каком либо JComponent в методе paintComponent, чтобы они не исчезали.
 * 3) программист, использующий эту разметку не должен реализовывать отрисовку линий самостоятельно, а должен лишь указать инструкцию из разряда .setVisible( true )
 * <p/>
 * Использовать LayeredPane для отрисовки.
 * <p/>
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 29.12.2011 10:00:08
 */
public class MyLayout implements LayoutManager
{
    @Override
    public void addLayoutComponent ( String name, Component comp )
    {
    }

    @Override
    public void removeLayoutComponent ( Component comp )
    {
    }

    @Override
    public Dimension preferredLayoutSize ( Container parent )
    {
        return null;
    }

    @Override
    public Dimension minimumLayoutSize ( Container parent )
    {
        return null;
    }

    public void layoutContainer ( Container parent )
    {
        MyLayeredPane layeredPane = new MyLayeredPane ();
        //my.setBounds ( parent.getBounds () );
        //тут можно передать необходимую информацию об объектах отрисовки в класс  MyLayeredPane
        parent.add ( layeredPane );
    }
}
