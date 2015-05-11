package ru.touchin.mylibrary.api;

import java.util.List;

public interface RequestFailListener {

    public void onRequestFailure(List<Exception> exceptionList);

}
