package com.trayis.simplimvvm.app

import android.app.Application
import android.content.Context
import com.trayis.simplimvvm.utils.SimpliProviderUtil
import com.trayis.simplimvvmannotation.generated.SimpliMvvmProviderImpl

class SampleApp : Application() {

    companion object {
        fun getInstance(context: Context) = context.applicationContext as SampleApp
    }

    override fun onCreate() {
        super.onCreate()

        SimpliProviderUtil.setProvider(SimpliMvvmProviderImpl(this))
    }
}
