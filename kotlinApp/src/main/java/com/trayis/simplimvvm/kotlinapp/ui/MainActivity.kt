package com.trayis.simplimvvm.kotlinapp.ui

import com.trayis.simplikmvvm.ui.SimpliActivity
import com.trayis.simplikmvvmannotation.SimpliInject
import com.trayis.simplikmvvmannotation.SimpliViewComponent
import com.trayis.simplimvvm.kotlinapp.R
import com.trayis.simplimvvm.kotlinapp.databinding.ActivityMainBinding
import com.trayis.simplimvvm.kotlinapp.ui.model.MainViewModel

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
