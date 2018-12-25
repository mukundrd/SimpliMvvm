package com.trayis.simplikmvvm

import android.app.Application
import com.trayis.simplikmvvm.utils.Logging
import com.trayis.simplikmvvm.utils.SimpliMvvmProvider
import com.trayis.simplikmvvm.utils.SimpliProviderUtil

abstract class SimpliMvvmApp : Application() {

    override fun onCreate() {
        super.onCreate()
        initLogging()
        SimpliProviderUtil.setProvider(getSimpliMvvmProvider())
    }

    protected open fun initLogging() {
        Logging.initLogging()
    }

    abstract fun getSimpliMvvmProvider(): SimpliMvvmProvider
}