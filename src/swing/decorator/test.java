package swing.decorator;


import java.awt.event.*;
import javax.swing.*;
import java.awt.*;

/**
 * Декоратор
 * Этот паттерн широко используется в Java и C#. Например, любая JPanel (Panel) содержит ссылки на свои компоненты (которые в свою очередь сами держат ссылки на их компоненты). Поскольку компоненты наследуют от Component (Control) то и интерфейс у них один.
Композит позволяет упростить работу с похожими объектами.
[doHTML]<div class='maintitle'><img src='style_images/1/nav_m.gif' border='0' alt='&gt;' title='&gt;' width='8' height='8' />&nbsp;<b>Decorator</b></div>[/doHTML]
Декоратор похож на Адаптер в том смысле, что он преобразует интерфейс исходного класса. Однако, как и в случае с многими паттернами, цель Декоратора другая.
Его задача - изменить интерфейс нескольких классов. Адаптер удобно использовать, делая его сабклассом нужного класса. А если нужно 20 классов "привести" к одному интерфейсу? Нужно создавать 20 сабклассов.
Другой момент - Декоратор часто используется в ГУИ.
Суть паттерна в том, что создается класс, содержащий поле; класс этого поля является суперклассом (или интерфейсом) для тех классов, которые нам нужно "продекорировать".

[doHTML]<div class='postlinksbar'><img src='style_images/1/nav_m.gif' border='0' alt='&gt;' title='&gt;' width='8' height='8' />&nbsp;<b>Пример</b></div>[/doHTML]
Java
Данный пример декорирует JPanel - когда указатель мыши находится над панелью, она обводится красной рамкой.
 В Декоратор мы помещаем не JPanel а JComponent; поэтому его можно использовать для декорации не только панели.
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 08.07.2010 11:49:44
 */
public class test extends JFrame
{
    test()
    {
        setSize(300, 300);
        JPanel p = new JPanel();
        JPanel p1 = new JPanel();
        p1.add(new JLabel(" * "));
        p.add(new SuperDecorator(p1));
        getContentPane().add(p, BorderLayout.CENTER);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }
    public static void main(String[] args)
    {
        new test();
    }
}
class Decorator extends JComponent
{
    public Decorator(JComponent c)
    {
        setLayout(new BorderLayout());
        add("Center", c);
    }
}
class SuperDecorator extends Decorator
{
    private boolean isMouseOver;
    private JComponent c;

    public SuperDecorator(JComponent com)
    {
        super(com);
        c = com;
        MyAdapter ma = new MyAdapter();
        c.addMouseListener(ma);
    }

    class MyAdapter extends MouseAdapter
    {
        public void mouseEntered(MouseEvent me)
        {
            isMouseOver = true;
            repaint();
        }
        public void mouseExited(MouseEvent me)
        {
            isMouseOver = false;
            repaint();
        }
    }

    public void paint(Graphics g)
    {
        super.paint(g);
        if (isMouseOver)
        {
            Dimension size = super.getSize();
            g.setColor(Color.RED);
            g.drawRect(0, 0, size.width-1, size.height-1);
        }
    }
}
