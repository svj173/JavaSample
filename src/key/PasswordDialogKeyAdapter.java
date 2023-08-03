package key;


import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


/**
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 28.07.2011 13:08:40
 */
public class PasswordDialogKeyAdapter   extends KeyAdapter
{
    private PasswordDialog passwordDialog;

    public PasswordDialogKeyAdapter ( PasswordDialog passwordDialog )
    {
        this.passwordDialog = passwordDialog;
    }

    public void keyPressed ( KeyEvent evt )
    {
        int key, location, retCode;
        char keyChar;
        Object source;
        JButton button;
        String name;

        source  = evt.getSource ();
        System.out.println ( "source = " + source );

        keyChar  = evt.getKeyChar ();
        System.out.println ( "keyChar = " + keyChar );

        key  = evt.getKeyCode ();
        System.out.println ( "key = " + key );

        location  = evt.getKeyLocation ();
        System.out.println ( "location = " + location );

        // Анализируем источник - текстовое поле, кнопки.
        retCode = PasswordDialog.RET_OK;     // Для текстовых полей
        if ( source instanceof JButton )
        {
            button  = ( JButton ) source;
            name = button.getName ();
            System.out.println ( "name = " + name );
            System.out.println ( "text = " + button.getText() );
            if ( name.equals ( "Auth" ))
                retCode = PasswordDialog.RET_OK;
            else
                retCode = PasswordDialog.RET_CANCEL;
        }


        switch ( key )
        {
            case KeyEvent.VK_ENTER :
                passwordDialog.doClose ( retCode );
                break;
            case KeyEvent.VK_ESCAPE :
                passwordDialog.doClose ( PasswordDialog.RET_CANCEL );
                break;
            // нажатие остальных кнопок игнорируем
        }
    }
}
