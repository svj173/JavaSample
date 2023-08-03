package scheduler.quartz.trigger.simple;


import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * This Example will demonstrate how job parameters can be 
 * passed into jobs and how state can be maintained
 *
 * Пример демонстрирует работы с изменяемыми переменными.
 * Переменные, прописанные в самой Задаче - не сохраняют свои изменения. (т.е. при каждом старте монитьора их значение всегда = 1)
 * Переменные, сохраняемые в jobDataMap - сохраняют свои изменения (счетчик запусков Монитора).
 * 
 * @author Bill Kratzer
 */
public class JobStateExample {

    public void run() throws Exception
    {
        Logger log = LoggerFactory.getLogger(JobStateExample.class);

        log.info("------- Initializing -------------------");

        // First we must get a reference to a scheduler
        SchedulerFactory sf = new StdSchedulerFactory();
        Scheduler sched = sf.getScheduler();

        log.info("------- Initialization Complete --------");

        log.info("------- Scheduling Jobs ----------------");

        // get a "nice round" time a few seconds in the future....
        long ts = TriggerUtils.getNextGivenSecondDate(null, 10).getTime();

        // job1 will only run 5 times, every 10 seconds
        JobDetail job1 = new JobDetail("job1", "group1", ColorJob.class);
        SimpleTrigger trigger1 = new SimpleTrigger("trigger1", "group1", "job1", "group1",
                new Date(ts), null, 4, 10000);
        // pass initialization parameters into the job
        job1.getJobDataMap().put(ColorJob.FAVORITE_COLOR, "Green");
        job1.getJobDataMap().put(ColorJob.EXECUTION_COUNT, 1);
        
        // schedule the job to run
        Date scheduleTime1 = sched.scheduleJob(job1, trigger1);
        log.info(job1.getFullName() +
                " will run at: " + scheduleTime1 +  
                " and repeat: " + trigger1.getRepeatCount() + 
                " times, every " + trigger1.getRepeatInterval() / 1000 + " seconds");

        // job2 will also run 5 times, every 10 seconds
        JobDetail job2 = new JobDetail("job2", "group1", ColorJob.class);
        SimpleTrigger trigger2 = new SimpleTrigger("trigger2", "group1", "job2", "group1",
                new Date(ts + 1000), null, 4, 10000);
        // pass initialization parameters into the job
        // this job has a different favorite color!
        job2.getJobDataMap().put(ColorJob.FAVORITE_COLOR, "Red");
        job2.getJobDataMap().put(ColorJob.EXECUTION_COUNT, 1);
        
        // schedule the job to run
        Date scheduleTime2 = sched.scheduleJob(job2, trigger2);
        log.info(job2.getFullName() +
                " will run at: " + scheduleTime2 +
                " and repeat: " + trigger2.getRepeatCount() +
                " times, every " + trigger2.getRepeatInterval() / 1000 + " seconds"); 


        log.info("------- Starting Scheduler ----------------");

        // All of the jobs have been added to the scheduler, but none of the jobs
        // will run until the scheduler has been started
        sched.start();

        log.info("------- Started Scheduler -----------------");
        
        log.info("------- Waiting 60 seconds... -------------");
        try {
            // wait five minutes to show jobs
            Thread.sleep(60L * 1000L); 
            // executing...
        } catch (Exception e) {
        }

        log.info("------- Shutting Down ---------------------");

        sched.shutdown(true);

        log.info("------- Shutdown Complete -----------------");

        SchedulerMetaData metaData = sched.getMetaData();
        log.info("Executed " + metaData.getNumberOfJobsExecuted() + " jobs.");

    }

    public static void main(String[] args) throws Exception {

        JobStateExample example = new JobStateExample();
        example.run();
    }

}
