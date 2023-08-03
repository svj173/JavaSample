package svj.zabbix;


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 25.06.2019 16:57
 */
public class SampleRequest {
    private static enum RequestType {AUTH, GET_HOSTS, CREATE_HOST, GET_GROUPS, GET_TEMPLATES, DELETE_HOST,
        GET_GRAPHIC_OPTIONS, GET_GRAPHIC, CREATE_GRAPHIC, CREATE_ITEM, GET_GRAPH_ITEM, GET_ITEM
    };

    public static void main(String[] args) {
        String jsonRequest, authSession;

        HttpClient httpClient = HttpClientBuilder.create().build();

        try {
            String url = "http://localhost/zabbix/api_jsonrpc.php";

            // Авторизация. Держится более суток.
            authSession = "49f8758d1661f08563cac6a98ffa4e86";

            jsonRequest = createRequest(RequestType.CREATE_GRAPHIC, authSession);


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



        }catch (Exception ex) {
            ex.printStackTrace();
        //} finally {
            //Deprecated
            //httpClient.getConnectionManager().shutdown();
        }
    }

    private static String createResponse(HttpEntity entity) throws IOException {
        StringBuilder sb = new StringBuilder(512);
        if (entity != null) {
            sb.append("\n");
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(entity.getContent(), StandardCharsets.UTF_8));
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }
            br.close();
        } else {
            sb.append("Null");
        }

        return sb.toString();
    }

    private static String createRequest(RequestType auth, String authSession) {
        String jsonRequest;

        switch (auth) {
            default:
            case AUTH: // Авторизация
                jsonRequest = "{\n    \"jsonrpc\": \"2.0\",\n    \"method\": \"user.login\",\n"
                        + "    \"params\": {\n        \"user\": \"Admin\",\n        \"password\": \"zabbix\"\n"
                        + "    },\n    \"id\": 1,\n    \"auth\": null\n}";
                break;

            case GET_GROUPS:
                jsonRequest = "{\n" + "    \"jsonrpc\": \"2.0\",\n" + "    \"method\": \"hostgroup.get\",\n"
                        + "    \"params\": {\n" + "        \"output\": \"extend\",\n" + "        \"filter\": {\n"
                        + "            \"name\": [\n" + "                \"Zabbix servers\",\n"
                        + "                \"WiFi\"\n" + "            ]\n" + "        }\n" + "    },\n"
                        + "    \"auth\": \""+authSession+"\",\n" + "    \"id\": 1\n" + "}\n";
                break;

            case GET_HOSTS:    // Список хостов
                jsonRequest = "{\n    \"jsonrpc\": \"2.0\",\n    \"method\": \"host.get\",\n"
                        + "    \"params\": {\n        \"output\": [\n            \"hostid\",\n"
                        + "            \"host\"\n        ],\n        \"selectInterfaces\": [\n"
                        + "            \"interfaceid\",\n            \"ip\"\n        ]\n    },\n"
                        + "    \"id\": 2,\n    \"auth\": \""+authSession+"\"\n}";
                break;

            case CREATE_HOST:  // Создать хост
                jsonRequest = createHost (authSession);
                break;

            case GET_TEMPLATES:  // Список шаблонов
                jsonRequest = getTemplates (authSession);
                break;

            case GET_GRAPHIC:  // График как массив значений
                jsonRequest = getGraphic (authSession);
                break;

            case CREATE_GRAPHIC:  // Создать График
                jsonRequest = createGraphic(authSession);
                break;

            case CREATE_ITEM:  // Создать Элемент Графика
                jsonRequest = createItem (authSession);
                break;

            case GET_GRAPH_ITEM:  // Получить Элемент Графика
                jsonRequest = getGraphItem(authSession);
                break;

            case GET_ITEM:  // Получить Элемент Графика - Элементы мониторинга?
                jsonRequest = getItem(authSession);
                break;

            case DELETE_HOST:  // Удалить хост. params - ID хостов через запятую
                jsonRequest = "{\n    \"jsonrpc\": \"2.0\",\n    \"method\": \"host.delete\",\n"
                        + "    \"params\": [\n        \"10264\"\n    ],\n"
                        + "    \"auth\": \""+authSession+"\",\n    \"id\": 6\n}\n";
                break;

            case GET_GRAPHIC_OPTIONS:  // График  - настройки графиков.
                jsonRequest = "{\n    \"jsonrpc\": \"2.0\",\n    \"method\": \"graph.get\",\n"
                        + "    \"params\": {\n        \"output\": \"extend\",\n        \"hostids\": 10263,\n"
                        + "        \"sortfield\": \"name\"\n    },\n"
                        + "    \"auth\": \""+authSession+"\",\n    \"id\": 8\n}";
                break;

        }


        // версия API zabbix
        //String jsonRequest = "{\"jsonrpc\":\"2.0\",\"method\":\"apiinfo.version\",\"id\":1,\"auth\":null,"
        //        + "\"params\":{}}";

        return jsonRequest;
    }

    private static String getGraphic(String authSession) {
        // Есть: time_from (начало), time_till (конец),
        // - time_from = time.mktime((2017,3,27,9,0,0,0,0,0))
        // jpgraph - создает картинку
        
        StringBuilder sb = new StringBuilder(512);
        sb.append("{");
        sb.append("    \"jsonrpc\": \"2.0\",");
        sb.append("    \"method\": \"history.get\",");
        sb.append("    \"params\": {");
        sb.append("        \"output\": \"extend\",");
        sb.append("        \"history\": 0,");
        sb.append("        \"hostids\": \"10263\",");
        //sb.append("        \"itemids\": \"23296\",");
        //sb.append("        \"sortfield\": \"clock\",");
        //sb.append("        \"sortorder\": \"DESC\",");
        // есть time_from - формат даты неизвестен (timestamp)
        sb.append("        \"limit\": 2");
        sb.append("    },");
        sb.append("    \"auth\": \"").append(authSession).append("\",");
        sb.append("    \"id\": 17");
        sb.append("}");

        return sb.toString();
    }

    private static String createItem(String authSession) {
        // Сначала необходимо создать элементы графика и получить их ИД.
        // - hostid - ИД хоста
        // - Простая проверка  (type=3)
        // - ключ: icmppingsec[,3]
        // - интерфейс узла сети: 192.168.51.13:161   (2)  -- его ИД узнаем из инфы о хосте.
        // - value_type - тип информации: с точкой  (0)
        // - измерение: сек
        // - интервал обновления: 30s
        // - пользвоательские интервалы: тип=переменный, интервал=50s, период (не исп?)=1-7,00:00-24:00
        // - Период хранения истории: 90d
        // - Период хранения динамики изменений: 365d
        // - Отображение значения: как есть

        // ??
        // - interfaceid = 4

        // График привязывается к Элементу? А Элемент - к хосту?


        StringBuilder sb = new StringBuilder(512);
        sb.append("{");
        sb.append("    \"jsonrpc\": \"2.0\",");
        sb.append("    \"method\": \"item.create\",");
        sb.append("    \"params\": {");
        sb.append("        \"name\": \"ICMP ping\",");
        sb.append("        \"key_\": \"icmppingsec[,3]\",");
        sb.append("        \"hostid\": \"10265\",");          // 192.168.51.18
        sb.append("        \"type\": 3,");                    // Простая проверка
        sb.append("        \"value_type\": 0,");              // с точкой
        sb.append("        \"interfaceid\": \"4\",");
        //sb.append("        \"applications\": [");           // Это ИД групп элементов.
        //sb.append("            \"609\",");
        //sb.append("            \"610\"");
        //sb.append("        ],");
        sb.append("        \"delay\": \"30s\"");
        sb.append("    },");
        sb.append("    \"auth\": \"").append(authSession).append("\",");
        sb.append("    \"id\": 17");
        sb.append("}");

        return sb.toString();
    }

    private static String getGraphItem(String authSession) {
        StringBuilder sb = new StringBuilder(512);
        sb.append("{");
        sb.append("    \"jsonrpc\": \"2.0\",");
        sb.append("    \"method\": \"graphitem.get\",");
        sb.append("    \"params\": {");
        sb.append("        \"output\": \"extend\",");
        sb.append("        \"expandData\": 1,");
        sb.append("        \"graphids\": \"817\"");
        sb.append("    },");
        sb.append("    \"auth\": \"").append(authSession).append("\",");
        sb.append("    \"id\": 18");
        sb.append("}");

        return sb.toString();
    }

    private static String getItem(String authSession) {
        StringBuilder sb = new StringBuilder(512);
        sb.append("{");
        sb.append("    \"jsonrpc\": \"2.0\",");
        sb.append("    \"method\": \"item.get\",");
        sb.append("    \"params\": {");
        sb.append("        \"output\": \"extend\",");
        sb.append("        \"hostids\": 10263");
        sb.append("    },");
        sb.append("    \"auth\": \"").append(authSession).append("\",");
        sb.append("    \"id\": 19");
        sb.append("}");

        /*
{
    "jsonrpc": "2.0",
    "method": "item.get",
    "params": {
        "output": "extend",
        "hostids": "10084",
        "search": {
            "key_": "system"
        },
        "sortfield": "name"
    },
    "auth": "038e1d7b1735c6a5436ee9eae095879e",
    "id": 1
}
         */
        return sb.toString();
    }

    private static String createGraphic(String authSession) {

        StringBuilder sb = new StringBuilder(512);
        sb.append("{");
        sb.append("    \"jsonrpc\": \"2.0\",");
        sb.append("    \"method\": \"graph.create\",");
        sb.append("    \"params\": {");
        sb.append("        \"name\": \"ICMP ping\",");
        sb.append("        \"width\": 900,");
        sb.append("        \"height\": 200,");
        sb.append("        \"gitems\": [");
        sb.append("            {");
        sb.append("                \"itemid\": \"28659\",");     // 28659 - для 51.18
        sb.append("                \"color\": \"00AA00\",");
        sb.append("                \"sortorder\": \"0\"");
        sb.append("            }");
        sb.append("        ]");
        sb.append("    },");
        sb.append("    \"auth\": \"").append(authSession).append("\",");
        sb.append("    \"id\": 21");
        sb.append("}");

        return sb.toString();
    }

    private static String getTemplates(String authSession) {
        StringBuilder sb = new StringBuilder(512);
        sb.append("{");
        sb.append("    \"jsonrpc\": \"2.0\",");
        sb.append("    \"method\": \"template.get\",");
        sb.append("    \"params\": {");
        sb.append("        \"output\": \"extend\",");
        sb.append("        \"filter\": {");
        sb.append("            \"host\": [");
        //sb.append("                \"Template OS Linux\",");
        //sb.append("                \"Template OS Windows\"");
        sb.append("                \"Template Module Interfaces SNMPv2\"");
        sb.append("            ]");
        sb.append("        }");
        sb.append("    },");
        sb.append("    \"auth\": \"").append(authSession).append("\",");
        sb.append("    \"id\": 10");
        sb.append("}");

        return sb.toString();
    }

    private static String createHost(String authSession) {
        // Cannot find host interface on \"w_192.168.51.18\" for item key \"net.if.discovery\"
        
        StringBuilder sb = new StringBuilder(512);
        sb.append("{");
        sb.append("    \"jsonrpc\": \"2.0\",");
        sb.append("    \"method\": \"host.create\",");
        sb.append("    \"params\": {");
        sb.append("        \"host\": \"w_192.168.51.18\",");
        sb.append("        \"interfaces\": [");
        sb.append("            {");
        sb.append("                \"type\": 2,");         // type=2 - SNMP   
        sb.append("                \"main\": 1,");
        sb.append("                \"useip\": 1,");
        sb.append("                \"ip\": \"192.168.51.18\",");
        sb.append("                \"dns\": \"\",");
        sb.append("                \"port\": \"161\",");
        sb.append("                \"bulk\": 1");
        sb.append("            }");
        sb.append("        ],");
        sb.append("        \"groups\": [");
        sb.append("            {");
        sb.append("                \"groupid\": \"15\"");   // 15 - WiFi устройства
        sb.append("            }");
        sb.append("        ],");
        sb.append("        \"tags\": [");
        sb.append("            {");
        sb.append("                \"tag\": \"Host name\",");
        sb.append("                \"value\": \"w_192.168.51.18\"");
        sb.append("            }");
        sb.append("        ],");
        sb.append("        \"templates\": [");     // Наверное - Template Module Interfaces SNMPv2 = 10190
        sb.append("            {");
        sb.append("                \"templateid\": \"10190\"");
        sb.append("            }");
        sb.append("        ],");
        sb.append("        \"macros\": [");
        sb.append("            {");
        sb.append("                \"macro\": \"{$USER_ID}\",");
        sb.append("                \"value\": \"123321\"");
        sb.append("            }");
        sb.append("        ],");
        sb.append("        \"inventory_mode\": 0,");
        sb.append("        \"inventory\": {");
        sb.append("            \"macaddress_a\": \"01234\",");
        sb.append("            \"macaddress_b\": \"56768\"");
        sb.append("        }");
        sb.append("    },");
        sb.append("    \"auth\": \"").append(authSession).append("\",");
        sb.append("    \"id\": 12");
        sb.append("}");


        /*
                jsonRequest = "{\n" + "    \"jsonrpc\": \"2.0\",\n" + "    \"method\": \"host.create\",\n"
                        + "    \"params\": {\n" + "        \"host\": \"w_192.168.51.18\",\n" + "        \"interfaces\": [\n"
                        + "            {\n" + "                \"type\": 1,\n" + "                \"main\": 1,\n"
                        + "                \"useip\": 1,\n" + "                \"ip\": \"192.168.51.18\",\n"
                        + "                \"dns\": \"\",\n" + "                \"port\": \"10050\"\n" + "            }\n"
                        + "        ],\n" + "        \"groups\": [\n" + "            {\n"
                        + "                \"groupid\": \"15\"\n" + "            }\n" + "        ],\n" + "    },\n"
                        + "    \"auth\": \""+authSession+"\",\n" + "    \"id\": \"5\"\n" + "}\n";

         */
        return sb.toString();
    }

}
