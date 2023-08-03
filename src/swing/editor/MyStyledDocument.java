package swing.editor;

import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import java.awt.*;

public class MyStyledDocument extends DefaultStyledDocument {
    int maxCharacters;

    public MyStyledDocument() {
        maxCharacters = 1000;
    }

    public MyStyledDocument(int maxChars) {
        maxCharacters = maxChars;
    }

    public void insertString(int offs, String str, AttributeSet a)
        throws BadLocationException {

        //This rejects the entire insertion if it would make
        //the contents too long. Another option would be
        //to truncate the inserted string so the contents
        //would be exactly maxCharacters in length.
        super.insertString(offs, str, a);
        /*
        if ((getLength() + str.length()) <= maxCharacters)
            super.insertString(offs, str, a);
        else
            Toolkit.getDefaultToolkit().beep();
            */
    }
}
