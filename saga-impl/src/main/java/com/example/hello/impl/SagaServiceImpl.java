/*
 * Copyright (C) 2016 Lightbend Inc. <http://www.lightbend.com>
 */
package com.example.hello.impl;

import akka.NotUsed;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.cluster.sharding.ClusterSharding;
import akka.cluster.sharding.ClusterShardingSettings;
import akka.cluster.sharding.ShardRegion;
import com.example.hello.api.SagaService;
import com.lightbend.lagom.javadsl.api.ServiceCall;
import play.api.inject.Injector;
import play.db.Database;
import play.libs.ws.WSClient;
import scala.compat.java8.FutureConverters;
import scala.concurrent.Future;

import javax.inject.Inject;
import java.util.concurrent.CompletionStage;

import static akka.pattern.PatternsCS.ask;
import static java.lang.System.out;

/**
 * Implementation of the SagaService.
 */
public class SagaServiceImpl implements SagaService {

    private final ActorSystem system;
    private final ActorRef sagaRegion;

    @Inject
    public SagaServiceImpl(ActorSystem system, Injector injector) {
        this.system = system;


        ShardRegion.MessageExtractor messageExtractor = new ShardRegion.MessageExtractor() {

            @Override
            public String entityId(Object message) {
                if (message instanceof SagaRequest){
                    String entityId = ((SagaRequest)message).id;
                    out.println("-----  entityId " + entityId);
                    return entityId;
                }
                else if (message instanceof SomeOtherMessage) {
                    return "SomeOtherActor";
                }
                else
                    return null;
            }

            @Override
            public Object entityMessage(Object message) {
                if (message instanceof SagaRequest)
                    return (SagaRequest)message;
                else
                    return message;
            }

            @Override
            public String shardId(Object message) {
                String id = null;
                if (message instanceof SagaRequest){
                    String entityId = ((SagaRequest)message).id;
                    out.println("-----  entityId " + entityId);
                    id =  "saga";
                }
                else if (message instanceof SomeOtherMessage) {
                    id =  "SomeOtherActor";
                }
                else
                    id =  null;

                out.println("id = " + id);

                return id;
            }


        };


        ClusterShardingSettings settings = ClusterShardingSettings.create(system);
        this.sagaRegion = ClusterSharding.get(system).start("saga", DispatcherActor.props(injector), settings, messageExtractor);


    }





    @Override
    public ServiceCall<NotUsed, String> saga(String id) {

        return request ->  {

            CompletionStage<Object> f = ask(sagaRegion,new SagaRequest(id),1000);

            CompletionStage<Object> g = ask(sagaRegion,new SomeOtherMessage(2),1000);


            return f.thenCombine(g, (a,b) -> a.toString() + b.toString() );
        };
    }

}
