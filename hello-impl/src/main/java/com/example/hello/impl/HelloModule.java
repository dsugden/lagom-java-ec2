/*
 * Copyright (C) 2016 Lightbend Inc. <http://www.lightbend.com>
 */
package com.example.hello.impl;

import com.google.inject.AbstractModule;
import com.lightbend.lagom.javadsl.api.ConfigurationServiceLocator;
import com.lightbend.lagom.javadsl.api.ServiceLocator;
import com.lightbend.lagom.javadsl.server.ServiceGuiceSupport;
import com.example.hello.api.HelloService;
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

    bindServices(serviceBinding(HelloService.class, HelloServiceImpl.class));

    if (environment.isProd()) {

      System.out.println("--------------------  configure ");

      bind(ServiceLocator.class).to(ServiceLocatorImpl.class);
    }

  }
}
