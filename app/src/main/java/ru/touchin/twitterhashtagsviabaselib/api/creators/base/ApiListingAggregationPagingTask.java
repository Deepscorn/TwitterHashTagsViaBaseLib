package ru.touchin.twitterhashtagsviabaselib.api.creators.base;

import org.zuzuk.tasks.aggregationtask.AggregationTaskStageState;
import org.zuzuk.tasks.aggregationtask.RequestAndTaskExecutor;

import ru.touchin.twitterhashtagsviabaselib.api.RequestSuccessListener;
import ru.touchin.twitterhashtagsviabaselib.api.creators.model.base.PartialListingResult;

public class ApiListingAggregationPagingTask<TItem, TResponse extends LolGuildsResponse<PartialListingResult<TItem>>>
        extends RemoteAggregationPagingTask<TItem> {

    private ApiListingAggregationPagingTaskCreator<TItem, TResponse> pagingTaskCreator;

    protected ApiListingAggregationPagingTask(ApiListingAggregationPagingTaskCreator<TItem, TResponse> pagingTaskCreator,
                                              int offset, int limit) {
        super(pagingTaskCreator.getRequestFailListener(), offset, limit);
        this.pagingTaskCreator = pagingTaskCreator;
    }

    @Override
    public void load(final RequestAndTaskExecutor executor, AggregationTaskStageState currentTaskStageState) {
        executor.<ApiExecutor>cast().executeApiRequest(pagingTaskCreator.createRequest(this), new RequestSuccessListener<PartialListingResult<TItem>>() {
            @Override
            public void onRequestSuccess(PartialListingResult<TItem> response) {
                setPageItems(response.getItems());
                pagingTaskCreator.processApiResult(executor, ApiListingAggregationPagingTask.this);
            }
        });
    }

    public ApiListingAggregationPagingTaskCreator<TItem, TResponse> getPagingTaskCreator() {
        return pagingTaskCreator;
    }

}
