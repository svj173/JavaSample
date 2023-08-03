package swing.label;



import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Dragging all Text from a JLabel
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 20.01.2012 11:05:30
 */
public class DragLabel
{
    public static void main ( String args[] )
    {
        String transferType;
        JFrame frame;

        frame = new JFrame ( "Drag Label" );
        frame.setDefaultCloseOperation ( JFrame.EXIT_ON_CLOSE );

        JLabel label = new JLabel ( "Hello, World" );

        // text - задаем что перетаскиваем - т.е. здесь это цвет фона. Также можно перетаскивать текст, цвета фона и текста и т.д.
        transferType    = "text";
        //transferType    = "foreground";

        label.setTransferHandler ( new TransferHandler ( transferType ) );
        MouseListener listener = new MouseAdapter()
        {
            public void mousePressed ( MouseEvent me )
            {
                JComponent comp = ( JComponent ) me.getSource ();
                TransferHandler handler = comp.getTransferHandler ();
                handler.exportAsDrag ( comp, me, TransferHandler.COPY );
            }
        };
        label.addMouseListener ( listener );
        frame.add ( label, BorderLayout.SOUTH );

        JTextField text = new JTextField ();
        frame.add ( text, BorderLayout.NORTH );

        frame.setSize ( 300, 150 );
        frame.setVisible ( true );
    }

}
