package swing.editor.html.html2;


/**
 * Парсер файлов HTML. (Валидатор)
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 14.07.2011 13:37:58
 */

import javax.swing.text.MutableAttributeSet;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.parser.ParserDelegator;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;

/**
 * This small demo program shows how to use the
 * HTMLEditorKit.Parser and its implementing class
 * ParserDelegator in the Swing system.
 */

public class HtmlParseDemo
{
    public static void main ( String[] args )
    {
        Reader r;
        String spec;

        /*
        if ( args.length == 0 )
        {
            System.err.println ( "Usage: java HTMLParseDemo [url | file]" );
            System.exit ( 0 );
        }
        spec = args[ 0 ];
        */
        spec = "/home/svj/projects/SVJ/JavaSample/test/html/samle01.html";

        try
        {
            if ( spec.indexOf ( "://" ) > 0 )
            {
                URL u = new URL ( spec );
                Object content = u.getContent ();
                if ( content instanceof InputStream )
                {
                    r = new InputStreamReader ( ( InputStream ) content );
                }
                else if ( content instanceof Reader )
                {
                    r = ( Reader ) content;
                }
                else
                {
                    throw new Exception ( "Bad URL content type." );
                }
            }
            else
            {
                r = new FileReader ( spec );
            }

            HTMLEditorKit.Parser parser;
            System.out.println ( "About to parse " + spec );
            parser = new ParserDelegator ();
            parser.parse ( r, new HTMLParseLister (), true );
            r.close ();
        }
        catch ( Exception e )
        {
            System.err.println ( "Error: " + e );
            e.printStackTrace ( System.err );
        }
    }
}

/**
 * HTML parsing proceeds by calling a callback for
 * each and every piece of the HTML do*****ent.  This
 * simple callback class simply prints an indented
 * structural listing of the HTML data.
 */
class HTMLParseLister extends HTMLEditorKit.ParserCallback
{
    int indentSize = 0;

    protected void indent ()
    {
        indentSize += 3;
    }

    protected void unIndent ()
    {
        indentSize -= 3;
        if ( indentSize < 0 ) indentSize = 0;
    }

    protected void pIndent ()
    {
        for ( int i = 0; i < indentSize; i++ ) System.out.print ( " " );
    }

    public void handleText ( char[] data, int pos )
    {
        pIndent ();
        System.out.println ( "Text(" + data.length + " chars)" );
    }

    public void handleComment ( char[] data, int pos )
    {
        pIndent ();
        System.out.println ( "Comment(" + data.length + " chars)" );
    }

    public void handleStartTag ( HTML.Tag t, MutableAttributeSet a, int pos )
    {
        pIndent ();
        System.out.println ( "Tag start(<" + t.toString () + ">, " +  a.getAttributeCount () + " attrs)" );
        indent ();
    }

    public void handleEndTag ( HTML.Tag t, int pos )
    {
        unIndent ();
        pIndent ();
        System.out.println ( "Tag end(</" + t.toString () + ">)" );
    }

    public void handleSimpleTag ( HTML.Tag t, MutableAttributeSet a, int pos )
    {
        pIndent ();
        System.out.println ( "Tag(<" + t.toString () + ">, " +  a.getAttributeCount () + " attrs)" );
    }

    public void handleError ( String errorMsg, int pos )
    {
        System.out.println ( "Parsing error: " + errorMsg + " at " + pos );
    }

}
