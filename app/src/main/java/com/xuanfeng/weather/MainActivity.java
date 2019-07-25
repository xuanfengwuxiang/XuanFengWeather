package com.xuanfeng.weather;

import android.os.Bundle;

import com.xuanfeng.mylibrary.mvp.BaseActivity;
import com.xuanfeng.mylibrary.mvp.BasePresenter;
import com.xuanfeng.mylibrary.utils.StatusBarUtil;
import com.xuanfeng.weather.databinding.ActivityMainBinding;

public class MainActivity extends BaseActivity<BasePresenter, ActivityMainBinding> {


    @Override
    public void onBackPressed() {
        MainUtil.exitApp(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @Override
    public void initData(Bundle bundle) {
        MainUtil.setCheckListener(mBinding.rbParent, this, mBinding.vpMain, mBinding.activityMain);
        MainUtil.setViewPagerAdapter(this, mBinding.vpMain);
        mBinding.rbOne.setChecked(true);
        StatusBarUtil.setStatusBarColor(this, R.color.baseThemeColor, false);
    }

    @Override
    public int getStatusBarColorResId() {
        return -1;
    }
}
