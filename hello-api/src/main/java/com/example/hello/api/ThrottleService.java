package com.example.hello.api;

import akka.Done;
import akka.NotUsed;
import com.lightbend.lagom.javadsl.api.Descriptor;
import com.lightbend.lagom.javadsl.api.Service;
import com.lightbend.lagom.javadsl.api.ServiceCall;
import static com.lightbend.lagom.javadsl.api.Service.named;
import static com.lightbend.lagom.javadsl.api.Service.pathCall;

/**
 * Created by dave on 2016-12-02.
 */
public interface ThrottleService  extends Service {

    /**
     * Example: curl http://localhost:9000/api/hello/Alice
     */
    ServiceCall<NotUsed, String> throttle(String id);



    @Override
    default Descriptor descriptor() {
        // @formatter:off
        return named("throttle").withCalls(
                pathCall("/api/throttle/:id",  this::throttle)
        ).withAutoAcl(true);
        // @formatter:on
    }

}
