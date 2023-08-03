package net;


import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;


/**
 * Получить IP адрес своего компа
 * <BR/>
 * <BR/> Можно ещё попробовать использовать InetAddress.getLocalHost(), но на разных операционках он ведёт себя по-разному.
 * У меня под Linux'ом он всегда выдаёт 127.0.0.1. А под виндой - нормальный адрес.
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 23.12.2010 15:16:41
 */
public class GetHost
{
    public static void main ( String[] args )
    {
        InetAddress addr;
        InetAddress SW[];
        try
        {
            //Получить инфу о локальном компе
            addr = InetAddress.getLocalHost();
            //String host = addr.getHostName();   // здесь хост резолвится и если не найден - ругается Unknow

            // Get hostname by textual representation of IP address
            addr = InetAddress.getByName ( "127.0.0.1" );

            // Get hostname by a byte array containing the IP address
            byte[] ipAddr = new byte[] { 127, 0, 0, 1 };
            addr = InetAddress.getByAddress ( ipAddr );

            // Get the host name
            String hostname = addr.getHostName();

            // Get canonical host name
            String hostnameCanonical = addr.getCanonicalHostName ();

            // Если у компа несколько IP  - здесь только localhost - остальные адреса компа НЕ берет
            SW = InetAddress.getAllByName("localhost");
            for (int i=0; i<SW.length; i++)
                System.out.println(SW[i]);

            // Если у компа несколько IP
            SW = InetAddress.getAllByName("www.nba.com");
            for (int i=0; i<SW.length; i++)
                System.out.println(SW[i]);

            // здесь автоматом идет проверка на парвильность адреса, протокола и т.д.
            try
            {
                URL http = new URL ( "http://www.cs.utexas.edu:80/index.html" );
                URL ftp = new URL ( "ftp", "prep.ai.mit.edu", 21, "/pub/gnu/gcl" );
            } catch ( MalformedURLException e )            {
                e.printStackTrace ();
            }

        } catch ( Exception e )        {
            e.printStackTrace ();
        }
    }
}
