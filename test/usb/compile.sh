JAVA_HOME=/usr/local/java
PATH=$PATH:$JAVA_HOME/bin
MODULE_HOME=/home/svj/java/usb2
#LD_LIBRARY_PATH=/usr/lib
LD_LIBRARY_PATH=$MODULE_HOME

SP=$MODULE_HOME/log4j.jar:$MODULE_HOME/svjUtil.jar:$MODULE_HOME/jsr80_linux-1.0.1.jar:$MODULE_HOME/jsr80.jar:$MODULE_HOME/jsr80_ri.jar
TOOLS=$JAVA_HOME/lib/tools.jar
DT=$JAVA_HOME/lib/dt.jar
CLASSPATH=$TOOLS:$DT:$LD_LIBRARY_PATH:$SP
CMD="javac -classpath $CLASSPATH *.java"
echo $CMD
$CMD
