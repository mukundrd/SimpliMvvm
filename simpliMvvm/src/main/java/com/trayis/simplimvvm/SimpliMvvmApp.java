package com.trayis.simplimvvm;

import android.app.Application;

import com.trayis.simplimvvm.utils.Logging;
import com.trayis.simplimvvm.utils.SimpliMvvmProvider;
import com.trayis.simplimvvm.utils.SimpliProviderUtil;

public abstract class SimpliMvvmApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        initLogger();

        SimpliProviderUtil.setProvider(getSimpliMvvmProvider());
    }

    protected void initLogger() {
        Logging.initLogger();
    }

    protected abstract SimpliMvvmProvider getSimpliMvvmProvider();

}
