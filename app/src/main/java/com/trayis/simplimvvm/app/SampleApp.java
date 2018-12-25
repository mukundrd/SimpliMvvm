package com.trayis.simplimvvm.app;

import android.app.Application;
import android.content.Context;

import com.trayis.simplimvvm.utils.SimpliProviderUtil;
import com.trayis.simplimvvm.utils.SimpliResource;
import com.trayis.simplimvvmannotation.generated.SimpliMvvmProviderImpl;

public class SampleApp extends Application implements SimpliResource {

    @Override
    public void onCreate() {
        super.onCreate();

        SimpliProviderUtil.setProvider(SimpliMvvmProviderImpl.getInstance(this));
    }

    public static SampleApp getInstance(Context context) {
        return (SampleApp) context.getApplicationContext();
    }

}
