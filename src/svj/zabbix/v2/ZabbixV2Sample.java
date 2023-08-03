package svj.zabbix.v2;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Используем скопированную библиотеку.
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 23.07.2019 10:27
 */
public class ZabbixV2Sample {

    public static void main(String[] args) {

        Request request;
        JSONArray array;
        ZabbixApi zabbixApi = null;

        try {

            String url = "http://localhost/zabbix/api_jsonrpc.php";
            zabbixApi = new DefaultZabbixApi(url);
            // создаем httpClient
            zabbixApi.init();

            // login
            boolean login = zabbixApi.login("Admin", "zabbix");

            if ( login ) {

                // Список хостов
                // -1)
                /*
                JSONObject filter = new JSONObject();
                filter.put("host", "127.0.0.1");
                request = RequestBuilder.newBuilder().method("host.get").paramEntry("filter", filter).build();
                */
                // -2
                RequestBuilder builder = RequestBuilder.newBuilder();
                builder.method("host.get");
                array = new JSONArray(new String[] {"hostid","host"});
                builder.paramEntry("output", array);
                array = new JSONArray(new String[] {"interfaceid","ip"});
                builder.paramEntry("selectInterfaces", array);
                request = builder.build();


                JSONObject response = zabbixApi.call(request);
                System.out.println("response = " + response);

            } else {
                throw new Exception("Bad auth.");
            }

        }catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            //Deprecated
            //httpClient.getConnectionManager().shutdown();
            if ( zabbixApi != null )  zabbixApi.destroy();
        }
    }

}
