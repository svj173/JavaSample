JAVA_HOME=/usr/java/j2sdk1.5-sun
PATH=$PATH:$JAVA_HOME/bin
MODULE_HOME=/home/kiosk/ba
#LD_LIBRARY_PATH=/usr/lib
LD_LIBRARY_PATH=$MODULE_HOME/linux

SP=$MODULE_HOME/jsample.jar:$MODULE_HOME/log4j.jar:$MODULE_HOME/linux/comm.jar:$MODULE_HOME/svjUtil.jar

TOOLS=$JAVA_HOME/lib/tools.jar
DT=$JAVA_HOME/lib/dt.jar
#JAVAX_COMM=$MODULE_HOME/lib

CLASSPATH=$TOOLS:$DT:$LD_LIBRARY_PATH:$SP

CMD="java -classpath $CLASSPATH comm.billacceptor.BillAcceptor ./logger.txt /dev/ttyS1"
echo $CMD
$CMD

