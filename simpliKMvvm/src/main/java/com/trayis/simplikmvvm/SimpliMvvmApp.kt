package com.trayis.simplikmvvm

import android.app.Application
import com.trayis.simplikmvvm.utils.Logging
import com.trayis.simplikmvvm.utils.SimpliMvvmProvider

abstract class SimpliMvvmApp : Application() {

    override fun onCreate() {
        super.onCreate()
        initLogging()
    }

    protected open fun initLogging() {
        Logging.initLogging()
    }

    abstract fun getSimpliMvvmProvider(): SimpliMvvmProvider<*>
}