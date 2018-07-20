package com.trayis.simplikmvvm.utils

import com.trayis.simplikmvvm.ui.Simpli
import com.trayis.simplikmvvm.viewmodel.SimpliViewModel
import java.util.*

interface SimpliMvvmProvider<V : SimpliViewModel> {

    @Throws(InvalidPropertiesFormatException::class)
    fun getViewModel(simpli: Simpli): V

}