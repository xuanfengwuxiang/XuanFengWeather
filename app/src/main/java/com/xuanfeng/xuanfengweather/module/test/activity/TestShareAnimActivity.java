package com.xuanfeng.xuanfengweather.module.test.activity;

import android.app.ActivityOptions;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.transition.ChangeBounds;
import android.transition.ChangeTransform;
import android.transition.Fade;
import android.transition.TransitionSet;
import android.util.Pair;
import android.widget.ImageView;
import android.widget.TextView;

import com.xuanfeng.mylibrary.mvp.BaseActivity;
import com.xuanfeng.xuanfengweather.R;

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
