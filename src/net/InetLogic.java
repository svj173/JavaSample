package net;


/**
 * демонстрирует при наличии Internet-соединения, как получить IP-адрес текущего компьютера и IP-адрес из имени домена
 * с помощью сервера имен доменов (DNS), к которому обращается метод getByName () класса InetAddress.
 * <BR/>
 * <BR/>
 * В результате будет выведено, например:

Мой IP -> 172.17.16.14

BSU -> 217.21.43.10
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 23.12.2010 14:54:01
 */

import java.net.*;

public class InetLogic
{

    public static void main ( String[] args )
    {

        InetAddress myIP = null;

        InetAddress bsuIP = null;

        try
        {

            myIP = InetAddress.getLocalHost ();

// вывод IP-адреса локального компьютера

            System.out.println ( "Мой IP -> " + myIP.getHostAddress () );

            bsuIP = InetAddress.getByName ( "www.bsu.by" );

// вывод IP-адреса БГУ www.bsu.by

            System.out.println ( "BSU -> " + bsuIP.getHostAddress () );

        } catch ( UnknownHostException e )
        {

// если не удается найти IP

            e.printStackTrace ();

        }

    }

}
