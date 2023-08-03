##!/bin/bash
PATH=$PATH:/usr/java/j2sdk1.5-sun/bin
JAVA_HOME=/usr/java/j2sdk1.5-sun; export JAVA_HOME;
LD_LIBRARY_PATH="/usr/lib"
export LD_LIBRARY_PATH
echo $LD_LIBRARY_PATH
echo "***********************************"
#MODULE_HOME=/home/kiosk/kiosk

TOOLS=$JAVA_HOME/lib/tools.jar
DT=$JAVA_HOME/lib/dt.jar
COMM=$JAVA_HOME/jre/lib/ext/comm.jar
#LOG4J_FILE=$MODULE_HOME/conf/logger.xml

CLASSPATH=$TOOLS:$DT:$COMM:$LD_LIBRARY_PATH
export CLASSPATH


#CMD="java -Dmodule.home=$MODULE_HOME -Dlog4j=$LOG4J_FILE -classpath $CLASSPATH: -jar #$MODULE_HOME/lib/kiosk.jar $MODULE_HOME/conf/config.xml"

#$CMD


SP=$CLASSPATH:./jsample.jar:./log4j.jar:./comm.jar:./svjUtil.jar:$JAVA_HOME/jre/lib
CMD="$JAVA_HOME/bin/java -classpath $SP comm.billacceptor.BAStart"
echo $CMD
$CMD
