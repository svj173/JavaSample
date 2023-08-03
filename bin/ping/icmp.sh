#!/bin/bash

# windows
#java -classpath shortpasta-icmp.jar;log4j-1.2.15.jar;. -Djava.library.path=. org.shortpasta.icmp.tool.IcmpPingTool google.com

CLASSPATH=../../lib/ping/shortpasta-icmp.jar:../../lib/log4j-1.2.14.jar

CMD="java -classpath $CLASSPATH org.shortpasta.icmp.tool.IcmpPingTool google.com"
echo $CMD
$CMD
