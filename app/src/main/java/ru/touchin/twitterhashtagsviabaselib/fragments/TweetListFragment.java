package ru.touchin.twitterhashtagsviabaselib.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.zuzuk.providers.RequestPagingProvider;
import org.zuzuk.tasks.aggregationtask.AggregationTaskStageState;
import org.zuzuk.tasks.aggregationtask.RequestAndTaskExecutor;

import ru.touchin.mylibrary.api.creators.MyTaskCreator;

/**
 * Created by Alex on 07.05.2015.
 */
public class TweetListFragment extends BaseListViewFragment {

    private RequestPagingProvider<Tweet> guildListProvider;

    private GuildListAdapter adapter;

    @Override
    protected void onCreateRenewable() {
        super.onCreateRenewable();
        guildListProvider = new RequestPagingProvider<>(this, new MyTaskCreator(this));
    }

    @Override
    protected void onDestroyRenewable() {
        super.onDestroyRenewable();
        guildListProvider = null;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = new GuildListAdapter();
        adapter.setProvider(guildListProvider);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        adapter = null;
    }

        @Override
    protected void loadFragmentData(RequestAndTaskExecutor executor, AggregationTaskStageState currentTaskStageState) {
            guildListProvider.initialize(getListPosition(), executor);
    }

    @Override
    protected View createContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_guild_list, container, false);
    }

    @Override
    protected void onItemClick(int position) {
        super.onItemClick(position);
        pushFragment(TweetFragment.class, TweetFragment.createArgs());
    }
}
