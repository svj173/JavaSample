package test.as.client;

import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: svj
 * Date: 16.02.2007
 * Time: 17:08:38
 * To change this template use File | Settings | File Templates.
 */
public interface IClient
{
    public void init ( String host, int port ) throws Exception;
    public Object remoteCall ( String serviceName, String methodName, Object[] params ) throws Exception;
}
