## Experimenting with lagom-java-ec2 with jdbc or cassandra, custom deploy (no conductR)


Using Maven.

Currently not using any PersistentEntities, but using akka cluster directly. Using cluster sharding backed
by Akka Distributed Data.

Using straight up old school jdbc to update a postgres db.

Simulating a non conductR build



### JDBC

    I Used PGAdmin to do the following

    * Install postgres
    * Create a db called "playdb"
    * Create a login/role called "playuser" with passwword "playuser"
    * Grant this role permissions to playdb
    
    
    In saga-impl/application.conf the following is used for db connection:
    
    db.default {
      driver = "org.postgresql.Driver"
    
      #jdbc:postgresql://localhost/playdb is fine... mine runs on 5433, so I put it there, usually its 5432
      url = "jdbc:postgresql://localhost:5433/playdb"
    
      username = playuser
      password = playuser
    }


### Cluster


    The recommendation is to have an akka cluster per service (NOT leveraging cluster for service discovery, ie. multiple
    services in one cluster).
    
    
    The following in saga-impl/application.conf set this up:
    
    akka{
      remote {
        netty.tcp {
          hostname = "127.0.0.1"
        }
      }
    }
    
    and in saga-impl/start.sh
    
    CONFIG="-Dhttps.port=9443 -Dplay.crypto.secret=$PLAY_SECRET  -Dakka.cluster.seed-nodes.0=akka.tcp://application@127.0.0.1:2552 -Dakka.remote.netty.tcp.port=2552"
    
    java -cp "target/saga-v1/saga/lib/*" $JAVA_OPTS $CONFIG play.core.server.ProdServerStart
    


### Maven package and run

   start first node from root dir:
   
   sh saga-impl/start.sh  (zip, unzip, and run).
   
   start second node:
   
   sh saga-impl/start2.sh
   



### debugging in intellij
    
    
    start server in debug mode on 5555:
    
    env MAVEN_OPTS="-Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=y,address=5555" mvn lagom:runAll
    
    create a maven remote debug configuration in intellij, attaching to 5555
    
    
    
    
    
    
    