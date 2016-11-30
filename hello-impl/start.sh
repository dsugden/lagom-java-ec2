#!/bin/sh

JVM_OPTS="-Xmx128m -Xms128m"

# This should be changed if you use Play sessions
PLAY_SECRET=none

#CONFIG="-Dhttp.address=localhost -Dhttps.port=9003 -Dhttp.port=disabled  -Dplay.crypto.secret=$PLAY_SECRET"

CONFIG="-Dhttps.port=9443 -Dplay.crypto.secret=$PLAY_SECRET"

#DIR=$(dirname $0)


mvn clean package

unzip target/hello-impl-1.0-SNAPSHOT-hello-assembly.zip -d target

java -cp "target/hello-v1/hello/lib/*" $JAVA_OPTS $CONFIG play.core.server.ProdServerStart