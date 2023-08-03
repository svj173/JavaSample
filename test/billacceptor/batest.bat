set SP=jsample.jar;log4j.jar;windows/comm.jar;svjUtil.jar

set JAVA_HOME=c:/Programm/Java/jdk1.5.0_01
set LD_LIBRARY_PATH=%MODULE_HOME%/windows
set MODULE_HOME=c:/Serg/Projects/SVJ/JavaSample/test/billacceptor

set TOOLS=%JAVA_HOME%/lib/tools.jar
set DT=%JAVA_HOME%/lib/dt.jar
REM set JAVAX_COMM=%MODULE_HOME%/windows

set CLASSPATH=%TOOLS%;%DT%;%LD_LIBRARY_PATH%;%SP%

set CMD=%JAVA_HOME%/bin/java -classpath %CLASSPATH% comm.billacceptor.BillAcceptor logger.txt COM1
echo %CMD%
%CMD%

