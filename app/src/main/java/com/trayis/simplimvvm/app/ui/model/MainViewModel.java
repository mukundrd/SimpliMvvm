package com.trayis.simplimvvm.app.ui.model;

import android.os.Build;

import com.trayis.simplimvvm.app.common.ConnectivityResource;
import com.trayis.simplimvvm.utils.Logging;
import com.trayis.simplimvvm.viewmodel.SimpliViewModel;

import java.util.Timer;
import java.util.TimerTask;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.MutableLiveData;

public class MainViewModel extends SimpliViewModel {

    private final ConnectivityResource res;

    private int mInitialTime;

    private Timer timer;

    public final MutableLiveData<String> internetViewData = new MutableLiveData<>();

    public final MutableLiveData<String> textViewData = new MutableLiveData<>();

    public MainViewModel(ConnectivityResource manager) {
        this.res = manager;
    }

    @RequiresApi(Build.VERSION_CODES.M)
    @Override
    public void onCreate() {

        internetViewData.postValue("Internet Connected : " + res.isConnected());

        mInitialTime = 100;

        Logging.i(TAG, "" + mInitialTime);
        textViewData.setValue("" + mInitialTime);

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (mInitialTime < 0) {
                    timer.cancel();
                    return;
                }
                Logging.i(TAG, "" + mInitialTime);
                textViewData.postValue("" + mInitialTime);
                mInitialTime--;
            }
        }, mInitialTime, 300);

    }

    @Override
    protected void onCleared() {
        timer.cancel();
        super.onCleared();
    }
}
