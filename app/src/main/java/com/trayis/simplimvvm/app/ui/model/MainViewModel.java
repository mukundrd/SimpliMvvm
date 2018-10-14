package com.trayis.simplimvvm.app.ui.model;

import androidx.lifecycle.MutableLiveData;

import com.trayis.simplimvvm.utils.Logging;
import com.trayis.simplimvvm.viewmodel.SimpliViewModel;

import java.util.Timer;
import java.util.TimerTask;

public class MainViewModel extends SimpliViewModel {

    private int mInitialTime;

    public final MutableLiveData<String> textViewData = new MutableLiveData<>();
    private Timer timer;

    @Override
    public void onCreate() {
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
