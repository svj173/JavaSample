package rmi.send_printscreen;

import java.rmi.*;
import java.rmi.server.*;


/**
 * Программа-сервер, который создаёт объекты-серверы:
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 08.07.2010 11:28:15
 */
public class RemoteScreenServer {

    /** Конструктор */
    public RemoteScreenServer() throws Exception {
        RemoteScreenImpl s1 = new RemoteScreenImpl();
        Naming.rebind("getRemoteScreen", s1);
    }

    /**
    * @param args
    */
    public static void main(String[] args) {
        try{
          new RemoteScreenServer();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
