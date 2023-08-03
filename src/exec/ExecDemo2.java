package exec;


import tools.Convert;

import java.io.*;
import java.util.List;

/**
 * Проверка выполненяи команд с символами >>
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 19.01.2015 17:41
 */
public class ExecDemo2
{
    public static void main ( String[] args )
            throws IOException
    {
        String[] result;
        ExecDemo2 ex;
        String cmds, logDir;

        logDir  = "/home/svj/tmp/11";
        cmds    = "/sbin/ifconfig;ps -ax;ps -ax | grep java" ;

        ex      = new ExecDemo2 ();
        result  = ex.processCommands ( cmds, logDir );
        //ex.processCmd6 ( logDir );

        System.out.println ( "Result: process size = " + result[ 0 ] + "; errMsg = " + result[1] );
    }

    // без генерации исключений
    private String[] processCommands ( String commands, String logDir )
    {
        String[]        cmds, result;
        StringBuilder   errMsg;

        result  = new String[2];
        errMsg  = new StringBuilder ( 128 );
        // берем список команд
        if ( (commands != null) && ( ! commands.isEmpty() ) )
        {
            cmds = commands.split ( ";" );
            for ( String cmd : cmds )
            {
                System.out.println ( "--- log-files CMD = " + cmd );
                // Нарезать команду на аргументы - по пробелам
                processCmd ( cmd, logDir, errMsg );
            }
            result[0] = Integer.toString ( cmds.length );
            result[1] = errMsg.toString();
        }
        else
        {
            result[0] = null;
        }
        return result;
    }

    private void processCmd ( String cmd, String logDir, StringBuilder errMsg )
    {
        String          fileName, str;
        Process         ps;
        ProcessBuilder  builder;
        String[]        cmdList;
        int             code;

        // Сформировать имя файла результата
        fileName = createProcessFileName0 ( cmd, logDir );

        // Выполнить команду
        try
        {
            if ( cmd.contains ( " " ) )
            {
                cmdList = cmd.split ( " " );
                builder = new ProcessBuilder ( cmdList );
            }
            else
            {
                builder = new ProcessBuilder ( cmd );
            }
            builder.redirectOutput ( new File (fileName) );
            builder.redirectError ( ProcessBuilder.Redirect.INHERIT );
            //builder.redirectErrorStream(true);
            ps = builder.start();

            // ждем завершения
            code    = ps.waitFor();
            System.out.println ( "--- code = " + code );
            if ( code != 0 )
            {
                // error msg
                //System.out.println ( "Error: " + builder.redirectError() );        // PIPE
                //System.out.println ( "Error 2: " + builder.redirectError() );        // PIPE
                InputStream is = ps.getErrorStream();
                str = readInput ( is );
                errMsg.append ( "Error for '" );
                errMsg.append ( cmd );
                errMsg.append ( "': " );
                errMsg.append ( str );
                errMsg.append ( '\n' );
                //System.out.println ( "Error 3: " + str );
                /*
                //Reader reader = new InputStreamReader ( is );
                int b;
                while ( (b = is.read()) != -1 )
                {
                    System.out.print ( b );
                }
                */
            }
            // удаляем процесс - чтобы освободил файлы.
            //ps.destroy();

        } catch ( Exception ex ) {
            ex.printStackTrace ();
            //LogWriter.file.error ( Convert.concatObj ( "EMS logs: exec command error, command: ", command ), ex);
            //throw new EltexException ( null, Msg.getMessage("system.server.error.commanderror"), " : \n", ex );
            errMsg.append ( "Error for cmd:" );
            errMsg.append ( cmd );
            errMsg.append ( " : " );
            errMsg.append ( ex );
            errMsg.append ( '\n' );
        }
    }

