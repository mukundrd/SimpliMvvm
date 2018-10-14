package com.trayis.simplimvvm.utils;

import com.trayis.simplimvvm.ui.SimpliBase;
import com.trayis.simplimvvm.viewmodel.SimpliViewModel;

import java.util.InvalidPropertiesFormatException;

/**
 * Created by mudesai on 10/9/17.
 */

public interface SimpliMvvmProvider {

    SimpliViewModel[] getViewModels(SimpliBase simpli) throws InvalidPropertiesFormatException;

}
