package com.example.hello.impl;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.lightbend.lagom.serialization.Jsonable;

import javax.annotation.concurrent.Immutable;

@SuppressWarnings("serial")
@Immutable
@JsonDeserialize
public class SomeOtherMessage implements Jsonable {

    public int id;

    @JsonCreator
    public SomeOtherMessage(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SomeOtherMessage that = (SomeOtherMessage) o;

        return id == that.id;

    }

    @Override
    public int hashCode() {
        return id;
    }
}