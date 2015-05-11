package ru.touchin.twitterhashtagsviabaselib.requests;

import com.octo.android.robospice.persistence.exception.SpiceException;

import org.zuzuk.tasks.aggregationtask.AggregationPagingTask;
import org.zuzuk.tasks.aggregationtask.AggregationTaskStage;
import org.zuzuk.tasks.aggregationtask.AggregationTaskStageState;
import org.zuzuk.tasks.aggregationtask.RequestAndTaskExecutor;
import org.zuzuk.tasks.realloading.BaseRequestWrapper;
import org.zuzuk.tasks.realloading.ChainedRequestListener;

import java.util.ArrayList;
import java.util.List;

import ru.touchin.twitterhashtagsviabaselib.model.Tweet;
import ru.touchin.twitterhashtagsviabaselib.model.Tweets;

public class TweetPagingTask extends BaseRequestWrapper implements AggregationPagingTask<Tweet> {

    private List<Tweet> pageItems;
    private final int offset;
    private final int limit;

    public TweetPagingTask(BaseTweetsRequest baseTweetsRequest, ChainedRequestListenerImpl chainedRequestListener, int offset, int limit) {
        super(baseTweetsRequest, chainedRequestListener, null);
        pageItems = chainedRequestListener.getTweets();
        this.offset = offset;
        this.limit = limit;
    }

    @Override
    public boolean isLoadingNeeded(AggregationTaskStageState currentTaskStageState) {
        return true;
    }

    @Override
    public boolean isLoaded(AggregationTaskStageState currentTaskStageState) {
        return currentTaskStageState.getTaskStage() != AggregationTaskStage.PRE_LOADING && !currentTaskStageState.hasExceptions();
    }

    @Override
    public void onLoadingStarted(AggregationTaskStageState currentTaskStageState) {
    }

    @Override
    public void onLoaded(AggregationTaskStageState currentTaskStageState) {
    }

    @Override
    public void onFailed(AggregationTaskStageState currentTaskStageState) {

    }

    public int getOffset() {
        return offset;
    }

    public int getLimit() {
        return limit;
    }

    @Override
    public List<Tweet> getPageItems() {
        return pageItems;
    }

    public void setPageItems(ArrayList<Tweet> pageItems) {
        this.pageItems = pageItems;
    }

    public static class ChainedRequestListenerImpl implements ChainedRequestListener<Tweets> {
        private List<Tweet> tweets;

        @Override
        public void onRequestSuccess(Tweets tweets, RequestAndTaskExecutor executor) {
            this.tweets = tweets.getTweets();
        }

        @Override
        public void onRequestFailure(SpiceException spiceException, RequestAndTaskExecutor executor) {

        }

        public List<Tweet> getTweets() {
            return tweets;
        }
    }
}
