package ru.touchin.twitterhashtagsviabaselib.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import org.zuzuk.providers.RequestPagingProvider;
import org.zuzuk.tasks.aggregationtask.AggregationTaskStageState;
import org.zuzuk.tasks.aggregationtask.RequestAndTaskExecutor;

import ru.touchin.twitterhashtagsviabaselib.R;
import ru.touchin.twitterhashtagsviabaselib.adapters.TweetListAdapter;
import ru.touchin.twitterhashtagsviabaselib.api.creators.base.TweetTaskCreator;
import ru.touchin.twitterhashtagsviabaselib.model.Tweet;


public class TweetListFragment extends BaseListViewFragment {
    public static final String ARG_PAGE = "ARG_PAGE";
    private String hashTag;
    private RequestPagingProvider<Tweet> tweetListProvider;
    private TweetListAdapter adapter;

    public static TweetListFragment newInstance(String hashTag) {
        Bundle args = new Bundle();
        args.putString(ARG_PAGE, hashTag);
        TweetListFragment fragment = new TweetListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hashTag = getArguments().getString(ARG_PAGE);
    }

    @Override
    protected void onCreateRenewable() {
        super.onCreateRenewable();
        tweetListProvider = new RequestPagingProvider<>(this, new TweetTaskCreator(this, getActivity(), hashTag));
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
        this.<ListView>findViewById(R.id.fragmentList).setAdapter(adapter);
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

    @Override
    protected void onItemClick(int position) {
        super.onItemClick(position);
        pushFragment(TweetFragment.class, TweetFragment.createArgs(adapter.get(position)));
    }
}
