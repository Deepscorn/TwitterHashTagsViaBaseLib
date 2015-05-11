package ru.touchin.twitterhashtagsviabaselib.api;

import java.util.List;

public interface RequestFailListener {

    void onRequestFailure(List<Exception> exceptionList);

}