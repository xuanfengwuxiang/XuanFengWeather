package com.xuanfeng.weather.module.test.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.xuanfeng.mylibrary.mvp.BaseActivity;
import com.xuanfeng.weather.R;

import butterknife.BindView;
import butterknife.OnClick;

public class TestShareAnimActivity extends BaseActivity {


    @BindView(R.id.tv_tittle)
    TextView tvTittle;
    @BindView(R.id.iv_share)
    ImageView ivShare;
    @BindView(R.id.tv_share)
    TextView tvShare;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_test_share_anim;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initData(Bundle bundle) {
        tvTittle.setText("share转场动画");
    }

    @Override
    public int getStatusBarColorResId() {
        return R.color.white;
    }


    @OnClick(R.id.iv_left)
    public void onViewClicked() {
        finishAfterTransition();
    }
}
