package swing.focus;


/**
 * Набор кнопко и верткиальный список. По таб - бегает по кнокпам и по выделенному обьекту в списке.
 * <BR/> По стрелкам - бегает в списке. причем скроллинг поднимается при необходимости.
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 28.06.2011 9:26:29
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.Vector;

public class FocusEventDemo extends JFrame
        implements FocusListener {
    final static String newline = "\n";
    JTextArea display;

    public FocusEventDemo(String name) {
        super(name);
    }

    public void addComponentsToPane(final Container pane) {
        GridBagLayout gridbag = new GridBagLayout();
        pane.setLayout(gridbag);

        GridBagConstraints c = new GridBagConstraints();

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1.0;  //Make column as wide as possible.
        JTextField textField = new JTextField("A TextField");
        textField.setMargin(new Insets(0,2,0,2));
        textField.addFocusListener(this);
        gridbag.setConstraints(textField, c);
        add(textField);

        c.weightx = 0.1;  //Widen every other column a bit, when possible.
        c.fill = GridBagConstraints.NONE;
        JLabel label = new JLabel("A Label");
        label.setBorder(BorderFactory.createEmptyBorder(0,5,0,5));
        label.addFocusListener(this);
        gridbag.setConstraints(label, c);
        add(label);

        String comboPrefix = "ComboBox Item #";
        final int numItems = 15;
        Vector<String> vector = new Vector<String>(numItems);
        for (int i = 0; i < numItems; i++) {
            vector.addElement(comboPrefix + i);
        }
        JComboBox comboBox = new JComboBox(vector);
        comboBox.addFocusListener(this);
        gridbag.setConstraints(comboBox, c);
        add(comboBox);

        c.gridwidth = GridBagConstraints.REMAINDER;
        JButton button = new JButton("A Button");
        button.addFocusListener(this);
        gridbag.setConstraints(button, c);
        add(button);

        c.weightx = 0.0;
        c.weighty = 0.1;
        c.fill = GridBagConstraints.BOTH;
        String listPrefix = "List Item #";
        Vector<String> listVector = new Vector<String>(numItems);
        for (int i = 0; i < numItems; i++) {
            listVector.addElement(listPrefix + i);
        }
        JList list = new JList(listVector);
        list.setSelectedIndex(1); //It's easier to see the focus change
        //if an item is selected.
        list.addFocusListener(this);
        JScrollPane listScrollPane = new JScrollPane(list);

        gridbag.setConstraints(listScrollPane, c);
        add(listScrollPane);

        c.weighty = 1.0; //Make this row as tall as possible.
        c.gridheight = GridBagConstraints.REMAINDER;
        //Set up the area that reports focus-gained and focus-lost events.
        display = new JTextArea();
        display.setEditable(false);
        //The setRequestFocusEnabled method prevents a
        //component from being clickable, but it can still
        //get the focus through the keyboard - this ensures
        //user accessibility.
        display.setRequestFocusEnabled(false);
        display.addFocusListener(this);
        JScrollPane displayScrollPane = new JScrollPane(display);

        gridbag.setConstraints(displayScrollPane, c);
        add(displayScrollPane);
        setPreferredSize(new Dimension(450, 450));
        ((JPanel)pane).setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
    }

    public void focusGained(FocusEvent e) {
        displayMessage("Focus gained", e);
    }

    public void focusLost(FocusEvent e) {
        displayMessage("Focus lost", e);
    }

    void displayMessage(String prefix, FocusEvent e) {
        display.append(prefix
                + (e.isTemporary() ? " (temporary):" : ":")
                + e.getComponent().getClass().getName()
                + "; Opposite component: "
                + (e.getOppositeComponent() != null ?
                    e.getOppositeComponent().getClass().getName() : "null")
                    + newline);
        display.setCaretPosition(display.getDocument().getLength());
    }

    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event dispatch thread.
     */
    private static void createAndShowGUI() {
        //Create and set up the window.
        FocusEventDemo frame = new FocusEventDemo("FocusEventDemo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Set up the content pane.
        frame.addComponentsToPane(frame.getContentPane());

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        /* Use an appropriate Look and Feel */
        try {
            //UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
        } catch (UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
        } catch (InstantiationException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }

        /* Turn off metal's use of bold fonts */
        UIManager.put("swing.boldMetal", Boolean.FALSE);
        //Schedule a job for the event dispatch thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}

