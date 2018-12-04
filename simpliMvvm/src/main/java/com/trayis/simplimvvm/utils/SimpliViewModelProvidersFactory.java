package com.trayis.simplimvvm.utils;

import java.util.HashMap;
import java.util.Map;

import androidx.lifecycle.ViewModelProvider;

public abstract class SimpliViewModelProvidersFactory implements ViewModelProvider.Factory {

    private Map<Class<? extends SimpliResource>, SimpliResource> resourcesMap = new HashMap<>();

    public void setResources(SimpliResource... resources) {
        if (resources != null) {
            for (SimpliResource resource : resources) {
                resourcesMap.put(resource.getClass(), resource);
            }
        }
    }

    @SuppressWarnings("unchecked")
    protected <T extends SimpliResource> T getResource(Class<T> resourceClass) {
        return (T) resourcesMap.get(resourceClass);
    }

}
