package scheduler.quartz.trigger.simple;


import org.quartz.JobExecutionContext;
import org.quartz.Trigger;
import org.quartz.TriggerListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Назначение: отслеживать работу триггеров. Когда какой-то триггер завершает свою работу - если это задача мониторинга - кинуть в БД инфу о завершении.
 * Здесь же есть доступ к мапу и скорее всего данные в мапе можно менять - должны сохраниться.
 * <BR/> При удалении триггера - ничего не вызывается.
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 18.03.2011 13:26:14
 */
public class InterruptTriggerListener02 implements TriggerListener
{
    private final Logger log = LoggerFactory.getLogger ( getClass () );

    @Override
    public String getName ()
    {
        return "InterruptTrigger";
    }

    /**
     * Get the <code>{@link org.slf4j.Logger}</code> for this
     * class's category.  This should be used by subclasses for logging.
     */
    protected Logger getLog ()
    {
        return log;
    }

    /* Вызывается перед вызовом job.execute */
    public void triggerFired ( Trigger trigger, JobExecutionContext context )
    {
        log.info ( "============ triggerFired ================" );
    }

    /* Вызывается перед вызовом triggerFired и job.execute */
    public boolean vetoJobExecution ( Trigger trigger, JobExecutionContext context )
    {
        log.info ( "============ vetoJobExecution ================" );
        return false;
    }

    /* Когда тригер гасят? */
    public void triggerMisfired ( Trigger trigger )
    {
        log.info ( "============ triggerMisfired ================" );
    }

    /* Вызывается после job.execute - по завершении рабоыт триггера */
    public void triggerComplete ( Trigger trigger, JobExecutionContext context, int triggerInstructionCode )
    {
        log.info ( "============ triggerComplete ================" );
    }

}
