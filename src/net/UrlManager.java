package net;


import exception.SvjException;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 01.12.2017 13:09
 */
public class UrlManager
{
    public String loadDataByUrl ( String urlStr ) throws SvjException
    {
        String              line;
        StringBuilder       sb;
        URL                 lab;
        InputStreamReader   isr;
        BufferedReader      d;

        sb  = new StringBuilder ( 128 );
        try
        {
            // Выдать URL запрос
            lab     = new URL ( urlStr );
            isr     = new InputStreamReader ( lab.openStream() );
            d       = new BufferedReader ( isr );

            while ( ( line = d.readLine() ) != null )
            {
                sb.append ( line );
                sb.append ( '\n' );
            }

        } catch ( Exception e )                  {
            e.printStackTrace();
            //LogWriter.l.error ( "NbHandler error", e );
        }

        return sb.toString();
    }

}
