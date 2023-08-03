package swing.editor.html.html1;


/**
 * Редактор HTML. Должен грузить html файл, но не отображает результат на экране.
 *
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 14.07.2011 13:22:01
 */

import javax.swing.*;
import javax.swing.text.JTextComponent;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.parser.ParserDelegator;
import java.awt.*;
import java.io.*;

public class LoadSync
{

    public static void main ( String args[] )
    {
        final String        filename;
        final JEditorPane   editorPane;
        JFrame      frame;
        Container   content;
        JScrollPane scrollPane;
        JPanel      panel;
        Action      loadAction;
        JButton     loadButton;

        //filename = "Test.html";
        filename = "/home/svj/projects/SVJ/JavaSample/test/html/samle01.html";

        frame   = new JFrame ( "Loading/Saving Example" );
        try
        {
            content = frame.getContentPane();

            editorPane = new JEditorPane();
            editorPane.setEditable ( true );
            editorPane.setVisible ( true );
            scrollPane = new JScrollPane ( editorPane );
            content.add ( scrollPane, BorderLayout.CENTER );

            // Создать руками -- НЕ работает.
            HTMLDocument doc = new HTMLDocument();

            HTMLEditorKit kit = new HTMLEditorKit();

            editorPane.setDocument(doc);
            editorPane.setEditorKit ( kit );


            kit.insertHTML(doc, doc.getLength(), "<label> This label will be inserted inside the body  directly </label>", 0, 0, null);
            kit.insertHTML(doc, doc.getLength(), "<br/>", 0, 0, null);
            kit.insertHTML(doc, doc.getLength(), "Text", 0, 0, null);
            //kit.insertHTML(doc, doc.getLength(), putYourVariableHere, 0, 0, null);

            scrollPane.revalidate();
            scrollPane.repaint ();

        } catch ( Exception e )        {
            e.printStackTrace ();
        }

        /*
        // Кнопка загрузки файла РЕЬД
        panel = new JPanel();

        // Setup actions
        loadAction = new AbstractAction()
        {
            {
                putValue ( Action.NAME, "Load" );
            }

            public void actionPerformed ( ActionEvent e )
            {
                doLoadCommand ( editorPane, filename );
                //editorPane.revalidate();
                //editorPane.repaint();
            }
        };
        loadButton = new JButton ( loadAction );
        panel.add ( loadButton );

        content.add ( panel, BorderLayout.SOUTH );
        */

        frame.setSize ( 550, 450 );
        frame.setPreferredSize ( new Dimension ( 550, 450 ) );
        frame.pack ();
        frame.setVisible ( true );
    }

    public static void doLoadCommand ( JTextComponent textComponent, String filename )
    {
        FileReader reader = null;
        try
        {
            System.out.println ( "Loading" );
            reader = new FileReader ( filename );

            // Загрузить из файла
            // Create empty HTMLDocument to read into
            HTMLEditorKit htmlKit = new HTMLEditorKit();
            HTMLDocument htmlDoc = ( HTMLDocument ) htmlKit.createDefaultDocument();

            System.out.println ( "htmlDoc 0 = " + htmlDoc );
            // Create parser (javax.swing.text.html.parser.ParserDelegator)
            HTMLEditorKit.Parser parser = new ParserDelegator();
            // Get parser callback from document
            HTMLEditorKit.ParserCallback callback = htmlDoc.getReader(0);
            // Load it (true means to ignore character set)
            parser.parse ( reader, callback, true );

            // Load HTML file synchronously
            /*
                URL url = new URL(filename);
                URLConnection connection = url.openConnection();
                InputStream is = connection.getInputStream();
                InputStreamReader isr = new InputStreamReader (is);
                BufferedReader br = new BufferedReader (isr);
            */

            // Создать руками - ???

            System.out.println ( "htmlDoc 5 = " + htmlDoc.getText ( 0, htmlDoc.getLength () ) );
            // Replace document
            textComponent.setDocument ( htmlDoc );
            //textComponent.revalidate();
            //textComponent.repaint();
            System.out.println ( "Loaded" );

        } catch ( Exception exception )        {
            System.out.println ( "Load oops" );
            exception.printStackTrace();
        } finally        {
            if ( reader != null )
            {
                try
                {
                    reader.close();
                } catch ( IOException ignoredException )
                {
                }
            }
        }
    }
}
