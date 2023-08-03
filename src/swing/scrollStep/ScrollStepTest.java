package swing.scrollStep;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Vector;


/**
 * Попытка изменять шаг прокрутки скроллинга, в зависимости от размера отображаемого массива.  - Рабочий.
 * <BR/> Выпадашка с размерами массива элементов - вверху.
 * <BR/> При выборе значения очищаются старые компоненты и генерятся новые в новом количестве.
 * <BR/> Здесь же происходит переопределение шага прокрутки скроллинга.
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 26.12.2011 11:39:03
 */
public class ScrollStepTest extends Frame
{
    private JScrollPane scrollPane;
    private JPanel      propsPanel;

    public ScrollStepTest ( String title ) throws HeadlessException
    {
        super ( title );

        JComboBox       boxSize;
        Vector<Integer> values;

        setLayout ( new BorderLayout() );

        propsPanel = new JPanel();
        propsPanel.setLayout ( new BoxLayout(propsPanel, BoxLayout.PAGE_AXIS) );

        //setProps ( 45 );

        scrollPane = new JScrollPane();
        scrollPane.getViewport().add ( propsPanel );

        add ( scrollPane, BorderLayout.CENTER );

        // boxSize
        values  = new Vector<Integer>();
        for ( int i=40; i<200; i=i+10 )
        {
            values.add ( i );
        }
        boxSize = new JComboBox(values);
        boxSize.addActionListener ( new ActionListener()
        {

            @Override
            public void actionPerformed ( ActionEvent event )
            {
                JComboBox cb;
                System.out.println ( "event = " + event );
                cb = (JComboBox) event.getSource ();
                setProps ( (Integer) cb.getSelectedItem() );
            }
        });
        add ( boxSize, BorderLayout.NORTH );
    }

    public JScrollPane getScrollPane ()
    {
        return scrollPane;
    }

    public JPanel getPropsPanel ()
    {
        return propsPanel;
    }

    public void setProps ( int size )
    {
        JLabel label;

        getPropsPanel().removeAll();

        // создать массив
        for ( int i=0; i<size; i++ )
        {
            label = new JLabel ( " Параметр "+i);
            getPropsPanel().add ( label );
        }

        //getScrollPane().getVerticalScrollBar().setMaximum ( size );
        //getScrollPane().getVerticalScrollBar().setBlockIncrement ( 200 );
        getScrollPane().getVerticalScrollBar().setUnitIncrement ( size /4 );

        getScrollPane().revalidate();
        //getScrollPane().repaint();
    }


    public static void main ( String[] args )
    {
        ScrollStepTest f = new ScrollStepTest ( " Прокрутка " );
        f.addWindowListener ( new WindowAdapter()
        {
            public void windowClosing ( WindowEvent ev )
            {
                System.exit ( 0 );
            }
        } );

        f.setProps ( 45 );

        f.setSize ( 400, 300 );
        f.setVisible ( true );
    }

}
