package ru.touchin.twitterhashtagsviabaselib.activities;

import android.os.Bundle;
import android.widget.Toast;

import org.zuzuk.tasks.aggregationtask.AggregationTaskStageState;
import org.zuzuk.tasks.realloading.RealLoadingAggregationTaskListener;
import org.zuzuk.ui.fragments.BaseFragment;
import org.zuzuk.utils.Lc;

import java.util.List;

import ru.touchin.twitterhashtagsviabaselib.R;
import ru.touchin.twitterhashtagsviabaselib.api.RequestFailListener;
import ru.touchin.twitterhashtagsviabaselib.fragments.BaseLoadedFragment;
import ru.touchin.twitterhashtagsviabaselib.fragments.TweetListFragment;

public class MainActivity extends MyBaseActivity implements RequestFailListener, RealLoadingAggregationTaskListener {

    private BaseLoadedFragment currentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupActionBar();
        if (savedInstanceState == null) {
            setFirstFragment(TweetListFragment.class);
        }
    }

    private void updateActionBarState() {
        boolean homeButtonVisible = currentFragment == null || currentFragment.isHomeButtonVisible();
        boolean drawerIndicatorEnabled = isCurrentFragmentTop() && homeButtonVisible;
        if (currentFragment != null) {
            currentFragment.configureActionBar();
        }
        if (!drawerIndicatorEnabled) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            if (!homeButtonVisible) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            }
        }
    }

    @Override
    public void onBackStackChanged() {
        super.onBackStackChanged();
        updateActionBarState();
    }


    @Override
    public void onFragmentStarted(BaseFragment fragment) {
        super.onFragmentStarted(fragment);
        hideSoftInput();
        currentFragment = (BaseLoadedFragment) fragment;
        updateActionBarState();
    }

    @Override
    protected void onStop() {
        super.onStop();
        currentFragment = null;
    }


    @Override
    public void onRequestFailure(List<Exception> exceptionList) {
        Exception error = exceptionList.get(0);

        if (error.getCause() instanceof Exception) {
            error = (Exception) error.getCause();
        }
        Lc.e(error.getMessage());

        String msg = error.getMessage();

        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onRealLoadingStarted(AggregationTaskStageState currentTaskStageState) {
    }

    @Override
    public void onRealLoaded(AggregationTaskStageState currentTaskStageState) {
    }

    @Override
    public void onRealFailed(AggregationTaskStageState currentTaskStageState) {
        onRequestFailure(currentTaskStageState.getExceptions());
    }

}
