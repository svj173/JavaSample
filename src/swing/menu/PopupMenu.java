package swing.menu;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Пустая панель на которой можно щелкать правой кнопкой мыши.
 * <BR> User: Zhiganov
 * <BR> Date: 24.09.2007
 * <BR> Time: 17:42:08
 */
public class PopupMenu extends JFrame
{
    private JPopupMenu popup;

    public PopupMenu() 
    {
        super("PopupMenus");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        // получаем всплывающее меню
        popup = createPopupMenu();
        // и привязываем к нашей панели содержимого
        // слушателя событий от мыши
        addMouseListener(new ML());
        // выводим окно на экран
        setSize(200, 200);
        setVisible(true);
    }

    // создаем наше всплывающее меню
    private JPopupMenu createPopupMenu() {
        // создаем собственно всплывающее меню
        JPopupMenu pm = new JPopupMenu();
        // создаем элементы всплывающего меню
        JMenuItem good = new JMenuItem("Отлично");
        JMenuItem excellent = new JMenuItem("Замечательно");
        // и добавляем все тем же методом add()
        pm.add(good);
        pm.add(excellent);
        return pm;
    }

    // этот класс будет отслеживать щелчки мыши
    class ML extends MouseAdapter {
        public void mouseClicked(MouseEvent me) {
            // проверяем, что это правая кнопка.
            // и показываем наше всплывающее меню
            if (SwingUtilities.isRightMouseButton(me)) {
                popup.show(getContentPane(), me.getX(), me.getY());
            }
        }
    }

    public static void main(String[] args) {
        new PopupMenu();
    }

}
