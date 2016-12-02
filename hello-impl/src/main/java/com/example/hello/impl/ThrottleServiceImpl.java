package com.example.hello.impl;

import akka.NotUsed;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.cluster.sharding.ClusterSharding;
import akka.cluster.sharding.ClusterShardingSettings;
import akka.cluster.sharding.ShardRegion;
import akka.japi.Option;
import com.example.hello.api.ThrottleService;
import javax.inject.Inject;
import com.lightbend.lagom.javadsl.api.ServiceCall;
import scala.compat.java8.FutureConverters;
import scala.concurrent.Future;

import java.util.concurrent.CompletionStage;

import static akka.pattern.Patterns.ask;

/**
 * Created by dave on 2016-12-02.
 */
public class ThrottleServiceImpl implements ThrottleService {


    private final ActorSystem system;
    private final ActorRef idmRegion;

    @Inject
    public ThrottleServiceImpl(ActorSystem system) {
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
        this.idmRegion = ClusterSharding.get(system).start("idm", Props.create(IDMActor.class), settings, messageExtractor);


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
