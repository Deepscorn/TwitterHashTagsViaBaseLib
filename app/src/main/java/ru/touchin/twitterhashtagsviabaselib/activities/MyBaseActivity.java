package ru.touchin.twitterhashtagsviabaselib.activities;

import android.support.v7.widget.Toolbar;

import org.zuzuk.ui.activities.BaseExecutorActivity;

import ru.touchin.twitterhashtagsviabaselib.R;

public abstract class MyBaseActivity extends BaseExecutorActivity {

    protected void setupActionBar() {
//        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected int getFragmentContainerId() {
        return R.id.mainFragmentContainer;
    }
}
