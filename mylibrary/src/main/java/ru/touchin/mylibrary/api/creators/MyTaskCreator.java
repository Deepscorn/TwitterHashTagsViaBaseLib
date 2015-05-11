package ru.touchin.mylibrary.api.creators;

import org.zuzuk.tasks.aggregationtask.AggregationPagingTask;
import org.zuzuk.tasks.aggregationtask.AggregationTaskStageState;
import org.zuzuk.tasks.aggregationtask.RequestAndTaskExecutor;

import ru.touchin.mylibrary.api.RequestFailListener;
import ru.touchin.mylibrary.api.creators.base.RemoteAggregationPagingTask;
import ru.touchin.mylibrary.api.creators.base.RemoteAggregationPagingTaskCreator;

/**
 * Created by Alex on 07.05.2015.
 */
public class MyTaskCreator extends RemoteAggregationPagingTaskCreator {

    protected MyTaskCreator(RequestFailListener requestFailListener) {
        super(requestFailListener);
    }

    @Override
    public AggregationPagingTask createPagingTask(int offset, int limit) {
        return new MyTask();
    }
}
