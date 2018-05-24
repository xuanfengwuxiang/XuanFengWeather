package com.xuanfeng.xuanfengweather.mvvm;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Window;
import android.view.WindowManager;

import com.xuanfeng.xuanfengweather.R;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseActivity<V extends ViewDataBinding> extends AppCompatActivity implements CreateMvvmInit{
    protected V mBinding;
    protected Unbinder mUnbinder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (isFullScreen()) {
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        mBinding =  DataBindingUtil.setContentView(this,getLayoutId());
        // 绑定ButterKnife
        mUnbinder = ButterKnife.bind(this);
        initViewModel();
        initListener();
        initData(getIntent().getExtras());
    }

    @Override
    public boolean isFullScreen() {
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }
}
