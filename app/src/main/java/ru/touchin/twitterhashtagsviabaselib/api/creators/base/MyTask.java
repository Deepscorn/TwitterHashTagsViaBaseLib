package ru.touchin.twitterhashtagsviabaselib.api.creators.base;

import org.zuzuk.tasks.aggregationtask.AggregationTaskStageState;
import org.zuzuk.tasks.aggregationtask.RequestAndTaskExecutor;
import org.zuzuk.tasks.remote.base.RemoteRequest;

import ru.touchin.mylibrary.api.RequestFailListener;
import ru.touchin.mylibrary.api.RequestSuccessListener;
import ru.touchin.mylibrary.api.creators.base.RemoteAggregationPagingTask;

/**
 * Created by Alex on 07.05.2015.
 */
public class MyTask extends RemoteAggregationPagingTask {

    protected MyTask(RequestFailListener requestFailListener, int offset, int limit) {
        super(requestFailListener, offset, limit);
    }

    @Override
    public void load(RequestAndTaskExecutor executor, AggregationTaskStageState currentTaskStageState) {
        executor.executeRequest(new RemoteRequest<Object>(getLimit(), getOffset()) {
        }, new RequestSuccessListener<Object>() {
            @Override
            public void onRequestSuccess(Object o) {
                setPageItems(response.get);
            }
        });
    }
}
