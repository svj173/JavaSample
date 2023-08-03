package comm.billacceptor;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * <BR>
 * <BR> User: svj
 * <BR> Date: 01.03.2006
 * <BR> Time: 17:07:10
 */
public class BAActionListener implements ActionListener
{
    public void actionPerformed ( ActionEvent e )
    {
        String command;

        command = e.getActionCommand ();

        if ( command.equals ( "enable") )
        {
            // Нажата кнопка - Разрешить прием купюр
            BillAcceptor.getInstance().sendCommand ( 0x3E );
        }
        if ( command.equals ( "disable") )
        {
            // Нажата кнопка - Разрешить прием купюр
            BillAcceptor.getInstance().sendCommand ( 0x5E );
        }
        if ( command.equals ( "reset") )
        {
            // Нажата кнопка - Разрешить прием купюр
            BillAcceptor.getInstance().sendCommand ( 0x30 );
        }
        if ( command.equals ( "mode") )
        {
            // Нажата кнопка - Разрешить прием купюр
            BillAcceptor.getInstance().sendCommand ( 0x0C );
        }
    }

}
