package com.trayis.simplimvvm.ui;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.trayis.simplimvvm.utils.Logging;
import com.trayis.simplimvvm.utils.SimpliProviderUtil;
import com.trayis.simplimvvm.viewmodel.SimpliViewModel;

import java.util.InvalidPropertiesFormatException;

public abstract class SimpliActivity<B extends ViewDataBinding> extends AppCompatActivity implements SimpliBase {

    protected final String TAG = getClass().getSimpleName();

    protected B mBinding;

    protected SimpliViewModel[] mViewModels;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        doDataBinding();
    }

    private void doDataBinding() {
        mBinding = DataBindingUtil.setContentView(this, getLayoutResourceId());
        mViewModels = getViewModels();
        setViewModelsToView();
        mBinding.setLifecycleOwner(this);
        if (mViewModels != null) {
            for (SimpliViewModel viewModel : mViewModels) {
                viewModel.onCreate();
            }
        }
        mBinding.executePendingBindings();
    }

    @SuppressWarnings("unchecked")
    private SimpliViewModel[] getViewModels() {
        if (mViewModels == null) {
            try {
                mViewModels = SimpliProviderUtil.getInstance().getProvider().getViewModels(this);
            } catch (InvalidPropertiesFormatException e) {
                Logging.e(TAG, e.getMessage(), e);
            }
        }
        return mViewModels;
    }

}
