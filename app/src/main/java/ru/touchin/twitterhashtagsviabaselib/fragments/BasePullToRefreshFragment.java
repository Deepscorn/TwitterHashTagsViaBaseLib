package ru.touchin.twitterhashtagsviabaselib.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import org.zuzuk.tasks.aggregationtask.AggregationTaskStage;
import org.zuzuk.tasks.aggregationtask.AggregationTaskStageState;
import org.zuzuk.tasks.aggregationtask.RequestAndTaskExecutor;

import ru.touchin.twitterhashtagsviabaselib.views.SwipeRefreshLayout;

public abstract class BasePullToRefreshFragment extends BaseLoadingFragment {

    private SwipeRefreshLayout swipeRefreshLayout;

    private boolean isRefreshing = false;

    @Override
    public void onResume() {
        disableRefreshing();
        super.onResume();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        swipeRefreshLayout = findViewByType(SwipeRefreshLayout.class, view);
        if (swipeRefreshLayout != null) {
            swipeRefreshLayout.setColorSchemeResources(R.color.cape_palliser, R.color.sundance, R.color.cape_palliser, R.color.sundance);
            swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    refreshInner();
                }
            });
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        swipeRefreshLayout = null;
    }

    protected void refresh() {
        swipeRefreshLayout.setRefreshing(true);
        refreshInner();
    }

    private void refreshInner() {
        isRefreshing = true;
        reload();
    }

    private void disableRefreshing() {
        isRefreshing = false;
        if (swipeRefreshLayout != null) {
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void onLoadingStarted(AggregationTaskStageState currentTaskStageState) {
        if (!isRefreshing) {
            super.onLoadingStarted(currentTaskStageState);
        }
    }

    @Override
    public void onLoaded(AggregationTaskStageState currentTaskStageState) {
        if (isRefreshing) {
            if (currentTaskStageState.getTaskStage() == AggregationTaskStage.REAL_LOADING) {
                super.onLoaded(currentTaskStageState);
                disableRefreshing();
            }
        } else {
            super.onLoaded(currentTaskStageState);
        }
    }

    @Override
    public void onFailed(AggregationTaskStageState currentTaskStageState) {
        super.onFailed(currentTaskStageState);
        if (currentTaskStageState.getTaskStage() == AggregationTaskStage.REAL_LOADING) {
            disableRefreshing();
        }
    }

    @Override
    public void load(RequestAndTaskExecutor executor, AggregationTaskStageState currentTaskStageState) {
        if (!isRefreshing || currentTaskStageState.getTaskStage() == AggregationTaskStage.REAL_LOADING) {
            super.load(executor, currentTaskStageState);
        }
    }

}
