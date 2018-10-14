package com.trayis.simplimvvm.kotlinapp.ui

import com.trayis.simplikmvvm.ui.SimpliActivity
import com.trayis.simplikmvvmannotation.SimpliViewComponent
import com.trayis.simplimvvm.kotlinapp.BR
import com.trayis.simplimvvm.kotlinapp.R
import com.trayis.simplimvvm.kotlinapp.databinding.ActivityMainBinding
import com.trayis.simplimvvm.kotlinapp.ui.model.MainViewModel

@SimpliViewComponent
class MainActivity : SimpliActivity<ActivityMainBinding, MainViewModel>() {

    override val layoutResourceId: Int
        get() = R.layout.activity_main

    override val modelVariable: Int
        get() = BR.viewModel

}
