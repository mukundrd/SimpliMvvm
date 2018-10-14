package com.trayis.simplikmvvm.utils

import com.trayis.simplikmvvm.ui.SimpliBase
import com.trayis.simplikmvvm.viewmodel.SimpliViewModel
import java.util.*

interface SimpliMvvmProvider {

    @Throws(InvalidPropertiesFormatException::class)
    fun getViewModels(base: SimpliBase): Array<SimpliViewModel?>?

}