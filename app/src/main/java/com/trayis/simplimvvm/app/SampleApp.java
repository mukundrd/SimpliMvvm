package com.trayis.simplimvvm.app;

import com.trayis.simplimvvm.SimpliMvvmApp;
import com.trayis.simplimvvm.utils.SimpliMvvmProvider;
import com.trayis.simplimvvmannotation.generated.SimpliMvvmProviderImpl;

public class SampleApp extends SimpliMvvmApp {

    @Override
    protected SimpliMvvmProvider getSimpliMvvmProvider() {
        return SimpliMvvmProviderImpl.getInstance();
    }

}
