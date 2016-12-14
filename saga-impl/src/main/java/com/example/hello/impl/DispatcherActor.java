package com.example.hello.impl;

import akka.actor.AbstractLoggingActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.japi.pf.ReceiveBuilder;
import play.api.inject.Injector;
import play.db.Database;
import play.libs.ws.WSClient;

public class DispatcherActor extends AbstractLoggingActor {


    public static Props props(Injector injector){
        return Props.create(DispatcherActor.class, () -> new DispatcherActor(injector));
    }


    private ActorRef sagaActorRef = null;
    private ActorRef someOtherActorRef = null;

    public DispatcherActor(Injector injector) {

        receive(ReceiveBuilder.
                match(SomeOtherMessage.class, someOtherMessage ->{
                    if( someOtherActorRef == null ){
                        someOtherActorRef = context().system().actorOf(SomeOtherActor.props());
                    }
                    someOtherActorRef.forward(someOtherMessage, context());
                        }
                ).match(SagaRequest.class, sagaRequest ->{
                    if( sagaActorRef == null ){

                        sagaActorRef = context().system().actorOf(SagaActor.props(injector.instanceOf(WSClient.class), injector.instanceOf(Database.class)));
                    }
                    sagaActorRef.forward(sagaRequest, context());
                }
                ).matchAny(this::unhandled).build()
        );

    }
}
