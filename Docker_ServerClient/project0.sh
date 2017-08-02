#!/bin/bash

# cleanup from earlier run
docker stop Catserver > /dev/null
docker stop Catclient >/dev/null
docker rm `docker ps -aq` > /dev/null
docker rmi client:v1 > /dev/null
docker rmi server:v1 > /dev/null

# build image for server 
cd server
docker build -t server:v1 .

#build image for client
cd ../client
docker build -t client:v1 .

# build image for data volume 
cd ../data
docker build -t data:v1 .

# creating the data volume container with datavol image above
docker create -v /data --name data data:v1 /bin/true > /dev/null

# create container for server and run the server program as daemon
docker run -d -P --volumes-from data --name Catserver server:v1 java Catserver "/data/string.txt" 25000 >/dev/null

# create container for client and run the client program
docker run -it --volumes-from data --link Catserver --name Catclient client:v1 java Catclient "/data/string.txt" 25000 >/dev/null

# verify everythin was OK with docker logs for catclient
docker logs Catclient |
{
   while read -r line ; do
       if [[ $line == *"MISSING"* ]]
       then
         echo "!!! FAILURE !!!"
	 break
       fi
       echo $line
   done
echo "!!! SUCCESS !!!"
}
