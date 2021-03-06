package com.trayis.simplimvvm.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.trayis.simplimvvm.ui.SimpliBase.Companion.SELF
import com.trayis.simplimvvm.utils.SimpliProviderUtil
import com.trayis.simplimvvm.viewmodel.SimpliViewModel

abstract class SimpliFragment<B : ViewDataBinding>() : Fragment(), SimpliBase {

    protected val TAG = javaClass.simpleName

    protected var mViewModels: Array<SimpliViewModel?>? = null

    protected var mBinding: B? = null

    private var flag = SELF

    override fun setViewModelProviderFlag(flag: Int) {
        this.flag = flag
    }

    override fun getViewModelProviderFlag() = flag

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModels = SimpliProviderUtil.getProvider()?.getViewModels(this)
        setHasOptionsMenu(false)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val binding: B = DataBindingUtil.inflate(inflater, layoutResourceId, container, false)
        mBinding = binding
        return binding.getRoot()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViewModels()
        mBinding?.setLifecycleOwner(this)
        mViewModels?.forEach { it?.onCreate() }
        mBinding?.executePendingBindings()
    }

}