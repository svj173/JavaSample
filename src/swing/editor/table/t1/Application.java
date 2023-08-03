package swing.editor.table.t1;

import javax.swing.*;
import javax.swing.text.*;
import javax.swing.tree.*;

public class Application extends JFrame {
    JEditorPane edit = new JEditorPane();
    public Application() {
        super("Tables in JEditorPane example");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        edit.setEditorKit(new TableEditorKit());
        initTableDemo();

        this.getContentPane().add(new JScrollPane(edit));
        this.setSize(500, 300);
        this.setLocationRelativeTo(null);

    }

    private void initTableDemo() {
        TableDocument doc = (TableDocument) edit.getDocument();
        doc.insertTable(0, 2, new int[] {200, 100, 150});
        doc.insertTable(4, 2, new int[] {100, 50});

        try {
            doc.insertString(10, "Paragraph after table.\nYou can set caret in table cell and start typing.", null);
            doc.insertString(4, "Inner Table", null);
            doc.insertString(3, "Cell with a nested table", null);
            doc.insertString(0, "Table\nCell", null);
        }
        catch (BadLocationException ex) {
        }
    }

    public static void main(String[] args) {
        Application m = new Application();
        m.setVisible(true);
    }
}

class TableEditorKit extends StyledEditorKit {
    ViewFactory defaultFactory = new TableFactory();
    public ViewFactory getViewFactory() {
        return defaultFactory;
    }

    public Document createDefaultDocument() {
        return new TableDocument();
    }
}

class TableFactory implements ViewFactory {
    public View create(Element elem) {
        String kind = elem.getName();
        if (kind != null) {
            if (kind.equals(AbstractDocument.ContentElementName)) {
                return new LabelView(elem);
            }
            else if (kind.equals(AbstractDocument.ParagraphElementName)) {
                return new ParagraphView(elem);
            }
            else if (kind.equals(AbstractDocument.SectionElementName)) {
                return new BoxView(elem, View.Y_AXIS);
            }
            else if (kind.equals(StyleConstants.ComponentElementName)) {
                return new ComponentView(elem);
            }
            else if (kind.equals(TableDocument.ELEMENT_NAME_TABLE)) {
                return new TableView(elem);
            }
            else if (kind.equals(TableDocument.ELEMENT_NAME_ROW)) {
                return new RowView(elem);
            }
            else if (kind.equals(TableDocument.ELEMENT_NAME_CELL)) {
                return new CellView(elem);
            }
            else if (kind.equals(StyleConstants.IconElementName)) {
                return new IconView(elem);
            }
        }

        // default to text display
        return new LabelView(elem);
    }
}
