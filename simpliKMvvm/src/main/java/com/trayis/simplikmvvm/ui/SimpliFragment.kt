package com.trayis.simplikmvvm.ui

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.trayis.simplikmvvm.utils.Logging
import com.trayis.simplikmvvm.utils.SimpliProviderUtil
import com.trayis.simplikmvvm.viewmodel.SimpliViewModel
import java.util.*

abstract class SimpliFragment<B : ViewDataBinding, V : SimpliViewModel> : Fragment(), Simpli {

    protected val TAG = javaClass.simpleName

    protected var mViewModel: V? = null
    protected var mBinding: B? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel = getViewModel()
        setHasOptionsMenu(false)
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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val binding: B = DataBindingUtil.inflate(inflater, layoutResourceId, container, false)
        mBinding = binding
        return binding.getRoot()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding?.setLifecycleOwner(this)
        val modelVariable = modelVariable
        if (modelVariable > 0) {
            mBinding?.setVariable(modelVariable, mViewModel)
        }
    }

}