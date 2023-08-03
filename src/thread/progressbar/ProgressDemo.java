package thread.progressbar;



import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.swing.ProgressMonitor;


/**
 * Нет мультипотоковости.
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 28.07.2010 9:52:51
 */
public class ProgressDemo
{

    public static void main ( String[] args )
    {
        WorkQueue work = new WorkQueue ();
        work.begin ();
    }

/*
 Запуск задачи в требуемом потоке Swing
        Runable run;
        if (SwingUtilities.isEventDispatchThread())
          run.run();
        else
          SwingUtilities.invokeLater(run);

Вариант отрисовки свинг-обьектов в другом потоке
        if ( SwingUtilities.isEventDispatchThread()) {
            super.firePropertyChange(evt);
        } else {
            SwingUtilities.invokeLater(
                new Runnable() {
                    public void run() {
                        firePropertyChange(evt);
                    }
                });
        }


 */
    static class WorkQueue
    {
        final int NUM_THREADS = 5;
        ExecutorService execService = Executors.newFixedThreadPool ( NUM_THREADS );
        int totalThingsToDo = 0;
        int percentComplete = 0;

        public void begin ()
        {
            totalThingsToDo = 100;
            List<LongTask> tasks = new ArrayList<LongTask> ();

            // create and submit tasks to thread pool
            for ( int i = 0; i < NUM_THREADS; i++ )
            {
                LongTask task = new LongTask ( totalThingsToDo / NUM_THREADS, i );
                tasks.add ( task );
                execService.submit ( task );
            }

            ProgressMonitor monitor = new ProgressMonitor ( null, "Doing stuff...", "", 0, totalThingsToDo );

            // monitor progress until done
            while ( percentComplete < totalThingsToDo )
            {
                int partialProgress = 0;
                for ( LongTask task : tasks )
                {
                    partialProgress += task.getProgress ();
                }

                percentComplete = (int) ( partialProgress / (float) tasks.size() );

                try
                {
                    // wait one second between progress updates
                    Thread.sleep ( 1000 );
                } catch ( InterruptedException ie )                 {
                    Thread.currentThread().interrupt ();
                }

                monitor.setNote ( percentComplete + " %" );
                monitor.setProgress ( percentComplete );
            }

            // shutdown the thread pool
            execService.shutdown();
        }

    }

/*
  // вариант окончания работы
  public void shutdown() throws InterruptedException {
    executor.shutdown();
    executor.awaitTermination(30, TimeUnit.SECONDS);
    executor.shutdownNow();
  }

 */
    /**
     * Mock task that does an arbitrarty number
     * <p/>
     * of "things" in a random amount of time.
     */

    static class LongTask implements Callable<Boolean>
    {
        int thingsToDo = 0;
        int thingsDone = 0;
        int taskId = 0;

        public LongTask ( int thingsToDo, int taskId )
        {
            this.thingsToDo = thingsToDo;
            this.taskId = taskId;
        }


        public Boolean call () throws Exception
        {
            boolean completed = false;

            while ( thingsDone < thingsToDo )
            {
                // random pause to simulate "working"
                Thread.sleep ( ( int ) ( Math.random () * 3000 ) );
                thingsDone++;
            }

            System.out.println ( taskId + " done" );
            return Boolean.valueOf ( completed );
        }

        public int getProgress ()
        {
            return ( int ) ( thingsDone / ( float ) thingsToDo * 100f );
        }

    }

}
