package com.trayis.simplimvvm.utils;

import com.trayis.simplimvvm.ui.SimpliBase;
import com.trayis.simplimvvm.ui.SimpliFragment;
import com.trayis.simplimvvm.viewmodel.SimpliViewModel;

import java.util.InvalidPropertiesFormatException;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import static com.trayis.simplimvvm.ui.SimpliBase.ViewModelProviderFlag.PARENT;
import static com.trayis.simplimvvm.ui.SimpliBase.ViewModelProviderFlag.PARENT_FRAGMENT;
import static com.trayis.simplimvvm.ui.SimpliBase.ViewModelProviderFlag.SELF;

/**
 * Created by mudesai on 10/9/17.
 */

public abstract class SimpliMvvmProvider {

    protected SimpliViewModelProvidersFactory factory;

    public abstract SimpliViewModel[] getViewModels(SimpliBase simpli) throws InvalidPropertiesFormatException;

    public final void setFactory(SimpliViewModelProvidersFactory factory) {
        this.factory = factory;
    }

    protected final ViewModelProvider getViewModelProviderFor(SimpliBase base) {

        switch (base.getViewModelProviderFlag()) {

            case SELF:
                if (base instanceof SimpliFragment) {
                    return ViewModelProviders.of((Fragment) base, factory);
                }
                return ViewModelProviders.of((FragmentActivity) base, factory);

            case PARENT:
                if (base instanceof SimpliFragment) {
                    return ViewModelProviders.of(((Fragment) base).getActivity(), factory);
                }
                return ViewModelProviders.of((FragmentActivity) base, factory);

            case PARENT_FRAGMENT:
                if (base instanceof SimpliFragment) {
                    Fragment fragment = (Fragment) base;
                    return ViewModelProviders.of(fragment.getParentFragment() == null ? fragment : fragment.getParentFragment(), factory);
                }
                throw new IllegalStateException("Cannot set PARENT_FAGMENT as ViewModelProviderFlag for Non-Fragment component");
        }

        throw new IllegalStateException("ViewModelProviderFlag for MVVM Component is out of scope, should be either (SELF, PARENT or PARENT_FRAGMENT).");
    }

    public SimpliViewModelProvidersFactory getFactory() {
        return factory;
    }
}
