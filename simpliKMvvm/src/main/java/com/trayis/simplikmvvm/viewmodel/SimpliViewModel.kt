package com.trayis.simplikmvvm.viewmodel

import android.arch.lifecycle.ViewModel

abstract class SimpliViewModel : ViewModel() {

    protected val TAG = javaClass.simpleName

    abstract fun onCreate()

}