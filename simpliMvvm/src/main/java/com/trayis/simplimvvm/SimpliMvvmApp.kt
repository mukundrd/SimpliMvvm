package com.trayis.simplimvvm

import android.app.Application
import com.trayis.simplimvvm.utils.SimpliMvvmProvider
import com.trayis.simplimvvm.utils.SimpliProviderUtil

abstract class SimpliMvvmApp : Application() {

    override fun onCreate() {
        super.onCreate()
        SimpliProviderUtil.setProvider(getSimpliMvvmProvider())
    }

    abstract fun getSimpliMvvmProvider(): SimpliMvvmProvider
}