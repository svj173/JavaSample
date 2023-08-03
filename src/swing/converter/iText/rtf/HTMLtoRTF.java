package swing.converter.iText.rtf;


/**
 * Use  itextrtf.sourceforge.net
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 27.02.2013 14:04
 */

import com.lowagie.text.DocumentException;

//import com.lowagie.text.rtf.*;

public class HTMLtoRTF
{
    public static void main ( String[] args ) throws DocumentException
    {
        /*
        Document document = new Document ();

        try
        {
            Reader htmlreader = new BufferedReader ( ( new InputStreamReader ( ( new FileInputStream ( "C:\\Users\\asrikantan\\Desktop\\sample.htm" ) ) ) ) );

            RtfWriter2 rtfWriter = RtfWriter2.getInstance ( document, new FileOutputStream ( ( "C:\\Users\\asrikantan\\Desktop\\sample12.rtf" ) ) );
            document.open ();
            document.add ( new Paragraph ( "Testing simple paragraph addition." ) );
            //ByteArrayOutputStream out = new ByteArrayOutputStream();

            StyleSheet styles = new StyleSheet ();
            styles.loadTagStyle ( "body", "font", "Bitstream Vera Sans" );
            ArrayList htmlParser = HTMLWorker.parseToList ( htmlreader, styles );
            //fetch HTML line by line

            for ( int htmlDatacntr = 0; htmlDatacntr < htmlParser.size (); htmlDatacntr++ )
            {
                Element htmlDataElement = ( Element ) htmlParser.get ( htmlDatacntr );
                document.add ( ( htmlDataElement ) );
            }
            htmlreader.close ();
            document.close ();

        } catch ( FileNotFoundException e )         {
            e.printStackTrace ();
        } catch ( Exception e )         {
            System.out.println ( e );
        }
        */
    }
}


