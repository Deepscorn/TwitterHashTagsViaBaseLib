package ru.touchin.twitterhashtagsviabaselib.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import ru.touchin.twitterhashtagsviabaselib.R;
import ru.touchin.twitterhashtagsviabaselib.fragments.TweetListFragment;

public class PagerAdapter extends FragmentPagerAdapter {
    final int PAGE_COUNT = 3;
    private String[] tabTitles;

    public PagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        String twitter = context.getString(R.string.twitter);
        String dribbble = context.getString(R.string.dribbble);
        String android = context.getString(R.string.android);
        tabTitles = new String[]{twitter, dribbble, android};
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {
        return TweetListFragment.newInstance(position + 1);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
}
