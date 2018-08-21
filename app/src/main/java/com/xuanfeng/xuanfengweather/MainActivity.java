package com.xuanfeng.xuanfengweather;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.xuanfeng.mylibrary.mvp.BaseActivity;
import com.xuanfeng.mylibrary.utils.StatusBarUtil;
import com.xuanfeng.mylibrary.widget.NoScrollViewPager;

import butterknife.BindView;

public class MainActivity extends BaseActivity {


    @BindView(R.id.vp_main)
    NoScrollViewPager mVpMain;

    @BindView(R.id.rb_one)
    RadioButton mRbOne;

    @BindView(R.id.rb_two)
    RadioButton mRbTwo;

    @BindView(R.id.rb_three)
    RadioButton mRbThree;

    @BindView(R.id.activity_main)
    LinearLayout mActivityMain;

    @BindView(R.id.rb_parent)
    RadioGroup mRbParent;


    @Override
    public void onBackPressed() {
        MainUtil.exitApp(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initData(Bundle bundle) {
        MainUtil.setCheckListener(mRbParent, this, mVpMain, mActivityMain);
        MainUtil.setViewPagerAdapter(this, mVpMain);
        mRbOne.setChecked(true);
        StatusBarUtil.setStatusBarColor(this, R.color.baseThemeColor, false);
    }

    @Override
    public int getStatusBarColorResId() {
        return -1;
    }
}
