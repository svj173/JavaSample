Попытка создать свой лоадер.
Особенно - для аплета - чтоыб побороть баг в IcedTea.

1) Разные классы грузятся разными лоадерами. Пример:

Let’s start by learning how different classes are loaded using various class loaders using a simple example:

public void printClassLoaders() throws ClassNotFoundException {

    System.out.println("Classloader of this class:"
        + PrintClassLoader.class.getClassLoader());

    System.out.println("Classloader of Logging:"
        + Logging.class.getClassLoader());

    System.out.println("Classloader of ArrayList:"
        + ArrayList.class.getClassLoader());
}

When executed the above method prints:

Class loader of this class:sun.misc.Launcher$AppClassLoader@18b4aac2
Class loader of Logging:sun.misc.Launcher$ExtClassLoader@3caeaf62
Class loader of ArrayList:null


2.1. Bootstrap Class Loader
Java classes are loaded by an instance of java.lang.ClassLoader. However, class loaders are classes themselves. Hence, the question is, who loads the java.lang.ClassLoader itself?

This is where the bootstrap or primordial class loader comes into the picture.

It’s mainly responsible for loading JDK internal classes, typically rt.jar and other core libraries located in $JAVA_HOME/jre/lib directory. Additionally, Bootstrap class loader serves as a parent of all the other ClassLoader instances.

This bootstrap class loader is part of the core JVM and is written in native code as pointed out in the above example. Different platforms might have different implementations of this particular class loader.


3) The default implementation of the method searches for classes in the following order: (Применение методов загрузки классов).

- Invokes the findLoadedClass(String) method to see if the class is already loaded.
- Invokes the loadClass(String) method on the parent class loader.
- Invoke the findClass(String) method to find the class.

-----------------------------------------------------------------------------------------------
Реализация

1) Грузим аплет из одного класса.
Этот аплет грузит уже настоящий ЕМС аплет, но использует наш класс-лоадер (но это уже как минимум вторйо класс!)


Искать попытки других...


-----------------------------------------------------------------------------------------------
-----------------------------------------------------------------------------------------------
