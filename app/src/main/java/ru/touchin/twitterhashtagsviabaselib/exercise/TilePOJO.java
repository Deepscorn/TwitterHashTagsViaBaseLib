package ru.touchin.twitterhashtagsviabaselib.exercise;

import com.google.api.client.util.Key;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class TilePOJO {
    @Key
    public int left;
    @Key
    public int top;
    @Key
    public int width;
    @Key
    public int height;
    @Key
    public String appfrontId;

    public TilePOJO() {} // for deserializing from json

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
