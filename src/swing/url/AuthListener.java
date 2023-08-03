package swing.url;

import org.json.JSONException;
import org.json.JSONObject;

import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

/**
 * Авторизация на Сервере IOT (Умный дом)
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 08.04.2022 12:15
 */
public class AuthListener implements ActionListener {

    private JTextField host;
    private JTextField port;
    private JTextField login;
    private JTextField pwd;
    private JTextArea leftText;

    public AuthListener(JTextField host, JTextField port, JTextField login, JTextField pwd, JTextArea leftText) {
        this.host = host;
        this.port = port;
        this.login = login;
        this.pwd = pwd;
        this.leftText = leftText;

    }

    @Override
    public void actionPerformed(ActionEvent event) {
        leftText.append("host = ");
        leftText.append(host.getText());
        leftText.append("; port = ");
        leftText.append(port.getText());
        leftText.append("; login = ");
        leftText.append(login.getText());
        leftText.append("; pwd = ");
        leftText.append(pwd.getText());

        // curl --basic --user client1:client1 -d "grant_type=password&username=user1&password=user1" localhost:8071/api/v1/oauth/token

        // создать урл
        String urlStr = "http://" + host.getText().trim() + ":" + port.getText().trim() + "/api/v1/oauth/token";
        leftText.append("\nurl = ");
        leftText.append(urlStr);

        String userName = login.getText().trim();
        String userPwd = pwd.getText().trim();

        String res = "";
           try {

               //auth1(urlStr, userName, userPwd);

               String token = getAuthToken(urlStr, userName, userPwd);
               leftText.append("\n\nGet token = ");
               leftText.append(token);


           } catch (Throwable e) {
               //e.printStackTrace();
               leftText.append("\nError\n");
               leftText.append(trace2str(e));
           }
    }

    private void auth1(String urlStr, String userName, String userPwd) throws IOException {
        URL url = new URL(urlStr);

        String encoding;
        //encoding = getBase64(userName, userPwd);
        encoding = "--user client1:client1 -d \"grant_type=password&username=" + userName + "&password=" + userPwd + "\"";

        leftText.append("\nencoding = ");
        leftText.append(encoding);

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Authorization", "Basic " + encoding);
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        //connection.setDoInput(true);
        connection.setDoOutput(true);

        // передать тело запроса
        /*
        OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
        String reqBody = "token=xoxp-1234567890-098765-4321-a1b2c3d4e5&limit=100";
        writer.write(reqBody);
        writer.close();
        */

        leftText.append("\nconnection = ");
        leftText.append(connection.toString());
        leftText.append("\nrequest = ");
        leftText.append(connection.getRequestMethod());
        leftText.append("\ngetContentType = ");
        leftText.append(connection.getContentType());
        leftText.append("\ngetContent = ");
        leftText.append(connection.getContent() != null ? connection.getContent().toString() : null);

        // - здесь идет обращение к inputStream - и падает с 403
        //leftText.append("\nheaders = ");
        //leftText.append(tools.DumpTools.printMap(connection.getHeaderFields()));



        leftText.append("\nresponse code = ");
        leftText.append(Integer.toString(connection.getResponseCode()));



        InputStream content = connection.getInputStream();
        BufferedReader in = new BufferedReader(new InputStreamReader(content));
        String res, line;
        res = "";
        while ((line = in.readLine()) != null) {
            res += line + "\n";
        }
        leftText.append("\n\nline = ");
        leftText.append(line);

    }

    private String getBase64(String login, String pwd){
          return Base64
                  .getEncoder()
                  .encodeToString(
                          (login + ":" + pwd)
                          .getBytes()
                  );
       }

    /* Преобразовать traceroute ошибки в текст. */
    public String trace2str(Throwable t) {
        StringBuilder result;

        result = new StringBuilder(128);

        if (t != null) {
            ByteArrayOutputStream ou;
            PrintStream ps;

            ou = new ByteArrayOutputStream(8192);
            ps = new PrintStream(ou);
            t.printStackTrace(ps);
            result.append("\tTrace:\r\n");
            result.append(ou.toString());
        }

        return result.toString();
    }

