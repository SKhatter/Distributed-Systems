#!/bin/bash
#Writer: Sumedha Khatter
export PATH=$PATH:$HADOOP_PREFIX/bin
export HADOOP_CLASSPATH=$JAVA_HOME/lib/tools.jar

cd $HADOOP_PREFIX
#create and input directory in  the container
mkdir /root/input

cp -R /root/input.txt /root/input/input.txt

#create an input directory in hadoop
hadoop fs -mkdir -p /input

#put the contents from the  files in the container to hadoop
hdfs dfs -put /root/input/* /input

#print the input files
echo -e "\n Input file content"
hdfs dfs -cat /input/input.txt

#compile the java files
hadoop com.sun.tools.javac.Main BigramPair.java Bigram.java

#create jar
jar cf Bigram.jar Bigram*.class

#run the jar with hadoop
hadoop jar Bigram.jar Bigram /input /output

#this can generate the output folder contents
#hdfs dfs -cat /output/part-r-00000
