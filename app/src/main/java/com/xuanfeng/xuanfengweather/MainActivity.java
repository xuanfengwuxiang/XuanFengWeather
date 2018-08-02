package com.xuanfeng.xuanfengweather;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xuanfeng.mylibrary.utils.StatusBarUtil;
import com.xuanfeng.mylibrary.widget.NoScrollViewPager;
import com.xuanfeng.xuanfengweather.base.BaseActivity;
import com.xuanfeng.xuanfengweather.module.weather.activity.SelectCityActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {


    @BindView(R.id.iv_left)
    ImageView mIvLeft1;

    @BindView(R.id.tv_left)
    public TextView mTvLeft3;

    @BindView(R.id.tv_tittle)
    TextView mTvTitle;

    @BindView(R.id.rl_header)
    RelativeLayout mRlHeader;

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
        MainUtil.setCheckListener(mRbParent, this, mVpMain, mTvLeft3, mIvLeft1, mActivityMain, mRlHeader, mTvTitle, mRbOne, mRbTwo, mRbThree);
    }

    @Override
    protected void initData() {
        MainUtil.setViewPagerAdapter(this, mVpMain);
        mRbOne.setChecked(true);
        StatusBarUtil.setStatusBarColor(this, R.color.baseThemeColor, false);
    }


    @OnClick({R.id.tv_left, R.id.iv_left})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_left:
                Intent intent = new Intent(this, SelectCityActivity.class);
                startActivity(intent);
                break;
            case R.id.iv_left:
                MainUtil.doLeftClick(mVpMain);
                break;
        }
    }


    @Override
    public void onBackPressed() {
        MainUtil.exitApp(this);
    }
}
