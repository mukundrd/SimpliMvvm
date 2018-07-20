package com.trayis.simplimvvm.viewmodel;

import android.arch.lifecycle.ViewModel;

public abstract class SimpliViewModel extends ViewModel {

    protected final String TAG = getClass().getSimpleName();

    @Override
    protected void onCleared() {
        super.onCleared();
    }

}
