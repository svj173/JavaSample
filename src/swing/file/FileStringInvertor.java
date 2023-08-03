package swing.file;


import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.List;


/**
 * Программа инвертирует строки в выбранном файле.
 * <BR/> Т.е. первая строка становится последней и т.д.
 * <BR/> Сначала заливает все в память а потмо - скидывает в файл.
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 09.12.2010 14:43:26
 */
public class FileStringInvertor extends JPanel implements ActionListener
{
    public FileStringInvertor ()
    {
        super();

        //setLayout ( new  );

        JButton button = new JButton ( "Инвертировать" );
        button.addActionListener ( this );

        add ( new JLabel ( "Инвертировать файл" ) );
        add ( button );
    }

    public void actionPerformed ( ActionEvent e )
    {
        File file;
        List<String> list;

        //File saveFile = new File ( "savedimage." + format );
        JFileChooser chooser = new JFileChooser ();
        //chooser.setSelectedFile ( saveFile );
        int rval = chooser.showOpenDialog ( this );
        if ( rval == JFileChooser.APPROVE_OPTION )
        {
            file = chooser.getSelectedFile();

            list    = readFile(file);
            saveInvert ( file, list );
        }
    }

    private void saveInvert ( File file, List<String> list )
    {
        String targetFileName;
        BufferedWriter out;

        targetFileName  = file.getAbsolutePath() + "_invert.txt";

        try
        {
            out = new BufferedWriter ( new OutputStreamWriter ( new FileOutputStream ( targetFileName ) ) );

            int size = list.size() - 1;
            for ( int i=size; i>=0; i-- )
            {
                out.write ( list.get(i) );
                out.newLine ();
            }
            /*
            for ( String str : list )
            {
                out.write ( str );
            }
            */

            out.flush();
            out.close();

        } catch ( IOException e )         {
            e.printStackTrace();
        }
    }

    private List<String> readFile ( File file )
    {
        List<String> result;
        BufferedReader in;
        String str;

        result  = new ArrayList<String>();
        try
        {
            in = new BufferedReader ( new InputStreamReader ( new FileInputStream ( file ) ) );
            while ( in.ready() )
            {
                str = in.readLine();
                //System.out.println ( str );
                result.add ( str );
            }
        } catch ( IOException e )         {
            e.printStackTrace();
        }

        return result;
    }

    public static void main ( String s[] )
    {
        JFrame f = new JFrame ( "Invert text file" );
        f.addWindowListener ( new WindowAdapter()
        {
            public void windowClosing ( WindowEvent e ) {System.exit ( 0 );}
        } );
        FileStringInvertor si = new FileStringInvertor ();
        f.add("Center", si);

        f.pack ();
        f.setVisible ( true );
    }

}
