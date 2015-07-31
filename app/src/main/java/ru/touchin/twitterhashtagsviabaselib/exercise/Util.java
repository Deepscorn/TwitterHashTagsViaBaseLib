package ru.touchin.twitterhashtagsviabaselib.exercise;

import android.util.Log;
import android.widget.Toast;

import ru.touchin.twitterhashtagsviabaselib.App;

/**
 * Created by iz on 31/07/15.
 */
public class Util {
    public static final String LOG_TAG = "RequestExercises";

    public static void show(Object e) {
        Log.d(LOG_TAG, e.toString());
        Toast.makeText(App.getInstance(), e.toString(), Toast.LENGTH_SHORT).show();
    }

    public static void show(String template, Object... args) {
        String formattedString = String.format(template, args);
        Log.d(LOG_TAG, formattedString);
        Toast.makeText(App.getInstance(), formattedString, Toast.LENGTH_SHORT).show();
    }
}
