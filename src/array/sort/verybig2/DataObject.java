package array.sort.verybig2;


import java.io.Serializable;

/**
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 11.09.2015 13:49
 */
public class DataObject implements Comparable<DataObject>, Serializable
{
    private int     number;
    private String  name;
    private Double  tx;

    public DataObject ( int number, String name, Double tx )
    {
        this.number = number;
        this.name   = name;
        this.tx     = tx;
    }

    public String toString()
    {
        StringBuilder result;

        result = new StringBuilder();

        result.append ( number );
        result.append ( '/' );
        result.append ( name );
        result.append ( '/' );
        result.append ( tx );

        return result.toString();
    }

    @Override
    public int compareTo ( DataObject obj )
    {
        if ( obj == null )  return 1;

        /*
        int ic = obj.getNumber();

        if ( number == ic )
            return 0;
        else if ( number > ic )
            return 1;
        else
            return -1;
        */

        double ic = obj.getTx ();

        if ( tx == ic )
            return 0;
        else if ( tx > ic )
            return 1;
        else
            return -1;

    }

    public boolean equals ( Object obj )
    {
        boolean result;

        result = false;

        if ( (obj != null) && (obj instanceof DataObject) )
        {
            DataObject dObj = (DataObject) obj;
            result = compareTo ( dObj ) == 0;
        }
        return result;
    }

    public int getNumber ()
    {
        return number;
    }

    public String getName ()
    {
        return name;
    }

    public Double getTx ()
    {
        return tx;
    }

}
