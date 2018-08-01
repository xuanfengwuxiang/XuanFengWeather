package com.xuanfeng.xuanfengweather.module.weather.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xuanfeng.xuanfengweather.R;
import com.xuanfeng.xuanfengweather.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SelectCityActivity extends BaseActivity {
    @BindView(R.id.iv_left_1)
    ImageView mIvLeft1;
    @BindView(R.id.iv_left_2)
    ImageView mIvLeft2;
    @BindView(R.id.tv_left_3)
    TextView mTvLeft3;
    @BindView(R.id.ly_left)
    LinearLayout mLyLeft;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.tv_right_1)
    TextView mTvRight1;
    @BindView(R.id.iv_right_2)
    ImageView mIvRight2;
    @BindView(R.id.ly_right)
    LinearLayout mLyRight;
    @BindView(R.id.rl_header)
    RelativeLayout mRlHeader;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_city);
        ButterKnife.bind(this);
        initViews();
        setListeners();
        initData();
    }

    @Override
    protected void initViews() {
        mTvTitle.setText("切换城市");
        mTvTitle.setTextColor(Color.BLACK);
    }

    @Override
    protected void setListeners() {
        mIvLeft1.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_left_1:
                finish();
                break;
        }
    }
}
