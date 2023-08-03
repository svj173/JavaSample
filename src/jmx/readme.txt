http://javagu.ru/portal/dt?last=false&provider=javaguru&ArticleId=GURU_ARTICLE_64541&SecID=GURU_SECTION_63111

Java Management Extensions (JMX) - это операционная среда для управления и мониторинга ресурсов. Она не являются стандартной частью Java 2 Standard Edition, но ссылочная реализация работает с J2SE 1.4 и в бета-версии является частью спецификации Java(tm) 2 Enterprise Edition (J2EE(tm)).

Назначение JMX - управление и мониторинг ресурсов. Ресурсы могут быть физическими, например, малые сетевые устройства, или логическими, например, установленные приложения. Вы определяете ресурсы в объектах, называемых MBeans. Эти объекты содержат управляемые ресурсы. Ресурсы могут рассматриваться как свойства компонента JavaBeans. MBeans затем отображаются на JMX-агенты. Агенты знают, как прочитать состояние ресурсов при помощи MBeans и могут получить разрешение на изменение их состояния при отображении способом, допускающим запись. Агенты доступны в свою очередь на распределенном уровне, обычно при помощи клиента, использующего HTML или SNMP (Simple Network Management Protocol). Это дает вам возможность управлять установками ресурсов без необходимости выполнения прилагающихся программ настройки или создания собственных средств управления приложениями.

В данной статье не делается попытки полного обсуждения архитектуры JMX. Вместо этого здесь приводится краткая, практическая информация по использованию JMX в течение цикла жизни программы. Для начала вам необходимы бинарные файлы ссылочной реализации JMX версии 1.2. Их можно загрузить с домашней страницы JMX. Там находится одна версия для всех платформ. Разархивируйте файлы в рабочий каталог.

В загрузочном пакете находятся два файла: lib/jmxri.jar и lib/jmxtools.jar. Файл jmxri.jar представляет собой ссылочную реализацию JMX. В нем находятся стандартные JMX-классы, расположенные в пакете javax.management. Файл jmxtools.jar - это набор инструментальных программ JMX. В нем находятся не поддерживаемые классы, которые Sun предоставляет для разработки. Оба этих JAR-файла должны быть расположены в вашем CLASSPATH. Для упрощения задачи просто скопируйте их в подкаталог jre/lib/ext вашего каталога с установленной Java Runtime Environment (JRE).

Например, подсчет количества IP-адресов, поступивших в сегмент сети за неделю.
Вот определение интерфейса:

   public interface HelloMBean {
     public String getMessage();
     public void setMessage(String message);
     public int getChangeCount();
     public void resetCounter();
   }

   Реализация этого интерфейса довольно проста:

      public class Hello implements HelloMBean {
        private String message;
        private int changeCount;

        public String getMessage() {
          return message;
        }

        public void setMessage(String message){
          this.message = message;
          changeCount++;
        }

        public int getChangeCount() {
          return changeCount;
        }

        public void resetCounter() {
          changeCount = 0;
        }
      }

   MBeanServer server =
      MBeanServerFactory.createMBeanServer();

Далее зарегистрируйте ваш MBeans на сервере. Каждый компонент регистрируется в форме domain:key1=XXX1, key2=XXX2, где key1 является названием атрибута, а XXX - соответствующим значением (domain заменяется названием домена). Представляйте себе этот процесс как дачу названия экземпляру класса. Перед символом ":" домен именует уникальное пространство имен для экземпляров. В общем случае они называются аналогично пакетам для гарантии уникальности, используя перевернутые DNS-имена (например, com.sun.java).

Итак, если вы хотите создать и зарегистрировать два компонента MBeans с названием hello1 и hello2 и типом Hello, вы должны использовать следующий код:

   HelloMBean hello1 = new Hello();
   ObjectName helloObjectName = new ObjectName(
     "HelloServer:type=Hello,name=hello1");
   server.registerMBean(hello, helloObjectName);
   HelloMBean hello2 = new Hello();
   ObjectName helloObjectName2 = new ObjectName(
     "HelloServer:type=Hello,name=hello2");
   server.registerMBean(hello2, helloObjectName2);

Для простоты имя домена здесь равно просто HelloServer, поскольку в примере нет конфликтов имен.

Программе для поддержки JMX этого было бы достаточно. Однако вы не сможете увидеть достаточно много. Для этого необходим клиентский интерфейс. Вместо разработки вашего собственного клиентского интерфейса используйте jmxtools.jar из загрузочного пакета.

Внутри jmxtools.jar находится HtmlAdaptorServer. Он обеспечивает HTML-просмотр сервера MBean. Вам необходимо создать и зарегистрировать сервер аналогично обычному MBean. Но есть одно исключение: вы должны указать серверу номер порта для прослушивания. В данном примере используется порт 8082. Если этот порт не доступен, выберите другой и сделайте необходимые изменения в коде. Затем вы можете присоединить HTML-адаптер к MBean-серверу и получить доступ к MBeans.

   HtmlAdaptorServer adapterServer =
     new HtmlAdaptorServer();
   ObjectName adapterObjectName = new ObjectName(
      "HelloServer:name=htmladapter,port=8082");
   adapterServer.setPort(8082);
   server.registerMBean(
      adapterServer, adapterObjectName);
   adapterServer.start();

Далее приведен полный текст серверной программы с явной обработкой всех возможных исключительных ситуаций:

import com.sun.jdmk.comm.*;
import javax.management.*;

 public class HelloAgent {
    public static void main(String args[]) {
     MBeanServer server =
       MBeanServerFactory.createMBeanServer();
     HtmlAdaptorServer adaptorServer =
        new HtmlAdaptorServer();
     HelloMBean hello1 = new Hello();
     HelloMBean hello2 = new Hello();
     try {
       ObjectName helloObjectName1 = new ObjectName(
         "HelloServer:type=Hello,name=hello1");
       server.registerMBean(hello1, helloObjectName1);
       ObjectName helloObjectName2 = new ObjectName(
         "HelloServer:type=Hello,name=hello2");
       server.registerMBean(hello2, helloObjectName2);
       ObjectName adaptorObjectName = new ObjectName(
          "HelloServer:type=htmladaptor,port=8082");
       adaptorServer.setPort(8082);
       server.registerMBean(
          adaptorServer, adaptorObjectName);
       adaptorServer.start();
     } catch (MalformedObjectNameException e) {
       System.out.println("Bad object name");
       e.printStackTrace();
     } catch (InstanceAlreadyExistsException e) {
       System.out.println("Already exists");
       e.printStackTrace();
     } catch (MBeanRegistrationException e) {
       System.out.println("Registration problems");
       e.printStackTrace();
     } catch (NotCompliantMBeanException e) {
       System.out.println("Registration problems");
       e.printStackTrace();
     }
   }
 }

Теперь вам нужно откомпилировать три класса и запустить программу HelloAgent:

javac HelloMBean.java Hello.java HelloAgent.java
java HelloAgent

Программа HelloAgent не возвратит управление. Она выполняет свою работу, включая настройку текущего состояния вашего компонента MBeans. Для получения доступа к серверу вы можете использовать любой HTML-клиент для соединения с соответствующим портом. Для управления агентом наберите в адресной строке вашего броузера http://localhost:8082/.

Ваш броузер должен отобразить список HelloServer и его три компонента MBeans (два компонента Hello и HTML-адаптер):

    hello1

    hello2

    htmladapter
