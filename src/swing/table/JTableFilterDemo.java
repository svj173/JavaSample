package swing.table;


import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

/**
 * Пример использования внутренней сортировки и фильтрации таблицы.
 * <BR/> Задан фильтр по второй колонке - значение должно быть больше 1000000000.
 * <BR/> При старте выводит только две строки, а не 4. Т.е. отфильтровал.
 * <BR/> Замечание: если руками правишь значение второй колонки и делаешь его меньше, то все равно при сортировке по колонкам эта строка почему-то НЕ исчезает.
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 17.09.2010 14:52:42
 */
public class JTableFilterDemo
{

    private static void constructGUI()
    {
        // Table content
        Object data[][] = {
                {"China"            , 1306313812},
                {"India"            , 1080264388},
                {"United States"    ,  297200005},
                {"Brazil"           ,  186112794}
        };
        String columnNames[] = {"Country", "Population"};
        TableModel model = new
                DefaultTableModel(data, columnNames) {
            public Class<?> getColumnClass(int column) {
                return getValueAt(0, column).getClass();
            }
        };
        JTable table = new JTable(model);

        // Apply row filter to JTable
        RowFilter<Object, Object> filter = new RowFilter<Object, Object>()
        {
            public boolean include ( Entry entry )
            {
                // Filter the JTable based on the content of the
                // second column
                Integer population = (Integer) entry.getValue(1);
                return population.intValue() > 1000000000;
            }
        };

        TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(model);
        sorter.setRowFilter(filter);
        table.setRowSorter(sorter);

        JScrollPane scrollPane = new JScrollPane(table);
        JFrame frame = new JFrame("Filtering Table");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(scrollPane);
        frame.setSize(300, 200);
        frame.setVisible(true);
    }

    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                constructGUI();
            }
        });
    }
    
}