    private String getAuthToken(String urlStr, String userName, String userPwd) throws IOException, JSONException {
        String authToken = null;

        //URL url = new URL("http://localhost:8071/api/v1/oauth/token");
        URL url = new URL(urlStr);

        HttpURLConnection authConnection = (HttpURLConnection) url.openConnection();

        String authenc;
        authenc = Base64.getEncoder().encodeToString("client:secret".getBytes());

        //authenc =  new String(Base64.encode("client:secret".getBytes()));

        authConnection.setRequestProperty("Authorization", "Basic " + authenc);
        authConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        authConnection.setRequestProperty("Accept-Charset", "UTF-8");
        authConnection.setRequestMethod("POST");
        authConnection.setDoOutput(true);

        String grant;
        //grant = "grant_type=password&username=svj&password=svj";
        grant = "grant_type=password&username=" + userName + "&password=" + userPwd;
        OutputStream os = authConnection.getOutputStream();
        os.write(grant.getBytes());
        os.flush();
        os.close();

        // this.authConnection.setConnectTimeout(9000);

        authConnection.connect();
        System.out.println("Подключились и  ждем ответа" + authConnection.toString());
        int respCode = authConnection.getResponseCode();


        System.out.println("respCode" + respCode);
        if (respCode == 200) {
            System.out.println("Запрос на получение ключа авторизации отработал");

            String responseJson = "";
            String inputLine;
            BufferedReader in = new BufferedReader(new InputStreamReader((authConnection.
                    getInputStream()), "UTF-8"));
            while ((inputLine = in.readLine()) != null)
                responseJson += inputLine;
            in.close();
            System.out.println("responseJson" + responseJson);
            JSONObject jsonObject = new JSONObject(responseJson);

            if (jsonObject.has("access_token")) {
                authToken = jsonObject.getString("access_token");
                System.out.println("Authorization Token = " + authToken);
            }
            ;
        } else {
            System.out.println("Сервис авторизации вернул код: " + respCode);
        }

        return authToken;
    }

