package ru.touchin.twitterhashtagsviabaselib.requests;

import android.content.Context;

import com.google.api.client.http.GenericUrl;
import com.squareup.okhttp.Request;

import org.zuzuk.tasks.remote.GetJsonRequest;

import java.io.IOException;

import ru.touchin.twitterhashtagsviabaselib.R;
import ru.touchin.twitterhashtagsviabaselib.model.Tweets;

public class BaseTweetsRequest extends GetJsonRequest<Tweets> {
    public static final String ADDRESS = "https://api.twitter.com/1.1/search/tweets.json";
    private Context context;
    private String hashTag;

    public BaseTweetsRequest(Context context, String hashTag) {
        super(Tweets.class);
        this.context = context;
        this.hashTag = hashTag;
    }

    @Override
    protected String getUrl() {
        return ADDRESS;
    }

    @Override
    protected void setupUrlParameters(GenericUrl url) {
        url.put("q", "%23" + hashTag);
        url.put("count", 10);
    }

    @Override
    protected Request.Builder createHttpRequest() throws IOException {
        String auth = "Bearer " + context.getString(R.string.access_token);
        return super.createHttpRequest().header("Authorization", auth);
    }
}
