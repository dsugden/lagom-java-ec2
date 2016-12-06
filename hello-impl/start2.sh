#!/bin/sh

rm hello-impl/RUNNING_PID

JVM_OPTS="-Xmx128m -Xms128m"

# This should be changed if you use Play sessions
PLAY_SECRET=none

CONFIG="-Dhttps.port=9444 -Dplay.crypto.secret=$PLAY_SECRET  -Dakka.cluster.seed-nodes.0=akka.tcp://application@127.0.0.1:2552 -Dakka.remote.netty.tcp.port=2553"

cd hello-impl

java -cp "target/hello-v1/hello/lib/*" $JAVA_OPTS $CONFIG play.core.server.ProdServerStart



