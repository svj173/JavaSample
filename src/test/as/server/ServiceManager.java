/**
 * Created by IntelliJ IDEA.
 * User: svj
 * Date: 16.02.2007
 * Time: 16:11:46
 * To change this template use File | Settings | File Templates.
 */
package test.as.server;

import org.apache.logging.log4j.*;
import test.as.Request;
import test.as.Response;

import java.lang.reflect.Method;
import java.util.Hashtable;

public class ServiceManager
{
    private static Logger logger = LogManager.getFormatterLogger ( ServiceManager.class );

    private static ServiceManager ourInstance = new ServiceManager();

    private Hashtable services    = new Hashtable();


    public static ServiceManager getInstance()
    {
        return ourInstance;
    }

    private ServiceManager()
    {
    }

    public Object processService ( Object object ) throws Exception
    {
        Object  result, service;
        Request request;
        Response response;
        String  serviceName, methodName;
        Method  method;
        Class   cl;
        Class[]   args;
        Object[]  params;
        int     size, i;

        //logger.debug("Start. object = " + object );

        result  = null;
        if ( object == null )   return result;
        if ( ! (object instanceof Request) )   return result;

        request = (Request) object;
        serviceName = request.getServiceName();
        service     = getService(serviceName);
        //logger.debug("serviceName = " + serviceName + ", service = " + service );
        methodName  = request.getMethodName();

        cl      = service.getClass();
        params  = request.getParams();
        size    = params.length;
        args    = new Class[size];
        for ( i=0; i<size; i++ )
        {
            args[i] = params[i].getClass();
        }
        method  = cl.getDeclaredMethod ( methodName, args );

        result  = method.invoke ( service, params );

        response    = new Response( request.getId(), result );

        //logger.debug("Finish. response = " + response );
        return response;
    }

    public void addService ( String name, Object cl)
    {
        //logger.debug ( "Add service = " + name );
        services.put (name, cl);
    }

    public Object   getService ( String id )
    {
        return services.get(id);
    }

}
