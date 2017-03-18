Project sumbmitted by-
Sumedha Khatter (A53094878)
Divya Aggarwal (A53093235)


-Steps to be followed-

Step1.Start the container using the command below

sudo ./start-container.sh

**This will start 1 master node and 4 client nodes**

start hadoop-master container...
start hadoop-slave1 container...
start hadoop-slave2 container...
start hadoop-slave3 container...
start hadoop-slave4 container...

root@hadoop-master:~# 

Step2- Now we are in the master container. And now start the hadoop using the command below

./start-hadoop.sh


Step3-Now run the bigram script using the command below

./run-bigram.sh

This will first print the input files
Then the sorted histogram of bigrams would be generated, top frequency bigrams on top
Then total number of bigram count would be generated
Then the bigrams required to meet 10% of total would be generated


