package com.trayis.simplikmvvm.ui

import androidx.annotation.LayoutRes

interface SimpliBase {

    fun bindViewModels()

    @get:LayoutRes

    val layoutResourceId: Int

}