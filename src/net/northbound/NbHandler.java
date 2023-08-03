package net.northbound;


import exception.SvjException;
import thread.IHandler;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * Тест для проверки работы Northbound под нагрузкой.
 * <BR/> Постоянно генерится запрос на чтение параметров: http://192.168.26.69:8080/northbound/getDeviceSnmpAttr?ip=192.168.16.223
 * <BR/>
 * <BR/>  <msg>OK</msg>
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 11.09.2013 14:24
 */
public class NbHandler implements IHandler<String>
{
    private  final String url; //

    public NbHandler ( String url )
    {
        this.url   = url;
    }

    @Override
    public String handle ( Object... obj ) throws SvjException
    {
        String              result, line;
        StringBuilder       sb;
        URL                 lab;
        InputStreamReader   isr;
        BufferedReader      d;

        result  = null;
        try
        {
            // Выдать URL запрос
            lab     = new URL ( url );
            isr     = new InputStreamReader ( lab.openStream() );
            d       = new BufferedReader ( isr );

            sb  = new StringBuilder ( 64 );
            while ( ( line = d.readLine() ) != null )
            {
                //System.out.println ( line );
                if ( line.contains ( "<msg>OK</msg>" ) )
                {
                    result = "OK";
                    break;
                }
                else
                {
                    /*
                    if ( line == null )
                        sb.append ( "Null" );
                    else
                        sb.append ( line );
                    */
                    sb.append ( line );
                    sb.append ( '\n' );
                }
            }

            if ( result == null)  result = sb.toString();

            NbTestTools.incAllCounter ();
            if ( ! result.equals ( "OK" ) )   NbTestTools.incErrCounter();

        } catch ( Exception e )                  {
            e.printStackTrace();
            //LogWriter.l.error ( "NbHandler error", e );
        }

        return result;
    }

}
