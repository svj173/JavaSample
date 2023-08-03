package annotation.s1;


import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


/**
 * Создадим аннтоацию, которую затем будем использовать для проверки прав:
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 26.05.2012 17:13:26
 */
@Retention ( RetentionPolicy.RUNTIME)
public @interface PermissionRequired {
    User.Permission value();
}
