package grafics.grafics2;


// ������� ������ � ������������ �� ��������� �����������
//  ����� ��������� � ��������� ���������� � ����

    import java.awt.*;
    import java.awt.event.*;
    import javax.swing.*;
    import javax.swing.event.*;
    import javax.swing.tree.*;
    import java.util.Vector;

    public class JTreeDemo {
        public static void main(String args[]) {
            JFrame frame = new JFrame("JTree Demo");

            // handle window close

            frame.addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent e) {
                    System.exit(0);
                }
            });

            JPanel panel1 = new JPanel();

            // set up tree root and nodes

            DefaultMutableTreeNode root =
                new DefaultMutableTreeNode("testing");

            DefaultMutableTreeNode one =
                new DefaultMutableTreeNode("one");
            one.add(new DefaultMutableTreeNode("1.1"));
            one.add(new DefaultMutableTreeNode("1.2"));

            DefaultMutableTreeNode two =
                new DefaultMutableTreeNode("two");
            two.add(new DefaultMutableTreeNode("2.1"));
            two.add(new DefaultMutableTreeNode("2.2"));

            DefaultMutableTreeNode three =
                new DefaultMutableTreeNode("three");
            Vector vec = new Vector();
            for (int i = 1; i <= 25; i++)
                vec.addElement("3." + i);
            JTree.DynamicUtilTreeNode.createChildren(three, vec);

            root.add(one);
            root.add(two);
            root.add(three);

            // set up tree and scroller for it
            // also set text selection color to red

            JTree jt = new JTree(root);
            DefaultTreeCellRenderer tcr =
                (DefaultTreeCellRenderer)jt.getCellRenderer();
            tcr.setTextSelectionColor(Color.red);

            JScrollPane jsp = new JScrollPane(jt);
            jsp.setPreferredSize(new Dimension(200, 300));

            // set text field for echoing selections

            JPanel panel2 = new JPanel();
            final JTextField tf = new JTextField(25);
            panel2.add(tf);

            // handle selections in the tree

            TreeSelectionListener listen;
            listen = new TreeSelectionListener() {
                public void valueChanged(TreeSelectionEvent e) {

                    // get selected path

                    TreePath path = e.getPath();
                    int cnt = path.getPathCount();
                    StringBuffer sb = new StringBuffer();

                    // pick out the path components

                    for (int i = 0; i < cnt; i++) {
                        String s =
                            path.getPathComponent(i).toString();
                        sb.append(s);
                        if (i + 1 != cnt)
                            sb.append("#");
                    }
                    tf.setText(sb.toString());
                }
            };
            jt.addTreeSelectionListener(listen);

            panel1.add(jsp);

            frame.getContentPane().add("North", panel1);
            frame.getContentPane().add("South", panel2);
            frame.setLocation(100, 100);
            frame.pack();
            frame.setVisible(true);
        }
    }
