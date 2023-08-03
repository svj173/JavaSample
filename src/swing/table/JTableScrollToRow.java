package swing.table;


/**
 * Управление скроллингом в таблице - scrollRectToVisible.
 * <BR/> Через временныееп отрезки сам выделяет разные ячейки, строки таблицы и смещает туда скроллинг.
 * <BR/> ЬТакже выделяет и колонки.
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 01.03.2013 14:07
 */
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Demonstrate displaying a specific cell in a JTable when a row is added.
 * <p>
 * The Table Row Index is displayed in one of the table's columns.
 * <p>
 * The cell containing the Value will be selected for displaying.
 * <p>
 * The specified cell will be made visible and, if possible, positioned in the center of the Viewport.
 * <p>
 * The code works regardless of:
 * <ul>
 * <li>Whether or not the table data is sorted</li>
 * <li>The position/visibility of the "Value" column</li>
 * </ul>
 */
public class JTableScrollToRow
{
    static  SecureRandom         random;
    private DefaultTableModel   dtm;

    static
    {
        try
        {
            random = SecureRandom.getInstance("SHA1PRNG");
            int seed = Integer.parseInt((new SimpleDateFormat("SSS")).format(new Date()));
            random.setSeed(random.generateSeed(seed));
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
    }

    public void buildGUI()
    {
        Object[][] data = {};
        Object colNames[] = {
                "Value",
                "TableRowIx",
                "Column A",
                "Column B",
                "Column C",
                "Column D",
                "Column E",
                "Column F" };

        dtm = new DefaultTableModel(data, colNames);
        final JTable sampleTable = new JTable(dtm);
        sampleTable.setDragEnabled(false);
        sampleTable.setAutoCreateRowSorter(true);

        // Turn off auto-resizing to allow for columns moved out of the Viewport
        sampleTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        // Populate the table with some data
        for (int x = 0; x < 200; x++)
        {
            addRow(x);
        }

        // Create a ScrollPane
        JScrollPane sp = new JScrollPane(sampleTable);

        // Provide a horizontal scroll bar so that columns can be scrolled out of the Viewport
        sp.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);

        final JFrame f = new JFrame();
        f.getContentPane().add(sp);
        f.setTitle("JTable cell display example");
        f.pack();
        f.setLocationRelativeTo(null);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);

        // Create a thread that periodically adds a row to the table
        Thread rowAdder = new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                do
                {
                    try
                    {
                        int secs = 5;
                        Thread.sleep(secs * 1000);
                    }
                    catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }

