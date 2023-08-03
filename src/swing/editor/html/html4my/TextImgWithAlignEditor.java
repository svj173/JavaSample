package swing.editor.html.html4my;


import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;


/**
 * Текстовый редактор с использованием стилей и возможностью вставлять картинки.
 * <BR/> Здесь же отрабатывается смещение (красная строка)
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 14.07.2011 14:21:42
 */
public class TextImgWithAlignEditor extends JFrame //implements ActionListener
{
    private String newline = "\n";

    public TextImgWithAlignEditor ()
    {
        setLayout ( new BorderLayout () );

        //Create a text pane.
        JTextPane textPane = createTextPane ();
        JScrollPane paneScrollPane = new JScrollPane ( textPane );
        paneScrollPane.setVerticalScrollBarPolicy (
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS );
        paneScrollPane.setPreferredSize ( new Dimension ( 250, 155 ) );
        paneScrollPane.setMinimumSize ( new Dimension ( 10, 10 ) );

        add ( paneScrollPane, BorderLayout.CENTER );

        // вешаем меню с возможностью доабвления картинко и изменения стиля у выбранного текста.
        JMenuBar mb = new JMenuBar();

        //JMenu editMenu = createEditMenu();
        //mb.add(editMenu);

        JMenu alignMenu = createAlignMenu();
        mb.add(alignMenu);

        JMenu styleMenu = createStyleMenu();
        mb.add(styleMenu);

        JMenu marginMenu = createRightMarginMenu();
        mb.add(marginMenu);

        setJMenuBar(mb);


        // Установить точку вывода
        setLocation ( 10, 10 );

        // Установить размер - если размер не был взят из сохраняемых параметров при загрузке книги.
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize ();
        int x, y;
        x   = d.width / 2;
        y   = d.height - 100;
        //Dimension size  = new Dimension ( x,y );
        //setSize ( x,y );
        setSize ( 250,400 );

    }

    private JMenu createAlignMenu ()
    {
        JMenu menu;
        JMenuItem item;

        menu = new JMenu("Align");

        Action action = new StyledEditorKit.BoldAction();
        action.putValue(Action.NAME, "Bold");
        menu.add(action);
        
        item    = new JMenuItem ( "Left" );
        action  = new StyledEditorKit.AlignmentAction("Left", StyleConstants.ALIGN_LEFT );
        item.addActionListener ( action );
        //item.addActionListener ( resetStyle );
        menu.add(item);
        //  1
        item    = new JMenuItem ( "Center" );
        action  = new StyledEditorKit.AlignmentAction("Center", StyleConstants.ALIGN_CENTER );
        item.addActionListener ( action );
        //item.addActionListener ( resetStyle );
        menu.add(item);
        //  2
        item    = new JMenuItem ( "Right" );
        action  = new StyledEditorKit.AlignmentAction("Right", StyleConstants.ALIGN_RIGHT );
        item.addActionListener ( action );
        //item.addActionListener ( resetStyle );
        menu.add(item);
        //  3
        item    = new JMenuItem ( "Justify" );
        action  = new StyledEditorKit.AlignmentAction("Justify", StyleConstants.ALIGN_JUSTIFIED );
        item.addActionListener ( action );
        //item.addActionListener ( resetStyle );
        //StyledEditorKit.beginLineAction
        menu.add(item);

        return menu;
    }

    private JMenu createRightMarginMenu ()
    {
        JMenu menu;
        JMenuItem item;
        Action action;

        menu = new JMenu("RightMargin");

        item    = new JMenuItem ( "1" );
        item.addActionListener ( new RightMarginAction ("1",1) );
        menu.add(item);

        item    = new JMenuItem ( "3" );
        item.addActionListener ( new RightMarginAction ("3",3) );
        menu.add(item);

        item    = new JMenuItem ( "5" );
        item.addActionListener ( new RightMarginAction ("5",5) );
        menu.add(item);

        return menu;
    }

    public void init ()
    {
        // Перерисовать меню согласно установленному языку
        rewriteMenu();
    }

