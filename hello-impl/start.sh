#!/bin/sh

JVM_OPTS="-Xmx128m -Xms128m"

# This should be changed if you use Play sessions
PLAY_SECRET=none

CONFIG="-Dhttps.port=9443 -Dplay.crypto.secret=$PLAY_SECRET  -Dakka.cluster.seed-nodes.0=akka.tcp://application@127.0.0.1:2552 -Dakka.remote.netty.tcp.port=2552"

mvn clean package

unzip target/hello-impl-1.0-SNAPSHOT-hello-assembly.zip -d target

java -cp "target/hello-v1/hello/lib/*" $JAVA_OPTS $CONFIG play.core.server.ProdServerStart