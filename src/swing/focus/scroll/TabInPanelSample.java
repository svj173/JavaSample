package swing.focus.scroll;


import javax.swing.*;
import java.awt.*;


/**
 * Пример использвоания фокуса. Усложнение для  TabSample - текстовые поля вложены в отдельные панели.
 * <BR/> Есть: панель с вертикально-расположенными панелями, имеющими текстовые поля. Все в панель не входят, следовательно - скроллинг.
 * <BR/> Задача: перемещаясь по текстовым полям сверху вниз нажатием клавиши Таб, добитсья того, что при уходе в невидимую
 * зону (вниз) скрол-панель сама бы прокручивалась с поднятием вверх.
 * <BR/>
 * <BR/> Решение - вешаем на каждый компонент фокус-листенер, который и дергает панель.
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 28.06.2011 9:05:07
 */
public class TabInPanelSample extends JFrame
{
    public TabInPanelSample ()
    {
        super("TabSample");

        JTextField tf;
        TabFocusAdapter focusAdapter;

        JPanel content, panel;

        content         = new JPanel();
        focusAdapter    = new TabFocusAdapter ( content );

        content.setBorder(BorderFactory.createLineBorder( Color.red));
        content.setLayout(new BoxLayout(content, BoxLayout.PAGE_AXIS));

        for ( int i=0; i<60; i++ )
        {
            panel   = new JPanel();
            focusAdapter    = new TabFocusAdapter ( panel );
            tf = new JTextField("text_"+i,15);
            tf.addFocusListener ( focusAdapter );
            panel.add ( tf );
            content.add(panel);
        }

        getContentPane().add( new JScrollPane ( content ), BorderLayout.CENTER);

        /*
        JLabel lbl = new JLabel("Label");
        lbl.setBorder(BorderFactory.createLineBorder(Color.blue));
        lbl.setFont(lbl.getFont().deriveFont(30f));
        //lbl.setAlignmentY(1);

        JButton btn = new JButton("Button");
        //btn.setAlignmentY(0);

        JTextField tf = new JTextField("text",5);
        tf.setMaximumSize(new Dimension(150, Integer.MAX_VALUE));
        JLabel lbl2 = new JLabel("Label 2");
        lbl2.setBorder(BorderFactory.createLineBorder(Color.green));
        lbl2.setFont(lbl2.getFont().deriveFont(20f));
        content.add(lbl);
        content.add(Box.createHorizontalStrut(5));
        content.add(btn);
        content.add(Box.createHorizontalStrut(5));
        content.add(tf);
        content.add(Box.createHorizontalGlue());
        content.add(lbl2);
        final JCheckBox chkOrientation = new JCheckBox("Right-to-left orientation");
        chkOrientation.addChangeListener(new ChangeListener(){
            public void stateChanged( ChangeEvent e){
                content.setComponentOrientation( chkOrientation.isSelected() ?
                                                 ComponentOrientation.RIGHT_TO_LEFT :
                                                 ComponentOrientation.LEFT_TO_RIGHT);
                content.doLayout();
            }
        });
        getContentPane().add(content, BorderLayout.CENTER);
        getContentPane().add(chkOrientation, BorderLayout.SOUTH);
        */
        setSize(410,220);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public static void main(String[] args){
        JFrame.setDefaultLookAndFeelDecorated(true);
        new TabInPanelSample ().setVisible(true);
    }

}