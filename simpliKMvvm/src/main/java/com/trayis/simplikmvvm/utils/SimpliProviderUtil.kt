package com.trayis.simplikmvvm.utils

class SimpliProviderUtil private constructor() {

    var provider: SimpliMvvmProvider? = null
        set(provider) {
            if (this.provider != null) {
                Logging.w(TAG, "provider already set")
                return
            }
            field = provider
        }

    companion object {

        private val TAG = "SimpliProviderUtil"

        val instance = SimpliProviderUtil()
    }

}