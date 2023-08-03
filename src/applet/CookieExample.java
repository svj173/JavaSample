package applet;


import java.net.*;
import java.util.List;

/**
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 26.11.2013 17:00
 */
public class CookieExample
{
    public void getCookieUsingCookieHandler()
    {
        try {
            // Instantiate CookieManager;
            // make sure to set CookiePolicy
            CookieManager manager = new CookieManager();
            manager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
            CookieHandler.setDefault(manager);

            // get content from URLConnection;
            // cookies are set by web site
            URL url = new URL("http://host.example.com");
            URLConnection connection = url.openConnection();
            connection.getContent();

            // get cookies from underlying
            // CookieStore
            CookieStore cookieJar =  manager.getCookieStore();
            List<HttpCookie> cookies = cookieJar.getCookies();
            for (HttpCookie cookie: cookies) {
              System.out.println("CookieHandler retrieved cookie: " + cookie);
            }
        } catch(Exception e) {
            System.out.println("Unable to get cookie using CookieHandler");
            e.printStackTrace();
        }
    }

    public void setCookieUsingCookieHandler()
    {
        try {
            // instantiate CookieManager
            CookieManager manager = new CookieManager ();
            CookieHandler.setDefault ( manager );
            CookieStore cookieJar =  manager.getCookieStore();

            // create cookie
            HttpCookie cookie = new HttpCookie ("UserName", "John Doe");

            // add cookie to CookieStore for a
            // particular URL
            URL url = new URL ("http://host.example.com");
            cookieJar.add(url.toURI(), cookie);
            System.out.println("Added cookie using cookie handler");
        } catch(Exception e) {
            System.out.println("Unable to set cookie using CookieHandler");
            e.printStackTrace();
        }
    }
}
