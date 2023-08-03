package xml.stax;


import java.io.Serializable;
import java.util.Date;
import java.util.Properties;


/**
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 08.07.2011 10:35:47
 */
public class MonitorInfo implements Serializable, Cloneable
{
    // Параметры, хранимые в памяти - динамические параметры
    private int             countAll, countOk, countError, countAlerts;
    private String         state;
    private Date nextStart;

    /* Дополнительные параметры монитора. Описаны в теге params monitors.xml-файла */
    private Properties props;

    // Параметры, хранимые в БД Мониторы, либо в БД Мониторинг (не все)
    private String          monitorClass, jobName, jobRuName, jobGroup, triggerName, triggerGroup, logFileName, cronDate;
    private String          jobType;
    private long            id;


    public MonitorInfo ()
    {
        countAll    = countOk = countError = countAlerts = 0;
        state       = "FREE";
        nextStart   = null;
        props       = null;
        monitorClass    = jobName = jobRuName = jobGroup = triggerName = triggerGroup = logFileName = cronDate   = null;
        jobType     = "MANUAL";
        id          = -3;
    }


    public void reset ()
    {
        countAll = countOk = countError = countAlerts = 0;
        //if ( monitoringTaskInfo != null )  monitoringTaskInfo.reset();
    }

    public Object clone () throws CloneNotSupportedException
    {
        return super.clone();
    }

    public String toString()
    {
        StringBuffer result;

        result = new StringBuffer(128);

        result.append ( "MonitorInfo [\n state = " );
        result.append ( getState() );
        result.append ( "\n countAll = " );
        result.append ( getCountAll() );
        result.append ( "\n countOk = " );
        result.append ( getCountOk() );
        result.append ( "\n countError = " );
        result.append ( getCountError() );
        result.append ( "\n countAlerts = " );
        result.append ( getCountAlerts() );
        result.append ( "\n nextStart = " );
        result.append ( getNextStart() );

        result.append ( "\n id = " );
        result.append ( getId() );
        result.append ( "\n jobType = " );
        result.append ( getJobType() );
        result.append ( "\n monitorClass = " );
        result.append ( getMonitorClass() );
        result.append ( "\n jobName = " );
        result.append ( getJobName() );
        result.append ( "\n jobRuName = " );
        result.append ( getJobRuName() );
        result.append ( "\n jobGroup = " );
        result.append ( getJobGroup() );
        result.append ( "\n triggerName = " );
        result.append ( getTriggerName() );
        result.append ( "\n triggerGroup = " );
        result.append ( getTriggerGroup() );
        result.append ( "\n logFileName = " );
        result.append ( getLogFileName() );
        result.append ( "\n cronDate = '" );
        result.append ( getCronDate() );
        result.append ( "\n props = '" );
        result.append ( getProps() );

        result.append ( "'\n]" );

        return result.toString();
    }

    public int getCountAll ()
    {
        return countAll;
    }

    public void setCountAll ( int countAll )
    {
        this.countAll = countAll;
    }

    public int getCountOk ()
    {
        return countOk;
    }

    public void setCountOk ( int countOk )
    {
        this.countOk = countOk;
    }

    public int getCountError ()
    {
        return countError;
    }

    public void setCountError ( int countError )
    {
        this.countError = countError;
    }

    public String getState ()
    {
        return state;
    }

    public void setState ( String state )
    {
        // здесь только для простых мониторов. для задач мониторинга - статус устанавливается отдельно  -- ???
        // -- тогда при обновлении root/Мониторов таски все всегда свободны
        this.state = state;
    }

    public Date getNextStart ()
    {
        return nextStart;
    }

    public void setNextStart ( Date nextStart )
    {
        this.nextStart = nextStart;
    }

    /* Счетчик - сколько всего сгенерировано алертов за время работы */
    public int getCountAlerts ()
    {
        return countAlerts;
    }

    public void setCountAlerts ( int countAlerts )
    {
        this.countAlerts = countAlerts;
    }

    public long getId ()
    {
        return id;
    }

    public void setId ( long id )
    {
        this.id = id;
    }

    public String getMonitorClass ()
    {
        return monitorClass;
    }

    public void setMonitorClass ( String monitorClass )
    {
        this.monitorClass = monitorClass;
    }

    public String getJobName ()
    {
        return jobName;
    }

    public void setJobName ( String jobName )
    {
        this.jobName = jobName;
    }

    public String getJobRuName ()
    {
        return jobRuName;
    }

    public void setJobRuName ( String jobRuName )
    {
        this.jobRuName = jobRuName;
    }

    public String getJobGroup ()
    {
        return jobGroup;
    }

    public void setJobGroup ( String jobGroup )
    {
        this.jobGroup = jobGroup;
    }

    public String getTriggerName ()
    {
        return triggerName;
    }

    public void setTriggerName ( String triggerName )
    {
        this.triggerName = triggerName;
    }

    public String getTriggerGroup ()
    {
        return triggerGroup;
    }

    public void setTriggerGroup ( String triggerGroup )
    {
        this.triggerGroup = triggerGroup;
    }

    public String getLogFileName ()
    {
        return logFileName;
    }

    public void setLogFileName ( String logFileName )
    {
        this.logFileName = logFileName;
    }

    public String getCronDate ()
    {
        return cronDate;
    }

    public void setCronDate ( String cronDate )
    {
        this.cronDate = cronDate;
    }

    public String getJobType ()
    {
        return jobType;
    }

    public void setJobType ( String jobType )
    {
        this.jobType = jobType;
    }

    public void setProps ( Properties props )
    {
        this.props = props;
    }

    public Properties getProps ()
    {
        return props;
    }

    public void addCountOk ()
    {
        countOk++;
    }

    public void addCountAll ()
    {
        countAll++;
    }

    public void addCountError ()
    {
        countError++;
    }

    public void addCountAlerts ()
    {
        countAlerts++;
    }

}

