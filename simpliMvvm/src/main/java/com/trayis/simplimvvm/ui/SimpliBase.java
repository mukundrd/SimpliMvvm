package com.trayis.simplimvvm.ui;

import androidx.annotation.LayoutRes;

public interface SimpliBase {

    @LayoutRes
    int getLayoutResourceId();

    void setViewModelsToView();

    int getViewModelProviderFlag();

    @interface ViewModelProviderFlag {
        int SELF = 0;
        int PARENT = 1;
        int PARENT_FRAGMENT = 3;
    }

}
