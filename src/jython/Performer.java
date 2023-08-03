package jython;


import com.svj.utils.FileTools;
import org.apache.logging.log4j.*;
import org.python.core.PyObject;
import org.python.core.PySystemState;
import org.python.util.PythonInterpreter;

import java.io.ByteArrayInputStream;

/**
 * Проверка работы с интерепретатором Jython.
 * User: svj
 * Date: 23.01.2007
 * Time: 16:55:24
 */
public class Performer
{
    private static final Logger logger = LogManager.getFormatterLogger( Performer.class );

    PythonInterpreter pythonInterpreter;

    String pythonCode;

    
    public Performer ()
    {
        PySystemState.initialize();
        pythonInterpreter   = new PythonInterpreter();

    }

    public void init ( String code )
    {
        pythonCode  = "import ..;\n" + code;
    }

    public void loadCode ( String fileName ) throws Exception
    {
        String  str;
        // C:/Projects/SVJ/JavaSample/test/jython/
        pythonCode = FileTools.loadFile(fileName);
        //str = FileTools.loadFile(fileName);
        //pythonCode  = "import ..;\n" + str;
    }

    public PyObject runCode ()
    {
        /** добавление фабрики для создания логера в контексте процедуры jython */
        pythonInterpreter.set( "LoggerFactory", logger );

        MyObject    my  = new MyObject("Serg", 5413);
        pythonInterpreter.set( "MyObject", my );

        byte[] codeBytes = pythonCode.getBytes();

        //ByteArrayInputStream - решение проблемы с кодировкой.
        pythonInterpreter.execfile( new ByteArrayInputStream( codeBytes ) );
        
        PyObject object = pythonInterpreter.get ( "returnValue" );
        logger.info ( "Return python object : " + object );
        return object;
    }

    private static Object PyObject2JavaObject ( PyObject pyObject )
    {
        return pyObject == null ? null : pyObject.__tojava__( Object.class );
    }

//=====================================================================================

    public static void main(String[] args)
    {
        Performer   performer;
        String      codeFile;
        PyObject    object;
        Object      obj;

        try
        {
            performer   = new Performer();
            codeFile    = "C:/Projects/SVJ/JavaSample/test/jython/prog1.py";
            performer.loadCode(codeFile);

            object      = performer.runCode();
            logger.debug ( "Result = " + object );
            logger.debug ( "Result class = " + object.getClass().getName() );

            obj         = performer.PyObject2JavaObject (object);
            logger.debug ( "Result 2 = " + obj );
            logger.debug ( "Result 2 class = " + obj.getClass().getName() );

        } catch (Exception e) {
            logger.error ( "Error", e );
        }

    }

}
