package com.trayis.simplikmvvm.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.trayis.simplikmvvm.utils.Logging
import com.trayis.simplikmvvm.utils.SimpliProviderUtil
import com.trayis.simplikmvvm.viewmodel.SimpliViewModel
import java.util.*

abstract class SimpliActivity<B : ViewDataBinding, V : SimpliViewModel> : AppCompatActivity(), Simpli {

    protected val TAG = javaClass.simpleName

    protected var mBinding: B? = null

    protected var mViewModel: V? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        doDataBinding()
    }

    private fun doDataBinding() {
        val binding: B = DataBindingUtil.setContentView(this, layoutResourceId)
        mViewModel = getViewModel()
        binding.setLifecycleOwner(this)
        val modelVariable = modelVariable
        if (modelVariable > 0) {
            binding.setVariable(modelVariable, mViewModel)
        }
        binding.executePendingBindings()
        mViewModel?.onCreate()
        mBinding = binding
    }

    private fun getViewModel(): V? {
        if (mViewModel == null) {
            try {
                @Suppress("UNCHECKED_CAST")
                mViewModel = SimpliProviderUtil.instance.provider?.getViewModel(this) as V
            } catch (e: InvalidPropertiesFormatException) {
                Logging.e(TAG, e.message, e)
            }

        }
        return mViewModel
    }

}