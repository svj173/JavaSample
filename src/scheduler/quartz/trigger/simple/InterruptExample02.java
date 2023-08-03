package scheduler.quartz.trigger.simple;


import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import scheduler.quartz.StatisticObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

/**
 * Пример прерывания работы Монитора.
 * При указании Планировщику прервать такую-то задачу, дергается метод interrup у этой задачи. И в нем уже выполняется функционал.
 * Т.е. прерывание - декларированное (т.е. монитору просто сообщается что он должен прервать свою работу).
 *
 * Каждый шаг - это своя задача в своем потоке.
 *
 * Здесь особенность
 * 1) Если задача запущена - т.е. будет выполняться N шагов, то считается что она Работает, даже если находится в промежутке между шагами (отдыхает)
 * Признак запущенности - внутренний счетчик шагов.
 * 2) При прерывании задачи необходимо обнулять этот счетчик.
 * 3) Счетчик хранится в jobDataMap
 * 4) Если прерывание приходит во время выполнения какого-то шага - все нормально - jobDataMap правится и сохраняется.
 * 5) Если прерывание приходит во время простоя между шагами - облом - jobDataMap правится но НЕ сохраняется.
 * И при след старте задачи счетчик считается не с 0.
 *
 * Проблемы
 * 1) Как обнулить счетчик в незапущенной задаче.
 * 2) Как отследить самый первый старт задачи.
 *
 * Вариант решения
 * - Для получения статуса задачи смотрим на наличие триггера у него. Если есть - BUSY, если нет - FREE.
 * Если конечно исходный статус не BREAK.
 *
 */
public class InterruptExample02
{
    private final Logger log = LoggerFactory.getLogger ( getClass () );

    public void run() throws Exception
    {
        SchedulerFactory sf;
        Scheduler sched;
        long ts;
        JobDetail job;
        SimpleTrigger trigger;
        Date ft;
        //TriggerListener triggerListener;


        log.info("------- Initializing ----------------------");


        // First we must get a reference to a scheduler
        sf      = new StdSchedulerFactory();
        sched   = sf.getScheduler();

        //triggerListener = new InterruptTriggerListener02();
        //sched.addGlobalTriggerListener ( triggerListener );

        log.info("------- Initialization Complete -----------");

        log.info("------- Scheduling Jobs -------------------");

        // get a "nice round" time a few seconds in the future...
        //ts = TriggerUtils.getNextGivenSecondDate(null, 15).getTime();

        // Задача: работает 4 сек, отдыхает - 3 сек.
        job     = new JobDetail ( "interruptableJob1", "group1", DumbInterruptableJob02.class );
        job.getJobDataMap().put ( DumbInterruptableJob02.COUNT, 0 );
        // true - иначе после окончания работы задачи Планировщик выкинет эту задачу из своего пула (Триггер по всей видимости убивается)
        job.setDurability ( true );
      
        trigger = new SimpleTrigger ( "trigger1", "group1", new Date(), null, 7, 7000L );   // ts  -  7, 7000L
        trigger.getJobDataMap().put ( DumbInterruptableJob02.COUNT, 0 );
        //trigger.addTriggerListener (  );

        ft      = sched.scheduleJob ( job, trigger );

        log.info(job.getFullName() + " will run at: " + ft + " and repeat: "
                + trigger.getRepeatCount() + " times, every "
                + trigger.getRepeatInterval() / 1000 + " seconds");

        // start up the scheduler (jobs do not start to fire until
        // the scheduler has been started)
        sched.start();
        log.info("------- Started Scheduler -----------------");

        // работают по отдельности
        //startShowWork ( sched );
        startWithWorkInterrupt ( sched );
        //startWithFreeInterrupt ( sched );

        log.info("------- Shutting Down ---------------------");

        sched.shutdown ( true );

        log.info("------- Shutdown Complete -----------------");
        SchedulerMetaData metaData = sched.getMetaData();
        log.info("Executed " + metaData.getNumberOfJobsExecuted() + " jobs.");
    }

    /* Цикл простого просмотра работы задач. */
    private void startShowWork ( Scheduler sched )
    {
        log.info("------- Starting 'startShowWork' ----------");
        for ( int i=0; i < 30; i++ )     // 6
        {
            try
            {
                Thread.sleep ( 2000L );     // 2000 - просто опрос статуса

                processInfo ( sched );

            } catch (Exception e) {
            }
        }
    }

    private void processInfo ( Scheduler sched )
    {
        int count;

        count   = getJobStatus2 ( sched, "interruptableJob1", "group1", log ); // as triggers size
        log.info("-->>>-- Job status: " + count );
        count   = getTaskCount ( sched, "interruptableJob1", "group1", log );
        log.info("-->>>-- Task count: " + count );
        count   = getTaskCountByTrigger ( sched, "trigger1", "group1", log );
        log.info("-->>>-- Task trigger count: " + count );
    }

