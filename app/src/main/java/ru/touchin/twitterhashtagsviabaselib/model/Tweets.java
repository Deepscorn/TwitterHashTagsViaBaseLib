package ru.touchin.twitterhashtagsviabaselib.model;

import com.google.api.client.util.Key;

import java.util.ArrayList;
import java.util.List;

public class Tweets {
    @Key("statuses")
    private ArrayList<Tweet> tweets;

    public ArrayList<Tweet> getTweets() {
        return tweets;
    }
}
