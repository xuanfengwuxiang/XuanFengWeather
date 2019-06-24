package com.xuanfeng.weather.mvvm;

import android.content.Context;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

//todo 等待完善
public abstract class BaseFragment<V extends ViewDataBinding> extends Fragment implements CreateMvvmInit{
    protected Context mContext;
    protected V mBinding;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext = getActivity();
        mBinding =  DataBindingUtil.inflate(LayoutInflater.from(mContext), getLayoutId(), container, false);
        initViewModel();
        initListener();
        initData(getArguments());
        return  mBinding.getRoot();
    }

    @Override
    public boolean isFullScreen() {
        return false;
    }

}
