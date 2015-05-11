package ru.touchin.twitterhashtagsviabaselib.utils;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class TwitterUtils {
    public static final String TWITTER_DATE_FORMAT = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";

    public static String getTwitterDateInLocalTZ(String createdAt) {

        SimpleDateFormat sf = new SimpleDateFormat(TWITTER_DATE_FORMAT, Locale.ENGLISH);

        Date localTime = new Date(System.currentTimeMillis());

        Date utcTime = null;
        try {
            utcTime = sf.parse(createdAt);
        } catch (ParseException e) {
            Log.e("TwitterUtils", "Can't parse the twitter date: " + createdAt);
        }
        Date tweetDate = new Date(utcTime.getTime() + TimeZone.getDefault().getOffset(localTime.getTime()));

        return new SimpleDateFormat("dd.MM.yyyy").format(tweetDate);
    }
}
