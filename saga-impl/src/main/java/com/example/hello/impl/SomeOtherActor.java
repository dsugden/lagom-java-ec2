package com.example.hello.impl;

import akka.actor.AbstractLoggingActor;
import akka.actor.Props;
import akka.japi.pf.ReceiveBuilder;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import javax.annotation.concurrent.Immutable;

/**
 * Created by dave on 2016-12-12.
 */
public class SomeOtherActor extends AbstractLoggingActor {

    public static Props props(){
        return Props.create(SomeOtherActor.class, SomeOtherActor::new);
    }

    public SomeOtherActor(){

        receive(ReceiveBuilder.
                match(SomeOtherMessage.class, someOtherMessage ->{
                    log().info("--------------- someOtherMessage " + someOtherMessage.id);
                    sender().tell(someOtherMessage.id + 1, self());
                        }
                  ).matchAny(this::unhandled).build()
        );


    }






}
