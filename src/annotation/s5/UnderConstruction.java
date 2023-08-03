package annotation.s5;


/**
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 26.05.2012 18:13:43
 */
public @interface UnderConstruction {
        String owner() default "Patrick Naughton";
        String value() default "Object is Under Construction.";
        String createdBy() default "Mike Sheridan";
        String lastChanged() default "08/07/2011";
}

