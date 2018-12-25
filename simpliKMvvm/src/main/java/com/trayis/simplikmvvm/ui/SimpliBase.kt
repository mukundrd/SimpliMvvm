package com.trayis.simplikmvvm.ui

import androidx.annotation.LayoutRes

interface SimpliBase {

    fun bindViewModels()

    @get:LayoutRes

    val layoutResourceId: Int

    fun setViewModelProviderFlag(flag: Int)

    fun getViewModelProviderFlag(): Int

    companion object {
        val SELF = 0
        val PARENT = 1
        val PARENT_FRAGMENT = 3
    }

}