package swing.editor;

import org.apache.logging.log4j.*;

import javax.swing.event.DocumentListener;
import javax.swing.event.DocumentEvent;
import javax.swing.text.Document;

/**
 * <BR> User: Zhiganov
 * <BR> Date: 25.09.2007
 * <BR> Time: 16:14:19
 */
public class MyDocumentListener implements DocumentListener
{
    private Logger logger = LogManager.getFormatterLogger ( MyDocumentListener.class );

        public void insertUpdate(DocumentEvent e) {
            System.out.println("e = " + e );
            logger.debug("e = " + e );
            //displayEditInfo(e);
        }
        public void removeUpdate(DocumentEvent e) {
            logger.debug("e = " + e );
            //displayEditInfo(e);
        }
        public void changedUpdate(DocumentEvent e) {
            logger.debug("e = " + e );
            //displayEditInfo(e);
        }
}  

