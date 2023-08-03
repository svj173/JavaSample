package swing.table;

/**
 * http://java.sun.com/docs/books/tutorial/uiswing/components/table.html
 * 
 * <BR> User: svj
 * <BR> Date: 28.05.2008
 * <BR> Time: 15:20:09
 */
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SimpleTableDemo extends JPanel {
    private boolean DEBUG = false;

    public SimpleTableDemo() {
        super(new GridLayout(1,0));

        String[] columnNames = {"First Name",
                                "Last Name",
                                "Sport",
                                "# of Years",
                                "Vegetarian"};

        Object[][] data = {
            {"Mary", "Campione",
             "Snowboarding", new Integer(5), new Boolean(false)},
            {"Alison", "Huml",
             "Rowing", new Integer(3), new Boolean(true)},
            {"Kathy", "Walrath",
             "Knitting", new Integer(2), new Boolean(false)},
            {"Sharon", "Zakhour",
             "Speed reading", new Integer(20), new Boolean(true)},
            {"Philip", "Milne",
             "Pool", new Integer(10), new Boolean(false)}
        };

        final JTable table = new JTable(data, columnNames);
        table.setPreferredScrollableViewportSize(new Dimension(500, 70));
        table.setFillsViewportHeight(true);

        // цвет линий
        //table.setGridColor ( Color.GRAY );
        table.setGridColor ( Color.RED );

        // рисовать линии или нет
        table.setShowGrid ( true );
        //table.setShowGrid ( false );

      //table.setDefaultRenderer ( Boolean.class, new JTable.BooleanRenderer() );  // --

      //JTable.getColumnModel().getColumn(n).setCellRenderer(new BooleanRenderer())
      // table.setDefaultRenderer( Class.forName ( "java.lang.Integer" ), renderer );

        if (DEBUG) {
            table.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    printDebugData(table);
                }
            });
        }

        /*
        // Вариант - заголовок таблицы поместить в отдельное поле - чтобы всегда был.
   JFrame jf = new JFrame("Пример заголовков таблицы размещенных внизу");
   JTable jt = new JTable(vec_data, column_names);

   jf.getContentPane().add (new JScrollPane(jt) , BorderLayout.CENTER);
   jf.getContentPane().add (new JScrollPane(jt.getTableHeader()),  BorderLayout.WEST);
        */

        //Create the scroll pane and add the table to it.
        JScrollPane scrollPane = new JScrollPane(table);

        setLayout ( new BorderLayout () );

        add ( new JScrollPane(table.getTableHeader()),  BorderLayout.NORTH );
        //add ( new JScrollPane(table),  BorderLayout.CENTER );
        //Add the scroll pane to this panel.
        add(scrollPane,  BorderLayout.CENTER );

        //BooleanRenderer br;
        DefaultTableModel dm;
    }

    private void printDebugData(JTable table) {
        int numRows = table.getRowCount();
        int numCols = table.getColumnCount();
        javax.swing.table.TableModel model = table.getModel();

        System.out.println("Value of data: ");
        for (int i=0; i < numRows; i++) {
            System.out.print("    row " + i + ":");
            for (int j=0; j < numCols; j++) {
                System.out.print("  " + model.getValueAt(i, j));
            }
            System.out.println();
        }
        System.out.println("--------------------------");
    }

    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("SimpleTableDemo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create and set up the content pane.
        SimpleTableDemo newContentPane = new SimpleTableDemo();
        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}
