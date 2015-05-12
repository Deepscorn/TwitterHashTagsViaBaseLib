package ru.touchin.twitterhashtagsviabaselib.api.creators.base;

import android.content.Context;

import org.zuzuk.tasks.aggregationtask.AggregationTaskStageState;
import org.zuzuk.tasks.aggregationtask.RequestAndTaskExecutor;

import java.util.ArrayList;

import ru.touchin.twitterhashtagsviabaselib.api.RequestFailListener;
import ru.touchin.twitterhashtagsviabaselib.api.RequestSuccessListener;
import ru.touchin.twitterhashtagsviabaselib.model.Tweet;
import ru.touchin.twitterhashtagsviabaselib.model.Tweets;
import ru.touchin.twitterhashtagsviabaselib.requests.ElderTweetsRequest;


public class TweetTask extends RemoteAggregationPagingTask {
    private Context context;
    private String hashTag;
    private String lastTweetId;

    public TweetTask(RequestFailListener requestFailListener, int offset, int limit, Context context, String hashTag) {
        super(requestFailListener, offset, limit);
        this.context = context;
        this.hashTag = hashTag;
    }

    @Override
    public void load(RequestAndTaskExecutor executor, AggregationTaskStageState currentTaskStageState) {
        executor.executeRequest(new ElderTweetsRequest(context, hashTag, getLimit(), lastTweetId) {
        }, new RequestSuccessListener<Tweets>() {
            @Override
            public void onRequestSuccess(Tweets tweets) {
                ArrayList<Tweet> lastPageTweets = tweets.getTweets();
                setPageItems(lastPageTweets);
                lastTweetId = lastPageTweets.get(lastPageTweets.size() - 1).getId();
            }
        });
    }
}
