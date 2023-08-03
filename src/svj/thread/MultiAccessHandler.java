package svj.thread;


import exception.SvjException;
import thread.IHandler;

/**
 * Тест для проверки работы под нагрузкой.
 * <BR/>
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 01.12.2017 11:24
 */
public class MultiAccessHandler implements IHandler<String>
{
    private  final String name;

    public MultiAccessHandler ( String name )
    {
        this.name = name;
    }

    @Override
    public String handle ( Object... obj ) throws SvjException
    {
        String  result;

        result = MultiAccessTest.commonProcess ( name );

        if ( result.equals ( name ))
            System.out.println ( "OK: "+name + " = " + result );
        else
            System.out.println ( "ERROR: "+name + " = " + result );
        return result;
    }

}
