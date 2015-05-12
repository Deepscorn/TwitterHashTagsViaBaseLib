package ru.touchin.twitterhashtagsviabaselib.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.zuzuk.providers.RequestPagingProvider;
import org.zuzuk.tasks.aggregationtask.AggregationTaskStageState;
import org.zuzuk.tasks.aggregationtask.RequestAndTaskExecutor;

import ru.touchin.twitterhashtagsviabaselib.R;
import ru.touchin.twitterhashtagsviabaselib.adapters.TweetListAdapter;
import ru.touchin.twitterhashtagsviabaselib.api.creators.base.MyTaskCreator;
import ru.touchin.twitterhashtagsviabaselib.model.Tweet;


public class TweetListFragment extends BaseListViewFragment {

    private RequestPagingProvider<Tweet> tweetListProvider;

    private TweetListAdapter adapter;

    @Override
    protected void onCreateRenewable() {
        super.onCreateRenewable();
        tweetListProvider = new RequestPagingProvider<>(this, new MyTaskCreator(this, getActivity().getApplicationContext(), "Twitter"));
    }

    @Override
    protected void onDestroyRenewable() {
        super.onDestroyRenewable();
        tweetListProvider = null;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = new TweetListAdapter();
        adapter.setProvider(tweetListProvider);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        adapter = null;
    }

    @Override
    protected void loadFragmentData(RequestAndTaskExecutor executor, AggregationTaskStageState currentTaskStageState) {
        tweetListProvider.initialize(getListPosition(), executor);
    }

    @Override
    protected View createContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tweet_list, container, false);
    }

//    @Override
//    protected void onItemClick(int position) {
//        super.onItemClick(position);
//        pushFragment(TweetFragment.class, TweetFragment.createArgs());
//    }
}
