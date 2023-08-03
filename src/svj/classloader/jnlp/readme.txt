Декомпилированные класс-лоадеры из OpenJDK.IcedTea - java applet plugin.

Ошибка работы родного лоадера:

2019-05-08 13:08:39.013 [GUI_admin] EltexEMS    ERROR   JVM error. Thread =     Trace:
java.lang.IllegalStateException: zip file closed
        at java.util.zip.ZipFile.ensureOpen(ZipFile.java:686)
        at java.util.zip.ZipFile.getEntry(ZipFile.java:315)
        at java.util.jar.JarFile.getEntry(JarFile.java:240)
        at java.util.jar.JarFile.getJarEntry(JarFile.java:223)
        at sun.misc.URLClassPath$JarLoader.getResource(URLClassPath.java:1054)
        at sun.misc.URLClassPath.getResource(URLClassPath.java:249)
        at java.net.URLClassLoader$1.run(URLClassLoader.java:366)
        at java.net.URLClassLoader$1.run(URLClassLoader.java:363)
        at java.security.AccessController.doPrivileged(Native Method)
        at java.net.URLClassLoader.findClass(URLClassLoader.java:362)
        at net.sourceforge.jnlp.runtime.JNLPClassLoader.access$1701(JNLPClassLoader.java:103)
        at net.sourceforge.jnlp.runtime.JNLPClassLoader$5.run(JNLPClassLoader.java:1666)
        at net.sourceforge.jnlp.runtime.JNLPClassLoader$5.run(JNLPClassLoader.java:1663)
        at java.security.AccessController.doPrivileged(Native Method)
        at net.sourceforge.jnlp.runtime.JNLPClassLoader.findClass(JNLPClassLoader.java:1662)
        at net.sourceforge.jnlp.runtime.JNLPClassLoader.loadClassExt(JNLPClassLoader.java:1699)
        at net.sourceforge.jnlp.runtime.JNLPClassLoader.loadClass(JNLPClassLoader.java:1498)
        at org.eltex.ems.web.gui.container.listener.EltexEventHandler.parseError(EltexEventHandler.java:313)

