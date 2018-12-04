package com.trayis.simplimvvm.app.ui;

import com.trayis.simplimvvm.app.R;
import com.trayis.simplimvvm.app.databinding.ActivityMainBinding;
import com.trayis.simplimvvm.app.databinding.ActivityNotSoMainBinding;
import com.trayis.simplimvvm.app.ui.model.NotSoMainViewModel;
import com.trayis.simplimvvm.ui.SimpliActivity;
import com.trayis.simplimvvmannotation.SimpliInject;
import com.trayis.simplimvvmannotation.SimpliViewComponent;

@SimpliViewComponent
public class NoSoMainActivity extends SimpliActivity<ActivityNotSoMainBinding> {

    @SimpliInject
    NotSoMainViewModel viewModel;

    @Override
    public int getLayoutResourceId() {
        return R.layout.activity_not_so_main;
    }

    @Override
    public void setViewModelsToView() {
        mBinding.setViewModel(viewModel);
    }
}
