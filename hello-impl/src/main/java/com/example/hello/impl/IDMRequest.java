package com.example.hello.impl;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.lightbend.lagom.serialization.Jsonable;

import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;


@SuppressWarnings("serial")
@Immutable
@JsonDeserialize
public class IDMRequest implements Jsonable {

    public String id;


    @JsonCreator
    public IDMRequest(String id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        IDMRequest that = (IDMRequest) o;

        return id != null ? id.equals(that.id) : that.id == null;

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
