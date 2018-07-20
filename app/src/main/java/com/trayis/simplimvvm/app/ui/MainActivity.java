package com.trayis.simplimvvm.app.ui;

import com.trayis.simplimvvm.app.BR;
import com.trayis.simplimvvm.app.R;
import com.trayis.simplimvvm.app.databinding.ActivityMainBinding;
import com.trayis.simplimvvm.app.ui.model.MainViewModel;
import com.trayis.simplimvvm.ui.SimpliActivity;
import com.trayis.simplimvvmannotation.SimpliViewComponent;

@SimpliViewComponent
public class MainActivity extends SimpliActivity<ActivityMainBinding, MainViewModel> {

    @Override
    public int getModelVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutResourceId() {
        return R.layout.activity_main;
    }

}
