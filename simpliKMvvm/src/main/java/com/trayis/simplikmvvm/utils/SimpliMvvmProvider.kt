package com.trayis.simplikmvvm.utils

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.trayis.simplikmvvm.ui.SimpliBase
import com.trayis.simplikmvvm.ui.SimpliBase.Companion.PARENT
import com.trayis.simplikmvvm.ui.SimpliBase.Companion.PARENT_FRAGMENT
import com.trayis.simplikmvvm.ui.SimpliBase.Companion.SELF
import com.trayis.simplikmvvm.ui.SimpliFragment
import com.trayis.simplikmvvm.viewmodel.SimpliViewModel
import java.util.*

abstract class SimpliMvvmProvider {

    protected var factory: SimpliViewModelProvidersFactory? = null

    protected var context: Context? = null

    @Throws(InvalidPropertiesFormatException::class)
    abstract fun getViewModels(simpli: SimpliBase): Array<SimpliViewModel?>?

    protected fun getViewModelProviderFor(base: SimpliBase): ViewModelProvider? {

        when (base.getViewModelProviderFlag()) {

            SELF -> {
                return if (base is SimpliFragment<*>) {
                    ViewModelProviders.of(base as Fragment, factory)
                } else ViewModelProviders.of(base as FragmentActivity, factory)
            }

            PARENT -> {
                return if (base is SimpliFragment<*>) {
                    (base as Fragment).getActivity()?.let { ViewModelProviders.of(it, factory) }
                } else ViewModelProviders.of(base as FragmentActivity, factory)
            }

            PARENT_FRAGMENT -> {
                if (base is SimpliFragment<*>) {
                    val fragment = base as Fragment
                    fragment.getParentFragment()?.let {
                        ViewModelProviders.of(it, factory)
                    } ?: ViewModelProviders.of(fragment, factory)
                }
                throw IllegalStateException("Cannot set PARENT_FAGMENT as ViewModelProviderFlag for Non-Fragment component")
            }
        }

        throw IllegalStateException("ViewModelProviderFlag for MVVM Component is out of scope, should be either (SELF, PARENT or PARENT_FRAGMENT).")
    }

}