    /* Цикл - c прерыванием задачи во время ее работы . */
    private void startWithWorkInterrupt ( Scheduler sched )
    {
        int count;
        boolean b;

        log.info("------- Starting 'startWithWorkInterrupt' ----------");
        for ( int i=0; i < 6; i++ )     // 6
        {
            try
            {
                Thread.sleep ( 10000L );     // 10000 - прерывание во время работы. 5500 - прерывание во время простоя
                // TRUE - значит задача была запущена и ей передали сообщение о прерывании. Исключение на interrupt - если эта задача не явл InterruptableJob
                b   = sched.interrupt ( "interruptableJob1", "group1" );
                log.info("-->>>-- Call interrupt JOB: " + b );
                // удаляем триггер
                b   = sched.unscheduleJob ( "trigger1", "group1" );
                log.info("-->>>-- Delete trigger: " + b );

                processInfo ( sched );

            } catch (Exception e) {
            }
        }
    }

    /* Цикл - c прерыванием задачи во время ее отдыха. */
    private void startWithFreeInterrupt ( Scheduler sched )
    {
        int count;
        boolean b;

        log.info("------- Starting 'startWithFreeInterrupt' ----------");
        for ( int i=0; i < 8; i++ )
        {
            try
            {
                Thread.sleep ( 5500L );     // 10000 - прерывание во время работы. 5500 - прерывание во время простоя
                // TRUE - значит задача была запущена и ей передали сообщение о прерывании. Исключение на interrupt - если эта задача не явл InterruptableJob
                b   = sched.interrupt ( "interruptableJob1", "group1" );
                log.info("-->>>-- Call interrupt JOB: " + b );
                // удаляем триггер
                b   = sched.unscheduleJob ( "trigger1", "group1" );
                log.info("-->>>-- Delete trigger: " + b );

                processInfo ( sched );

            } catch (Exception e) {
            }
        }
    }

    private int getJobStatus2 ( Scheduler sched, String jobName, String jobGroup, Logger log )
    {
        JobDetail   job;
        JobDataMap  jobMap;
        int         count;
        Collection<StatisticObject> jobs;
        Trigger[]   triggers;

        log.info ( "Start. jobName = '" + jobName + "',  jobGroup = '" + jobGroup + "'" );

        try
        {
            // взять все триггеры, привязанные к данной задаче
            triggers    = sched.getTriggersOfJob ( jobName, jobGroup );
            //if ( triggers.length == 0 )                count   = 0;
            count       = triggers.length;

        } catch ( Exception e )        {
            log.error ( "err", e );
            count   = -100;
        }

        return count;
    }

    private int getTaskCount ( Scheduler sched, String jobName, String jobGroup, Logger log )
    {
        JobDetail   job;
        JobDataMap  jobMap;
        int         count;

        //log.info ( "Start. jobName = '" + jobName + "',  jobGroup = '" + jobGroup + "'" );

        try
        {
            job     = sched.getJobDetail ( jobName, jobGroup );
            jobMap  = job.getJobDataMap();
            count   = jobMap.getInt ( DumbInterruptableJob02.COUNT );

        } catch ( Exception e )        {
            log.error ( "err", e );
            count   = -100;
        }

        return count;
    }

    private int getTaskCountByTrigger ( Scheduler sched, String triggerName, String triggerGroup, Logger log )
    {
        JobDataMap  triggerMap;
        int         count;
        Trigger     trigger;
        SimpleTrigger simpleTrigger;

        //log.info ( "Start. triggerName = '" + triggerName + "',  triggerGroup = '" + triggerGroup + "'" );

        try
        {
            trigger     = sched.getTrigger ( triggerName, triggerGroup );
            if ( (trigger != null) && (trigger instanceof SimpleTrigger) )
            {
                //triggerMap  = trigger.getJobDataMap();
                //count       = triggerMap.getInt ( DumbInterruptableJob02.COUNT );
                simpleTrigger   = (SimpleTrigger) trigger;
                count           = simpleTrigger.getTimesTriggered();
            }
            else
                count   = 0;

        } catch ( Exception e )        {
            log.error ( "err", e );
            count   = -100;
        }

        return count;
    }

    /**
     * Выдать статистическую информацию о процессах
     * @return Список инфы о процессах -- группа-имя
     * @param scheduler  Планировщик
     * @param log        Логгер
     */
    public Collection<StatisticObject> getJobsInfo ( Scheduler scheduler, Logger log )
    {
        String[]        jobGroups, jobNames;
        JobDetail       jobDetail;
        StatisticObject stat;
        Collection<StatisticObject> result;

        result  = new ArrayList<StatisticObject> ();
        try
        {
            jobGroups   = scheduler.getJobGroupNames();
            for ( String groupName : jobGroups )
            {
                jobNames    = scheduler.getJobNames ( groupName );
                for ( String jobName : jobNames )
                {
                    jobDetail    = scheduler.getJobDetail ( jobName, groupName );
                    if ( jobDetail != null )
                    {
                        stat    = new StatisticObject<String> ( groupName, jobName );
                        result.add ( stat );
                    }
                }
            }
        } catch ( SchedulerException e )        {
            stat    = new StatisticObject<String> ( "Ошибка:", e.toString() );
            result.add ( stat );
            log.error ( "err", e );
        }
        return result;
    }

    public static void main ( String[] args ) throws Exception
    {
        InterruptExample02 example = new InterruptExample02();
        example.run();
    }

}
