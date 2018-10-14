package com.trayis.simplimvvm.ui;

import androidx.annotation.LayoutRes;

public interface SimpliBase {

    @LayoutRes
    int getLayoutResourceId();

    void setViewModelsToView();

}