    /*
package com.mkyong;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class HttpURLConnectionExample {

    private final String USER_AGENT = "Mozilla/5.0";

    public static void main(String[] args) throws Exception {

        HttpURLConnectionExample http = new HttpURLConnectionExample();

        System.out.println("Testing 1 - Send Http GET request");
        http.sendGet();

        System.out.println("\nTesting 2 - Send Http POST request");
        http.sendPost();

    }

    // HTTP-запрос GET
    private void sendGet() throws Exception {

        String url = "http://www.google.com/search?q=mkyong";

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

                 // Значение по умолчанию - GET
        con.setRequestMethod("GET");

                 // Добавляем заголовок запроса
        con.setRequestProperty("User-Agent", USER_AGENT);

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'GET' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

                 // Распечатываем результат
        System.out.println(response.toString());

    }

         // HTTP-запрос POST
    private void sendPost() throws Exception {

        String url = "https://selfsolve.apple.com/wcResults.do";
        URL obj = new URL(url);
        HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

                 // Добавляем заголовок запроса
        con.setRequestMethod("POST");
        con.setRequestProperty("User-Agent", USER_AGENT);
        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

        String urlParameters = "sn=C02G8416DRJM&cn=&locale=&caller=&num=12345";

                 // Отправить запрос на публикацию
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(urlParameters);
        wr.flush();
        wr.close();

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'POST' request to URL : " + url);
        System.out.println("Post parameters : " + urlParameters);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

                 // Распечатываем результат
        System.out.println(response.toString());

    }

}


== Use Apache

package com.mkyong;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

public class HttpClientExample {

    private final String USER_AGENT = "Mozilla/5.0";

    public static void main(String[] args) throws Exception {

        HttpClientExample http = new HttpClientExample();

        System.out.println("Testing 1 - Send Http GET request");
        http.sendGet();

        System.out.println("\nTesting 2 - Send Http POST request");
        http.sendPost();

    }

         // HTTP-запрос GET
    private void sendGet() throws Exception {

        String url = "http://www.google.com/search?q=developer";

        HttpClient client = new DefaultHttpClient();
        HttpGet request = new HttpGet(url);

                 // Добавляем заголовок запроса
        request.addHeader("User-Agent", USER_AGENT);

        HttpResponse response = client.execute(request);

        System.out.println("\nSending 'GET' request to URL : " + url);
        System.out.println("Response Code : " +
                       response.getStatusLine().getStatusCode());

        BufferedReader rd = new BufferedReader(
                       new InputStreamReader(response.getEntity().getContent()));

        StringBuffer result = new StringBuffer();
        String line = "";
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }

        System.out.println(result.toString());

    }

         // HTTP-запрос POST
    private void sendPost() throws Exception {

        String url = "https://selfsolve.apple.com/wcResults.do";

        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(url);

                 // Добавляем заголовок запроса
        post.setHeader("User-Agent", USER_AGENT);

        List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
        urlParameters.add(new BasicNameValuePair("sn", "C02G8416DRJM"));
        urlParameters.add(new BasicNameValuePair("cn", ""));
        urlParameters.add(new BasicNameValuePair("locale", ""));
        urlParameters.add(new BasicNameValuePair("caller", ""));
        urlParameters.add(new BasicNameValuePair("num", "12345"));

        post.setEntity(new UrlEncodedFormEntity(urlParameters));

        HttpResponse response = client.execute(post);
        System.out.println("\nSending 'POST' request to URL : " + url);
        System.out.println("Post parameters : " + post.getEntity());
        System.out.println("Response Code : " +
                                    response.getStatusLine().getStatusCode());

        BufferedReader rd = new BufferedReader(
                        new InputStreamReader(response.getEntity().getContent()));

        StringBuffer result = new StringBuffer();
        String line = "";
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }

        System.out.println(result.toString());

    }

}

======= oauth

 private String getauthToken () throws IOException, JSONException {
    	 String authToken = null;
    	 URL url = new URL (Constants.auuthURLString);
    	 this.authConnection = (HttpURLConnection) url.openConnection();

    	 String authenc =  new String(Base64.encode(Constants.authClient.getBytes()));

    	 this.authConnection.setRequestProperty("Authorization", "Basic "+authenc);
    	 this.authConnection.setRequestProperty("Content-Type",Constants.authContentType);
    	 this.authConnection.setRequestProperty("Accept-Charset", "UTF-8");
    	 this.authConnection.setRequestMethod("POST");
    	 this.authConnection.setDoOutput(true);
    	 OutputStream os = this.authConnection.getOutputStream();
         os.write(Constants.authPostParmas.getBytes());
         os.flush();
         os.close();

    	// this.authConnection.setConnectTimeout(9000);

         this.authConnection.connect();
         System.out.println("Подключились и  ждем ответа"+this.authConnection.toString());
         int respCode = this.authConnection.getResponseCode();


         System.out.println("respCode"  +respCode);
         if (respCode ==200) {
        	 System.out.println("Запрос на получение ключа авторизации отработал");

        	 String responseJson = "";
        	   String inputLine;
        	   BufferedReader in = new BufferedReader(new InputStreamReader((this.authConnection.
        			   getInputStream()),"UTF-8"));
               while ((inputLine = in.readLine()) != null)
            	   responseJson += inputLine;
                in.close();
        	 System.out.println("responseJson" + responseJson);
                JSONObject jsonObject = new JSONObject(responseJson);

                if (jsonObject.has("access_token")) {
                	authToken = jsonObject.getString("access_token");
                	System.out.println("Authorization Token = "+authToken);
                };
         } else {
        	 System.out.println("Сервис авторизации вернул код: "+respCode);
         }

    	return authToken;
}

public class Constants {

	public static final String auuthURLString = "http://localhost:8080/bankref/rest/v2/oauth/token";
	public static final String serviceURLString =
		//"http://localhost:8080/bankref/rest/v2/services/bankref_EmployeeService/createNewEmployee";
		"http://localhost:8080/bankref/rest/myapi/createNewEmployee";
	public static final String serviceURLStringObj =
		"http://localhost:8080/bankref/rest/myapi/createNewEmployeeObj";
	public static final String authPostParmas = "grant_type=password&username=admin&password=admin";
	public static final String authClient = "client:secret";
	public static final String authContentType = "application/x-www-form-urlencoded";

}

     */

}
