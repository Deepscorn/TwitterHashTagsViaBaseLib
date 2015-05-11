package ru.touchin.twitterhashtagsviabaselib.model;

import com.google.api.client.util.Key;


import org.zuzuk.utils.serialization.json.ObjectFromJson;

import ru.touchin.twitterhashtagsviabaselib.utils.TwitterUtils;

public class Tweet extends ObjectFromJson {
    public final static String THE_END = "...";

    @Key("id_str")
    private String id;
    @Key("text")
    private String text;
    @Key("created_at")
    private String createdAt;
    private String date;
    @Key("user")
    private User user;

    private String link;

    public String getText() {
        return text;
    }

    public String getId() {
        return id;
    }

    public String getDate() {
        if (date == null) {
            date = TwitterUtils.getTwitterDateInLocalTZ(createdAt);
        }
        return date;
    }

    public String getLink() {
        if (link == null) {
            link = "<a href='https://twitter.com/" + user.getId() + "/status/" + getId() + "'>link</a>";
        }
        return link;
    }

    public User getUser() {
        return user;
    }

    @Override
    public String toString() {
        String result;
        if (getText().length() > 37) {
            result = getText().substring(0, 37) + THE_END;
        } else {
            result = (getText() + THE_END).replaceAll("\n|\r\n", " ");
        }
        return result.replaceAll("\n|\r\n", " ");
    }
}
