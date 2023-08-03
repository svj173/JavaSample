package svj.zip.socket;

import java.io.ObjectOutputStream;
import java.util.zip.GZIPOutputStream;
import java.util.Hashtable;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * <BR> Упаковать данные и Выдать клиенту.
 * <BR>
 * <BR> User: svj
 * <BR> Date: 01.06.2006
 * <BR> Time: 11:51:16
 */
public class Server
{
    public static void main (String argv[]) throws     Exception
    {
        GZIPOutputStream     gzipout;
        ObjectOutputStream   oos;
        ServerSocket        server;
        Socket              socket;
        Object              obj;

        obj     = new Hashtable();
        socket  = new Socket ();
        
        // write to client
        gzipout = new  GZIPOutputStream(socket.getOutputStream());
        oos     = new  ObjectOutputStream(gzipout);
        oos.writeObject(obj);
        gzipout.finish();

        //oos.flush();
        //oos.close();
        //gzipout.close();

    }

}
