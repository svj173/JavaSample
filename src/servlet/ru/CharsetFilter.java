package servlet.ru;

import javax.servlet.*;
import java.io.IOException;


/**
 *
 Русские буквы в сервлете.
 Здесь решается проблема когда русксие буквы до прихода в doPost проходят через какие-то фильтры и тогда там-то кодирвока и поломается, т.к. натсрока кодирвоки происходит только в
 httpServletRequest.setCharacterEncoding ( EPCons.UTF8 );
 httpServletResponse.setCharacterEncoding("UTF-8");

 Следовательно необходимо создать фильтр перекодирвоко и поставить его первым в ряду фильтров.
 Текст фильтра ниже.

 web.xml
<pre>
 --CharsetFilter start--

   <filter>
     <filter-name>Charset Filter</filter-name>
     <filter-class>servlet.ru.CharsetFilter</filter-class>
      <init-param requestEncoding='UTF-8'/>
   </filter>

   <filter-mapping>
     <filter-name>Charset Filter</filter-name>
     <url-pattern>/*</url-pattern>
   </filter-mapping>

  --CharsetFilter end--

</pre>
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 31.10.2014 13:36
 */

public class CharsetFilter implements Filter
{
    // кодировка
    private String encoding;

    public void init ( FilterConfig config ) throws ServletException
    {
        // читаем из конфигурации
        encoding = config.getInitParameter ( "requestEncoding" );

        // если не установлена - устанавливаем Cp1251
        if ( encoding == null ) encoding = "Cp1251";
    }

    public void doFilter ( ServletRequest request, ServletResponse response, FilterChain next )
            throws IOException, ServletException
    {
        request.setCharacterEncoding ( encoding );
        next.doFilter ( request, response );
    }

    public void destroy () {}

}