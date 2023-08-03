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
public class DumbInterruptableJob implements InterruptableJob
{
    // logging services
    private static Logger log = LoggerFactory.getLogger(DumbInterruptableJob.class);
    
    // has the job been interrupted?
    private boolean interrupted = false;


    /**
     * <p>
     * Empty constructor for job initilization
     * </p>
     */
    public DumbInterruptableJob() {
    }


    /**
     * <p>
     * Called by the <code>{@link org.quartz.Scheduler}</code> when a <code>{@link org.quartz.Trigger}</code>
     * fires that is associated with the <code>Job</code>.
     * </p>
     * 
     * @throws JobExecutionException if there is an exception while executing the job.
     */
    public void execute ( JobExecutionContext context )   throws JobExecutionException
    {
        String jobName;

        jobName = context.getJobDetail().getFullName();
        log.info("---- Start: " + jobName + " executing at " + new Date());

        try
        {
            for ( int i = 0; i < 4; i++ )
            {
                try
                {
                    Thread.sleep(1000L);
                } catch (Exception ignore) {
                    ignore.printStackTrace();
                }
                
                // periodically check if we've been interrupted...
                if ( interrupted ) {
                    log.info("--- " + jobName + "  -- Interrupted... Stop work!");
                    return; // could also choose to throw a JobExecutionException 
                             // if that made for sense based on the particular  
                             // job's responsibilities/behaviors
                }
            }
            
        } finally {
            log.info("---- Finish: " + jobName + " completed at " + new Date());
        }
    }
    
    /**
     * Метод вызывается Планировщиком когда он получает команду - Прервать указанную задачу.
     * <p>
     * Called by the <code>Scheduler</code> when a user
     * interrupts the <code>Job</code>.
     * </p>
     * 
     * @throws UnableToInterruptJobException if there is an exception while interrupting the job.
     */
    public void interrupt() throws UnableToInterruptJobException {
        log.info("---" + "  -- INTERRUPTING --");
        interrupted = true;
    }

}
