package com.trayis.simplimvvm.app.common;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Build;

import com.trayis.simplimvvm.utils.SimpliResource;

import androidx.annotation.RequiresApi;

public class ConnectivityResource implements SimpliResource {

    private final Context context;

    private ConnectivityResource(Context context) {
        this.context = context;
    }

    public static ConnectivityResource getInstance(Context context) {
        return new ConnectivityResource(context.getApplicationContext());
    }

    @RequiresApi(Build.VERSION_CODES.M)
    public boolean isConnected() {
        return context.getSystemService(ConnectivityManager.class).getActiveNetwork() != null;
    }

}