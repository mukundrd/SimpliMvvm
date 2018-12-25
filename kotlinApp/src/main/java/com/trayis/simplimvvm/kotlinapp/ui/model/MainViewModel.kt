package com.trayis.simplimvvm.kotlinapp.ui.model

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import com.trayis.simplikmvvm.utils.Logging
import com.trayis.simplikmvvm.viewmodel.SimpliViewModel
import com.trayis.simplimvvm.kotlinapp.SampleApp
import com.trayis.simplimvvm.kotlinapp.common.ConnectivityResource
import java.util.*

class MainViewModel(val conn: ConnectivityResource?, val app: SampleApp) : SimpliViewModel() {

    val textViewData: MutableLiveData<String> = MutableLiveData();

    val internetViewData: MutableLiveData<String> = MutableLiveData();

    private var mInitialTime: Long = 100

    private var timer: Timer? = null

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate() {
        internetViewData.postValue("Internet Connected : ${conn?.isConnected()}")
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