    protected JMenu createStyleMenu()
    {
        JMenu menu = new JMenu("Style");

        Action action = new StyledEditorKit.BoldAction();
        action.putValue(Action.NAME, "Bold");
        menu.add(action);

        action = new StyledEditorKit.ItalicAction();
        action.putValue(Action.NAME, "Italic");
        menu.add(action);

        action = new StyledEditorKit.UnderlineAction();
        action.putValue(Action.NAME, "Underline");
        menu.add(action);

        menu.addSeparator();

        menu.add(new StyledEditorKit.FontSizeAction("12", 12));
        menu.add(new StyledEditorKit.FontSizeAction("14", 14));
        menu.add(new StyledEditorKit.FontSizeAction("18", 18));

        menu.addSeparator();

        menu.add(new StyledEditorKit.FontFamilyAction("Serif", "Serif"));
        menu.add(new StyledEditorKit.FontFamilyAction("SansSerif", "SansSerif"));

        menu.addSeparator();

        menu.add(new StyledEditorKit.ForegroundAction("Red", Color.red));
        menu.add(new StyledEditorKit.ForegroundAction("Green", Color.green));
        menu.add(new StyledEditorKit.ForegroundAction("Blue", Color.blue));
        menu.add(new StyledEditorKit.ForegroundAction("Black", Color.black));

        return menu;
    }

    /**
     * Перерисовать согласно установленному языку
     */
    public void rewriteMenu()
    {
        // todo Взять меню
        //WEMenuBar   menu    = (WEMenuBar) getRootPane().getJMenuBar();
        //menu.rewrite();
        // Оглавление
        //contentPanel.rewrite ();
    }

    private JTextPane createTextPane ()
    {
        String[] initString =
                {
                        "This is an editable JTextPane, ",            //regular
                        "another ",                                   //italic
                        "styled ",                                    //bold
                        "text ",                                      //small
                        "component, ",                                //large
                        "which supports embedded components..." + newline,//regular
                        " " + newline,                                //button
                        "...and embedded icons..." + newline,         //regular
                        " ",                                          //icon
                        newline + "JTextPane is a subclass of JEditorPane that " +
                                "uses a StyledEditorKit and StyledDocument, and provides " +
                                "cover methods for interacting with those objects."
                };

        String[] initStyles =
                {
                        "regular", "italic", "bold", "small", "large",
                        "regular", "button", "regular", "icon",
                        "align"
                };

        JTextPane textPane = new JTextPane ();
        StyledDocument doc = textPane.getStyledDocument();
        addStylesToDocument ( doc );

        int offset, p0, p1;
        Style style;
        //JEditorPane editor;

        MutableAttributeSet attrAlign = new SimpleAttributeSet();
        StyleConstants.setFirstLineIndent ( attrAlign, 10 );
        StyleConstants.setAlignment ( attrAlign, StyleConstants.ALIGN_JUSTIFIED );

        try
        {
            for ( int i = 0; i < initString.length; i++ )
            {
                //logger.debug (" - " + i + ". str = " + initString[i]
                //System.out.println ( " - " + i + ". str = " + initString[ i ]+ ", styleName = " + initStyles[ i ] + ", Style = " + doc.getStyle ( initStyles[ i ] ) );
                offset  = doc.getLength();
                // предыдущая граница
                p0      = offset;
                style   = doc.getStyle ( initStyles[ i ] );
                doc.insertString ( offset, initString[i], style );
                if ( initStyles[i].equals ( "align" ))
                {
                    // это параграф - обновить текст на экране (иначе не отобразится - смещение)
                    //setParagraphAttributes ( textPane, attr, false);
                    //int p0 = textPane.getSelectionStart();
                    //int p1 = textPane.getSelectionEnd();
                    p1  = doc.getLength();
                    // int offset, int length, AttributeSet s, boolean replace - очищать предыдущий стиль или нет.
                    doc.setParagraphAttributes(p0, p1 - p0, attrAlign, false );
                }
            }

            // Попытка создать параграф - с левым смещением, но так чтобы в середине его был текст, выделенный другим цветом.
            p0 = doc.getLength();
            // - Простой текст
            style   = doc.getStyle ( "italic" );
            doc.insertString ( doc.getLength(), "\nПростой italic текст. ", style );
            // - Цветной текст
            style   = doc.getStyle ( "red_color");
            doc.insertString ( doc.getLength(), "\nКрасный текст. ", style );
            // - Простой текст
            style   = doc.getStyle ( "regular" );
            doc.insertString ( doc.getLength(), "Снова простой regular текст. ", style );
            // - Говорим что все три текста - это один параграф
            p1  = doc.getLength();
            doc.setParagraphAttributes(p0, p1 - p0, attrAlign, false );

            // ПС. Если в указанном параграфе несколько переводов строк - то все они будут оформлены как абзацы.
            //  - Т.е. можно накладывать параграф на весь текст - который не является другим параграфом (по умолчанию)
            // - т.е. счетчик начала и конца для параграфов - по умолчанию, заданный и т.д.

        } catch ( BadLocationException ble )         {
            System.err.println ( "Couldn't insert initial text into text pane." );
        }

        return textPane;
    }

