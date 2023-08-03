package key;


import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


/**
 * Работа с отслеживанием нажатий клавиш клавиатуры.
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 28.07.2011 12:20:44
 */
public class KeyAdapter_01
{
    public static void main ( String[] args )
    {
        PasswordDialog  dialog;
        int             result;
        String          newName, newPassw, msg;
        Boolean         ok;
        
        JFrame frame = new JFrame("File Chooser demo");
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing( WindowEvent e) {
                System.exit(0);
            }
        });

        // label to attach image icon to
        final JLabel label =
            new JLabel("", SwingConstants.CENTER);

        JPanel panel1 = new JPanel();

        dialog = new PasswordDialog ( frame, true);
        dialog.setNameValue ( "test-02" );

        dialog.setVisible(true);

        if ( dialog.getReturnStatus() == PasswordDialog.RET_OK )
        {
            newName  = dialog.getNameValue();
            System.out.println ( "name = " + newName );

            newPassw = dialog.getPasswordValue();
            System.out.println ( "password = " + newPassw );
        }

        /*
        frame.getContentPane().add("North", panel1);
        frame.pack();
        frame.setVisible(true);
        */
    }

}
