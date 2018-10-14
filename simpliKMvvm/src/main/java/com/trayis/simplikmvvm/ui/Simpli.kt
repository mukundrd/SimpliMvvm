package com.trayis.simplikmvvm.ui

import androidx.annotation.LayoutRes

interface Simpli {

    @get:LayoutRes

    val layoutResourceId: Int

    val modelVariable: Int

}