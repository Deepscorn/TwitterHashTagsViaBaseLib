package ru.touchin.twitterhashtagsviabaselib.api.creators.model.base;

import com.google.api.client.util.Key;

public class PartialListingResult<T> extends ListingResult<T> {

    @Key("total_count")
    private int totalCount;

    public int getTotalCount() {
        return totalCount;
    }

}
