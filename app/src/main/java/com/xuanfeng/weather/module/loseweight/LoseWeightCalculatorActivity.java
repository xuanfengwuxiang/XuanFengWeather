package com.xuanfeng.weather.module.loseweight;

import android.os.Bundle;
import android.transition.Explode;
import android.view.View;

import com.xuanfeng.mylibrary.mvp.BaseActivity;
import com.xuanfeng.mylibrary.mvp.BasePresenter;
import com.xuanfeng.weather.R;
import com.xuanfeng.weather.databinding.ActivityLoseWeightCalculatorBinding;

public class LoseWeightCalculatorActivity extends BaseActivity<BasePresenter, ActivityLoseWeightCalculatorBinding> {


    @Override
    public int getLayoutId() {
        return R.layout.activity_lose_weight_calculator;
    }

    @Override
    public BasePresenter initPresenter() {
        mBinding.setLoseWeightActivity(this);
        return null;
    }

    @Override
    public void initData(Bundle bundle) {
        mBinding.includeTittle.tvTittle.setText("减肥计算器");
    }

    @Override
    public int getStatusBarColorResId() {
        return 0;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setEnterTransition(new Explode());
        super.onCreate(savedInstanceState);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_left:
                finishAfterTransition();
                break;
            case R.id.bt_get_rate://获取燃脂心率
                LoseWeightUtil.getHeartRate(this, mBinding.etAge, mBinding.etStaticHeartRate, mBinding.tvBestHeartRate);
                break;
            case R.id.bt_get_rmr://获取REE
                LoseWeightUtil.getREE(this, mBinding.tvSex, mBinding.etAge, mBinding.etWeight, mBinding.etHeight, mBinding.tvRee);
                break;
            case R.id.bt_get_bmr://获取REE
                LoseWeightUtil.getBmr(this, mBinding.tvSex, mBinding.etAge, mBinding.etWeight, mBinding.etHeight, mBinding.tvBmr);
                break;
            case R.id.tv_sex://设置性别
                LoseWeightUtil.setSex(this, mBinding.tvSex);
                break;
        }
    }


}
