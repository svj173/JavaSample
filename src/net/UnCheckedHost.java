package net;


/**
 * присваивание фиктивного имени реальному ресурсу, заданному через IP : UnCheckedHost.java
 * <BR/>
 * В результате будет выведено в случае подключения к сети Интернет:

University-> cоединение:true
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 23.12.2010 14:56:21
 */

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class UnCheckedHost
{

    public static void main ( String[] args )
    {
        // задание IP-адреса лаборатории bsu.iba.by в виде массива
        byte ip[] = { ( byte ) 217, ( byte ) 21, ( byte ) 43, ( byte ) 10 };

        try
        {

            InetAddress addr = InetAddress.getByAddress ( "University", ip );

            System.out.println ( addr.getHostName() + "-> cоединение:" + addr.isReachable ( 1000 ) );

        } catch ( UnknownHostException e )        {
            System.out.println ( "адрес недоступен" );
            e.printStackTrace ();
        } catch ( IOException e )        {
            System.out.println ( "ошибка потока" );
            e.printStackTrace ();
        }
    }
    
}