    protected void addStylesToDocument ( StyledDocument doc )
    {
        //Initialize some styles.
        Style def = StyleContext.getDefaultStyleContext().getStyle ( StyleContext.DEFAULT_STYLE );

        Style regular = doc.addStyle ( "regular", def );
        StyleConstants.setFontFamily ( def, "SansSerif" );

        Style s = doc.addStyle ( "italic", regular );
        StyleConstants.setItalic ( s, true );

        s = doc.addStyle ( "bold", regular );
        StyleConstants.setBold ( s, true );

        s = doc.addStyle ( "align", regular );
        StyleConstants.setAlignment ( s, StyleConstants.ALIGN_JUSTIFIED );
        //SimpleAttributeSet result  = new SimpleAttributeSet ();

        //StyleConstants.setFirstLineIndent ( s, 5 );

        s = doc.addStyle ( "small", regular );
        StyleConstants.setFontSize ( s, 10 );

        s = doc.addStyle ( "large", regular );
        StyleConstants.setFontSize ( s, 16 );

        s = doc.addStyle ( "red_color", regular );
        StyleConstants.setForeground ( s, Color.RED );

        s = doc.addStyle ( "icon", regular );
        StyleConstants.setAlignment ( s, StyleConstants.ALIGN_CENTER );
        ImageIcon pigIcon = createImageIcon ( "images/Pig.gif", "a cute pig" );
        if ( pigIcon != null )
        {
            StyleConstants.setIcon ( s, pigIcon );
        }

        /*
        s = doc.addStyle("button", regular);
        StyleConstants.setAlignment(s, StyleConstants.ALIGN_CENTER);
        ImageIcon soundIcon = createImageIcon("images/sound.gif","sound icon");
        JButton button = new JButton();
        if (soundIcon != null) {
            button.setIcon(soundIcon);
        } else {
            button.setText("BEEP");
        }
        button.setCursor(Cursor.getDefaultCursor());
        button.setMargin(new Insets(0,0,0,0));
        button.setActionCommand(buttonString);
        button.addActionListener(this);
        StyleConstants.setComponent(s, button);
        */
    }

    /**
     * Returns an ImageIcon, or null if the path was invalid.
     */
    protected static ImageIcon createImageIcon ( String path, String description )
    {
        java.net.URL imgURL = TextImgWithAlignEditor.class.getResource ( path );
        if ( imgURL != null )
        {
            return new ImageIcon ( imgURL, description );
        }
        else
        {
            System.err.println ( "Couldn't find file: " + path );
            return null;
        }
    }

    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event dispatch thread.
     */
    private static void createAndShowGUI ()
    {
        //Create and set up the window.
        JFrame frame;

        //frame = new JFrame ( "TextSamplerDemo" );
        frame = new TextImgWithAlignEditor ();
        frame.setDefaultCloseOperation ( JFrame.EXIT_ON_CLOSE );

        //Add content to the window.
        //frame.add ( new TextImgEditor () );

        // Повесить функцию на крестик
        //WEditWindowAdapter cwa   = new WEditWindowAdapter ();
        //addWindowListener ( cwa );

        //Display the window.
        //frame.pack ();
        frame.setVisible ( true );

        /*
        final TextImgEditor frame = new TextImgEditor();
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing( WindowEvent e) {
                System.exit(0);
            }
            public void windowActivated(WindowEvent e) {
                //frame.textPane.requestFocus();
            }
        });

        frame.pack();
        frame.setVisible(true);
        */
    }

    public static void main ( String[] args )
    {
        //Schedule a job for the event dispatching thread:
        //creating and showing this application's GUI.
        SwingUtilities.invokeLater ( new Runnable()
        {
            public void run ()
            {
                //Turn off metal's use of bold fonts
                UIManager.put ( "swing.boldMetal", Boolean.FALSE );
                createAndShowGUI ();
            }
        } );


    }

}
