package eToken.basic;


/**
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 21.09.12 14:05
 */

import eToken.util.Util;
import iaik.pkcs.pkcs11.*;
import iaik.pkcs.pkcs11.objects.Data;

import java.io.*;

/**
 * This demo program can be used to download data to the card.
 * <p/>
 * WriteDataObjects <PKCS#11 module> <data file> <data object label> [<slot>] [<pin>]");
 * e.g.: WriteDataObjects gclib.dll data.dat \"Student Data\"");
 *
 * 1) libName
 * 2) file
 * 3) fileLabel
 * 4) [slot]
 * 5) [pin'
 *
 * @author <a href="mailto:Karl.Scheibelhofer@iaik.at"> Karl Scheibelhofer </a>
 * @version 0.1
 * @invariants
 */
public class WriteDataObjects
{

    static BufferedReader input_;

    static PrintWriter output_;

    static
    {
        try
        {
            //output_ = new PrintWriter(new FileWriter("SignAndVerify_output.txt"), true);
            output_ = new PrintWriter ( System.out, true );
            input_ = new BufferedReader ( new InputStreamReader ( System.in ) );
        } catch ( Throwable thr )
        {
            thr.printStackTrace ();
            output_ = new PrintWriter ( System.out, true );
            input_ = new BufferedReader ( new InputStreamReader ( System.in ) );
        }
    }

    public static void main ( String[] args )
            throws IOException, TokenException
    {
        if ( args.length < 3 )
        {
            printUsage ();
            System.exit ( 1 );
        }

        Module pkcs11Module = Module.getInstance ( args[ 0 ] );
        pkcs11Module.initialize ( null );

        Token token;
        if ( 3 < args.length ) token = Util.selectToken ( pkcs11Module, output_, input_, args[ 3 ] );
        else token = Util.selectToken ( pkcs11Module, output_, input_ );
        if ( token == null )
        {
            output_.println ( "We have no token to proceed. Finished." );
            output_.flush ();
            System.exit ( 0 );
        }
        TokenInfo tokenInfo = token.getTokenInfo ();

        output_
                .println ( "################################################################################" );
        output_.println ( "Information of Token:" );
        output_.println ( tokenInfo );
        output_
                .println ( "################################################################################" );

        output_
                .println ( "################################################################################" );
        output_.println ( "Reading data from: " + args[ 1 ] );
        InputStream dataInputStream = new FileInputStream ( args[ 1 ] );
        ByteArrayOutputStream bufferStream = new ByteArrayOutputStream ( 256 );

        // read the data from the file
        byte[] buffer = new byte[ 4096 ];
        int bytesRead;
        while ( ( bytesRead = dataInputStream.read ( buffer ) ) >= 0 )
        {
            bufferStream.write ( buffer, 0, bytesRead );
        }
        dataInputStream.close ();

        byte[] data = bufferStream.toByteArray ();
        output_
                .println ( "################################################################################" );

        // open an read-write user session
        Session session;
        if ( 4 < args.length ) session = Util.openAuthorizedSession ( token,
                                                                      Token.SessionReadWriteBehavior.RW_SESSION, output_, input_, args[ 4 ] );
        else session = Util.openAuthorizedSession ( token,
                                                    Token.SessionReadWriteBehavior.RW_SESSION, output_, input_, null );

        output_
                .println ( "################################################################################" );
        output_.println ( "creating data object on the card... " );
        output_.flush ();

        // create certificate object template
        Data dataObjectTemplate = new Data ();

        // we could also set the name that manages this data object
        //dataObjectTemplate.getApplication().setCharArrayValue("Application Name");

        // - label
        dataObjectTemplate.getLabel ().setCharArrayValue ( args[ 2 ].toCharArray () );

        // - data content
        dataObjectTemplate.getValue ().setByteArrayValue ( data );

        // ensure that it is stored on the token and not just in this session
        dataObjectTemplate.getToken().setBooleanValue ( Boolean.TRUE );

        // print template
        output_.println ( dataObjectTemplate );

        // create object
        session.createObject ( dataObjectTemplate );

        output_
                .println ( "################################################################################" );

        session.closeSession ();
        pkcs11Module.finalize ( null );
    }

    public static void printUsage ()
    {
        output_.println ( "Usage: DownloadData <PKCS#11 module> <data file> <data object label> [<slot>] [<pin>]" );
        output_.println ( " e.g.: DownloadData gclib.dll data.dat \"Student Data\"" );
        output_.println ( "The given DLL must be in the search path of the system." );
    }

}
