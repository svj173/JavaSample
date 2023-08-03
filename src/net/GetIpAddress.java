package net;


import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;


/**
 * Получить IP адрес своего компа
 * <BR/> Исп железный метод опроса своих адресов.
 * <BR/>
 * <BR/> Можно ещё попробовать использовать InetAddress.getLocalHost(), но на разных операционках он ведёт себя по-разному.
 * У меня под Linux'ом он всегда выдаёт 127.0.0.1. А под виндой - нормальный адрес.
 * <BR/>
 * <BR/> Вопрос: Как узнать основной IP? Под именем eth0 - но не обязательно.
 * <BR/>
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 23.12.2010 15:16:41
 */
public class GetIpAddress
{
    public static void main ( String[] args )
    {
        InetAddress addr;
        InetAddress SW[];
        try
        {
            Enumeration networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaces.hasMoreElements())
            {
                NetworkInterface networkInterface = ( NetworkInterface ) networkInterfaces.nextElement();
                System.out.println("networkInterface.displayName=" + networkInterface.getDisplayName());
                System.out.println("networkInterface.name=" + networkInterface.getName());
                Enumeration inetAddresses = networkInterface.getInetAddresses();
                while (inetAddresses.hasMoreElements()) {
                    InetAddress inetAddress = (InetAddress) inetAddresses.nextElement();
                    System.out.println("networkInterface.[" + networkInterface.getName() + "].inetAddress=" + inetAddress);
                }
                System.out.println("-----------------------------------");
            }

        } catch ( Exception e )        {
            e.printStackTrace ();
        }
    }
}
