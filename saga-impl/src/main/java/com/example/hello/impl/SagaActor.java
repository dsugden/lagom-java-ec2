package com.example.hello.impl;

import akka.actor.AbstractFSM;
import akka.actor.Props;
import play.db.DB;
import play.db.Database;
import play.libs.ws.WSClient;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Properties;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;


public class SagaActor extends AbstractFSM<SagaActor.SagaState, Queue<SagaRequest>> {



    public static Props props(WSClient client,Database db){
        return Props.create(SagaActor.class, () -> new SagaActor(client, db));
    }


    private SagaState state = new SagaState();
    private Database db;

    public SagaActor(WSClient client,Database db) {

        this.db = db;

        startWith(state, new ConcurrentLinkedQueue<SagaRequest>());


        when(state,
                matchEvent(SagaRequest.class, ConcurrentLinkedQueue.class,
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

            Connection conn = db.getConnection();
            Statement stmt = conn.createStatement();
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



    public static final class SagaState {}



}
