package com.trayis.simplimvvm.viewmodel;

import androidx.lifecycle.ViewModel;

public abstract class SimpliViewModel extends ViewModel {

    protected final String TAG = getClass().getSimpleName();

    public abstract void onCreate();

}
