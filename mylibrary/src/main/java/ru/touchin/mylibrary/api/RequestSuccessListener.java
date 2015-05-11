package ru.touchin.mylibrary.api;

import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

public abstract class RequestSuccessListener<TResponse> implements RequestListener<TResponse> {

    @Override
    public void onRequestFailure(SpiceException spiceException) {
    }

}
