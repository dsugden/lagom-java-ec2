package com.example.hello.impl;

import com.lightbend.lagom.javadsl.api.Descriptor;
import com.lightbend.lagom.javadsl.api.ServiceLocator;

import java.net.URI;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.function.Function;

public class ServiceLocatorImpl implements ServiceLocator{





    @Override
    public CompletionStage<Optional<URI>> locate(String s, Descriptor.Call<?, ?> call) {

        System.out.println("locate " +s + " " + call.callId());

        URI uri = null;

        try{
            uri = new URI("http://google.com");
        }catch (Exception e){
            e.printStackTrace();
        }

        return CompletableFuture.completedFuture(Optional.of(uri));
    }

    @Override
    public <T> CompletionStage<Optional<T>> doWithService(String s, Descriptor.Call<?, ?> call, Function<URI, CompletionStage<T>> function) {
        return null;
    }
}
