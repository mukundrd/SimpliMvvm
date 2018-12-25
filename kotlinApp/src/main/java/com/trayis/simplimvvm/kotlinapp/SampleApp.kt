package com.trayis.simplimvvm.kotlinapp

import android.app.Application
import android.content.Context
import com.trayis.simplikmvvm.utils.SimpliProviderUtil
import com.trayis.simplikmvvmannotation.generated.SimpliMvvmProviderImpl

class SampleApp : Application() {

    companion object {
        fun getInstance(context: Context) = context.applicationContext as SampleApp
    }

    override fun onCreate() {
        super.onCreate()

        SimpliProviderUtil.setProvider(SimpliMvvmProviderImpl(this))
    }
}
