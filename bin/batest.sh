SP=./jsample.jar:./log4j.jar:./comm.jar:./svjUtil.jar

PATH=$PATH:/usr/java/j2sdk1.5-sun/bin
JAVA_HOME=/usr/java/j2sdk1.5-sun
LD_LIBRARY_PATH=/usr/lib
MODULE_HOME=/home/kiosk/kiosk

TOOLS=$JAVA_HOME/lib/tools.jar
DT=$JAVA_HOME/lib/dt.jar
JAVAX_COMM=$MODULE_HOME/lib

CLASSPATH=$TOOLS:$DT:$LD_LIBRARY_PATH:$SP:$JAVAX_COMM

CMD="java -classpath $CLASSPATH comm.billacceptor.BillAcceptor ./logger.txt /dev/ttyS1"
echo $CMD
$CMD

