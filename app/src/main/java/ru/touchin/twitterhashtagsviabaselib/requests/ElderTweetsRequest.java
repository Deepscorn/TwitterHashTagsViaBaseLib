package ru.touchin.twitterhashtagsviabaselib.requests;

import android.content.Context;

import com.google.api.client.http.GenericUrl;

public class ElderTweetsRequest extends BaseTweetsRequest {

    private String max_id;

    public ElderTweetsRequest(Context context, String hashTag, int limit, String max_id) {
        super(context, hashTag, limit);
        this.max_id = max_id;
    }

    @Override
    protected void setupUrlParameters(GenericUrl url) {
        super.setupUrlParameters(url);
        if (max_id != null)
            url.put("max_id", max_id);
    }
}