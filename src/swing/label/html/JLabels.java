package swing.label.html;


import java.awt.*;
import javax.swing.*;

/**
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 13.07.2010 17:28:23
 */
public class JLabels extends JFrame
{
    public static void main ( String[] args )
    {
        new JLabels ();
    }

    public JLabels ()
    {
        super ( "Using HTML in JLabels" );

        //WindowUtilities.setNativeLookAndFeel ();
        //addWindowListener ( new ExitListener () );
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Container content = getContentPane ();
        Font font = new Font ( "Serif", Font.PLAIN, 30 );
        content.setFont ( font );


        String labelText =
                "<html><body bgcolor='red'><FONT COLOR=RED>Red</FONT> and " +
                        "<FONT COLOR=BLUE>Blue</FONT> Text</body></html>";
        JLabel coloredLabel = new JLabel ( labelText, JLabel.CENTER );
        coloredLabel.setBackground ( Color.BLUE );
        coloredLabel.setBorder ( BorderFactory.createTitledBorder ( "Mixed Colors" ) );
        content.add ( coloredLabel, BorderLayout.NORTH );


        //labelText = "<html><B>Bold</B> and <I>Italic</I> Text</html>";
        labelText = "------------- ============== ++++++++++++++++++++ ********************** '''''''''''''''' ";
        JLabel boldLabel =
                new JLabel ( labelText, JLabel.CENTER );
        boldLabel.setBorder  ( BorderFactory.createTitledBorder ( "Mixed Fonts" ) );
        boldLabel.setBackground ( Color.YELLOW );
        content.add ( boldLabel, BorderLayout.CENTER );


        JScrollPane scrollPane = new javax.swing.JScrollPane();
        scrollPane.setAutoscrolls(true);
        
        labelText =
                "<html><body bgcolor='green'>The Applied Physics Laboratory is a division " +
                        "of the Johns Hopkins University." +
                        "<P>" +
                        "Major JHU divisions include:" +
                        "<UL>" +
                        "  <LI>The Applied Physics Laboratory" +
                        "  <LI>The Krieger School of Arts and Sciences" +
                        "  <LI>The Whiting School of Engineering" +
                        "  <LI>The School of Medicine" +
                        "  <LI>The School of Public Health" +
                        "  <LI>The School of Nursing" +
                        "  <LI>The Peabody Institute" +
                        "  <LI>The Nitze School of Advanced International Studies" +
                        "</UL></body>";
        JLabel fancyLabel = new JLabel ( labelText, new ImageIcon ( "images/JHUAPL.gif" ), JLabel.CENTER );
        fancyLabel.setBorder ( BorderFactory.createTitledBorder ( "Multi-line HTML" ) );
        fancyLabel.setBackground ( Color.GREEN );
        scrollPane.setViewportView(fancyLabel);
        //content.add ( fancyLabel, BorderLayout.SOUTH );
        content.add ( scrollPane, BorderLayout.SOUTH );

        pack ();
        setVisible ( true );
    }
}



