package com.xuanfeng.weather.module.media.activity;

import android.os.Bundle;
import android.view.View;

import com.xuanfeng.mylibrary.mvp.BaseActivity;
import com.xuanfeng.mylibrary.mvp.BasePresenter;
import com.xuanfeng.weather.R;

public class SurfaceViewActivity extends BaseActivity implements View.OnClickListener {

    @Override
    public int getLayoutId() {
        return R.layout.activity_surface_view;
    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }


    @Override
    public void initData(Bundle bundle) {
        //do nothing

    }

    @Override
    public int getStatusBarColorResId() {
        return 0;
    }

    @Override
    public void onClick(View view) {
        //do nothing

    }

    @Override
    public boolean isFullScreen() {
        return true;
    }
}
