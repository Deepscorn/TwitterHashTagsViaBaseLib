package ru.touchin.twitterhashtagsviabaselib.model;

import com.google.api.client.util.Key;

import org.zuzuk.utils.serialization.json.ObjectFromJson;


public class User extends ObjectFromJson {
    @Key("id_str")
    private String id;
    @Key("name")
    private String name;
    @Key("profile_image_url")
    private String normalSizeImageURL;
    private String fullSizeImageURL;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getNormalSizeImageURL() {
        return normalSizeImageURL;
    }

    public String getFullSizeImageURL() {
        if (fullSizeImageURL == null) {
            fullSizeImageURL = normalSizeImageURL.replace("_normal", "");
        }
        return fullSizeImageURL;
    }
}
