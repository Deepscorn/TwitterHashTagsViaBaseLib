package ru.touchin.twitterhashtagsviabaselib.activities;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.widget.TextView;

import com.octo.android.robospice.persistence.exception.SpiceException;

import org.zuzuk.tasks.aggregationtask.RequestAndTaskExecutor;
import org.zuzuk.tasks.realloading.BaseRequestWrapper;
import org.zuzuk.tasks.realloading.ChainedRequestListener;
import org.zuzuk.ui.activities.BaseActivity;
import org.zuzuk.ui.activities.BaseExecutorActivity;

import ru.touchin.twitterhashtagsviabaselib.R;
import ru.touchin.twitterhashtagsviabaselib.adapters.PagerAdapter;
import ru.touchin.twitterhashtagsviabaselib.layouts.SlidingTabLayout;
import ru.touchin.twitterhashtagsviabaselib.model.Tweets;
import ru.touchin.twitterhashtagsviabaselib.requests.BaseTweetsRequest;


public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new PagerAdapter(getSupportFragmentManager(),
                MainActivity.this));

        SlidingTabLayout slidingTabLayout = (SlidingTabLayout) findViewById(R.id.sliding_tabs);

        slidingTabLayout.setDistributeEvenly(true);
        slidingTabLayout.setViewPager(viewPager);

        // remove the shadow
        getSupportActionBar().setElevation(0);
    }

    @Override
    protected void onResume() {
        super.onResume();

    }
}
