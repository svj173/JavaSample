package swing.editor;

import org.apache.logging.log4j.*;

import javax.swing.*;
import javax.swing.text.StyledEditorKit;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.BadLocationException;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Попытка изменять в тексте стили, а потом распознавать элементы как Заголовок1,
 *  Заголовок2 и т.д.
 * <BR> User: Zhiganov
 * <BR> Date: 25.09.2007
 * <BR> Time: 16:05:06
 */
public class StyledDoc   extends JFrame
{
    private Logger logger = LogManager.getFormatterLogger ( StyledDoc.class );
    JTextPane textPane;
    String newline = "\n";


    public StyledDoc()
    {
        super("StyledDoc");

        MyStyledDocument lsd    = new MyStyledDocument();
        MyDocumentListener  ml  = new MyDocumentListener();
        lsd.addDocumentListener(ml);

        textPane = new JTextPane(lsd);  //All right! No 60's jokes.
        textPane.setCaretPosition(0);
        textPane.setMargin(new Insets(5,5,5,5));
        textPane.setEditable(true);
        textPane.setVisible(true);

        //Add the components to the frame.
        JPanel contentPane = new JPanel(new BorderLayout());
        contentPane.add(textPane, BorderLayout.CENTER);
        setContentPane(contentPane);

        JMenu editMenu = createEditMenu();
        JMenu styleMenu = createStyleMenu();
        JMenuBar mb = new JMenuBar();
        mb.add(editMenu);
        mb.add(styleMenu);
        setJMenuBar(mb);

        // text
        SimpleAttributeSet attrs;

        attrs = new SimpleAttributeSet();
        StyleConstants.setFontFamily(attrs, "SansSerif");
        StyleConstants.setFontSize(attrs, 16);
        try {
            lsd.insertString(lsd.getLength(), "проверка тескта была здесь." + newline,attrs);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }

    }

    private JMenu createEditMenu() {
        JMenu menu = new JMenu("Edit");

        Action action = new StyledEditorKit.CutAction();
        action.putValue(Action.NAME, "Cut");
        menu.add(action);

        action = new StyledEditorKit.CopyAction();
        action.putValue(Action.NAME, "Copy");
        menu.add(action);

        action = new StyledEditorKit.PasteAction();
        action.putValue(Action.NAME, "Paste");
        menu.add(action);

        action = new StyledEditorKit.AlignmentAction("Align10", StyleConstants.ALIGN_JUSTIFIED );
        action.putValue(Action.NAME, "Align-10");
        menu.add(action);

        action = new Title1Action();
        action.putValue(Action.NAME, "Title1");
        menu.add(action);
        action = new Title2Action();
        action.putValue(Action.NAME, "Title2");
        menu.add(action);

        return menu;
    }

    //Create the style menu.
    protected JMenu createStyleMenu()
    {
        JMenu menu = new JMenu("Style");

        return menu;
    }


    public static void main(String[] args)
    {
        final StyledDoc frame = new StyledDoc();
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
            public void windowActivated(WindowEvent e) {
                frame.textPane.requestFocus();
            }
        });

        frame.pack();
        frame.setVisible(true);
    }

}
