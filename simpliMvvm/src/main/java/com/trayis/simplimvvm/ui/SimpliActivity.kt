package com.trayis.simplimvvm.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.trayis.simplimvvm.ui.SimpliBase.Companion.SELF
import com.trayis.simplimvvm.utils.Logging
import com.trayis.simplimvvm.utils.SimpliProviderUtil
import com.trayis.simplimvvm.viewmodel.SimpliViewModel
import java.util.*

abstract class SimpliActivity<B : ViewDataBinding> : AppCompatActivity(), SimpliBase {

    protected val TAG = javaClass.simpleName

    private var flag = SELF

    override fun setViewModelProviderFlag(flag: Int) {
        this.flag = flag
    }

    override fun getViewModelProviderFlag() = flag

    protected var mBinding: B? = null

    private var mViewModels: Array<SimpliViewModel?>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = DataBindingUtil.setContentView(this, layoutResourceId)
        mViewModels = getViewModels()
        bindViewModels()
        mBinding?.setLifecycleOwner(this)
        mViewModels?.forEach { it?.onCreate() }
        mBinding?.executePendingBindings()
    }

    private fun getViewModels(): Array<SimpliViewModel?>? {
        var viewModels: Array<SimpliViewModel?>? = null
        if (viewModels == null) {
            try {
                viewModels = SimpliProviderUtil.getProvider()?.getViewModels(this)
            } catch (e: InvalidPropertiesFormatException) {
                Logging.e(TAG, e.message, e)
            }

        }
        return viewModels
    }

}