package com.trayis.simplimvvm.ui;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.trayis.simplimvvm.viewmodel.SimpliViewModel;

public abstract class SimpliFragment<B extends ViewDataBinding, V extends SimpliViewModel> extends Fragment implements Simpli {

    protected final String TAG = getClass().getSimpleName();

    private V mViewModel;
    private B mBinding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = getViewModel();
        setHasOptionsMenu(false);
    }

    private V getViewModel() {
        return null;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, getLayoutResourceId(), container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mBinding.setLifecycleOwner(this);
        int modelVariable = getModelVariable();
        if (modelVariable > 0) {
            mBinding.setVariable(modelVariable, mViewModel);
        }
    }

}
