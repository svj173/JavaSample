package statics;

/**
 * <BR> Проверяем статические методы на одновременное обращение - будут ли они мешать друг другу.
 * <BR> Результат: Выдавались правильные значения. Без ошибок. Но в синглетонах должно
 *  ломаться - было подобное уже на моей практике.
 * <BR>
 * <BR> User: svj
 * <BR> Date: 17.03.2006
 * <BR> Time: 13:26:59
 */
public class TestStaticMethod  implements Runnable
{
    private int     max, osn, ok;
    private Thread  thread;
    private String  name;


    public TestStaticMethod ( int max, int osn )
    {
        this.max = max;
        this.osn = osn;
        name    = "Name_" + max + "_" + osn;
        thread  = new Thread ( this, name );
        System.out.println ( "Start thread = " + name );
        thread.start();
    }

    public void run ()
    {
        int ic, ir;
        String  str;
        for (int i=0; i<max; i++ )
        {
            ic  = Statics.get ( i ) + osn;
            ir  = i + osn;
            if ( ic != ir )
            {
                str = "-----";
                System.out.println ( name + ": i = " + i + ", result = " + ic + "\t\t" + (i+osn) + "\t" + str );
            }
            else    str = "";
            //logger.debug ( "" );
        }
        System.out.println ( "Finish thread = " + name );
    }

    public static void main ( String args[] )
    {
        TestStaticMethod    p1, p2, p3;
        System.out.println ( "Start" );
        p1  = new TestStaticMethod ( 1000, 10000 );
        p2  = new TestStaticMethod ( 1000, 20000 );
        p3  = new TestStaticMethod ( 1000, 30000 );

        try
        {
            // Ждем завершения потоков
            //Thread.sleep ( 10000 );
            p1.thread.join();
            p2.thread.join();
            p3.thread.join();
        } catch ( InterruptedException e ) {
            e.printStackTrace ();
        }
        System.out.println ( "Finish" );
    }

}
