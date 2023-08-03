package swing.editor.html.html3;


/**
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 14.07.2011 13:57:19
 */
// A simple extension of the HTMLEditor kit that uses a verbose ViewFactory.

import javax.swing.*;
import javax.swing.text.*;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;

public class DebugHTMLEditorKit extends HTMLEditorKit
{
    public static HTML.Tag ORA = new HTML.UnknownTag ( "ora" );
    public static AttributeSet currentAnchor;

    public void install ( JEditorPane paneEditor )
    {
        super.install ( paneEditor );
        StyleSheet ss = getStyleSheet ();
        java.util.Enumeration e = ss.getStyleNames ();
        while ( e.hasMoreElements () )
        {
            System.out.println ( e.nextElement () );
        }
    }

    public ViewFactory getViewFactory ()
    {
        return new VerboseViewFactory ();
    }

    public static class VerboseViewFactory extends HTMLEditorKit.HTMLFactory
    {
        public View create ( Element elem )
        {
            System.out.print ( "Element: " + elem.getName () );
            Object o = elem.getAttributes ().
                    getAttribute ( StyleConstants.NameAttribute );
            HTML.Tag kind = ( HTML.Tag ) o;
            System.out.println ( " view as: " + o );
            dumpElementAttributes ( elem );
            return super.create ( elem );
        }

        private void dumpElementAttributes ( Element elem )
        {
            AttributeSet attrs = elem.getAttributes ();
            java.util.Enumeration names = attrs.getAttributeNames ();
            while ( names.hasMoreElements () )
            {
                Object key = names.nextElement ();
                System.out.println ( "  " + key + " : " + attrs.getAttribute ( key ) );
            }
            try
            {
                System.out.println ( "  " +  elem.getDocument().getText ( elem.getStartOffset(),  elem.getEndOffset() ) );
            } catch ( Exception e )
            { // We don't deal with null elements for now.
            }
        }
    }
}