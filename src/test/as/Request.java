package test.as;

import java.io.Serializable;

/**
 * Обьект запрсоа сервиса.
 *
 *
 - Порядковый номер (уникальный в сессии)
 - Название сервиса
 - Название функции (метода)
 - Список объектов (параметры функции)

 * User: svj
 * Date: 16.02.2007
 * Time: 15:43:51
 */
public class Request  implements Serializable
{
    private int         id;
    private String      serviceName;
    private String      methodName;
    private Object[]    params;


    public Request ( int id, String serviceName, String methodName, Object[] params )
    {
        this.id             = id;
        this.serviceName    = serviceName;
        this.methodName     = methodName;
        this.params         = params;
    }

    public String toString()
    {
        StringBuffer result = new StringBuffer(64);
        result.append ( "Request: id = " );
        result.append ( id );
        result.append ( ", serviceName = " );
        result.append ( serviceName );
        result.append ( ", methodName = " );
        result.append ( methodName );
        result.append ( ", params size = " );
        result.append ( params.length );
        return result.toString();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Object[] getParams() {
        return params;
    }

    public void setParams(Object[] params) {
        this.params = params;
    }
    
}
