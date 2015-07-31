package ru.touchin.twitterhashtagsviabaselib.exercise;

import android.os.Bundle;

import org.zuzuk.ui.activities.BaseExecutorActivity;

import java.util.List;

import ru.touchin.twitterhashtagsviabaselib.R;
import ru.touchin.twitterhashtagsviabaselib.api.RequestFailListener;

public class MyActivity extends BaseExecutorActivity implements RequestFailListener  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
    }

    @Override
    public void onRequestFailure(List<Exception> exceptionList) {
        Util.show("% errors, first is %s", exceptionList.size(), exceptionList.get(0));
    }
}
