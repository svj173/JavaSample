package xml.stax;


/**
 * Парсит XML. Вложенные друг в друга конструкции 'group name="Name"' складывает в виде Properties, где ключ - имя группы.
 * <BR/> Глубина вложения - неограничена.
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 08.07.2011 10:29:03
 */

import tools.FileTools;
import exception.SvjException;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.events.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Properties;
import java.util.Stack;


/**
 * Парсит файл описания Мониторов monitors.xml и формирует собственно объекты описания Мониторов.
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 01.09.2010 11:16:31
 */
public class MonitorsStaXParser
{
    private static final String MONITORS        = "monitors";
    private static final String MONITOR         = "monitor";
    private static final String CLASS           = "class";
    private static final String ID              = "id";
    private static final String RU_NAME         = "ru_name";
    private static final String CRON_DATE       = "cron_date";
    private static final String JOB_GROUP       = "job_group";
    private static final String TYPE            = "type";
    private static final String LOG_FILE_NAME   = "log_file_name";
    private static final String TRIGGER_NAME    = "trigger_name";
    private static final String TRIGGER_GROUP   = "trigger_group";

    //private static final String NAME            = "name";
    private static final String PROPERTIES      = "properties";
    private static final String GROUP           = "group";

    private static final QName  NAME = new QName("name");

    
    public void read ( Collection<MonitorInfo> monitors, String xmlFile ) throws SvjException
    {
        String      fileName = null;
        File        file;
        InputStream in;

        try
        {
            fileName = FileTools.createFileName ( xmlFile );
            System.out.println ( "MonitorsStaXParser.read: Start. fileName = '"+ fileName+ "'");

            file    = new File (fileName);
            if ( ! file.exists() )
                throw new SvjException ( null, "Отсутствует файл описания мониторов '", fileName, "'");

            in      = new FileInputStream (file);

            read ( in, monitors );

            System.out.println ( "MonitorsStaXParser.read: Finish. fileName = '" + fileName + "'");

        } catch ( SvjException ex )        {
            throw ex;
        } catch ( Exception e )        {
            e.printStackTrace();
            throw new SvjException ( e, "Системная ошибка чтения файла опиcания мониторов '", fileName, "' :\n", e );
        }
    }

