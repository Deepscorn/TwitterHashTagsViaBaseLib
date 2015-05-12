package ru.touchin.twitterhashtagsviabaselib.api.creators.base;

import android.content.Context;

import org.zuzuk.tasks.aggregationtask.AggregationPagingTask;

import ru.touchin.twitterhashtagsviabaselib.api.RequestFailListener;


public class TweetTaskCreator extends RemoteAggregationPagingTaskCreator {
    private Context context;
    private String hashTag;

    public TweetTaskCreator(RequestFailListener requestFailListener, Context context, String hashTag) {
        super(requestFailListener);
        this.context = context;
        this.hashTag = hashTag;
    }

    @Override
    public AggregationPagingTask createPagingTask(int offset, int limit) {
        return new TweetTask(getRequestFailListener(), offset, limit, context, hashTag);
    }
}
