package svj.zabbix.v2;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONArray;
import org.json.JSONObject;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;

//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONArray;
//import com.alibaba.fastjson.JSONObject;

public class DefaultZabbixApi implements ZabbixApi {
	//private static final Logger logger = LoggerFactory.getLogger(DefaultZabbixApi.class);

	private CloseableHttpClient httpClient;

	private URI uri;

	private volatile String auth;

	public DefaultZabbixApi(String url) {
		try {
			uri = new URI(url.trim());
		} catch (URISyntaxException e) {
			throw new RuntimeException("url invalid", e);
		}
	}

	public DefaultZabbixApi(URI uri) {
		this.uri = uri;
	}

	public DefaultZabbixApi(String url, CloseableHttpClient httpClient) {
		this(url);
		this.httpClient = httpClient;
	}

	public DefaultZabbixApi(URI uri, CloseableHttpClient httpClient) {
		this(uri);
		this.httpClient = httpClient;
	}

	@Override
	public void init() {
		if (httpClient == null) {
			httpClient = HttpClients.custom().build();
		}
	}

	@Override
	public void destroy() {
		if (httpClient != null) {
			try {
				httpClient.close();
			} catch (Exception e) {
				//logger.error("close httpclient error!", e);
			}
		}
	}

	@Override
	public boolean login(String user, String password) {
		this.auth = null;
		Request request = RequestBuilder.newBuilder().paramEntry("user", user).paramEntry("password", password)
				.method("user.login").build();
		JSONObject response = call(request);
		String auth = response.getString("result");
		if (auth != null && !auth.isEmpty()) {
			this.auth = auth;
			return true;
		}
		return false;
	}

	@Override
	public String apiVersion() {
		Request request = RequestBuilder.newBuilder().method("apiinfo.version").build();
		JSONObject response = call(request);
		return response.getString("result");
	}

	public boolean hostExists(String name) {
		Request request = RequestBuilder.newBuilder().method("host.exists").paramEntry("name", name).build();
		JSONObject response = call(request);
		return response.getBoolean("result");
	}

	public String hostCreate(String host, String groupId) {
		JSONArray groups = new JSONArray();
		JSONObject group = new JSONObject();
		group.put("groupid", groupId);
		//groups.add(group);
		groups.put(group);
		Request request = RequestBuilder.newBuilder().method("host.create").paramEntry("host", host)
				.paramEntry("groups", groups).build();
		JSONObject response = call(request);
		return response.getJSONObject("result").getJSONArray("hostids").getString(0);
	}

	public boolean hostgroupExists(String name) {
		Request request = RequestBuilder.newBuilder().method("hostgroup.exists").paramEntry("name", name).build();
		JSONObject response = call(request);
		return response.getBoolean("result");
	}

	/**
	 *
	 * @param name
	 * @return groupId
	 */
	public String hostgroupCreate(String name) {
		Request request = RequestBuilder.newBuilder().method("hostgroup.create").paramEntry("name", name).build();
		JSONObject response = call(request);
		return response.getJSONObject("result").getJSONArray("groupids").getString(0);
	}

	@Override
	public JSONObject call(Request request) {
		if (request.getAuth() == null) {
			request.setAuth(this.auth);
		}

		System.out.println("request = " + request);

		try {
			JSONObject jo = new JSONObject(request);
			String jsonString = jo.toString();
			System.out.println("jsonString = " + jsonString);
			StringEntity stringEntity = new StringEntity(jsonString, ContentType.APPLICATION_JSON);

			org.apache.http.client.methods.RequestBuilder buider = org.apache.http.client.methods.RequestBuilder.post();
			buider.setUri(uri);
			buider.addHeader("Content-Type", "application/json");
			buider.setEntity(stringEntity);
			HttpUriRequest httpRequest = buider.build();

			CloseableHttpResponse response = httpClient.execute(httpRequest);
			System.out.println("response = " + response);

			HttpEntity entity = response.getEntity();
			System.out.println("entity = " + entity);

			String responseMsg = createResponse(entity);
			System.out.println("responseMsg = " + responseMsg);

			JSONObject result = new JSONObject(responseMsg);
			//System.out.println("result = " + result);
			return result;

		} catch (IOException e) {
			throw new RuntimeException("DefaultZabbixApi call exception!", e);
		}
	}

	private static String createResponse(HttpEntity entity) throws IOException {
		StringBuilder sb = new StringBuilder(512);
		if (entity != null) {
			//sb.append("\n");
			BufferedReader br = new BufferedReader(new InputStreamReader(entity.getContent(), StandardCharsets.UTF_8));
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

}
