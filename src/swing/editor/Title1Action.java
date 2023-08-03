package swing.editor;

import javax.swing.text.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.*;

/**
 * <BR> User: Zhiganov
 * <BR> Date: 25.09.2007
 * <BR> Time: 17:45:24
 */
public class Title1Action extends StyledEditorKit.StyledTextAction
{
    private StyleSet    title2;
    private SimpleAttributeSet    title1;

    public Title1Action()
    {
        super("font-bold");
        //title1  = new StyleSet("title1");
        title1  = new SimpleAttributeSet();
        title1.addAttribute("titleName", "title1");
        //title1.setName("title1");
        StyleConstants.setBold(title1, true);
        //StyleConstants.setAlignment(title1,StyleConstants.ALIGN_CENTER);
        StyleConstants.setForeground(title1, Color.RED );
    }

    /**
     * Toggles the bold attribute.
     *
     * @param e the action event
     */
    public void actionPerformed(ActionEvent e)
    {
        JEditorPane editor = getEditor(e);
        if (editor != null)
        {                                   
            StyledEditorKit kit = getStyledEditorKit(editor);
            // Получает атрибуты данной текстовой точки?
            MutableAttributeSet attr = kit.getInputAttributes();
            System.out.println("attr = " + attr);
            Class classA;
            classA  = attr.getClass();
            System.out.println("attr class = " + classA.getName() );
            System.out.println("attr getDeclaringClass = " + classA.getDeclaringClass() );
            if ( attr instanceof StyleSet )
            {
                System.out.println("it is StyleSet" );
            }
            if ( attr instanceof SimpleAttributeSet )
            {
                System.out.println("it is SimpleAttributeSet" );
            }
            //boolean bold = (StyleConstants.isBold(attr)) ? false : true;
            //SimpleAttributeSet sas = new SimpleAttributeSet();
            //StyleConstants.setBold(sas, bold);
            setCharacterAttributes(editor, title1, false);

            //
            Document doc    = editor.getDocument();
            System.out.println("doc = " + doc );
            Element el  = doc.getDefaultRootElement();
            String str  = printElement (el, "0");
            System.out.println( str );

        }
    }

    private String printElement ( Element element, String nm )
    {
        StringBuffer result;
        String  name;

        result  = new StringBuffer(512);
        name    = element.getName();
        result.append ( nm + ". element = " + element + ", name = " + name  );

        if ( name.equals(AbstractDocument.ContentElementName ))
        {
            // Это конечный элемент -  с текстом. Анализировать.
        }
        
        AttributeSet st = element.getAttributes();
        result.append ( ", titleName = " );
        result.append ( st.getAttribute("titleName") );
        //result.append ( ", text = " );
        Document ed = element.getDocument();
        String text;
        try {
            int istart, iend, ic;
            istart  = element.getStartOffset();
            iend    = element.getEndOffset();
            ic      = iend - istart;
            result.append ( ", istart = " );
            result.append ( istart );
            result.append ( ", iend = " );
            result.append ( iend );
            result.append ( ", ic = " );
            result.append ( ic );
            text    = ed.getText(istart,ic);
        } catch (BadLocationException e) {
            text = "error";
            //e.printStackTrace();
        }
        result.append ( ", text = '" );
        result.append ( text );
        if ( text.length() == 1 )
        {
            result.append ( ", char = " );
            result.append ( (int) text.charAt(0) );
        }
        result.append ( "'\n" );
        int isize   = element.getElementCount();
        Element el2;
        int ic2;
        for ( int i=0; i<isize; i++ )
        {
            el2 = element.getElement(i);
            result.append(printElement(el2, nm + "." + i) );
        }

        return result.toString();

    }

}
