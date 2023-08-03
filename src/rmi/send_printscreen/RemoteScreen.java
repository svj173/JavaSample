package rmi.send_printscreen;

import java.rmi.*;
import javax.swing.*;

/**
 * Удаленный интерфейс:
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 08.07.2010 11:29:06
 */
public interface RemoteScreen extends Remote{

    ImageIcon getIcon() throws RemoteException;

}
