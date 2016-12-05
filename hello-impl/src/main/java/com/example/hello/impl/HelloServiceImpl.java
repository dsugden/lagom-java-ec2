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
import com.example.hello.api.HelloService;
import com.lightbend.lagom.javadsl.api.ServiceCall;
import play.api.inject.Injector;
import play.libs.ws.WSClient;
import scala.compat.java8.FutureConverters;
import scala.concurrent.Future;

import javax.inject.Inject;
import java.util.concurrent.CompletionStage;

import static akka.pattern.Patterns.ask;

/**
 * Implementation of the HelloService.
 */
public class HelloServiceImpl implements HelloService {

    private final ActorSystem system;
    private final ActorRef idmRegion;

    @Inject
    public HelloServiceImpl(ActorSystem system, Injector injector) {
        this.system = system;


        ShardRegion.MessageExtractor messageExtractor = new ShardRegion.MessageExtractor() {

            @Override
            public String entityId(Object message) {
                if (message instanceof IDMActor.IDMRequest)
                    return "IDMRequest";
                else
                    return null;
            }

            @Override
            public Object entityMessage(Object message) {
                if (message instanceof IDMActor.IDMRequest)
                    return (IDMActor.IDMRequest)message;
                else
                    return message;
            }

            @Override
            public String shardId(Object message) {
                return "idm";
            }

        };


        ClusterShardingSettings settings = ClusterShardingSettings.create(system);
        this.idmRegion = ClusterSharding.get(system).start("idm", Props.create(IDMActor.class,
                () -> new IDMActor(injector.instanceOf(WSClient.class))), settings, messageExtractor);


    }





    @Override
    public ServiceCall<NotUsed, String> throttle(String id) {

        return request ->  {

            Future<Object> f = ask(idmRegion,new IDMActor.IDMRequest(id),1000);
            CompletionStage<Object> comp =  FutureConverters.toJava(f);
            return comp.thenApply(x -> x.toString());
        };
    }

}
