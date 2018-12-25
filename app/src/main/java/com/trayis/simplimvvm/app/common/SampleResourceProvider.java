package com.trayis.simplimvvm.app.common;

import android.content.Context;

import com.trayis.simplimvvmannotation.SimpliResourcesProvider;

@SimpliResourcesProvider
public class SampleResourceProvider {

    private static final SampleResourceProvider INSTANCE = new SampleResourceProvider();

    private ConnectivityResource connResource;

    public static SampleResourceProvider getInstance() {
        return INSTANCE;
    }

    public ConnectivityResource prepareConnectivityResource(Context context) {
        if (connResource == null) {
            connResource = ConnectivityResource.getInstance(context);
        }
        return connResource;
    }
}
