package com.trayis.simplikmvvm.utils

import android.content.Context
import androidx.lifecycle.ViewModelProvider

/**
 * @author mudesai (Mukund Desai)
 * @created on 12/21/18
 */
abstract class SimpliViewModelProvidersFactory : ViewModelProvider.Factory {

    var context: Context? = null

    val resourcesMap = HashMap<Class<SimpliResource>, SimpliResource>()

    fun putResource(resource: SimpliResource) {
        resourcesMap.put(resource.javaClass, resource)
    }

    fun <T : SimpliResource> getResource(resourceClass: Class<in T>) = resourcesMap.get(resourceClass)

}