    public String readInput ( InputStream is ) {
        StringBuilder buffer;
        buffer = new StringBuilder();
            try {
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                buffer = new StringBuilder();
                String line;
                while ( (line = br.readLine()) != null) {
                    buffer.append(line);//.append("\n");
                }
                //message = buffer.toString();
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        return buffer.toString();
        }

    private void process ()
    {
        String logDir = "/home/svj/tmp/11";
        //String[] cmds = new String[] { "ps", "-ax" };     // OK
        //String[] cmds = new String[] { "ps", "-ax", " | ", "grep", "java" };    // none
        String[] cmds = new String[] { "ps", "-ax", " | grep java" };    // none
        //String[] cmds = new String[] { "ps", "-ax", "|", "grep", "java" };
        //String[] cmds = new String[] { "/sbin/ifconfig", "ps -ax | grep java" };
        //String[] cmds = new String[] { "/sbin/ifconfig" };
        List<String> list;

        processCmd5 ( logDir, cmds );

        /*
        for ( String cmd : cmds )
        {
            //processCmd ( cmd, logDir );
            processCmd4 ( cmd, logDir );
        }
        */

        //list = new ArrayList<> ();
        //list.add ( "/sbin/ifconfig" );
        //processCmd2 ( logDir, list );
    }

    /*

// this is where the action is
public static void main(String[] args) {
            Runtime rt = Runtime.getRuntime();
            RuntimeExec rte = new RuntimeExec();
            StreamWrapper error, output;

            try {
                        Process proc = rt.exec("ping localhost");
                        error = rte.getStreamWrapper(proc.getErrorStream(), "ERROR");
                        output = rte.getStreamWrapper(proc.getInputStream(), "OUTPUT");
                        int exitVal = 0;

                        error.start();
                        output.start();
                        error.join(3000);
                        output.join(3000);
                        exitVal = proc.waitFor();
                        System.out.println("Output: "+output.message+"\nError: "+error.message);
            } catch (IOException e) {
                        e.printStackTrace();
            } catch (InterruptedException e) {
                        e.printStackTrace();
            }
            }
}


// Redirect output to this file.
	b.redirectOutput(new File("C:\\folder\\test.txt"));

// cat /proc/kmsg > /mnt/sdcard/klog.log
ProcessBuilder pb = new ProcessBuilder("/bin/sh", "-c", "cat /proc/kmsg > ~/kmem");
pb.start().waitFor();

Java 7

Java program that uses ProcessBuilder, start

import java.io.IOException;
import java.lang.ProcessBuilder;

public class Program {
    public static void main(String[] args) throws IOException {

	// Create ProcessBuilder.
	ProcessBuilder p = new ProcessBuilder();

	// Use command "notepad.exe" and open the file.
	p.command("notepad.exe", "C:\\file.txt");
	p.start();
    }
}

Java

Folder, EXE. In this example we invoke a specific executable at a known location on the computer. We concat a folder path and an executable name. Here I invoke photoshop.exe.

Tip:To pass an argument to the exe, specify a second argument to command. This can be a target file location to open.

Java program that uses ProcessBuilder

import java.io.IOException;
import java.lang.ProcessBuilder;

public class Program {
    public static void main(String[] args) throws IOException {

	String folder = "C:\\Program Files\\Adobe\\Adobe Photoshop CC\\";
	String exe = "photoshop.exe";

	// Create and start Process with ProcessBuilder.
	ProcessBuilder p = new ProcessBuilder();
	p.command(folder + exe);
	p.start();
    }
}

Open and close

RedirectOutput. This program uses the redirectOutput method to have an executable program write to a file. It uses the 7-Zip EXE, a compression utility, and writes to "test.txt."

File:The file must exist on the disk for this example to correctly work. We can separately create the file.

Output:The program runs 7za.exe with no arguments, so the EXE does nothing of importance. We can pass further arguments to Zza.
7-Zip Command-Line

Java program that uses redirectOutput

import java.io.File;
import java.io.IOException;

public class Program {
    public static void main(String[] args) throws IOException {

	// Create ProcessBuilder and target 7-Zip executable.
	ProcessBuilder b = new ProcessBuilder();
	b.command("C:\\7za.exe");

	// Redirect output to this file.
	b.redirectOutput(new File("C:\\folder\\test.txt"));
	b.start();
    }
}


ArrayList, command. The ProcessBuilder class supports an ArrayList (or any List) as an argument. We can separate parts of a command into an ArrayList and then execute them.
ArrayList

Note:This program starts Internet Explorer and opens Wikipedia in it. This is not ideal for systems that use other browsers.

Note 2:Sometimes a specific browser is standardized upon in an organization. This command would work there.

Java program that uses ArrayList for command

import java.io.IOException;
import java.util.ArrayList;

public class Program {
    public static void main(String[] args) throws IOException {

	ProcessBuilder b = new ProcessBuilder();

	// Create an ArrayList with two values.
	// ... This starts a specific browser, which is not ideal.
	ArrayList<String> values = new ArrayList<>();
	values.add("C:\\Program Files\\Internet Explorer\\iexplore.exe");
	values.add("http://en.wikipedia.org/");

	// Pass List to command method.
	b.command(values);
	b.start();
    }
}



public class ProcessBuildDemo {

    public static void main(String [] args) throws IOException {

        String[] command = {"CMD", "/C", "dir"};
        ProcessBuilder probuilder = new ProcessBuilder( command );

        //You can set up your work directory
        probuilder.directory(new File("c:\\xyzwsdemo"));

        Process process = probuilder.start();

        //Read out dir output
        InputStream is = process.getInputStream();
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        String line;
        System.out.printf("Output of running %s is:\n",
                Arrays.toString(command));
        while ((line = br.readLine()) != null) {
            System.out.println(line);
        }

        //Wait to get exit value
        try {
            int exitValue = process.waitFor();
            System.out.println("\n\nExit Value is " + exitValue);
        } catch (InterruptedException e) {
            // Auto-generated catch block
            e.printStackTrace();
        }
    }
}


     */
    private void processCmd2 ( String logDir, List<String> cmd )
    {
        String          fileName, command;
        Process         ps;
        ProcessBuilder  builder;

        // Сформировать имя файла результата
        fileName = Convert.collectionToString ( cmd, '_' );
        fileName = fileName.replace ( ' ', '_' );
        fileName = fileName.replace ( '|', '_' );
        fileName = fileName.replace ( '/', '_' );
        fileName = fileName.replace ( '\\', '_' );

        fileName = Convert.concatObj ( logDir, '/', fileName, ".txt" );
        //LogWriter.file.debug ( "----- log-file.  CMD = %s; fileName = %s", cmd, fileName );

        // Сформировать полное представление команды
        //command  = Convert.concatObj ( cmd, " >> ", fileName );
        //System.out.println ( "----- log-file. full command = " + command );
        cmd.add ( ">>" );
        cmd.add ( fileName );

        // Выполнить команду
        try
        {
            //ps = Runtime.getRuntime().exec ( command );

            builder = new ProcessBuilder ( cmd );
            ///builder.directory ( new File (System.getenv("temp")) );
            ///builder.redirectErrorStream(true);
            ps = builder.start();

            // ждем завершения
            ps.waitFor();
            // удаляем процесс - чтобы освободил файлы.
            ps.destroy();

        } catch ( Exception ex ) {
            ex.printStackTrace ();
            //LogWriter.file.error ( Convert.concatObj ( "EMS logs: exec command error, command: ", command ), ex);
            //throw new EltexException ( null, Msg.getMessage("system.server.error.commanderror"), " : \n", ex );
            //errMsg.append ( "Error for cmd:" );
            //errMsg.append ( cmd );
            //errMsg.append ( " : " );
            //errMsg.append ( ex );
            //errMsg.append ( '\n' );
        }
    }

    /**
     * "/sbin/ifconfig" -- OK
     * , "ps -ax | grep java" - error=2
     *
     * @param cmd
     * @param logDir
     */
    private void processCmd ( String cmd, String logDir )
    {
        String          fileName, command;
        Process         ps;
        ProcessBuilder  builder;

        // Сформировать имя файла результата
        fileName = cmd.replace ( ' ', '_' );
        fileName = fileName.replace ( '|', '_' );
        fileName = fileName.replace ( '/', '_' );
        fileName = fileName.replace ( '\\', '_' );

        fileName = Convert.concatObj ( logDir, '/', fileName, ".txt" );
        //LogWriter.file.debug ( "----- log-file.  CMD = %s; fileName = %s", cmd, fileName );

        // Сформировать полное представление команды
        command  = Convert.concatObj ( cmd, " >> ", fileName );
        System.out.println ( "----- log-file. full command = " + command );

        // Выполнить команду
        try
        {
            //ps = Runtime.getRuntime().exec ( command );

            builder = new ProcessBuilder ( command );
            ///builder.directory ( new File (System.getenv("temp")) );
            ///builder.redirectErrorStream(true);
            ps = builder.start();

            // ждем завершения
            ps.waitFor();
            // удаляем процесс - чтобы освободил файлы.
            ps.destroy();

        } catch ( Exception ex ) {
            ex.printStackTrace ();
            //LogWriter.file.error ( Convert.concatObj ( "EMS logs: exec command error, command: ", command ), ex);
            //throw new EltexException ( null, Msg.getMessage("system.server.error.commanderror"), " : \n", ex );
            //errMsg.append ( "Error for cmd:" );
            //errMsg.append ( cmd );
            //errMsg.append ( " : " );
            //errMsg.append ( ex );
            //errMsg.append ( '\n' );
        }
    }


    // OK
    private void processCmd3 ( String cmd, String logDir )
    {
        String          fileName, command;
        Process         ps;
        ProcessBuilder  builder;

        // Сформировать имя файла результата
        fileName = cmd.replace ( ' ', '_' );
        fileName = fileName.replace ( '|', '_' );
        fileName = fileName.replace ( '/', '_' );
        fileName = fileName.replace ( '\\', '_' );

        fileName = Convert.concatObj ( logDir, '/', fileName, ".txt" );
        //LogWriter.file.debug ( "----- log-file.  CMD = %s; fileName = %s", cmd, fileName );

        // Сформировать полное представление команды
        //command  = Convert.concatObj ( cmd, " >> ", fileName );
        //System.out.println ( "----- log-file. full command = " + command );

        // Выполнить команду
        try
        {
            builder = new ProcessBuilder ( cmd );
            ///builder.directory ( new File (System.getenv("temp")) );
            ///builder.redirectErrorStream(true);
            ps = builder.start();

            InputStream is = ps.getInputStream();
                InputStreamReader isr = new InputStreamReader (is);
                BufferedReader br = new BufferedReader (isr);
                String line;
                while ((line = br.readLine()) != null) {
                  System.out.println(line);
                }
                System.out.println("Program terminated!");

            // ждем завершения
            //ps.waitFor();
            // удаляем процесс - чтобы освободил файлы.
            //ps.destroy();

        } catch ( Exception ex ) {
            ex.printStackTrace ();
            //LogWriter.file.error ( Convert.concatObj ( "EMS logs: exec command error, command: ", command ), ex);
            //throw new EltexException ( null, Msg.getMessage("system.server.error.commanderror"), " : \n", ex );
            //errMsg.append ( "Error for cmd:" );
            //errMsg.append ( cmd );
            //errMsg.append ( " : " );
            //errMsg.append ( ex );
            //errMsg.append ( '\n' );
        }
    }

    // OK
    private void processCmd4 ( String cmd, String logDir )
    {
        String          fileName;
        ProcessBuilder  builder;

        // Сформировать имя файла результата
        fileName    = createProcessFileName0 ( cmd, logDir );

        // Выполнить команду
        try
        {
            builder = new ProcessBuilder ( cmd );
            ///builder.directory ( new File (System.getenv("temp")) );
            ///builder.redirectErrorStream(true);
            builder.redirectOutput ( new File (fileName) );
            builder.start ();

        } catch ( Exception ex ) {
            ex.printStackTrace ();
        }
    }

    // OK
    private void processCmd5 ( String logDir, String[] cmd )
    {
        String          fileName;
        ProcessBuilder  builder;

        // Сформировать имя файла результата
        fileName    = createProcessFileName ( logDir, cmd );

        // Выполнить команду
        try
        {
            builder = new ProcessBuilder ( cmd );
            ///builder.directory ( new File (System.getenv("temp")) );
            ///builder.redirectErrorStream(true);
            builder.redirectOutput ( new File (fileName) );
            builder.start ();

        } catch ( Exception ex ) {
            ex.printStackTrace ();
        }
    }

    // none - попытка доабвить фильтр | grep java
    private void processCmd6 ( String logDir )
    {
        String          fileName;
        ProcessBuilder  builder;
        Process ps;

        String[] cmd = new String[] { "ps", "-ax" };
        // Сформировать имя файла результата
        fileName    = createProcessFileName ( logDir, cmd );

        // Выполнить команду
        try
        {
            builder = new ProcessBuilder ( cmd );
            ///builder.directory ( new File (System.getenv("temp")) );
            ///builder.redirectErrorStream(true);
            builder.redirectOutput ( new File (fileName) );
            ps = builder.start();
            java.io.OutputStream os = ps.getOutputStream();
                    os.write("grep java".getBytes());
                    os.close();

        } catch ( Exception ex ) {
            ex.printStackTrace ();
        }
    }

    private String createProcessFileName0 ( String cmd, String logDir )
    {
        String          fileName;
        fileName = cmd.replace ( ' ', '_' );
        fileName = fileName.replace ( '|', '_' );
        fileName = fileName.replace ( '/', '_' );
        fileName = fileName.replace ( '\\', '_' );

        fileName = Convert.concatObj ( logDir, '/', fileName, ".txt" );
        //LogWriter.file.debug ( "----- log-file.  CMD = %s; fileName = %s", cmd, fileName );

        return fileName;
    }

    private String createProcessFileName ( String logDir, String[] cmds )
    {
        String          fileName;

        fileName = Convert.concatObj2 ( cmds );
        fileName = fileName.replace ( ' ', '_' );
        fileName = fileName.replace ( '|', '_' );
        fileName = fileName.replace ( '/', '_' );
        fileName = fileName.replace ( '\\', '_' );

        fileName = Convert.concatObj ( logDir, '/', fileName, ".txt" );
        //LogWriter.file.debug ( "----- log-file.  CMD = %s; fileName = %s", cmd, fileName );
        System.out.println ( "--- create fileName = " + fileName );

        return fileName;
    }

}
