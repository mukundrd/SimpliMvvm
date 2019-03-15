package com.trayis.simplimvvm.app.common

import android.content.Context
import com.trayis.simplimvvmannotation.SimpliResourcesProvider

/**
 * @author mudesai (Mukund Desai)
 * @created on 12/25/18
 */
@SimpliResourcesProvider
class SampleResourceProvider {

    companion object {

        private var INSTANCE: SampleResourceProvider? = null

        fun getInstance(): SampleResourceProvider {
            if (INSTANCE == null) {
                INSTANCE = SampleResourceProvider()
            }
            return INSTANCE!!
        }
    }

    private var connResource: ConnectivityResource? = null

    fun prepareConnectivityResource(context: Context): ConnectivityResource {
        if (connResource == null) {
            connResource = ConnectivityResource.getInstance(context)
        }
        return connResource!!
    }

}