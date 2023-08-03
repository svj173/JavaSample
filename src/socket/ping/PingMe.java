package socket.ping;

import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

/**
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 16.07.2010 11:10:22
 */
public class PingMe
{
    public static void main ( String args[] ) throws IOException
    {
        PingMe ping;
        //System.out.println ( "host = " + host + "\nport = " + port + "\ncount = " + count + "\nsize = " + size + "\ndelay = " + delay );

        ping = new PingMe();
        ping.pingMe ();
    }

    public void pingMe()
    {
        DatagramChannel dgChannel = null;
        try{
          ByteBuffer send = ByteBuffer.wrap("Hello".getBytes());
          ByteBuffer receive = ByteBuffer.allocate( "Hello".getBytes().length);
          //use echo port 7
          InetSocketAddress socketAddress =  new InetSocketAddress("192.168.0.215", 7);
          dgChannel = DatagramChannel.open();
          //we have the channel non-blocking.
          dgChannel.configureBlocking(false);
          dgChannel.connect(socketAddress);
          dgChannel.send(send, socketAddress);
          /* it's non-blocking so we need some amount of delay
           * to get the response
           */
          Thread.sleep(1000);
          dgChannel.receive(receive);
          String response = new String(receive.array());
            System.out.println("response = " + response);
          if (response.equals("Hello")){
             System.out.println("Ping is alive");
          }else{
             System.out.println("No response");
          }

        } catch(Exception e){
           e.printStackTrace();
        } finally {
            if ( dgChannel != null )
            {
                try
                {
                    dgChannel.close ();
                } catch ( Exception e )                {
                    e.printStackTrace();
                }
            }
        }
    }

}
