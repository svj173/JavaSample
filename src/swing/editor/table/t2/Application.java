package swing.editor.table.t2;


import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Отображается таблица.
 * Кнопки - вставить строку, втавить колокну  -- полсе текущей.
 * Можно везде вводить данные.
 * Когда вводимый текст шире ячейки то текст перепозает на соседнюю колорнку и непонятно, он все еще относится к этйо колонке или уже нет.
 *
 * В JEditorPane заносится свой EditorKit -- class TableEditorKit extends StyledEditorKit
 *
 */
public class Application extends JFrame
{
    JEditorPane edit = new JEditorPane ();

    public Application ()
    {
        super ( "Tables in JEditorPane example (insert row and column)" );

        setDefaultCloseOperation ( JFrame.EXIT_ON_CLOSE );
        edit.setEditorKit ( new TableEditorKit() );
        initTableDemo ();

        this.getContentPane ().add ( new JScrollPane ( edit ) );
        JToolBar tb = new JToolBar ();
        JButton btnInsertRow = new JButton ( "Insert row" );
        btnInsertRow.addActionListener ( new ActionListener ()
        {
            public void actionPerformed ( ActionEvent e )
            {
                ( ( TableDocument ) edit.getDocument () ).insertRow ( edit.getCaretPosition () );
            }
        } );
        tb.add ( btnInsertRow );
        JButton btnInsertCol = new JButton ( "Insert column" );
        btnInsertCol.addActionListener ( new ActionListener ()
        {
            public void actionPerformed ( ActionEvent e )
            {
                ( ( TableDocument ) edit.getDocument () ).insertCol ( edit.getCaretPosition () );
            }
        } );
        tb.add ( btnInsertCol );
        this.getContentPane ().add ( tb, BorderLayout.NORTH );

        this.setSize ( 640, 300 );
        this.setLocationRelativeTo ( null );

    }

    private void initTableDemo ()
    {
        try
        {
            TableDocument doc = ( TableDocument ) edit.getDocument ();
            doc.insertTable ( 0, 2, new int[] { 250, 70, 100 } );
            doc.insertString ( 3, "Cell with a nested table\n", null );
            doc.insertTable ( 28, 2, new int[] { 90, 40 } );

            doc.insertString ( 35, "Paragraph after table.\nYou can set caret in table cell and start typing.", null );
            doc.insertString ( 28, "Inner Table", null );
            doc.insertString ( 43, "Cell with a nested table", null );
            doc.insertString ( 0, "Table\nCell", null );
        } catch ( BadLocationException ex )
        {
            ex.printStackTrace ();
        }
    }

    public static void main ( String[] args )
    {
        Application m = new Application ();
        m.setVisible ( true );
    }
}

class TableEditorKit extends StyledEditorKit
{
    ViewFactory defaultFactory = new TableFactory ();

    public ViewFactory getViewFactory ()
    {
        return defaultFactory;
    }

    public Document createDefaultDocument ()
    {
        return new TableDocument();
    }
}

class TableFactory implements ViewFactory
{
    public View create ( Element elem )
    {
        String kind = elem.getName ();
        if ( kind != null )
        {
            if ( kind.equals ( AbstractDocument.ContentElementName ) )
            {
                return new LabelView ( elem );
            }
            else if ( kind.equals ( AbstractDocument.ParagraphElementName ) )
            {
                return new ParagraphView ( elem );
            }
            else if ( kind.equals ( AbstractDocument.SectionElementName ) )
            {
                return new BoxView ( elem, View.Y_AXIS );
            }
            else if ( kind.equals ( StyleConstants.ComponentElementName ) )
            {
                return new ComponentView ( elem );
            }
            else if ( kind.equals ( TableDocument.ELEMENT_NAME_TABLE ) )
            {
                return new TableView ( elem );
            }
            else if ( kind.equals ( TableDocument.ELEMENT_NAME_ROW ) )
            {
                return new RowView ( elem );
            }
            else if ( kind.equals ( TableDocument.ELEMENT_NAME_CELL ) )
            {
                return new CellView ( elem );
            }
            else if ( kind.equals ( StyleConstants.IconElementName ) )
            {
                return new IconView ( elem );
            }
        }

        // default to text display
        return new LabelView ( elem );
    }

}
