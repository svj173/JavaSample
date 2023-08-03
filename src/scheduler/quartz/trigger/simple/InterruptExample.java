package scheduler.quartz.trigger.simple;


import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * Пример прерывания работы Монитора.
 * При указании Планировщику прервать такую-то задачу, дергается метод interrup у этой задачи. И в нем уже выполняется функционал.
 * Т.е. прерывание - декларированное (т.е. монитору просто сообщается что он должен прервать свою работу).
 *
 * Demonstrates the behavior of <code>StatefulJob</code>s, as well as how
 * misfire instructions affect the firings of triggers of <code>StatefulJob</code>
 * s - when the jobs take longer to execute that the frequency of the trigger's
 * repitition.
 * 
 * <p>
 * While the example is running, you should note that there are two triggers
 * with identical schedules, firing identical jobs. The triggers "want" to fire
 * every 3 seconds, but the jobs take 10 seconds to execute. Therefore, by the
 * time the jobs complete their execution, the triggers have already "misfired"
 * (unless the scheduler's "misfire threshold" has been set to more than 7
 * seconds). You should see that one of the jobs has its misfire instruction
 * set to <code>SimpleTrigger.MISFIRE_INSTRUCTION_RESCHEDULE_NOW_WITH_EXISTING_REPEAT_COUNT</code>-
 * which causes it to fire immediately, when the misfire is detected. The other
 * trigger uses the default "smart policy" misfire instruction, which causes
 * the trigger to advance to its next fire time (skipping those that it has
 * missed) - so that it does not refire immediately, but rather at the next
 * scheduled time.
 * </p>
 * 
 * @author <a href="mailto:bonhamcm@thirdeyeconsulting.com">Chris Bonham</a>
 */
public class InterruptExample 
{

    public void run() throws Exception
    {
        final Logger log = LoggerFactory.getLogger(InterruptExample.class);

        log.info("------- Initializing ----------------------");

        // First we must get a reference to a scheduler
        SchedulerFactory sf = new StdSchedulerFactory();
        Scheduler sched = sf.getScheduler();

        log.info("------- Initialization Complete -----------");

        log.info("------- Scheduling Jobs -------------------");

        // get a "nice round" time a few seconds in the future...
        long ts = TriggerUtils.getNextGivenSecondDate(null, 15).getTime();

        JobDetail job = new JobDetail("interruptableJob1", "group1",
                DumbInterruptableJob.class);
        SimpleTrigger trigger = 
            new SimpleTrigger("trigger1", "group1", 
                    new Date(ts), 
                    null, 
                    SimpleTrigger.REPEAT_INDEFINITELY, 
                    5000L);
        Date ft = sched.scheduleJob(job, trigger);
        log.info(job.getFullName() + " will run at: " + ft + " and repeat: "
                + trigger.getRepeatCount() + " times, every "
                + trigger.getRepeatInterval() / 1000 + " seconds");

        // start up the scheduler (jobs do not start to fire until
        // the scheduler has been started)
        sched.start();
        log.info("------- Started Scheduler -----------------");
        

        log.info("------- Starting loop to interrupt job every 7 seconds ----------");
        for(int i=0; i < 50; i++) {
            try {
                Thread.sleep(7000L); 
                // tell the scheduler to interrupt our job
                sched.interrupt(job.getName(), job.getGroup());
            } catch (Exception e) {
            }
        }
        
        log.info("------- Shutting Down ---------------------");

        sched.shutdown(true);

        log.info("------- Shutdown Complete -----------------");
        SchedulerMetaData metaData = sched.getMetaData();
        log.info("Executed " + metaData.getNumberOfJobsExecuted() + " jobs.");

    }

    public static void main(String[] args) throws Exception {

        InterruptExample example = new InterruptExample();
        example.run();
    }

}
