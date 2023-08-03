package swing.thread_pool;


import javax.swing.*;
import java.util.concurrent.*;


/**
 * использование конкурент с возможностью передавать в EDT поток результат работы из потоков-обработчиков (из пула потоков).
 * <BR/>
 * <BR/>
 * * Create a single SwingWorker.
 * Within the doInBackground() method kick off 10 threads either directly or by using an ExecutorService.
 * Use a CountDownLatch or CompletionService to synchronize between your master thread (i.e. SwingWorker background thread) and worker threads.
 * <p/>
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 01.02.2011 11:42:37
 */
public class ThreadPoolWorker extends SwingWorker<Integer, String>
{
    final int nThreads = 10;
    JLabel myLbl = new JLabel ();


    public Integer doInBackground () throws Exception
    {
        // Define executor service containing exactly nThreads threads.
        ExecutorService execService = Executors.newFixedThreadPool ( nThreads );


        // Define completion service that will contain the processing results.
        CompletionService<String> compService = new ExecutorCompletionService<String> ( execService );

        // Submit work to thread pool using the CompletionService.  Future<String>
        // instances will be added to the completion service's internal queue until complete.
        for ( int i = 0; i < nThreads; ++i )
        {
            compService.submit ( new MyCallable () );
        }

        // Take results from each worker as they appear and publish back to Swing thread.
        String result;
        while ( ( result = compService.take ().get () ) != null )
        {
            publish ( result );
        }

        return 10;
    }

    public void process ( String... chunks )
    {
        if ( chunks.length > 0 )
        {
            // Update label with last value in chunks in case multiple results arrive together.
            myLbl.setText ( chunks[ chunks.length - 1 ] );
        }
    }

    public void done ()
    {
        try
        {
            get (); // Will return null (as per Void type) but will also propagate exceptions.
        } catch ( Exception ex )        {
            //JOptionPane.show ... // Show error in dialog.
        }
    }

    private static class MyCallable implements Callable<String>
    {
        public String call ()
        {
            return "1111";
        }
    }

    public static void main ( String[] args )
    {
        new ThreadPoolWorker().execute ();
    }
    
}
