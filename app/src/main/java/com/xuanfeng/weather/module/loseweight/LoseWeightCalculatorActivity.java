package com.xuanfeng.weather.module.loseweight;

import android.os.Bundle;
import android.transition.Explode;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.xuanfeng.mylibrary.mvp.BaseActivity;
import com.xuanfeng.weather.R;

import butterknife.BindView;
import butterknife.OnClick;

public class LoseWeightCalculatorActivity extends BaseActivity {


    @BindView(R.id.tv_tittle)
    TextView tvTittle;
    @BindView(R.id.et_age)
    EditText etAge;
    @BindView(R.id.et_static_heart_rate)
    EditText etStaticHeartRate;
    @BindView(R.id.tv_best_heart_rate)
    TextView tvBestHeartRate;
    @BindView(R.id.et_weight)
    EditText etWeight;
    @BindView(R.id.et_height)
    EditText etHeight;
    @BindView(R.id.tv_ree)
    TextView tvRee;
    @BindView(R.id.tv_bmr)
    TextView tvBmr;
    @BindView(R.id.tv_sex)
    TextView tvSex;

    @Override
    public int getLayoutId() {
        return R.layout.activity_lose_weight_calculator;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initData(Bundle bundle) {
        tvTittle.setText("减肥计算器");
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

    @OnClick({R.id.iv_left, R.id.bt_get_rate, R.id.bt_get_rmr, R.id.bt_get_bmr, R.id.tv_sex})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_left:
                finishAfterTransition();
                break;
            case R.id.bt_get_rate://获取燃脂心率
                LoseWeightUtil.getHeartRate(this, etAge, etStaticHeartRate, tvBestHeartRate);
                break;
            case R.id.bt_get_rmr://获取REE
                LoseWeightUtil.getREE(this, tvSex, etAge, etWeight, etHeight, tvRee);
                break;
            case R.id.bt_get_bmr://获取REE
                LoseWeightUtil.getBmr(this, tvSex, etAge, etWeight, etHeight, tvBmr);
                break;
            case R.id.tv_sex://设置性别
                LoseWeightUtil.setSex(this, tvSex);
                break;
        }
    }


}
