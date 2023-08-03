Пример

Предположим, нам нужно ограничить доступ к некоторым функциям веб-приложения для разных пользователей.
Иными словами необходимо реализовать права (permissions).
Для этого можно добавить следующее перечисление в класс пользователя: User

Создадим аннтоацию, которую затем будем использовать для проверки прав: PermissionRequired

Теперь предположим у нас есть некоторое действие, право на выполнение которого мы хотим ограничить,
например, UserDeleteAction. Мы добавляем аннтоацию на это действие следующим образом:

Теперь используя reflection можно принимать решение, разрешать или не разрешать выполнение определенного действия:

User user = ...;
Class actionClass = ...;
PermissionRequired permissionRequired = actionClass.getAnnotation(PermissionRequired.class);
if (permissionRequired != null)
    if (user != null && user.getPermissions().contains(permissionRequired.value()))
        // выполнить действие
