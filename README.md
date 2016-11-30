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
    
    
    play{
      akka.actor-system = "MyService"
    }
    
    akka{
      cluster.seed-nodes = ["akka.tcp://MyService@127.0.0.1:2552"]
      remote {
        log-remote-lifecycle-events = off
        netty.tcp {
          hostname = "127.0.0.1"
          port = 2552
        }
      }
    }


### Maven package and run

   hello-impl/start.sh  will package into a zip, unzip, and run.



    
    
    
    