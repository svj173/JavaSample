package thread.worker;


import java.util.LinkedList;

/**
 * Обработчик каких-то приходящих из вне работ.
 * <BR/>
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 07.10.2015 13:42
 */
public class ThreadWorker implements Runnable
{

    final LinkedList<Work> list = new LinkedList<Work> ();

    public void addWork ( Work work )
    {
        synchronized ( list )
        {
            list.add ( work );
            list.notifyAll ();
        }
    }

    public void run ()
    {
        while ( true )
        {
            synchronized ( list )
            {
                if ( list.size () == 0 )
                {
                    try
                    {
                        list.wait ();
                    } catch ( InterruptedException e )        {
                        e.printStackTrace ();
                    }
                }
                Work work = list.removeFirst();
                work.doWork ();
            }
        }
    }

    /**
     * Пример:
     * получаем следующее:
     This thread#1 is owner of Work implementation
     This thread#7 invokes Work implementation

     * @param args
     * @throws Throwable
     */
    public static void main(String[] args) throws Throwable
    {
        ThreadWorker worker = new ThreadWorker ();
        Thread thread = new Thread ( worker );
        thread.start();
        Work work = new MyWork ( Thread.currentThread () );

        worker.addWork ( work );
    }

}

