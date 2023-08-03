package swing.split.multi.examples;


import swing.split.multi.MultiSplitLayout;
import swing.split.multi.MultiSplitPane;

import javax.swing.*;
import java.awt.*;



/**
 *
 * @author Hans Muller (Hans.Muller@Sun.COM)
 */
public class Example2 extends Example {
    protected void initialize(String[] ignore) {
	super.initialize(ignore);

        /*
        Leaf left = new Leaf("left");
        Leaf right = new Leaf("right");
        left.setWeight(0.5);
        right.setWeight(0.5);
        List children = Arrays.asList(left, new Divider(), right);
        Split modelRoot = new Split();
        modelRoot.setChildren(children);
         */
        String layoutDef = "(ROW (LEAF name=left weight=0.5) (LEAF name=right weight=0.5))";
        MultiSplitLayout.Node modelRoot = MultiSplitLayout.parseModel(layoutDef);
                
        MultiSplitPane multiSplitPane = new MultiSplitPane();
        multiSplitPane.getMultiSplitLayout().setModel(modelRoot);
       	multiSplitPane.add(new JButton("Left Component"), "left");
	multiSplitPane.add(new JButton("Right Component"), "right");

	Container cp = mainFrame.getContentPane();
	cp.add(multiSplitPane, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        launch(Example2.class, args);
    }
}
