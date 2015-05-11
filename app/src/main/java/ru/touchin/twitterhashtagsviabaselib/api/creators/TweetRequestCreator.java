package ru.touchin.twitterhashtagsviabaselib.api.creators;

import android.content.Context;

import org.zuzuk.providers.base.PagingTaskCreator;
import org.zuzuk.tasks.aggregationtask.AggregationPagingTask;

import ru.touchin.twitterhashtagsviabaselib.model.Tweet;
import ru.touchin.twitterhashtagsviabaselib.requests.BaseTweetsRequest;
import ru.touchin.twitterhashtagsviabaselib.requests.TweetPagingTask;

public class TweetRequestCreator implements PagingTaskCreator<Tweet> {

    private Context context;
    private String hashTag;

    public TweetRequestCreator(Context context, String hashTag) {
        this.context = context;
        this.hashTag = hashTag;
    }

    @Override
    public AggregationPagingTask createPagingTask(int offset, int limit) {
        return new TweetPagingTask(new BaseTweetsRequest(context, hashTag), new TweetPagingTask.ChainedRequestListenerImpl(), offset, limit);
    }
}
