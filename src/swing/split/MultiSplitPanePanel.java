package swing.split;


/**
 * MultiSplitPane - - сторонняя разработка.
 * <BR/> Создает панели согласно раскладке, описанной в тексте layoutDef.
 * <BR/> Без ярко выраженного разделителя (пустое пространство).
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 20.07.12 11:10
 */

import swing.split.multi.MultiSplitLayout;
import swing.split.multi.MultiSplitPane;

import javax.swing.*;
import java.awt.*;

public class MultiSplitPanePanel
{
    public static void main ( String args[] ) throws Exception
    {
        JFrame frame = new JFrame ( "Property Split" );
        frame.setDefaultCloseOperation ( JFrame.EXIT_ON_CLOSE );

        String layoutDef =
            "(COLUMN (ROW weight=1.0 left (COLUMN middle.top middle middle.bottom) right) bottom)";
        MultiSplitLayout.Node modelRoot = MultiSplitLayout.parseModel(layoutDef);

        MultiSplitPane multiSplitPane = new MultiSplitPane();
        multiSplitPane.getMultiSplitLayout().setModel(modelRoot);
        multiSplitPane.add(new JButton("Left Column"), "left");
        multiSplitPane.add(new JButton("Right Column"), "right");
        multiSplitPane.add(new JButton("Bottom Row"), "bottom");
        multiSplitPane.add(new JButton("Middle Column Top"), "middle.top");
        multiSplitPane.add(new JButton("Middle"), "middle");
        multiSplitPane.add(new JButton("Middle Bottom"), "middle.bottom");

        frame.add ( multiSplitPane, BorderLayout.CENTER );
        frame.setSize ( 500, 450 );
        frame.setVisible ( true );
    }

}
