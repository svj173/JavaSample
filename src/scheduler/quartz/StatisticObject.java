package scheduler.quartz;


/**
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 18.03.2011 11:28:04
 */
public class StatisticObject<T>
{
    private String  paramName;
    /* счетчик использования параметра */
    private Integer useCount;
    /* Значение параметра */
    private T       value;


    public StatisticObject ( String paramName, T value )
    {
        this.paramName = paramName;
        this.value      = value;
        useCount        = null;
    }

    public StatisticObject ( String paramName )
    {
        this ( paramName, null );
    }

    @Override
    public String toString()
    {
        StringBuffer result;

        result = new StringBuffer(128);
        result.append ( getParamName() );
        result.append ( "\t" );
        result.append ( getValue() );
        if ( isUseCount() )
        {
            result.append ( "\t" );
            result.append ( getUseCount() );
        }

        return result.toString();
    }

    public String getParamName ()
    {
        return paramName;
    }

    public Integer getUseCount ()
    {
        return useCount;
    }

    public boolean isUseCount ()
    {
        return useCount != null;
    }

    public void setUseCount ( Integer useCount )
    {
        this.useCount = useCount;
    }

    public T getValue ()
    {
        return value;
    }

    public void setValue ( T value )
    {
        this.value = value;
    }

}
