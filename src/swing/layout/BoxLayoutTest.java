package swing.layout;


/**
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 22.06.2011 17:25:48
 */
import javax.swing.*;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import java.awt.*;

/**
 * BoxLayoutTest
 *
 * Можете раскомментировать строчки с выставлением alignmentY и посмотреть, как при этом изменится раскладка.

Собственно, про BoxLayout сказать больше нечего. Самый наглядный пример его применения – строка меню (javax.swing.JMenuBar).
 * @author Eugene Matyushkin
 */
public class BoxLayoutTest extends JFrame
{

    public BoxLayoutTest()
    {
        super("BoxLayout");

        final JPanel content = new JPanel();
        content.setBorder(BorderFactory.createLineBorder(Color.red));
        content.setLayout(new BoxLayout(content, BoxLayout.LINE_AXIS));

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
            public void stateChanged(ChangeEvent e){
                content.setComponentOrientation( chkOrientation.isSelected() ?
                                                 ComponentOrientation.RIGHT_TO_LEFT :
                                                 ComponentOrientation.LEFT_TO_RIGHT);
                content.doLayout();
            }
        });
        getContentPane().add(content, BorderLayout.CENTER);
        getContentPane().add(chkOrientation, BorderLayout.SOUTH);
        setSize(410,220);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public static void main(String[] args){
        JFrame.setDefaultLookAndFeelDecorated(true);
        new BoxLayoutTest().setVisible(true);
    }
}
