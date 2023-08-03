package svj.zip.socket;

import java.io.ObjectInputStream;
import java.util.zip.GZIPInputStream;
import java.net.Socket;

/**
 * <BR> Читает данные от сервера.
 * <BR>
 * <BR> User: svj
 * <BR> Date: 01.06.2006
 * <BR> Time: 11:47:03
 */
public class Client
{
    public static void main ( String argv[] ) throws   Exception
    {
        Socket socket;
        Object obj;
        String  remoteServerIP;
        int     PORT;
        GZIPInputStream gzipin;
        ObjectInputStream ois;

        remoteServerIP  = "localhost";
        PORT    = 3019;
        // read from server
        socket  = new Socket (remoteServerIP, PORT);
        gzipin  = new GZIPInputStream(socket.getInputStream());
        ois     = new ObjectInputStream(gzipin);
        obj     = ois.readObject();

        //ois.close();
        //gzipin.close();
    }

}
