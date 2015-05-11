package ru.touchin.twitterhashtagsviabaselib.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import org.zuzuk.providers.RequestPagingProvider;
import org.zuzuk.ui.fragments.BaseExecutorFragment;

import ru.touchin.twitterhashtagsviabaselib.R;
import ru.touchin.twitterhashtagsviabaselib.adapters.TweetAdapter;
import ru.touchin.twitterhashtagsviabaselib.api.creators.TweetRequestCreator;
import ru.touchin.twitterhashtagsviabaselib.model.Tweet;

public class HashTagFragment extends BaseExecutorFragment {
    public static final String ARG_PAGE = "ARG_PAGE";
    public static final String TWEETS = "TWEETS";
    public static final String TWEET = "TWEET";

    private int mPage;

    private TweetAdapter adapter;
    private RequestPagingProvider<Tweet> tweetProvider;
    private ListView listView;
    private Context context;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(ARG_PAGE);
    }

    @Override
    protected void onCreateRenewable() {
        super.onCreateRenewable();
        tweetProvider = new RequestPagingProvider<>(this, new TweetRequestCreator(getActivity().getApplicationContext(), "Twitter"));
    }

    @Override
    protected View onCreateViewInternal(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        adapter = new TweetAdapter();
        adapter.setProvider(tweetProvider);
        SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) inflater.inflate(R.layout.fragment_page, container, false);
        listView = (ListView) swipeRefreshLayout.findViewById(R.id.listview);
        listView.setAdapter(adapter);
        return swipeRefreshLayout;
    }

    public static HashTagFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        HashTagFragment fragment = new HashTagFragment();
        fragment.setArguments(args);
        return fragment;
    }

}
