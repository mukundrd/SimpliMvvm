package com.trayis.simplikmvvm.utils

class SimpliProviderUtil private constructor() {

    private var mProvider: SimpliMvvmProvider? = null

    companion object {

        private val INSTANCE = SimpliProviderUtil()

        fun setProvider(provider: SimpliMvvmProvider) {
            INSTANCE.mProvider = provider
        }

        fun getProvider(): SimpliMvvmProvider? {
            return INSTANCE.mProvider
        }
    }

}