package string.regexc;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * С помощью паттерна выделяем из xml-тектса данные для одного тега.
 *
 * Результат:
 * true
 * 0
 * 8
 *
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 17.11.2010 13:45:31
 */
public class RegexpXml
{
    public static void main ( String[] args )
    {
        boolean  b;
        String   regexp, stext, result;

        result = null;

        //regexp = "<code>.*</code>";
        regexp = "<msg>.*</msg>";

        stext = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<xml>\n" +
                "<status>error</status>\n" +
                "<code>4014</code>\n" +
                "<msg>System error : \tTrace:\n" +
                "[ EltexException (EltexException.NBI) : codeError = 4014; msg = 'No connection to host localhost:9310'; infoObject = 'null' ]\n" +
                "\tat org.eltex.ems.web.northbound.socket.v2.NbSocketConnector2v.send(NbSocketConnector2v.java:135)\n" +
                "\tat org.eltex.ems.web.northbound.NorthboundServlet.authProcess(NorthboundServlet.java:708)\n" +
                "\tat org.eltex.ems.web.northbound.NorthboundServlet.doPost(NorthboundServlet.java:573)\n" +
                "\tat javax.servlet.http.HttpServlet.service(HttpServlet.java:643)\n" +
                "\tat javax.servlet.http.HttpServlet.service(HttpServlet.java:723)\n" +
                "\tat org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:290)\n" +
                "\tat org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:206)\n" +
                "\tat org.apache.catalina.core.StandardWrapperValve.invoke(StandardWrapperValve.java:233)\n" +
                "\tat org.apache.catalina.core.StandardContextValve.invoke(StandardContextValve.java:191)\n" +
                "\tat org.apache.catalina.core.StandardHostValve.invoke(StandardHostValve.java:127)\n" +
                "\tat org.apache.catalina.valves.ErrorReportValve.invoke(ErrorReportValve.java:103)\n" +
                "\tat org.apache.catalina.core.StandardEngineValve.invoke(StandardEngineValve.java:109)\n" +
                "\tat org.apache.catalina.connector.CoyoteAdapter.service(CoyoteAdapter.java:293)\n" +
                "\tat org.apache.coyote.http11.Http11Processor.process(Http11Processor.java:861)\n" +
                "\tat org.apache.coyote.http11.Http11Protocol$Http11ConnectionHandler.process(Http11Protocol.java:606)\n" +
                "\tat org.apache.tomcat.util.net.JIoEndpoint$Worker.run(JIoEndpoint.java:489)\n" +
                "\tat java.lang.Thread.run(Thread.java:745)\n" +
                "Caused by: java.net.ConnectException: В соединении отказано\n" +
                "\tat java.net.PlainSocketImpl.socketConnect(Native Method)\n" +
                "\tat java.net.AbstractPlainSocketImpl.doConnect(AbstractPlainSocketImpl.java:350)\n" +
                "\tat java.net.AbstractPlainSocketImpl.connectToAddress(AbstractPlainSocketImpl.java:206)\n" +
                "\tat java.net.AbstractPlainSocketImpl.connect(AbstractPlainSocketImpl.java:188)\n" +
                "\tat java.net.SocksSocketImpl.connect(SocksSocketImpl.java:392)\n" +
                "\tat java.net.Socket.connect(Socket.java:589)\n" +
                "\tat java.net.Socket.connect(Socket.java:538)\n" +
                "\tat java.net.Socket.<init>(Socket.java:434)\n" +
                "\tat java.net.Socket.<init>(Socket.java:211)\n" +
                "\tat org.eltex.ems.web.northbound.socket.v2.NbSocketConnector2v.send(NbSocketConnector2v.java:103)\n" +
                "\t... 16 more\n" +
                "</msg>\n" +
                "<remoteAddress>127.0.0.1</remoteAddress>\n" +
                "<requestAction>/getDeviceList</requestAction>\n" +
                "<requestMsg>orderType=ASC&count=10&domains=root&orderBy=name&page=1&locale=ru_RU</requestMsg>\n" +
                "<port>8080</port>\n" +
                "<processTime>0</processTime>\n" +
                "</xml>";


        //pattern = Pattern.compile(regexp);
        System.out.println ( "regexp : " + regexp );

        // 1) Выделяет только однострочный текст.
        Pattern pattern = Pattern.compile(regexp);
      	Matcher matcher = pattern.matcher(stext);

      	if ( matcher.find() )  result = matcher.group();

        System.out.println ( "Pattern result : "+ result );

        // 2)
        int istart, iend;
        istart  = stext.indexOf ( "<msg>" );
        iend    = stext.indexOf ( "</msg>" );
        if ( (istart >= 0) && (iend >= 0 ) )
        {
            result = stext.substring(istart+"<msg>".length(), iend );
        }
        System.out.println ( "Substring result : "+ result );
    }

}
