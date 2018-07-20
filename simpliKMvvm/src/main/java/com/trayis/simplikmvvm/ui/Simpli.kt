package com.trayis.simplikmvvm.ui

import android.support.annotation.LayoutRes

interface Simpli {

    @get:LayoutRes
    val layoutResourceId: Int

    val modelVariable: Int

}