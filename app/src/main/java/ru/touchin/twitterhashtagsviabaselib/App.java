package ru.touchin.twitterhashtagsviabaselib;

import android.app.Application;
import android.content.Intent;
import android.os.StrictMode;
import android.util.Log;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.backends.okhttp.OkHttpImagePipelineConfigFactory;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.squareup.okhttp.OkHttpClient;

import org.zuzuk.tasks.aggregationtask.RequestAndTaskExecutor;
import org.zuzuk.tasks.aggregationtask.TaskExecutorHelper;
import org.zuzuk.tasks.remote.base.TaskExecutorHelperCreator;
import org.zuzuk.utils.Lc;

public class App extends Application implements TaskExecutorHelperCreator {

    private static App instance;

    @Override
    public TaskExecutorHelper createTaskExecutorHelper() {
        return new TaskExecutorHelper(){
            @Override
            protected RequestAndTaskExecutor createRequestAndTaskExecutor() {
                return new RequestAndTaskExecutor();
            }
        };
    }

    @Override
    public void onCreate() {
        if (BuildConfig.DEBUG) {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                    .detectAll()
                    .penaltyLog()
                    .build());
        }

        super.onCreate();
        instance = this;

        if (BuildConfig.DEBUG) {
            Lc.initialize(Log.DEBUG);
            Lc.setRobospiceLogLevel(Log.ERROR);
        } else {
          //  Fabric.with(this, new Crashlytics());
            Lc.initialize(Log.ERROR);
        }

        OkHttpClient httpClient = new OkHttpClient();
        ImagePipelineConfig config = OkHttpImagePipelineConfigFactory.newBuilder(getApplicationContext(), httpClient).build();
        Fresco.initialize(getApplicationContext(), config);
    }

    public static App getInstance() {
        return instance;
    }

}
