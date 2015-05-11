package ru.touchin.twitterhashtagsviabaselib.model;

import com.google.api.client.util.Key;

import java.util.List;

public class Tweets {
    @Key("statuses")
    private List<Tweet> tweets;

    public List<Tweet> getTweets() {
        return tweets;
    }
}
