package com.trayis.simplimvvm.kotlinapp.ui.model

import android.arch.lifecycle.MutableLiveData
import com.trayis.simplikmvvm.utils.Logging
import com.trayis.simplikmvvm.viewmodel.SimpliViewModel
import java.util.*

class MainViewModel() : SimpliViewModel() {

    val textViewData: MutableLiveData<String> = MutableLiveData();

    private var mInitialTime: Long = 100

    private var timer: Timer

    init {
        timer = Timer()
        timer.schedule(object : TimerTask() {
            override fun run() {
                if (mInitialTime < 0) {
                    timer.cancel();
                    return;
                }
                Logging.i(TAG, "" + mInitialTime);
                textViewData.postValue("" + mInitialTime);
                mInitialTime--;
            }
        }, mInitialTime, 300)
    }

    override fun onCleared() {
        timer.cancel()
        super.onCleared()
    }
}
