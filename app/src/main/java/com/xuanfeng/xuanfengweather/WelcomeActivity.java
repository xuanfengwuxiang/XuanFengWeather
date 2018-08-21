package com.xuanfeng.xuanfengweather;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;

import com.xuanfeng.mylibrary.mvp.BaseActivity;

public class WelcomeActivity extends BaseActivity {

    public static final String[] PERMISSIONS = new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.CAMERA};// 需要的权限


    @Override//权限检查放在onresume里原因，从权限检查界面回来的时候不需要走result方法
    protected void onResume() {
        super.onResume();
        delayToNextActivity();
    }

    private void delayToNextActivity() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                SystemClock.sleep(2000);
                Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }).start();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_welcome;
    }

    @Override
    public void initPresenter() {

    }


    @Override
    public void initData(Bundle bundle) {

    }

    @Override
    public int getStatusBarColorResId() {
        return -1;
    }

    @Override
    public boolean isFullScreen() {
        return true;
    }
}
