package com.trayis.simplimvvm.kotlinapp

import com.trayis.simplikmvvm.SimpliMvvmApp
import com.trayis.simplikmvvm.utils.SimpliMvvmProvider
import com.trayis.simplimvvmannotation.generated.SimpliMvvmProviderImpl

class SampleApp : SimpliMvvmApp() {

    override fun onCreate() {
        super.onCreate()
    }

    override fun getSimpliMvvmProvider(): SimpliMvvmProvider<*> {
        return SimpliMvvmProviderImpl.instance
    }

}
