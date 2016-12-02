package com.example.hello.impl;

import com.example.hello.api.ThrottleService;
import com.google.inject.AbstractModule;
import com.lightbend.lagom.javadsl.persistence.PersistentEntityRegistry;
import com.lightbend.lagom.javadsl.persistence.jdbc.JdbcPersistenceModule;
import com.lightbend.lagom.javadsl.server.ServiceGuiceSupport;
import com.example.hello.api.HelloService;

/**
 * The module that binds the HelloService so that it can be served.
 */
public class HelloModule extends AbstractModule implements ServiceGuiceSupport {
  @Override
  protected void configure() {

    bindServices(
            serviceBinding(HelloService.class, HelloServiceImpl.class),
            serviceBinding(ThrottleService.class, ThrottleServiceImpl.class));
  }
}
