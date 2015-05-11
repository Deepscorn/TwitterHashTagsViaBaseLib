package ru.touchin.twitterhashtagsviabaselib.requests;

import android.content.Context;

import com.google.api.client.http.GenericUrl;

public class NewTweetsRequest extends BaseTweetsRequest {

    private String minId;

    public NewTweetsRequest(Context context, String hashTag, String minId) {
        super(context, hashTag);
        this.minId = minId;
    }

    @Override
    protected void setupUrlParameters(GenericUrl url) {
        super.setupUrlParameters(url);
        url.put("since_id", minId);
    }
}
