package swing.split.multi.examples;


import swing.split.multi.MultiSplitLayout;
import swing.split.multi.MultiSplitPane;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Hans Muller (Hans.Muller@Sun.COM)
 */
public class Example1 extends Example {
    protected void initialize(String[] ignore) {
	super.initialize(ignore);
        
        List children = 
	    Arrays.asList(new MultiSplitLayout.Leaf ("left"), new MultiSplitLayout.Divider (), new MultiSplitLayout.Leaf ("right"));
        MultiSplitLayout.Split modelRoot = new MultiSplitLayout.Split ();
        modelRoot.setChildren(children);
                
        MultiSplitPane multiSplitPane = new MultiSplitPane();
        multiSplitPane.getMultiSplitLayout().setModel(modelRoot);
       	multiSplitPane.add(new JButton("Left Component"), "left");
	multiSplitPane.add(new JButton("Right Component"), "right");

	Container cp = mainFrame.getContentPane();
	cp.add(multiSplitPane, BorderLayout.CENTER);
    }
    
    public static void main(String[] args) {
        launch(Example1.class, args);
    }
}
