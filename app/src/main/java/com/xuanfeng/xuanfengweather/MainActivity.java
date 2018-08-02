package com.xuanfeng.xuanfengweather;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.xuanfeng.mylibrary.utils.StatusBarUtil;
import com.xuanfeng.mylibrary.widget.NoScrollViewPager;
import com.xuanfeng.xuanfengweather.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initViews();
        setListeners();
        initData();
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void setListeners() {
        MainUtil.setCheckListener(mRbParent, this, mVpMain, mActivityMain);
    }

    @Override
    protected void initData() {
        MainUtil.setViewPagerAdapter(this, mVpMain);
        mRbOne.setChecked(true);
        StatusBarUtil.setStatusBarColor(this, R.color.baseThemeColor, false);
    }


    @Override
    public void onBackPressed() {
        MainUtil.exitApp(this);
    }
}
