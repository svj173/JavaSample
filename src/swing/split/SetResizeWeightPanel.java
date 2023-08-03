package swing.split;


/**
 * Ставим флаг - при изменениях размеров фрейма, высота нижней панели остается неизменной.
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 20.07.12 11:10
 */

import javax.swing.*;
import java.awt.*;

public class SetResizeWeightPanel
{
    public static void main ( String args[] ) throws Exception
    {
        JFrame frame = new JFrame ( "Property Split" );
        frame.setDefaultCloseOperation ( JFrame.EXIT_ON_CLOSE );
        JSplitPane splitPane = new JSplitPane ( JSplitPane.VERTICAL_SPLIT );

        splitPane.setResizeWeight ( 1.0 );

        splitPane.setTopComponent ( new JLabel ( "www.jexp.ru" ) );
        splitPane.setBottomComponent ( new JLabel ( "www.jexp.ru" ) );

        frame.add ( splitPane, BorderLayout.CENTER );
        frame.setSize ( 300, 150 );
        frame.setVisible ( true );
    }

}
