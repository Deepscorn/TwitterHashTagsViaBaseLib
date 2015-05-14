package ru.touchin.twitterhashtagsviabaselib.activities;

import org.zuzuk.ui.activities.BaseExecutorActivity;

import ru.touchin.twitterhashtagsviabaselib.R;

public abstract class MyBaseActivity extends BaseExecutorActivity {

    protected void setupActionBar() {
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected int getFragmentContainerId() {
        return R.id.mainFragmentContainer;
    }
}
