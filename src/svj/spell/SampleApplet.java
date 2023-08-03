package svj.spell;

import com.inet.jortho.SpellChecker;

import javax.swing.*;

/**
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 11.06.2019 16:36
 */
public class SampleApplet extends JApplet {

    @Override
    public void start() {
        // Build the test frame for the sample
        JEditorPane text = new JTextPane();
        text.setText( "This is a simppler textt with spellingg errors." );
        add( text );

        // Load the configuration from the file dictionaries.cnf and
        // use the English dictionary as default
        // You can download the dictionary files from http://sourceforge.net/projects/jortho/files/Dictionaries/
        SpellChecker.registerDictionaries( getCodeBase(), "en" );

        // enable the spell checking on the text component with all features
        SpellChecker.register( text );
    }

}

