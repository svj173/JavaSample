package scrolling;


/**
 * Рассчитать общее кол-во пакетов
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 26.07.2011 17:39:33
 */
public class ScrollSizeCounter
{
    /**
     * Рассчитать общее кол-во пакетов
     *
     * @param fullSize  Общее кол-во записей
     * @param maxSize   Максимальное кол-во записей для отображения на экране
     * @return    кол-во порций-пакетов для скроллинга
     */
    public int createPacketsSize ( int fullSize, int maxSize )
    {
        int   result;
        float f, f2;
        double  d3;

        //Logger.getInstance().debug ( "ScrollingPanel.createPacketsSize: Start. fullSize = " + fullSize + ", maxViewSize = " + maxSize );

        // надо округлять результат деления в бОльшую сторону. При делении без остатка +1 - лишнее (svj)
        //result = fullSize / maxSize + 1;
        f       = (float) fullSize;
        f2      = f / maxSize;
        System.out.println ( "f2 = " + f2 );
        //result  = Math.round ( f2 );            // 54/53=1     -- 3.0=3, 1.018=1, 3.44=3
        //result  = (int) Math.floor(f2) + 1;   // 27/9=4          -- 3.0=4, 1.018=2,

        //d3  = Math.floor(f2);
        //System.out.println ( "d3 = " + d3 );     // 54/53=1     -- 3.0=3, 1.018=1, 3.44=3
        //result  = (int) d3;       // 54/53=1

        // x = (a+(b-1))/b.

        // int x = Math.ceil((double)a / b).intValue();
        //result = Math.ceil((double)fullSize / maxSize).intValue();
        result = (int)Math.ceil((double)fullSize / maxSize);

        //Logger.getInstance().debug ( "ScrollingPanel.createPacketsSize: Finish. result = " + result );

        return result;
    }

    public static void main(String[] args) throws Exception
    {
        ScrollSizeCounter s;
        int[] iFull, iMax;
        int ic;

        iFull = new int[] { 27, 54, 86, 12 };
        iMax  = new int[] { 9, 53, 25, 12 };

        s   = new ScrollSizeCounter();

        for ( int i=0; i<iMax.length; i++ )
        {
            ic  = s.createPacketsSize ( iFull[i], iMax[i] );
            System.out.println ( "-- "+i+". full="+iFull[i]+ ", max=" + iMax[i] + ", packetSize=" + ic );
        }
    }
    
}
