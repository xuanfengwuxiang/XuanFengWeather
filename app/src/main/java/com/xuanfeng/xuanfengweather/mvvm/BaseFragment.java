package com.xuanfeng.xuanfengweather.mvvm;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xuanfeng.xuanfengweather.R;

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
