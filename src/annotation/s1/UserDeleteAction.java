package annotation.s1;


/**
 * Теперь предположим у нас есть некоторое действие, право на выполнение которого мы хотим ограничить,
 * например, UserDeleteAction. Мы добавляем аннотацию на это действие следующим образом:
 * <BR/>
 * <BR/> Т.е. можем теперь обратиться к этому классу и вызвав аннотацию получить ответ - User.Permission.USER_MANAGEMENT
 * <BR/>
User user = ...;

// Определяем класс акции UserDeleteAction
Class actionClass = ...;

// Получаем от класса его аннотацию
PermissionRequired permissionRequired = actionClass.getAnnotation(PermissionRequired.class);

// Анализируем значение аннотации
if (permissionRequired != null)
    if (user != null && user.getPermissions().contains(permissionRequired.value()))
        // выполнить действие
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 26.05.2012 17:14:16
 */
@PermissionRequired ( User.Permission.USER_MANAGEMENT )
public class UserDeleteAction
{
    public void invoke ( User user )
    {
        /* */
    }
}
