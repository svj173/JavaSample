package swing.table.popup.example2;



import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

/**
 * Пример использования контекстного меню в таблице.
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 19.10.2010 12:00:31
 */
public class MyWidgetTablePanel extends JPanel
{
    BorderLayout borderLayout1 = new BorderLayout ();
    JScrollPane MyWidgetTableScrollPane = new JScrollPane ();
    MyWidgetController controller;
    MyWidgetTableModel myWidgetTableModel;
    JPopupMenu popupMenu = new JPopupMenu ();

    private static String INSERT_CMD = "Insert Rows";
    private static String DELETE_CMD = "Delete Rows";

    JTable MyWidgetTable = new JTable ();

    public MyWidgetTablePanel ( MyWidgetController controller,
                                MyWidgetTableModel myWidgetTableModel )
    {
        this.controller = controller;
        this.myWidgetTableModel = myWidgetTableModel;
        MyWidgetTable.setModel ( myWidgetTableModel );

        this.setLayout ( borderLayout1 );
        this.add ( MyWidgetTableScrollPane, BorderLayout.CENTER );
        MyWidgetTableScrollPane.getViewport ().add ( MyWidgetTable, null );

        JMenuItem menuItem = new JMenuItem ( INSERT_CMD );
        menuItem.addActionListener ( new InsertRowsActionAdapter ( this ) );
        popupMenu.add ( menuItem );

        MouseListener popupListener = new PopupListener ();
        MyWidgetTable.addMouseListener ( popupListener );
    }

    public void insertRowsActionPerformed ( ActionEvent e )
    {

    }

    class PopupListener extends MouseAdapter
    {
        public void mousePressed ( MouseEvent e )
        {
            showPopup ( e );
        }

        public void mouseReleased ( MouseEvent e )
        {
            showPopup ( e );
        }

        private void showPopup ( MouseEvent e )
        {
            if ( e.isPopupTrigger () )
            {
                popupMenu.show ( e.getComponent (), e.getX (), e.getY () );
            }
        }
    }

    void insertRowsctionPerformed ( ActionEvent e )
    {
        int row = MyWidgetTable.getSelectedRow ();
        controller.doInsertPagesAction ( row );
    }

} // end of class


class InsertRowsActionAdapter implements ActionListener
{
    MyWidgetTablePanel adaptee;

    InsertRowsActionAdapter ( MyWidgetTablePanel adaptee )
    {
        this.adaptee = adaptee;
    }

    public void actionPerformed ( ActionEvent e )
    {
        adaptee.insertRowsActionPerformed ( e );
    }
}
