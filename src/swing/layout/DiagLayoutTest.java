package swing.layout;

import java.awt.*;
import javax.swing.*;

/**
 * Свой собственный Layout. Располагает по диагонали.
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 08.07.2010 11:15:18
 */
class DiagLayoutTest extends JFrame
{
    public DiagLayoutTest()
    {
        getContentPane().setLayout(new DiagLayout());
        for(int k=0; k<5; k++) {
          getContentPane().add(new JButton(""+k));
        }
        for(int k=0; k<5; k++) {
          getContentPane().add(new JLabel(""+k, JLabel.CENTER));
        }
        setBounds(100,100,600,400);
    }
    public static void main(String[] args)
    {
        DiagLayoutTest flt = new DiagLayoutTest();
        flt.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        flt.setVisible(true);
    }
}
// Вот наш собственный layout
class DiagLayout implements LayoutManager
{
    // Эти методы нас не интересуют и их можно сделать пустыми
    public void addLayoutComponent(String name, Component comp)
    {}
    public void removeLayoutComponent(Component comp)
    {}
    public Dimension minimumLayoutSize(Container parent)
    {
        return computeLayoutSize(parent);
    }
    public Dimension preferredLayoutSize(Container parent)
    {
        return computeLayoutSize(parent);
    }
    private Dimension computeLayoutSize(Container parent)
    {
        int prefWidth = 0;
        int prefHeight = 0;
        Component[] components = parent.getComponents();
        for(int k=0; k<components.length; k++) {
            prefWidth += components[k].getWidth();
            prefHeight += components[k].getHeight();
        }
        return new Dimension(prefWidth, prefHeight);
    }
    // Вот наш фактически самый главный метод. Здесь мы располагаем компоненты
    // по диагонали
    public void layoutContainer(Container parent)
    {
        // Получаем список компонентов
        Component[] components = parent.getComponents();
        int row = 0;
        int col = 0;
        // Эти две строки можно закрыть комментариями (см. замечание ниже)
        int width = parent.getWidth()/components.length;
        int height = parent.getHeight()/components.length;
        for(int k=0; k<components.length; k++) {
            // Вы можете снять комментарии здесь и поставить их двумя строками
            // выше и увидите разницу
            //int width = (int)(components[k].getPreferredSize().getWidth());
            //int height = (int)(components[k].getPreferredSize().getHeight());
            // Определяем местоположение компонента и его размеры
            Rectangle r = new Rectangle(col, row, width, height);
            // Устанавливаем его
            components[k].setBounds(r);
            // Заготавливаем координаты следующего компонента
            col += width;
            row += height;
        }
    }
}
