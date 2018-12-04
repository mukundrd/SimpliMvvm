package com.trayis.simplimvvm.app;

import android.app.Application;

import com.trayis.simplimvvm.utils.SimpliMvvmProvider;
import com.trayis.simplimvvm.utils.SimpliProviderUtil;
import com.trayis.simplimvvm.utils.SimpliResource;
import com.trayis.simplimvvmannotation.generated.SimpliMvvmProviderImpl;

public class SampleApp extends Application implements SimpliResource {

    @Override
    public void onCreate() {
        super.onCreate();

        SimpliMvvmProvider instance = SimpliMvvmProviderImpl.getInstance();
        instance.getFactory().setResources(this);
        SimpliProviderUtil.getInstance().setProvider(instance);
    }

}
