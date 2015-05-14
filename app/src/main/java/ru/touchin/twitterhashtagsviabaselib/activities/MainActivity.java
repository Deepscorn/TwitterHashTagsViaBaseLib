package ru.touchin.twitterhashtagsviabaselib.activities;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import org.zuzuk.ui.fragments.BaseFragment;
import org.zuzuk.utils.Lc;

import java.util.List;

import ru.touchin.twitterhashtagsviabaselib.R;
import ru.touchin.twitterhashtagsviabaselib.api.RequestFailListener;
import ru.touchin.twitterhashtagsviabaselib.fragments.BaseLoadedFragment;
import ru.touchin.twitterhashtagsviabaselib.fragments.TweetTabFragment;

public class MainActivity extends MyBaseActivity implements RequestFailListener {

    private BaseLoadedFragment currentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupActionBar();
        if (savedInstanceState == null) {
            setFirstFragment(TweetTabFragment.class);
        }
        getSupportActionBar().setElevation(0);
    }

    private void updateActionBarState() {
        boolean homeButtonVisible = currentFragment == null || currentFragment.isHomeButtonVisible();
        boolean enabled = isCurrentFragmentTop() && homeButtonVisible;
        if (currentFragment != null) {
            currentFragment.configureActionBar();
        }

        if (!enabled) {
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
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            upButtonClicked();
            return true;
        }
        return false;
    }
}
