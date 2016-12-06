/*
 * Copyright (C) 2016 Lightbend Inc. <http://www.lightbend.com>
 */
package com.example.hello.api;

import static com.lightbend.lagom.javadsl.api.Service.*;

import akka.NotUsed;
import com.lightbend.lagom.javadsl.api.Descriptor;
import com.lightbend.lagom.javadsl.api.Service;
import com.lightbend.lagom.javadsl.api.ServiceCall;

/**
 * The saga service interface.
 * <p>
 * This describes everything that Lagom needs to know about how to serve and
 * consume the Saga.
 */
public interface SagaService extends Service {

    /**
     * Example: curl http://localhost:9000/api/saga/Alice
     */
    ServiceCall<NotUsed, String> saga(String id);



    @Override
    default Descriptor descriptor() {
        // @formatter:off
        return named("saga").withCalls(
                pathCall("/api/saga/:id",  this::saga)
        ).withAutoAcl(true);
        // @formatter:on
    }

}
