package com.example.hello.impl;

import com.example.hello.api.HelloService;
import com.google.inject.AbstractModule;
import com.lightbend.lagom.javadsl.server.ServiceGuiceSupport;
import com.lightbend.lagom.javadsl.api.ServiceLocator;
import play.Configuration;
import play.Environment;

/**
 * The module that binds the HelloService so that it can be served.
 */
public class HelloModule extends AbstractModule implements ServiceGuiceSupport {


  private final Environment environment;
  private final Configuration configuration;

  public HelloModule(Environment environment, Configuration configuration) {
    this.environment = environment;
    this.configuration = configuration;
  }

  @Override
  protected void configure() {
    if (environment.isProd()) {
      bind(ServiceLocator.class).to(ServiceLocatorImpl.class);
    }


    bindServices(serviceBinding(HelloService.class, HelloServiceImpl.class));
  }
}
