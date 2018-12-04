package com.trayis.simplimvvm.app.ui.model;

import com.trayis.simplimvvm.app.SampleApp;
import com.trayis.simplimvvm.utils.Logging;
import com.trayis.simplimvvm.viewmodel.SimpliViewModel;

import java.util.Timer;
import java.util.TimerTask;

import androidx.lifecycle.MutableLiveData;

public class NotSoMainViewModel extends SimpliViewModel {

    // private final SampleApp app;

    private int mInitialTime;

    public NotSoMainViewModel(SampleApp app) {
        // this.app = app;
    }

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
