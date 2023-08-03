package swing.layout.my.DiagLayout;


import javax.swing.*;


/**
 * вариант создания нестандартного layout. Любой layout должен просто реализовать необходимые методы. Вот они:
-    public void addLayoutComponent(String name, Component comp)
-    public void removeLayoutComponent(Component comp)
-    public Dimension minimumLayoutSize(Container parent)
-    public Dimension preferredLayoutSize(Container parent)
-    public void layoutContainer(Container parent)

Наиболее важным является последний метод. Он как раз и занимается тем, что «расставляет» все элементы в том порядке, в котором нам надо.
Я написал в качестве примера простой layout, который располагает свои элементы вдоль диагонали.
 
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 29.12.2011 9:52:46
 */
public class DiagLayoutTest extends JFrame
{
    public DiagLayoutTest ()
    {
        getContentPane().setLayout ( new DiagLayout() );

        for ( int k = 0; k < 5; k++ )
        {
            getContentPane().add ( new JButton ( "" + k ) );
        }

        for ( int k = 0; k < 5; k++ )
        {
            getContentPane().add ( new JLabel ( "" + k, JLabel.CENTER ) );
        }

        setBounds ( 100, 100, 600, 400 );
    }

    public static void main ( String[] args )
    {
        DiagLayoutTest flt = new DiagLayoutTest();
        flt.setDefaultCloseOperation ( JFrame.EXIT_ON_CLOSE );
        flt.setVisible ( true );
    }

}
