package com.trayis.simplimvvm

import android.app.Application
import com.trayis.simplimvvm.utils.Logging
import com.trayis.simplimvvm.utils.SimpliMvvmProvider
import com.trayis.simplimvvm.utils.SimpliProviderUtil

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