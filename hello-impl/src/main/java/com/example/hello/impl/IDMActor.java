package com.example.hello.impl;

import akka.actor.AbstractActor;
import akka.actor.AbstractFSM;
import akka.actor.AbstractLoggingActor;

import java.util.PriorityQueue;
import java.util.Queue;


public class IDMActor extends AbstractFSM<IDMActor.IDMState, Queue<IDMActor.IDMRequest>> {




    public IDMActor() {

        startWith(new IDMState(), new PriorityQueue<IDMRequest>());


        when(new IDMState(),
                matchEventEquals(IDMRequest.class, PriorityQueue.class,
                        (request, queue) -> {
                            queue.add(request);
                            return stay().using(queue);
                        }).build());
    }




    public static final class IDMState{}


    public static final class IDMRequest{

        public String id;

        public IDMRequest(String id) {
            this.id = id;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            IDMRequest that = (IDMRequest) o;

            return id != null ? id.equals(that.id) : that.id == null;

        }

        @Override
        public int hashCode() {
            return id != null ? id.hashCode() : 0;
        }
    }

}
