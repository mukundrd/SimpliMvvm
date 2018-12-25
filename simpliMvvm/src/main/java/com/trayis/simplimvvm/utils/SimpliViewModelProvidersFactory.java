package com.trayis.simplimvvm.utils;

import android.content.Context;

import java.util.HashMap;
import java.util.Map;

import androidx.lifecycle.ViewModelProvider;

public abstract class SimpliViewModelProvidersFactory implements ViewModelProvider.Factory {

    private Map<Class<? extends SimpliResource>, SimpliResource> resourcesMap = new HashMap<>();

    protected Context context;

    protected void putResource(SimpliResource resource) {
        resourcesMap.put(resource.getClass(), resource);
    }

    @SuppressWarnings("unchecked")
    protected <T extends SimpliResource> T getResource(Class<T> resourceClass) {
        return (T) resourcesMap.get(resourceClass);
    }

}
