package scheduler.quartz.trigger.cron;


import org.quartz.CronTrigger;

import java.util.Date;

/**
 * Пример изменения cron-триггера с перезапуском его в планировщике.
 * Проблема в том что планировщик сразу после изменения стартует данную задачу, а не по плану..
 * Пробуем поигарться с параметром startTime триггера.
 * <p/>
 * so many people looks for updating cron trigger with new cron expression. updating cron expression is very easy
 * First get the existing cron and now create new cronTrigger with the existing cron trigger details
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 03.07.12 15:13
 */
public class UpdatingCronExp
{
    public void updateCronExpression ( org.quartz.impl.StdScheduler stdSchedular, String triggerName, String groupName )
    {
        CronTrigger oldCronTrigger, newCronTrigger;
        Date startTime;

        String newCronExpression = "0 20 9 * * ?";
        try
        {
            oldCronTrigger = ( CronTrigger ) stdSchedular.getTrigger ( triggerName, groupName );

            // Creating a new cron trigger
            newCronTrigger = new CronTrigger ();
            newCronTrigger.setJobName ( oldCronTrigger.getJobName() );
            newCronTrigger.setName ( triggerName );
            newCronTrigger.setCronExpression ( newCronExpression );

            // startTime
            startTime   = newCronTrigger.getNextFireTime();

            // Reschedule the job with updated cron expression
            stdSchedular.rescheduleJob ( triggerName, groupName, newCronTrigger );

        } catch ( Exception e )        {
            e.printStackTrace ();
        }
    }

    public void test ( String jobName, String triggerName, String groupName )
    {
        CronTrigger     newCronTrigger;
        Date            startTime;
        String          newCronExpression;

        newCronExpression = "0 0/10 * * * ?";
        System.out.println ( "newCronExpression = " + newCronExpression );

        try
        {
            // Creating a new cron trigger
            newCronTrigger = new CronTrigger();
            newCronTrigger.setJobName ( jobName );
            newCronTrigger.setName ( triggerName );
            newCronTrigger.setCronExpression ( newCronExpression );

            // startTime
            startTime   = newCronTrigger.getNextFireTime();
            System.out.println ( "startTime = " + startTime );

        } catch ( Exception e )        {
            e.printStackTrace ();
        }
    }

    public static void main ( String[] args )
    {
        UpdatingCronExp u;

        u   = new UpdatingCronExp();
        u.test ( "jobName", "triggerName", "groupName" );
    }

}
