package ru.touchin.twitterhashtagsviabaselib.exercise;

import android.app.Application;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Toast;

import com.google.api.client.util.Key;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.zuzuk.tasks.remote.GetJsonRequest;

import java.util.ArrayList;

import ru.touchin.twitterhashtagsviabaselib.App;
import ru.touchin.twitterhashtagsviabaselib.activities.MyActivityFragment;

public class LayoutsPOJO {
    @Key
    public Layouts layouts;

    public static class Layouts {
        @Key
        public Layout landscape;
        @Key
        public Layout portrait;

        @Override
        public String toString() {
            return ReflectionToStringBuilder.toString(this);
        }
    }

    public static class Layout {
        @Key
        public ArrayList<TilePOJO> tiles;

        @Override
        public String toString() {
            return ReflectionToStringBuilder.toString(this);
        }
    }

    public LayoutsPOJO() {} // for deserializing from json

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }

    public static class Request extends GetJsonRequest<LayoutsPOJO> {
        private final String platform;

        public Request(String platform) {
            super(LayoutsPOJO.class);
            if(StringUtils.isEmpty(platform))
                throw(new IllegalArgumentException("platform is empty"));
            this.platform = platform;
        }

        @Override
        protected String getUrl() {
            return "https://api2-mock.4game.com/ui-settings/layout/"+platform;
        }

        @Override
        public LayoutsPOJO loadDataFromNetwork() throws Exception {
            for (int i = 0; i < 5; ++i) {
                Log.d(MyActivityFragment.LOG_TAG, "layouts load in " + i);
                long start = SystemClock.uptimeMillis();
                while(SystemClock.uptimeMillis() - start < 2010) {}
            }
            return super.loadDataFromNetwork();
        }
    }
}
