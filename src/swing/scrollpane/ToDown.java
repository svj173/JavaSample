package swing.scrollpane;


import javax.swing.*;
import java.awt.*;

/**
 * Кусок кода, по которому если мышку держать в углу скролл-панели, то крусор-ползунок будет сам автоматически медленно сползать вниз.
 * <BR/>
 * <BR/> Внимание! Необходимо использовать EventQueue.invokeLater.
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 20.10.2014 10:52
 */
public class ToDown
{
    private boolean scrollDown = true;
    private int currentValue, maxValue, speed;
    private JScrollPane scroll;
    private long pause;

    void startScrollDown ()
    {
        if ( !scrollDown )
        {
            scrollDown = true;
            //new Thread ( new Runnable ()        // EventQueue.invokeLater.
            EventQueue.invokeLater ( new Runnable ()        // EventQueue.invokeLater.
            {
                public void run ()
                {
                    currentValue = scroll.getVerticalScrollBar ().getValue ();
                    maxValue = scroll.getVerticalScrollBar ().getMaximum ();
                    while ( scrollDown )
                    {
                        if ( scroll != null )
                        {
                            if ( currentValue < maxValue - speed + 1 )
                            {
                                currentValue += speed;
                                scroll.getVerticalScrollBar ().setValue ( currentValue );
                                scroll.revalidate ();
                            }
                            else if ( currentValue < maxValue )
                            {
                                currentValue = maxValue;
                                scroll.getVerticalScrollBar ().setValue ( currentValue );
                                scroll.revalidate ();
                            }
                        }
                        try
                        {
                            Thread.sleep ( pause );
                        } catch ( InterruptedException e )   {
                            e.printStackTrace ();
                        }
                    }
                }
            } );
            //} ).start ();
        }
    }

    /*

// Делаем еще один JScrollPane - иначе при Редактировании, при фокусе на текстовое поле ползунок будет смещаться вправо и наименования параметров скроются.
addToCenter ( new JScrollPane(scrollPane) );


jScrollPane1 = new JScrollPane();
TableModel jTable1Model = new DefaultTableModel(...);
JTable jTable1 = new JHorizontalFriendlyTable();
jScrollPane1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
jScrollPane1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
jScrollPane1.setViewPortView(jTable1);
jTable1.setModel(jTable1Model);
jTable1.setPreferredSize(new java.awt.Dimension(1051,518));
jTable1.setPreferredScrollableViewPortSize(new java.awt.Dimension(1000,528));
jTable1.getSize(new java.awt.Dimension(1051, 528));

if (jTable1.getPreferredScrollableViewPortSize().getWidth() >
  ((JViewPort) jTable1.getParent()).getPreferredSize().getWidth())
  {
  jTable1.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
  jTable1.doLayout();
}

jTable1.setDragEnabled(false);
jTable1.setColumnSelectionAllowed(false);
jTable1.getTableHeader().setReorderingAllowed(false);

     */
}
