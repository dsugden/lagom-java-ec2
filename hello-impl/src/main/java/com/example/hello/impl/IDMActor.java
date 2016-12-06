package com.example.hello.impl;

import akka.actor.AbstractFSM;
import play.libs.ws.WSClient;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Properties;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;


public class IDMActor extends AbstractFSM<IDMActor.IDMState, Queue<IDMRequest>> {



    private IDMState state = new IDMState();


    public IDMActor(WSClient client) {

        startWith(state, new ConcurrentLinkedQueue<IDMRequest>());


        when(state,
                matchEvent(IDMRequest.class, ConcurrentLinkedQueue.class,
                        (request, queue) -> {

                            System.out.println("-----------------   request " + request);

                            queue.add(request);
                            sender().tell("good", self());
                            System.out.println("-----------------   XXX");
                            write();
                            return stay().using(queue);
                        }).build());
    }



    private void write(){
        try{

        Class.forName("org.postgresql.Driver");
        String URL = "jdbc:postgresql://localhost:5433/playdb";
        Properties info = new Properties( );
        info.put( "user", "playuser" );
        info.put( "password", "playuser" );

            Connection conn = DriverManager.getConnection(URL, info);
            Statement stmt = null;

            stmt = conn.createStatement();
            String sql = "CREATE TABLE If NOT EXISTS TESTXX " +
                    "(id VARCHAR(128) not NULL, " +
                    " first VARCHAR(255), " +
                    " PRIMARY KEY ( id ))";

            stmt.executeUpdate(sql);
            String SQL = "INSERT INTO TESTXX values('" + java.util.UUID.randomUUID() + "', 'asd')";
            stmt = conn.createStatement();
            stmt.execute(SQL);

        }catch (Exception e){
                e.printStackTrace();
        }

    }



    public static final class IDMState{}



}
