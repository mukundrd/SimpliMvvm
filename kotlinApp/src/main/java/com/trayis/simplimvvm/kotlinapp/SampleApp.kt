package com.trayis.simplimvvm.kotlinapp

import com.trayis.simplikmvvm.SimpliMvvmApp
import com.trayis.simplikmvvm.utils.SimpliMvvmProvider
import com.trayis.simplikmvvmannotation.generated.SimpliMvvmProviderImpl

class SampleApp : SimpliMvvmApp() {

    override fun getSimpliMvvmProvider(): SimpliMvvmProvider {
        return SimpliMvvmProviderImpl.instance
    }

}
