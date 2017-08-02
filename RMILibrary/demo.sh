#!/bin/bash

#Demo Script:

#!/bin/bash
make clean
docker stop $(docker ps -a -q) && docker rm $(docker ps -a -q)
docker rmi my-java-app
docker network rm pingnetwork


make
docker network create -d bridge pingnetwork
docker build -f Dockerfile -t my-java-app .

docker run -d  --net=pingnetwork --name pingserver my-java-app java -cp . PingPong/server/Main 7000
docker run -d --net=pingnetwork --name pingclient my-java-app java -cp . PingPong/client/PingPongClient pingserver 7000

sleep 5
docker logs pingclient
