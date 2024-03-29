package ru.touchin.twitterhashtagsviabaselib.adapters;

import android.net.Uri;
import android.view.View;
import android.widget.TextView;

import ru.touchin.twitterhashtagsviabaselib.R;
import ru.touchin.twitterhashtagsviabaselib.model.Tweet;
import ru.touchin.twitterhashtagsviabaselib.views.RoundedLoadingImageView;

public class TweetListAdapter extends BaseAdapter<Tweet> {

    private static final int EMPTY_ITEM = 0;
    private static final int ITEM = 1;

    @Override
    public boolean isEnabled(int position) {
        return get(position) != null;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        return get(position) == null ? EMPTY_ITEM : ITEM;
    }

    @Override
    public int getResourceId() {
        return R.layout.tweet_list;
    }

    @Override
    public void fillItem(View view, Tweet tweet) {
        RoundedLoadingImageView profileImage = (RoundedLoadingImageView) view.findViewById(R.id.profile_image_normal);
        Uri uri = Uri.parse(tweet.getUser().getNormalSizeImageURL());
        profileImage.setImageURI(uri);

        ((TextView) view.findViewById(R.id.user_name_item)).setText(tweet.getUser().getName());
        ((TextView) view.findViewById(R.id.tweet_item)).setText(tweet.toString());
    }
}
