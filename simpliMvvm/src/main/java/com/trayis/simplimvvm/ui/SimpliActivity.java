package com.trayis.simplimvvm.ui;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.trayis.simplimvvm.utils.Logging;
import com.trayis.simplimvvm.utils.SimpliProviderUtil;
import com.trayis.simplimvvm.viewmodel.SimpliViewModel;

import java.util.InvalidPropertiesFormatException;

public abstract class SimpliActivity<B extends ViewDataBinding, V extends SimpliViewModel> extends AppCompatActivity implements Simpli {

    protected final String TAG = getClass().getSimpleName();

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
        int modelVariable = getModelVariable();
        if (modelVariable > 0) {
            mBinding.setVariable(modelVariable, mViewModel);
        }
        mBinding.executePendingBindings();
    }

    private V getViewModel() {
        if (mViewModel == null) {
            try {
                mViewModel = (V) SimpliProviderUtil.getInstance().getProvider().getViewModel(this);
            } catch (InvalidPropertiesFormatException e) {
                Logging.e(TAG, e.getMessage(), e);
            }
        }
        return mViewModel;
    }

}
