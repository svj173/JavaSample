package svj.zabbix;

import com.zabbix4j.ZabbixApi;
import com.zabbix4j.host.HostCreateRequest;
import com.zabbix4j.host.HostCreateResponse;
import com.zabbix4j.hostinteface.HostInterfaceObject;
import com.zabbix4j.usermacro.Macro;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * use com.zabbix4j libs
 * <BR/> Не рабоатет т.к. внутри кода ссылки на старые пути для log4j.
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 22.07.2019 16:11
 */
public class Zabbix4JSample {

    public static void main(String[] args) {
        //String jsonRequest, authSession;

        //HttpClient httpClient = HttpClientBuilder.create().build();

        try {

            String user = "admin";   // Admin
            String password = "zabbix";

            // login to zabbix
            ZabbixApi zabbixApi = new ZabbixApi("http://localhost/zabbix/api_jsonrpc.php");
            zabbixApi.login(user, password);

            final Integer groupId = 25;
            final Integer templateId = 10093;

            HostCreateRequest request = new HostCreateRequest();
            HostCreateRequest.Params params = request.getParams();
            params.addTemplateId(templateId);
            params.addGroupId(groupId);

            // set macro
            List<Macro> macros = new ArrayList<Macro>();
            Macro macro1 = new Macro();
            macro1.setMacro("{$MACRO1}");
            macro1.setValue("value1");
            macros.add(macro1);
            params.setMacros(macros);

            // set host interface
            HostInterfaceObject hostInterface = new HostInterfaceObject();
            hostInterface.setIp("192.168.255.255");
            params.addHostInterfaceObject(hostInterface);

            params.setHost("test host created1. " + new Date().getTime());
            params.setName("test host created1 name " + new Date().getTime());

            HostCreateResponse response = zabbixApi.host().create(request);

            int hostId = response.getResult().getHostids().get(0);
            System.out.println("hostId = "+hostId);
            /*
            String url = "http://localhost/zabbix/api_jsonrpc.php";

            // Авторизация. Держится более суток.
            authSession = "49f8758d1661f08563cac6a98ffa4e86";

            jsonRequest = createRequest(SampleRequest.RequestType.CREATE_GRAPHIC, authSession);


            HttpPost request = new HttpPost(url);
            StringEntity params = new StringEntity(jsonRequest);
            //request.addHeader("content-type", "application/x-www-form-urlencoded");
            request.addHeader("content-type", "application/json-rpc");
            request.addHeader("charset", "UTF-8");
            request.setEntity(params);
            HttpResponse response = httpClient.execute(request);

            //System.out.println(response);

            System.out.println(jsonRequest);

            HttpEntity entity = response.getEntity();

            String responseMsg = createResponse(entity);
            System.out.println(responseMsg);
            */


        }catch (Exception ex) {
            ex.printStackTrace();
        //} finally {
            //Deprecated
            //httpClient.getConnectionManager().shutdown();
        }
    }

}
