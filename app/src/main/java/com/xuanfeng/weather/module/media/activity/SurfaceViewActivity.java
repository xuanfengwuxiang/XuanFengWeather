package com.xuanfeng.weather.module.media.activity;

import android.os.Bundle;
import android.view.View;

import com.xuanfeng.weather.R;
import com.xuanfeng.weather.mvvm.BaseActivity;

public class SurfaceViewActivity extends BaseActivity {

    @Override
    public int getLayoutId() {
        return R.layout.activity_surface_view;
    }

    @Override
    public void initViewModel() {
        //do nothing
    }

    @Override
    public void initListener() {
        //do nothing

    }

    @Override
    public void initData(Bundle bundle) {
        //do nothing

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
