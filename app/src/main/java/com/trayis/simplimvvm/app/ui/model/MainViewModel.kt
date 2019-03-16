package com.trayis.simplimvvm.app.ui.model

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import com.trayis.simplimvvm.app.common.ConnectivityResource
import com.trayis.simplimvvm.viewmodel.SimpliViewModel
import java.util.*

class MainViewModel(val conn: ConnectivityResource?) : SimpliViewModel() {

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
                    Log.i(TAG, "" + mInitialTime);
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
