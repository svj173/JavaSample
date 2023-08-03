package scheduler.quartz.trigger.simple;


import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;


/**
 *
 * <p>
 * A dumb implementation of an InterruptableJob, for unittesting purposes.
 * </p>
 *
 * @author <a href="mailto:bonhamcm@thirdeyeconsulting.com">Chris Bonham</a>
 * @author Bill Kratzer
 */
public class DumbInterruptableJob02 implements StatefulJob, InterruptableJob
{
    public static final String COUNT   = "counters_step";

    // logging services
    private static Logger log = LoggerFactory.getLogger( DumbInterruptableJob02.class);

    // has the job been interrupted? -- параметр передается только при запущенной задаче. Если передать до запуска - не сохранится.
    private boolean interrupted = false;




    /**
     * <p>
     * Empty constructor for job initilization
     * </p>
     */
    public DumbInterruptableJob02 () {
    }


    /**
     * <p>
     * Called by the <code>{@link org.quartz.Scheduler}</code> when a <code>{@link org.quartz.Trigger}</code>
     * fires that is associated with the <code>Job</code>.
     * </p>
     *
     * @throws org.quartz.JobExecutionException if there is an exception while executing the job.
     */
    public void execute ( JobExecutionContext context )   throws JobExecutionException
    {
        String      jobName;
        JobDataMap  jobMap, triggerDataMap;
        int         count;

        jobName = context.getJobDetail().getFullName();
        log.info("--- Start: " + jobName + " executing at " + new Date());

        try
        {
            // счетчик в памяти задачи
            jobMap  = context.getJobDetail().getJobDataMap();
            count   = jobMap.getInt ( COUNT );
            count++;
            log.info("------ count = " + count );
            jobMap.put ( COUNT, count );

            // счетчик в памяти триггера. Чтобы при удалении триггера счетчик 100% обнулялся.
            triggerDataMap  = context.getTrigger().getJobDataMap();
            count           = triggerDataMap.getInt ( COUNT );
            count++;
            log.info("------ trigger count = " + count );
            triggerDataMap.put ( COUNT, count );

            for ( int i = 0; i < 4; i++ )
            {
                // periodically check if we've been interrupted...
                if ( interrupted )
                {
                    log.info("------ " + jobName + "  -- Interrupted... Stop work!");
                    return;
                }
                
                try
                {
                    Thread.sleep(1000L);
                } catch (Exception ignore) {
                    ignore.printStackTrace();
                }

                log.info("------ " + jobName + "  -- work");
            }

        } finally {
            log.info("--- Finish: " + jobName + " completed at " + new Date());
        }
    }

    /**
     * Метод вызывается Планировщиком когда он получает команду - Прервать указанную задачу.
     * 
     * <p>
     * Called by the <code>Scheduler</code> when a user
     * interrupts the <code>Job</code>.
     * </p>
     *
     * @throws UnableToInterruptJobException if there is an exception while interrupting the job.
     */
    public void interrupt() throws UnableToInterruptJobException
    {

        log.info("---" + "  -- INTERRUPTING --");
        interrupted = true;
    }

}
