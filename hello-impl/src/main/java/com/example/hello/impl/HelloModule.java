package com.example.hello.impl;

import com.example.hello.api.HelloService;
import com.google.inject.AbstractModule;
import com.lightbend.lagom.javadsl.server.ServiceGuiceSupport;
import com.lightbend.lagom.javadsl.api.ServiceLocator;

/**
 * The module that binds the HelloService so that it can be served.
 */
public class HelloModule extends AbstractModule implements ServiceGuiceSupport {
  @Override
  protected void configure() {
    bind(ServiceLocator.class).to(ServiceLocatorImpl.class);
    bindServices(serviceBinding(HelloService.class, HelloServiceImpl.class));
  }
}
