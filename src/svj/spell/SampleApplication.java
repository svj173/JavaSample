package svj.spell;

import com.inet.jortho.FileUserDictionary;
import com.inet.jortho.SpellChecker;

import javax.swing.*;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 11.06.2019 16:41
 */
public class SampleApplication extends JFrame{

    public static void main(String[] args){
        new SampleApplication().setVisible( true );
    }

    private SampleApplication(){
        // Build the test frame for the sample
        super("JOrtho Sample");
        JEditorPane text = new JTextPane();
        text.setText( "This is a simppler textt with spellingg errors." );
        add( text );
        setSize(200, 160);
        setDefaultCloseOperation( EXIT_ON_CLOSE );
        setLocationRelativeTo( null );

        // Create user dictionary in the current working directory of your application
        SpellChecker.setUserDictionaryProvider( new FileUserDictionary() );

        // Load the configuration from the file dictionaries.cnf and
        // use the current locale or the first language as default
        // You can download the dictionary files from http://sourceforge.net/projects/jortho/files/Dictionaries/
        try {
            URL url = new File("/home/svj/projects/SVJ/JavaSample/lib/spell/dictionaries.cnf").toURI().toURL();
            //SpellChecker.registerDictionaries( null, null );   // 2) en, ru
            SpellChecker.registerDictionaries( url, null );

            // enable the spell checking on the text component with all features
            SpellChecker.register( text );

            // Когда страница закрывается (удаляется с экрана)
            //SpellChecker.unregister( text );

        } catch (MalformedURLException e) {
            JTextArea textArea = new JTextArea("Error = "+e.getMessage());
            add ( textArea );
        }

        /*
Here, messageWriter is JEditor pane. Refer to documentation explanation.
Put the dictionaries.cnf and dictionary_en.ortho files inside src/dictionary folder.

You can also manipulate the pop-up menu options. Here is an example what I have done:

    SpellCheckerOptions sco=new SpellCheckerOptions();
    sco.setCaseSensitive(true);
    sco.setSuggestionsLimitMenu(10);
    JPopupMenu popup = SpellChecker.createCheckerPopup(sco);
    messageWriter.addMouseListener(new PopupListener(popup));
         */
    }
}

