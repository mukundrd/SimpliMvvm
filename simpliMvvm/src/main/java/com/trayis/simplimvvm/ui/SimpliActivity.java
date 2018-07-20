package com.trayis.simplimvvm.ui;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.trayis.simplimvvm.utils.SimpliProviderUtil;
import com.trayis.simplimvvm.viewmodel.SimpliViewModel;

import java.util.InvalidPropertiesFormatException;

public abstract class SimpliActivity<B extends ViewDataBinding, V extends SimpliViewModel> extends AppCompatActivity implements Simpli {

    protected String TAG = getClass().getSimpleName();

    private B mBinding;

    private V mViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        doDataBinding();
    }

    private void doDataBinding() {
        mBinding = DataBindingUtil.setContentView(this, getLayoutResourceId());
        mViewModel = getViewModel();
        mBinding.setLifecycleOwner(this);
        mBinding.setVariable(getModelVariable(), mViewModel);
        mBinding.executePendingBindings();
    }

    private V getViewModel() {
        if (mViewModel == null) {
            try {
                mViewModel = (V) SimpliProviderUtil.getInstance().getProvider().getViewModel(this);
            } catch (InvalidPropertiesFormatException e) {
                e.printStackTrace();
            }
        }
        return mViewModel;
    }

    protected abstract int getModelVariable();

    @LayoutRes
    protected abstract int getLayoutResourceId();
}
