package com.trayis.simplimvvm.kotlinapp.ui.model

import android.arch.lifecycle.MutableLiveData
import com.trayis.simplikmvvm.utils.Logging
import com.trayis.simplikmvvm.viewmodel.SimpliViewModel
import java.util.*

class MainViewModel() : SimpliViewModel() {

    val textViewData: MutableLiveData<String> = MutableLiveData();

    private var mInitialTime: Long = 100

    private var timer: Timer? = null

    override fun onCreate() {
        timer = Timer();
        timer?.let {
            it.schedule(object : TimerTask() {
                override fun run() {
                    if (mInitialTime < 0) {
                        it.cancel()
                        it.purge()
                        return;
                    }
                    Logging.i(TAG, "" + mInitialTime);
                    textViewData.postValue("" + mInitialTime);
                    mInitialTime--;
                }
            }, mInitialTime, 300)
        }
    }

    override fun onCleared() {
        timer?.let {
            it.cancel()
            it.purge()
        }
        super.onCleared()
    }
}
