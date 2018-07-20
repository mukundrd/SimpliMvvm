package com.trayis.simplimvvm.utils;

/**
 * Created by mudesai on 10/9/17.
 */

public class SimpliProviderUtil {

    private static final String TAG = "SimpliProviderUtil";

    private static final SimpliProviderUtil INSTANCE = new SimpliProviderUtil();

    private SimpliMvvmProvider mProvider;

    private SimpliProviderUtil() {
    }

    public static SimpliProviderUtil getInstance() {
        return INSTANCE;
    }

    public void setProvider(SimpliMvvmProvider provider) {
        if (this.mProvider != null) {
            Logging.w(TAG, "provider already set");
            return;
        }
        this.mProvider = provider;
    }

    public SimpliMvvmProvider getProvider() {
        return mProvider;
    }
}
