package swing.split.multi.examples;


import swing.split.multi.MultiSplitLayout;
import swing.split.multi.MultiSplitPane;

import javax.swing.*;
import java.awt.*;

/**
 *
 * @author Hans Muller (Hans.Muller@Sun.COM)
 */
public class Example3 extends Example {
    protected void initialize(String[] ignore) {
	super.initialize(ignore);

        String layoutDef = "(COLUMN (ROW weight=1.0 left (COLUMN middle.top middle middle.bottom) right) bottom)";
        MultiSplitLayout.Node modelRoot = MultiSplitLayout.parseModel(layoutDef);

        MultiSplitPane multiSplitPane = new MultiSplitPane();
	multiSplitPane.setDividerSize(5);
        multiSplitPane.getMultiSplitLayout().setModel(modelRoot);
       	multiSplitPane.add(new JButton("Left Column"), "left");
	multiSplitPane.add(new JButton("Right Column"), "right");
        multiSplitPane.add(new JButton("Bottom Row"), "bottom");
        multiSplitPane.add(new JButton("Middle Column Top"), "middle.top");
        multiSplitPane.add(new JButton("Middle"), "middle");
        multiSplitPane.add(new JButton("Middle Bottom"), "middle.bottom");
                
	Container cp = mainFrame.getContentPane();
	cp.add(multiSplitPane, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        launch(Example3.class, args);
    }
}
