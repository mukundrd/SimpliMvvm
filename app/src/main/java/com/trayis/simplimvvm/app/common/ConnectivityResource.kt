package com.trayis.simplimvvm.app.common

import android.content.Context
import android.net.ConnectivityManager
import android.os.Build
import androidx.annotation.RequiresApi
import com.trayis.simplimvvm.utils.SimpliResource

/**
 * @author mudesai (Mukund Desai)
 * @created on 12/23/18
 */
class ConnectivityResource(val context: Context) : SimpliResource {

    @RequiresApi(Build.VERSION_CODES.M)
    fun isConnected(): Boolean {
        return context.getSystemService(ConnectivityManager::class.java).activeNetwork != null
    }

    companion object {
        fun getInstance(context: Context) = ConnectivityResource(context)
    }

}