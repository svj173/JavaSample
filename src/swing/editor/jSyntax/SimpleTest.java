package swing.editor.jSyntax;


import jsyntaxpane.DefaultSyntaxKit;
import tools.FileTools;
import exception.SvjException;

import javax.swing.*;
import java.awt.*;

/**
 * Полнофункциональны редактор программных текстов.
 *
 * Применеятся Библиотека редактора - JSyntaxPane
 * Форматы: Java, JavaScript, Properties, Groovy, C, C++, XML, SQL, Ruby and Python.
 * Использует стандартный JEditorPane, который расширяет по DefaultSyntaxKit.initKit();
 *
 *
 * Открыть текстовый файл и положить его в Редактор.
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 18.03.2013 10:15
 */

public class SimpleTest
{
    private JFrame f;

    public static void main(String[] args)
    {
        java.awt.EventQueue.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                new SimpleTest().showTest();
            }
        });
    }

    private void showTest ()
    {
        f.setVisible ( true );
    }

    public SimpleTest()
    {
        String text, fileName, type;

        f = new JFrame ( "Test" );
        final Container c = f.getContentPane();
        c.setLayout(new BorderLayout());

        DefaultSyntaxKit.initKit();

        final JEditorPane codeEditor = new JEditorPane();
        JScrollPane scrPane = new JScrollPane(codeEditor);
        c.add ( scrPane, BorderLayout.CENTER );
        c.doLayout();

        type    = "text/javascript";
        //type    = "text/java";
        codeEditor.setContentType ( type );

        //text        = "public static void main(String[] args) {\n}";
        fileName    = "/home/svj/Soft/java_editors/js_sample/SocialHistory.js";
        try
        {
            text    = FileTools.loadFile ( fileName, null );
        } catch ( SvjException e )   {
            text = e.toString();
            e.printStackTrace();
        }
        codeEditor.setText( text );

        f.setSize ( 800, 600 );
        //f.setVisible(true);
        f.setDefaultCloseOperation ( WindowConstants.EXIT_ON_CLOSE );
    }

}
