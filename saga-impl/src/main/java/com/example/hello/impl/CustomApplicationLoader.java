package com.example.hello.impl;

import play.Application;
import play.ApplicationLoader;
import play.Configuration;
import play.inject.guice.GuiceApplicationBuilder;
import play.inject.guice.GuiceApplicationLoader;
import play.libs.Scala;

public class CustomApplicationLoader extends GuiceApplicationLoader {

    @Override
    public GuiceApplicationBuilder builder(ApplicationLoader.Context context) {

        System.out.println("GuiceApplicationBuilder   " + context.initialConfiguration().getConfig("akka").toString());

        Configuration extra = new Configuration("a = 1");
        return initialBuilder
                .in(context.environment())
                .loadConfig(extra.withFallback(context.initialConfiguration()))
                .overrides(overrides(context));
    }

}