package ru.touchin.twitterhashtagsviabaselib.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

import org.zuzuk.tasks.aggregationtask.AggregationTaskStageState;
import org.zuzuk.tasks.aggregationtask.RequestAndTaskExecutor;
import org.zuzuk.tasks.realloading.OnlyRealLoadingAggregationTask;
import org.zuzuk.ui.fragments.BaseExecutorFragment;

import ru.touchin.twitterhashtagsviabaselib.R;
import ru.touchin.twitterhashtagsviabaselib.exercise.LayoutsPOJO;

/**
 * A placeholder fragment containing a simple view.
 */
public class MyActivityFragment extends BaseExecutorFragment {

    public static final String LOG_TAG = "MyActivityFragment";

    @Override
    protected View onCreateViewInternal(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_my, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        findViewById(R.id.go).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // service runs after home is pressed, on resume listeners are not reattached automatically
                executeAggregationTask(new OnlyRealLoadingAggregationTask() {
                    @Override
                    protected void realLoad(RequestAndTaskExecutor executor, AggregationTaskStageState currentTaskStageState) {
                        executor.executeRequest(new LayoutsPOJO.Request("android-phone"), new RequestListener<LayoutsPOJO>() {
                            @Override
                            public void onRequestFailure(SpiceException e) {
                                show(e);
                            }

                            @Override
                            public void onRequestSuccess(LayoutsPOJO layoutsPOJO) {
                                show(layoutsPOJO);
                            }
                        });
                    }
                });
            }
        });
    }

    private void show(Object e) {
        Log.d(LOG_TAG, e.toString());
        Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_SHORT).show();
    }


}
