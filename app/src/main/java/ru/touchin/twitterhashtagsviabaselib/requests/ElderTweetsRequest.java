package ru.touchin.twitterhashtagsviabaselib.requests;

import android.content.Context;

import com.google.api.client.http.GenericUrl;

/**
 * Created by Alex on 05.05.2015.
 */
public class ElderTweetsRequest extends BaseTweetsRequest {

    private String max_id;

    public ElderTweetsRequest(Context context, String hashTag, String max_id) {
        super(context, hashTag);
        this.max_id = max_id;
    }

    @Override
    protected void setupUrlParameters(GenericUrl url) {
        super.setupUrlParameters(url);
        url.put("max_id", max_id);
    }
}