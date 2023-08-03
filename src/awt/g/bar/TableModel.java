package awt.g.bar;


import java.util.Vector;


/**
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 28.06.2011 11:36:19
 */
public class TableModel<D>
{
    private Vector<D> data = new Vector<D>();

    public Vector<D> getData ()
    {
        return data;
    }

    public void setData ( Vector<D> data )
    {
        this.data = data;
    }

}
