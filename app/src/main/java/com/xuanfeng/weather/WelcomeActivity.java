package com.xuanfeng.weather;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;

import com.xuanfeng.countdownprogressview.CountDownProgressBar;
import com.xuanfeng.mylibrary.mvp.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WelcomeActivity extends BaseActivity {

    public static final String[] PERMISSIONS = new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.CAMERA};// 需要的权限
    @BindView(R.id.countDownProgressBar)
    CountDownProgressBar countDownProgressBar;


    @Override//权限检查放在onresume里原因，从权限检查界面回来的时候不需要走result方法
    protected void onResume() {
        super.onResume();
        delayToNextActivity();
    }

    private void delayToNextActivity() {

        countDownProgressBar.setOnCountDownFinishListener(new CountDownProgressBar.OnCountDownFinishListener() {
            @Override
            public void countDownFinished() {
                goToMainActivity();
            }
        }).startCountDown();
    }

    private void goToMainActivity() {
        Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.countDownProgressBar)
    public void onViewClicked() {
        countDownProgressBar.removeOnCountDownFinishListener();
        goToMainActivity();
    }
}