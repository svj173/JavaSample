package array.sort.verybig2;


/**
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 11.09.2015 15:58
 */
public class DataObjectComparator implements SortComparator<DataObject>
{
    private String orderField;
    private boolean useAsc;    // TRUE - по возрастанию.

    public DataObjectComparator ( String orderField, String orderType )
    {
        this.orderField = orderField;
        useAsc = true;
        if ( (orderType != null) && orderType.equalsIgnoreCase ( "DESC" ) ) useAsc = false;
    }

    @Override
    public int compare ( DataObject o1, DataObject o2 )
    {
        int result;
        if ( o2 == null )
        {
            result = 1;
        }
        else if ( o1 == null )
        {
            result = -1;
        }
        else
        {
            if ( orderField != null )
            {
                result = 0;
                // сравнение по полю обьекта
                if ( orderField.equalsIgnoreCase ( "TX" ) )
                {
                    double d1, d2;
                    if ( useAsc )
                    {
                        d1 = o1.getTx();
                        d2 = o2.getTx();
                    }
                    else
                    {
                        d2 = o1.getTx();
                        d1 = o2.getTx();
                    }
                    if ( d1 > d2 ) result = 1;
                    if ( d1 < d2 ) result = -1;
                }
                else
                {
                    result = o1.compareTo ( o2 );
                }
            }
            else
            {
                result = o1.compareTo ( o2 );
            }
        }

        //System.out.println ( "=---= useAsc: "+ useAsc + "; o1: "+o1+"; o2: "+ o2 +"; result = "+ result );

        /*
        // Для Collections.sort ( part, comparator );
        if ( ! useAsc )
        {
            // инвертируем результат
            if ( result < 0 ) result = 1;
            if ( result > 0 ) result = -1;
        }
        //*/

        return result;
    }


    @Override
    public boolean compareIsOk ( DataObject obj1, DataObject obj2 )
    {
        boolean result;
        int     ic;

        result  = false;
        ic      = compare ( obj1, obj2 );

        if ( useAsc )
        {
            // obj1 < obj2 == -1
            if ( ic < 0 )   result = true;
            //if ( ic > 0 )   result = true;
        }
        else
        {
            //if ( ic > 0 )   result = true;
            if ( ic < 0 )   result = true;
        }

        return result;
    }
}
