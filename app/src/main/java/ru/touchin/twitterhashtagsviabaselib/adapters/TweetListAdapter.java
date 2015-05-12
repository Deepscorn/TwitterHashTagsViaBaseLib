package ru.touchin.twitterhashtagsviabaselib.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.zuzuk.providers.RequestPagingProvider;
import org.zuzuk.ui.adapters.ProviderAdapter;

import ru.touchin.twitterhashtagsviabaselib.R;
import ru.touchin.twitterhashtagsviabaselib.model.Tweet;

public class TweetListAdapter extends ProviderAdapter<Tweet, RequestPagingProvider<Tweet>> {

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
    protected View newView(int position, LayoutInflater inflater, ViewGroup container) {
        if (getItemViewType(position) == EMPTY_ITEM) {
            return new ProgressBar(inflater.getContext());
        } else {
            View newView = inflater.inflate(R.layout.tweet_list, container, false);

            ViewHolder holder = new ViewHolder();
            holder.profileImage = (ImageView) newView.findViewById(R.id.profile_image_normal);
            holder.userName = (TextView) newView.findViewById(R.id.user_name_item);
            holder.tweetText = (TextView) newView.findViewById(R.id.tweet_item);

            newView.setTag(holder);
            return newView;
        }
    }

    @Override
    protected void bindView(View view, Tweet tweet, int position) {
        if (getItemViewType(position) == ITEM) {
            ViewHolder holder = (ViewHolder) view.getTag();
            Picasso.with(view.getContext()).load(tweet.getUser().getNormalSizeImageURL()).into(holder.profileImage);
            holder.userName.setText(tweet.getUser().getName());
            holder.tweetText.setText(tweet.toString());
        }
    }

    private class ViewHolder {
        ImageView profileImage;
        TextView userName;
        TextView tweetText;
    }
}
