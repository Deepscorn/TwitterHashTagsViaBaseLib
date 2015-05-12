package ru.touchin.twitterhashtagsviabaselib.api.creators.base;

import android.content.Context;

import org.zuzuk.tasks.aggregationtask.AggregationTaskStageState;
import org.zuzuk.tasks.aggregationtask.RequestAndTaskExecutor;

import ru.touchin.twitterhashtagsviabaselib.api.RequestFailListener;
import ru.touchin.twitterhashtagsviabaselib.api.RequestSuccessListener;
import ru.touchin.twitterhashtagsviabaselib.model.Tweets;
import ru.touchin.twitterhashtagsviabaselib.requests.BaseTweetsRequest;


public class MyTask extends RemoteAggregationPagingTask {
    private Context context;
    private String hashTag;

    public MyTask(RequestFailListener requestFailListener, int offset, int limit, Context context, String hashTag) {
        super(requestFailListener, offset, limit);
        this.context = context;
        this.hashTag = hashTag;
    }

    @Override
    public void load(RequestAndTaskExecutor executor, AggregationTaskStageState currentTaskStageState) {
        executor.executeRequest(new BaseTweetsRequest(context, hashTag) {
        }, new RequestSuccessListener<Tweets>() {
            @Override
            public void onRequestSuccess(Tweets tweets) {
                setPageItems(tweets.getTweets());
            }
        });
    }
}
