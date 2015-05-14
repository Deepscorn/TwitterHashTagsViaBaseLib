package ru.touchin.twitterhashtagsviabaselib.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBarActivity;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import ru.touchin.twitterhashtagsviabaselib.R;
import ru.touchin.twitterhashtagsviabaselib.model.Tweet;

public class TweetFragment extends BaseLoadedFragment {
    public static final String TWEET = "tweet";

    public static Bundle createArgs(Tweet tweet) {
        Bundle args = new Bundle();
        args.putSerializable(TWEET, tweet);
        return args;
    }

    @Override
    protected View createContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tweet, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Tweet tweet = (Tweet) getArguments().get(TWEET);
        ((TextView) findViewById(R.id.tweet_text)).setText(tweet.getText());

        ((TextView) findViewById(R.id.tweet_date)).setText(tweet.getDate());

        ((TextView) findViewById(R.id.authorName)).setText(tweet.getUser().getName());

        TextView link = findViewById(R.id.tweet_link);
        link.setMovementMethod(LinkMovementMethod.getInstance());
        link.setText(Html.fromHtml(tweet.getLink()));

        ImageView imageView = findViewById(R.id.profile_image_full);
        Picasso.with(getActivity()).load(tweet.getUser().getFullSizeImageURL()).into(imageView);
    }
}
