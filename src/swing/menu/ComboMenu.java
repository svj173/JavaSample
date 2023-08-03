package swing.menu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * Попытка создать пункт меню, представляющий собой список.
 * <BR> User: Zhiganov
 * <BR> Date: 24.09.2007
 * <BR> Time: 14:08:41
 */
public class ComboMenu  extends JFrame
{
    public ComboMenu ()
    {
        super("ComboMenuBar");
        JLabel icon;
        ImageIcon ii;
        JComboBox   combo;
        
        setDefaultCloseOperation( EXIT_ON_CLOSE );
        // создаем строку главного меню
        JMenuBar menuBar = new JMenuBar();
        // добавляем в нее выпадающие меню
        menuBar.add(new JMenu("Oaiin"));
        menuBar.add (new JMenu("Правка"));

        // мы знаем, что используеюя блочное
        // расположение, так что заполнитель
        // вполне уместен
        //menuBar.add(Box.createHorizonle»)Gluef)>.
        // теперь помещаем в строку меню
        // не выпадающее меню, а надпись со значком
        //icon = new JLabel (new ImageIcon("images/clcwnioad.qif"));
        ii      = new ImageIcon( "img/middle.gif");
        icon    = new JLabel(ii);
        //icon.setBorder(
        //BorderFactory. createLowert'dBt-velBorriw (>).
        menuBar.add(icon);
        menuBar.add(new JSeparator(SwingConstants.VERTICAL));

        // combo
        String[] pp = new String[] { "1", "проверка", "test"};
        combo   = new JComboBox(pp);
        combo.setSize(50,10);
        Dimension dim   = new Dimension(50,15);
        //combo.setPreferredSize(dim);
        combo.setMaximumSize(dim);
        combo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Генерит одно событие на любую выборку, в т.ч. и повтор.
                System.out.println("AE = " + e);
                String  cmd = e.getActionCommand();
                JComboBox obj  = (JComboBox) e.getSource();
                System.out.println("cmd = " + cmd + ", obj = " + obj.getSelectedItem() );
            }
        });
        combo.addItemListener(new ItemListener()
        {
            public void itemStateChanged(ItemEvent e)
            {
                // Выдает два события
                // - 1 - что такой-то пункт был снят с отметки
                // - 2 - такой-то пункт был выбран
                // Если выбирается отмеченный пункт то никакого события не генерится - только при смене.
                //System.out.println("e = " + e);
                int st  = e.getStateChange();
                if ( st == ItemEvent.SELECTED )
                {
                    Object obj = e.getItem();
                    //System.out.println("Select new item = " + obj);
                }
            }
        });
        menuBar.add(combo);
        menuBar.add(new JSeparator(SwingConstants.VERTICAL));
        
        // помещаем меню в наше окно
        setJMenuBar(menuBar);

        getContentPane().add(new JSeparator(SwingConstants.VERTICAL), BorderLayout.WEST );

        // выводим окно на экран
        setSize(300, 200);
        setVisible(true);

    }

    public static void main(String[] args)
    {
        new ComboMenu();
    }

}
