PATH=$PATH:/usr/java/j2sdk1.5-sun/bin
JAVA_HOME=/usr/java/j2sdk1.5-sun
LD_LIBRARY_PATH=/usr/lib
export LD_LIBRARY_PATH SP=$CLASSPATH:./jsample.jar:./log4j.jar:./comm.jar:./svjUtil.jar
java -classpath $SP comm.touchscreen.TSStart logger.txt touchparams.txt