                    // Add a row
                    addRow(dtm.getRowCount());
                } while (true);
            }
        });
        rowAdder.start();

        // Add the custom ComponentListener
        sampleTable.addComponentListener(new JTableCellDisplayer(sampleTable));
    }

    /**
     * Display a table row when it is added to a JTable.<br>
     * Details available at <a
     * href="http://stackoverflow.com/questions/4890282/howto-to-scroll-to-last-row-on-jtable">StackOverflow</a>.
     * <p>
     * <b>Key information:</b> Whenever a row is added or removed the JTable resizes. This occurs even if the row is
     * outside of the JScrollPane's Viewport (i.e., the row is not visible).
     */
    class JTableCellDisplayer extends ComponentAdapter
    {
        boolean selRow      = false;
        boolean selCol      = false;
        boolean firstTime   = true;
        boolean selectData  = false;
        JTable  table;

        public JTableCellDisplayer ( JTable jTable )
        {
            table = jTable;
        }

        @Override
        public void componentResized ( ComponentEvent e )
        {
            if (firstTime)
            {
                firstTime = false;
                return;
            }

            int viewIx = table.convertRowIndexToView(table.getRowCount() - 1);

            if (!selRow  && !selCol)
            {
                System.out.println(" - Select nothing - selectData="  + selectData);
            }
            else if (selRow && !selCol)
            {
                System.out.println(" - Select row only - selectData=" + selectData);
            }
            else if (!selRow  && selCol)
            {
                System.out.println(" - Select column only - selectData="  + selectData);
            }
            else
            {
                System.out.println(" - Select cell - selectData="  + selectData);
            }

            // If data should be selected, set the selection policies on the table.
            if ( selectData )
            {
                table.setRowSelectionAllowed(selRow);
                table.setColumnSelectionAllowed(selCol);
            }

            // Scroll to the VALUE cell (columnIndex=0) that was added
            displayTableCell(table, viewIx, table.convertColumnIndexToView(0), selectData);

            // Cycle through all possibilities
            if (!selRow && !selCol)
            {
                selRow = true;
            }
            else if (selRow && !selCol)
            {
                selRow = false;
                selCol = true;
            }
            else if (!selRow  && selCol)
            {
                selRow = true;
                selCol = true;
            }
            else
            {
                selRow = false;
                selCol = false;
                selectData = !selectData;
            }
        }
    }

    /**
     * Assuming the table is contained in a JScrollPane, scroll to the cell (vRowIndex, vColIndex). <br>
     * The specified cell is guaranteed to be made visible.<br>
     * Every attempt will be made to position the cell in the center of the Viewport. <b>Note:</b> This may not be
     * possible if the row is too close to the top or bottom of the Viewport.
     * <p>
     * It is possible to select the specified cell. The amount of data selected (none, entire row, entire column or a
     * single cell) is dependent on the settings specified by {@link JTable#setColumnSelectionAllowed(boolean)} and
     * {@link JTable#setRowSelectionAllowed(boolean)}.
     * <p>
     * Original code found <a href="http://www.exampledepot.com/egs/javax.swing.table/VisCenter.html">here</a>.
     * <p>
     *
     * @param table
     *            - The table
     * @param vRowIndex
     *            - The view row index
     * @param vColIndex
     *            - The view column index
     * @param selectCell
     *            - If <code>true</code>, the cell will be selected in accordance with the table's selection policy;
     *            otherwise the selected data will not be changed.
     * @see JTable#convertRowIndexToView(int)
     * @see JTable#convertColumnIndexToView(int)
     */
    public static void displayTableCell(JTable table, int vRowIndex, int vColIndex, boolean selectCell)
    {
        if (!(table.getParent() instanceof JViewport))   return;

        JViewport viewport = (JViewport) table.getParent();

        /* This rectangle is relative to the table where the
         * northwest corner of cell (0,0) is always (0,0).
         */
        Rectangle rect = table.getCellRect(vRowIndex, vColIndex, true);

        // The location of the view relative to the table
        Rectangle viewRect = viewport.getViewRect();

        /*
         *  Translate the cell location so that it is relative
         *  to the view, assuming the northwest corner of the
         *  view is (0,0).
         */
        rect.setLocation(rect.x
                - viewRect.x, rect.y
                - viewRect.y);

        // Calculate location of rectangle if it were at the center of view
        int centerX = (viewRect.width - rect.width) / 2;
        int centerY = (viewRect.height - rect.height) / 2;

        /*
         *  Fake the location of the cell so that scrollRectToVisible
         *  will move the cell to the center
         */
        if (rect.x < centerX)  centerX = -centerX;
        if (rect.y < centerY)  centerY = -centerY;

        rect.translate ( centerX, centerY );

        // If desired and allowed, select the appropriate cell
        if ( selectCell  && (table.getRowSelectionAllowed() || table.getColumnSelectionAllowed()))
        {
            // Clear any previous selection
            table.clearSelection();

            table.setRowSelectionInterval(vRowIndex, vRowIndex);
            table.setColumnSelectionInterval(vColIndex, vColIndex);
        }

        // Scroll the area into view.
        viewport.scrollRectToVisible ( rect );
    }

    private String addRow(int tableRowIndex)
    {
        String retVal;

        int value = random.nextInt(99999999);
        dtm.addRow(new Object[] {
                value,
                tableRowIndex,
                random.nextInt(99999999),
                random.nextInt(99999999),
                random.nextInt(99999999),
                random.nextInt(99999999),
                random.nextInt(99999999),
                random.nextInt(99999999), });

        retVal = "Row added - value=" + value + " & tableRowIx=" + tableRowIndex;

        System.out.println(retVal);
        return retVal;
    }

    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                new JTableScrollToRow().buildGUI();
            }
        });
    }

}
