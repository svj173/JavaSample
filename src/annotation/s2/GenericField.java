package annotation.s2;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 26.05.2012 17:26:00
 */

/**
* Аннотация для пометки полей класса, обратите внимание на политику распространения
* аннотации RetentionPolicy.RUNTIME
* это значит, что маркеры не будут удалены после компиляции программы,
* а будут доступны во время выполнения кода
*/
@Retention(RetentionPolicy.RUNTIME)
public @interface GenericField {
    /**
     * Приоритет поля, именно это число будет управлять тем в каком порядке
     * выполняется сравнение полей в операциях equals, hashCode, compareTo
     * и в операции печати (преобразования в строку с помощью toString)
     *
     * @return
     */
    int priority() default 0;

        /**
     * Это множитель на которые будет выполняться умножение поля при расчете hashCode.
     * Формула hashCode = поле_1*priority_1 + поле_2*priority_2 + ....
     *
     * @return
     */
    byte multiplier() default 17;

    /**
     * Политика использования поля, определяется перечислымым типом данных  GenericField.USEPOLICY
     *
     * @return
     */
    USEPOLICY kind() default USEPOLICY.BOTH_PRINT_AND_EQUALS;

    public static enum USEPOLICY {
                 /**
         * Поле используется только для преоразования объекта в строку toString
         */
        ONLY_PRINT,
        /**
         * Поле используется только при расчете hashCode, equals и compareTo
         */
        ONLY_EQUALS,
        /**
         * Поле используется для двух перечисленных выше операций
         */
        BOTH_PRINT_AND_EQUALS
    }
}