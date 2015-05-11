package ru.touchin.twitterhashtagsviabaselib.api.creators.base;



import org.zuzuk.tasks.aggregationtask.AggregationPagingTask;
import org.zuzuk.tasks.aggregationtask.RequestAndTaskExecutor;
import org.zuzuk.tasks.remote.base.RemoteRequest;

import ru.touchin.twitterhashtagsviabaselib.api.RequestFailListener;
import ru.touchin.twitterhashtagsviabaselib.api.creators.model.base.PartialListingResult;

public abstract class ApiListingAggregationPagingTaskCreator<TItem, TResponse extends LolGuildsResponse<PartialListingResult<TItem>>>
        extends RemoteAggregationPagingTaskCreator<TItem> {

    protected ApiListingAggregationPagingTaskCreator(RequestFailListener requestFailListener) {
        super(requestFailListener);
    }

    @Override
    public AggregationPagingTask<TItem> createPagingTask(int offset, int limit) {
        return new ApiListingAggregationPagingTask<>(this, offset, limit);
    }

    public void processApiResult(RequestAndTaskExecutor executor, ApiListingAggregationPagingTask<TItem, TResponse> pagingTask) {
    }

    public abstract RemoteRequest<TResponse> createRequest(ApiListingAggregationPagingTask<TItem, TResponse> pagingTask);

}