package svj.zabbix.v2;

//import com.alibaba.fastjson.JSONObject;

import org.json.JSONObject;

public interface ZabbixApi {

	void init();

	void destroy();

	String apiVersion();

	JSONObject call(Request request);

	boolean login(String user, String password);
}
