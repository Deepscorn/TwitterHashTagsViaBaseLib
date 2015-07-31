package ru.touchin.twitterhashtagsviabaselib.exercise;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

import org.zuzuk.tasks.aggregationtask.AggregationTaskStageState;
import org.zuzuk.tasks.aggregationtask.RequestAndTaskExecutor;

import ru.touchin.twitterhashtagsviabaselib.R;
import ru.touchin.twitterhashtagsviabaselib.fragments.BasePullToRefreshFragment;

/**
 * Created by iz on 31/07/15.
 */
public class LoadAndCacheWithPullToRefreshFragment extends BasePullToRefreshFragment {

    // service runs after home is pressed, on resume listeners are reattached automatically
    // even when developer option "do not keep activities" is checked
    // Listener + onFragmentDataLoaded triggers 2 times: on load (get from cache) and then
    // second load (load from net to update if obsolete). See example log:
    // RequestListener <data>
    // onFragmentDataLoaded
    // layouts download... 0
    // layouts download... 1
    // layouts download... 2
    // layouts download... 3
    // layouts download... 4
    // RequestListener <data>
    // onFragmentDataLoaded
    // Exactly same you'll get on orientation change.
    // At first success listener is triggered, then onFragmentDataLoaded.
    @Override
    protected void loadFragmentData(RequestAndTaskExecutor executor, AggregationTaskStageState currentTaskStageState) {
        // this get's executed twice on resume - one from cache (if exist), other is real loading from network
        Log.d(Util.LOG_TAG, "loadFragmentData");
        executor.executeRequest(new LayoutsPOJO.Request("android-phone"), new RequestListener<LayoutsPOJO>() {
            @Override
            public void onRequestFailure(SpiceException e) {
                Util.show("RequestListener " + e.toString());
            }

            @Override
            public void onRequestSuccess(LayoutsPOJO layoutsPOJO) {
                Util.show("RequestListener " + layoutsPOJO.toString());
            }
        });
    }

    @Override
    protected void onFragmentDataLoaded(AggregationTaskStageState currentTaskStageState) {
        Util.show("onFragmentDataLoaded");
    }

    @Override
    protected View createContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_game_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        findViewByType(RecyclerView.class, view).setLayoutManager(
                new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
    }
}
