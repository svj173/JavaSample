package swing.table.multiLineCell.pr02;


import javax.swing.*;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.EventListenerList;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.util.EventObject;

/**
 * Редактор текста в ячейке. Для больших текстов - в JTextArea
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 20.01.2012 17:01:27
 */
public class LocationTableCellEditor implements TableCellRenderer, TableCellEditor
{
    private JPanel panel = new JPanel ( new GridLayout ( 1, 1, 2, 1 ) );
    private EventListenerList listenerList = new EventListenerList ();
    private ChangeEvent event = new ChangeEvent ( this );

    private JTextArea ta;
    private JScrollPane scrollPane;

    public LocationTableCellEditor ( String type )
    {
        ta = new JTextArea ();
        scrollPane = new JScrollPane ( ta );
        scrollPane.setBorder ( BorderFactory.createEmptyBorder () );
        ta.setLineWrap ( true );
        ta.setWrapStyleWord ( true );
        ta.setOpaque ( true );

        panel.add ( scrollPane );
        panel.setPreferredSize ( new Dimension ( 80, 15 ) );
    }

    public Component getTableCellRendererComponent ( JTable table, Object value,
                                                     boolean isSelected, boolean hasFocus, int row, int column )
    {
        String s = ( String ) value;
        ta.setText ( s );
        return panel;
    }

    public Component getTableCellEditorComponent ( JTable table,
                                                   Object value, boolean isSelected, int row, int column )
    {
        return getTableCellRendererComponent ( table, value, isSelected, true, row, column );
    }

    public boolean isCellEditable ( EventObject anEvent )
    {
        return true;
    }

    public boolean shouldSelectCell ( EventObject anEvent )
    {
        return true;
    }

    public void cancelCellEditing ()
    {
    }

    public boolean stopCellEditing ()
    {

        fireEditingStopped ();

        return true;

    }


    public Object getCellEditorValue ()
    {

        return ta.getText ();

    }


    public void addCellEditorListener ( CellEditorListener l )
    {

        listenerList.add ( CellEditorListener.class, l );

    }


    public void removeCellEditorListener ( CellEditorListener l )
    {

        listenerList.remove ( CellEditorListener.class, l );

    }


    protected void fireEditingStopped ()
    {

        Object[] listeners = listenerList.getListenerList ();

        for ( int i = listeners.length - 2; i >= 0; i -= 2 )

            ( ( CellEditorListener ) listeners[ i + 1 ] ).editingStopped ( event );

    }


    protected void fireEditingCanceled ()
    {

        Object[] listeners = listenerList.getListenerList ();

        for ( int i = listeners.length - 2; i >= 0; i -= 2 )

            ( ( CellEditorListener ) listeners[ i + 1 ] ).editingCanceled ( event );

    }

    void p ( String s ) {System.out.println ( s );}

}
