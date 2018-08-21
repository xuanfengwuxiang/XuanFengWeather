package com.xuanfeng.xuanfengweather.module.weather.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiResult;
import com.xuanfeng.mylibrary.mvp.BaseActivity;
import com.xuanfeng.xuanfengweather.R;
import com.xuanfeng.xuanfengweather.module.weather.utils.WeatherUtil;

import butterknife.BindView;
import butterknife.OnClick;

public class SelectCityActivity extends BaseActivity {
    @BindView(R.id.iv_left)
    ImageView mIvLeft;

    @BindView(R.id.tv_left)
    TextView mTvLeft;

    @BindView(R.id.tv_tittle)
    TextView mTvTitle;

    @BindView(R.id.rl_header)
    RelativeLayout mRlHeader;

    @BindView(R.id.et_input)
    EditText mEtInput;

    @BindView(R.id.mapView)
    MapView mMapView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SDKInitializer.initialize(getApplicationContext());
        super.onCreate(savedInstanceState);
    }


    @OnClick({R.id.tv_search, R.id.iv_left})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_left://退出
                finish();
                break;
            case R.id.tv_search://搜索
                WeatherUtil.searchFromBaidu(this, mEtInput, poiListener);
                break;
        }
    }


    OnGetPoiSearchResultListener poiListener = new OnGetPoiSearchResultListener() {

        public void onGetPoiResult(PoiResult result) {
            WeatherUtil.updateMapPosition(result, mMapView);
        }

        public void onGetPoiDetailResult(PoiDetailResult result) {

        }

        @Override
        public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {

        }
    };


    @Override
    public void onDestroy() {
        if (mMapView != null) {
            mMapView.onDestroy();
        }
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_select_city;
    }

    @Override
    public void initPresenter() {

    }


    @Override
    public void initData(Bundle bundle) {
        mTvTitle.setText("切换城市");
    }

    @Override
    public int getStatusBarColorResId() {
        return R.color.baseThemeColor;
    }


}
