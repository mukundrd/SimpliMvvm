package com.trayis.simplimvvm.app.ui

import com.trayis.simplimvvm.ui.SimpliActivity
import com.trayis.simplimvvmannotation.SimpliInject
import com.trayis.simplimvvmannotation.SimpliViewComponent
import com.trayis.simplimvvm.app.R
import com.trayis.simplimvvm.app.databinding.ActivityMainBinding
import com.trayis.simplimvvm.app.ui.model.MainViewModel

@SimpliViewComponent
class MainActivity : SimpliActivity<ActivityMainBinding>() {

    @SimpliInject
    var viewModel: MainViewModel? = null

    override val layoutResourceId: Int
        get() = R.layout.activity_main

    override fun bindViewModels() {
        mBinding?.viewModel = viewModel
    }

}
