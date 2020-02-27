package com.xuanfeng.weather;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.core.app.ActivityCompat;

import com.xuanfeng.mylibrary.mvp.BaseActivity;
import com.xuanfeng.mylibrary.mvp.BasePresenter;
import com.xuanfeng.weather.databinding.ActivityWelcomeBinding;


public class WelcomeActivity extends BaseActivity<BasePresenter, ActivityWelcomeBinding> {

    protected static final String[] PERMISSIONS = new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.CAMERA, Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_EXTERNAL_STORAGE};// 需要的权限
    private static final int REQUEST_CODE = 1;

    @Override//权限检查放在onresume里原因，从权限检查界面回来的时候不需要走result方法
    protected void onResume() {
        super.onResume();
        delayToNextActivity();
    }

    private void delayToNextActivity() {

        mBinding.countDownProgressBar.setOnCountDownFinishListener(this::goToMainActivity).startCountDown();
    }

    private void goToMainActivity() {
        ActivityCompat.requestPermissions(this, PERMISSIONS, REQUEST_CODE);

        Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_welcome;
    }

    @Override
    public BasePresenter initPresenter() {
        mBinding.setListener(this);
        return null;
    }


    @Override
    public void initData(Bundle bundle) {
        //do nothing
    }

    @Override
    public int getStatusBarColorResId() {
        return -1;
    }

    @Override
    public boolean isFullScreen() {
        return true;
    }


    public void onClick(View view) {
        mBinding.countDownProgressBar.removeOnCountDownFinishListener();
        goToMainActivity();
    }
}
