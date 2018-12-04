package com.trayis.simplikmvvm.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.trayis.simplikmvvm.utils.SimpliProviderUtil
import com.trayis.simplikmvvm.viewmodel.SimpliViewModel

abstract class SimpliActivity<B : ViewDataBinding> : AppCompatActivity(), SimpliBase {

    protected val TAG = javaClass.simpleName

    protected var mBinding: B? = null

    private var mViewModels: Array<SimpliViewModel?>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: B = DataBindingUtil.setContentView(this, layoutResourceId)
        mViewModels = SimpliProviderUtil.instance.provider?.getViewModels(this)
        bindViewModels()
        binding.setLifecycleOwner(this)
        binding.executePendingBindings()
        mViewModels?.forEach { it?.onCreate() }
        mBinding = binding
    }

}