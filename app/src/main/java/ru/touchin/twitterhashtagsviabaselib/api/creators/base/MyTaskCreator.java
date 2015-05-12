package ru.touchin.twitterhashtagsviabaselib.api.creators.base;

import android.content.Context;

import org.zuzuk.tasks.aggregationtask.AggregationPagingTask;

import ru.touchin.twitterhashtagsviabaselib.api.RequestFailListener;


public class MyTaskCreator extends RemoteAggregationPagingTaskCreator {
    private Context context;
    private String hashTag;

    public MyTaskCreator(RequestFailListener requestFailListener, Context context, String hashTag) {
        super(requestFailListener);
        this.context = context;
        this.hashTag = hashTag;
    }

    @Override
    public AggregationPagingTask createPagingTask(int offset, int limit) {
        return new MyTask(getRequestFailListener(), offset, limit, context, hashTag);
    }
}
