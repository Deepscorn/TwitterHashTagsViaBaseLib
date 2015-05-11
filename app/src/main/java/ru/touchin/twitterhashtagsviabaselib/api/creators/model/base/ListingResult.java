package ru.touchin.twitterhashtagsviabaselib.api.creators.model.base;

import com.google.api.client.util.Key;

import org.zuzuk.utils.serialization.json.ObjectFromJson;

import java.util.ArrayList;

public class ListingResult<T> extends ObjectFromJson {

    @Key("items")
    private ArrayList<T> items;

    public ArrayList<T> getItems() {
        return items;
    }

}