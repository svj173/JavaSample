package annotation.s4;


/**
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 26.05.2012 18:09:00
 */

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


public class AnnotationTester
{

    @InProgress
    @GroupTODO (
            severity = GroupTODO.Severity.CRITICAL,
            item = "Figure out the amount of interest per month",
            assignedTo = "Brett McLaughlin",
            dateAssigned = "04-26-2004"
    )
    public void calculateInterest ( float amount, float rate )
    {
        // Need to finish this method later
    }
}


/**
 * Marker annotation to indicate that a method or class
 * is still in progress.
 */
/*
@Documented
@Inherited
@Retention ( RetentionPolicy.RUNTIME )
        @interface InProgress
{
}


@Documented
@Retention ( RetentionPolicy.RUNTIME )
        @interface GroupTODO
{

    public enum Severity
    {
        CRITICAL, IMPORTANT, TRIVIAL, DOCUMENTATION
    }

    ;

    Severity severity () default Severity.IMPORTANT;

    String item ();

    String assignedTo ();

    String dateAssigned ();
}
*/
