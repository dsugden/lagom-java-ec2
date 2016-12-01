## lagom-java-ec2 with jdbc or cassandra, custom deploy (no conductR)


### JDBC

    I Used PGAdmin to do the following

    * Install postgres
    * Create a db called "playdb"
    * Create a login/role called "playuser" with passwword "playuser"
    * Grant this role permissions to playdb
    
    
    In hello-impl/application.conf the following is used for db connection:
    
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
    
    
    The following in hello-impl/application.conf set this up:
    
    akka{
      remote {
        netty.tcp {
          hostname = "127.0.0.1"
        }
      }
    }
    
    and in hello-impl/start.sh
    
    CONFIG="-Dhttps.port=9443 -Dplay.crypto.secret=$PLAY_SECRET  -Dakka.cluster.seed-nodes.0=akka.tcp://application@127.0.0.1:2552 -Dakka.remote.netty.tcp.port=2552"
    
    java -cp "target/hello-v1/hello/lib/*" $JAVA_OPTS $CONFIG play.core.server.ProdServerStart
    


### Maven package and run

   start first node:
   
   hello-impl/start.sh  (zip, unzip, and run).
   
   start second node:
   
   hello-impl/start2.sh
   



    
    
    
    