    public void read ( InputStream in, Collection<MonitorInfo> monitors ) throws SvjException
    {
        MonitorInfo     monitor = null;
        String          tagName;
        Attribute       attr;
        XMLEvent        event;
        StartElement    startElement;
        XMLEventReader  eventReader;
        XMLInputFactory inputFactory;
        Properties      props = null;
        EndElement      endElement;
        long            id;
        Stack<Properties> currentProps;

        try
        {
            currentProps    = new Stack<Properties>();
            // Read the XML document

            inputFactory = XMLInputFactory.newInstance();
            eventReader  = inputFactory.createXMLEventReader(in);

            while ( eventReader.hasNext() )
            {
                event = eventReader.nextEvent();

                if ( event.isStartElement() )
                {
                    startElement = event.asStartElement();
                    tagName      = startElement.getName().getLocalPart();
                    System.out.println ( "--- tagName = '" + tagName + "'");

                    if ( tagName.equals(MONITORS) )
                    {
                        // Начало документа
                        continue;
                    }

                    if ( tagName.equals(MONITOR) )
                    {
                        attr    = startElement.getAttributeByName ( NAME );
                        if ( attr == null )
                            throw new SvjException ( null, "Отсутствует имя монитора");
                        monitor = new MonitorInfo();
                        monitor.setJobName ( attr.getValue() );
                        continue;
                    }

                    if ( tagName.equals(CLASS) )
                    {
                        monitor.setMonitorClass ( getText ( eventReader ) );
                        continue;
                    }

                    if ( tagName.equals ( PROPERTIES ) )
                    {
                        props = new Properties();
                        continue;
                    }

                    if ( tagName.equals ( ID ) )
                    {
                        try
                        {
                            id = Long.valueOf ( getText ( eventReader ) );
                        } catch ( Exception e )        {
                            id = -2;
                        }
                        monitor.setId ( id );
                        continue;
                    }

                    if ( tagName.equals ( RU_NAME ) )
                    {
                        monitor.setJobRuName ( getText ( eventReader ) );
                        continue;
                    }

                    if ( tagName.equals ( CRON_DATE ) )
                    {
                        monitor.setCronDate ( getText ( eventReader ) );
                        continue;
                    }

                    if ( tagName.equals ( JOB_GROUP ) )
                    {
                        monitor.setJobGroup ( getText ( eventReader ) );
                        continue;
                    }

                    if ( tagName.equals ( TYPE ) )
                    {
                        monitor.setJobType ( getText ( eventReader ) );
                        continue;
                    }

                    if ( tagName.equals ( LOG_FILE_NAME ) )
                    {
                        monitor.setLogFileName ( getText ( eventReader ) );
                        continue;
                    }

                    if ( tagName.equals ( TRIGGER_NAME ) )
                    {
                        monitor.setTriggerName ( getText ( eventReader ) );
                        continue;
                    }

                    if ( tagName.equals ( TRIGGER_GROUP ) )
                    {
                        monitor.setTriggerGroup ( getText ( eventReader ) );
                        continue;
                    }

                    // неизвестные имена - значит это индивидуальные параметры мониторов. складываем их
                    if ( tagName.equals ( GROUP ) )
                    {
                        Properties p;
                        // Это группа параметров
                        // - взять имя группы
                        attr    = startElement.getAttributeByName ( NAME );
                        // - занести в текущие проперти
                        p   = new Properties();
                        props.put ( attr, p );
                        // - изменить текущие проперти
                        // -- занести старые проперти в  стек
                        currentProps.push ( props );
                        props   = p;
                        continue;
                    }

                    // - это тег параметров
                    props.put ( tagName, getText ( eventReader ) );
                }
                else if ( event.isEndElement () )
                {
                    endElement  = event.asEndElement ();
                    tagName     = endElement.getName ().getLocalPart ();

                    if ( tagName.equals ( PROPERTIES ) )
                    {
                        monitor.setProps ( props );
                        continue;
                    }

                    if ( tagName.equals ( MONITOR ) )
                    {
                        monitors.add ( monitor );
                    }

                    if ( tagName.equals ( GROUP ) )
                    {
                        // извлечь старые проперти (c удалением)
                        props   = currentProps.pop();
                    }
                }
            }

        } catch ( SvjException ex ) {
            throw ex;
        } catch ( Exception e ) {
            e.printStackTrace();
            throw new  SvjException ( e, "Ошибка загрузки файла со свойствами алерт-обработчиков :\n" + e, e );
        }
    }

    private String getText ( XMLEventReader eventReader ) throws SvjException
    {
        String result = null;

        try
        {
            XMLEvent    event = eventReader.nextEvent();
            //System.out.println ("ElementStaXParser.getText: event = " + event );

            if ( event.isCharacters() )
            {
                Characters characters = event.asCharacters();
                if ( characters != null )
                {
                    result = characters.getData().trim();
                    if ( result.length() == 0 ) result = null;
                }
            }
            // Иначе - это тег закрытия - при отсутствии данных - например: <object_class></object_class>

        } catch ( Exception e )        {
            e.printStackTrace();
            throw new  SvjException ( e, "Ошибка получения текстовых данных во время загрузки файла с описанием мониторов :\n" + e.getMessage(), e );
        }

        return result;
    }


    public static void main ( String[] args )
    {
        Collection<MonitorInfo>     result;
        MonitorsStaXParser          xmlParser;
        String                      xmlFile;

        xmlFile     = "projects/SVJ/JavaSample/test/xml/monitors.xml";
        result      = new LinkedList<MonitorInfo>();

        System.out.println ( "Start. monitors xml file config = '" + xmlFile + "'" );

        try
        {
            // ------------ Получить список мониторов, прописанных в xml файле --------------------
            xmlParser   = new MonitorsStaXParser();
            xmlParser.read ( result, xmlFile );
        } catch ( Exception e )        {
            e.printStackTrace();
        }

        System.out.println ( "monitors from XML =\n" + result );
    }

}

