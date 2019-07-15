package com.xuanfeng.weather.module.weather.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiResult;
import com.xuanfeng.mylibrary.mvp.BaseActivity;
import com.xuanfeng.mylibrary.mvp.BasePresenter;
import com.xuanfeng.weather.R;
import com.xuanfeng.weather.databinding.ActivitySelectCityBinding;
import com.xuanfeng.weather.module.weather.utils.WeatherUtil;

import butterknife.BindView;
import butterknife.OnClick;

public class SelectCityActivity extends BaseActivity<BasePresenter, ActivitySelectCityBinding> {

    public static final String LONGITUDE = "longitude";
    public static final String LATITUDE = "latitude";

    @BindView(R.id.tv_tittle)
    TextView mTvTitle;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SDKInitializer.initialize(getApplicationContext());
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initData(Bundle bundle) {
        mTvTitle.setText("切换城市");
        Intent intent = getIntent();
        if (intent != null) {
            double lontitude = intent.getDoubleExtra(LONGITUDE, 0);
            double latitude = intent.getDoubleExtra(LATITUDE, 0);
            WeatherUtil.updateMapPosition(latitude, lontitude, mBinding.mapView);
        }
    }


    @OnClick({R.id.tv_search, R.id.iv_left})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_left://退出
                finish();
                break;
            case R.id.tv_search://搜索
                WeatherUtil.searchFromBaidu(this, mBinding.etInput, poiListener);
                break;
        }
    }


    OnGetPoiSearchResultListener poiListener = new OnGetPoiSearchResultListener() {

        public void onGetPoiResult(PoiResult result) {
            WeatherUtil.updateMapPosition(result, mBinding.mapView);
        }

        public void onGetPoiDetailResult(PoiDetailResult result) {

        }

        @Override
        public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {

        }
    };


    @Override
    public void onDestroy() {
        if (mBinding.mapView != null) {
            mBinding.mapView.onDestroy();
        }
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mBinding.mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mBinding.mapView.onPause();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_select_city;
    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @Override
    public int getStatusBarColorResId() {
        return R.color.baseThemeColor;
    }


}
