package com.trayis.simplikmvvm.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.trayis.simplikmvvm.utils.SimpliProviderUtil
import com.trayis.simplikmvvm.viewmodel.SimpliViewModel

abstract class SimpliFragment<B : ViewDataBinding> : Fragment(), SimpliBase {

    protected val TAG = javaClass.simpleName

    protected var mViewModels: Array<SimpliViewModel?>? = null

    protected var mBinding: B? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModels = SimpliProviderUtil.instance.provider?.getViewModels(this)
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