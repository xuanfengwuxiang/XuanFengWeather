package com.xuanfeng.xuanfengweather.module.weather.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.xuanfeng.mylibrary.mvp.BaseFragment;
import com.xuanfeng.mylibrary.utils.AnimUtil;
import com.xuanfeng.mylibrary.utils.StringUtils;
import com.xuanfeng.xuanfengweather.R;
import com.xuanfeng.xuanfengweather.module.weather.activity.SelectCityActivity;
import com.xuanfeng.xuanfengweather.module.weather.adapter.WeatherAdapter;
import com.xuanfeng.xuanfengweather.module.weather.bean.WeatherBean;
import com.xuanfeng.xuanfengweather.module.weather.presenter.WeatherViewModel;
import com.xuanfeng.xuanfengweather.module.weather.utils.WeatherUtil;
import com.xuanfeng.xuanfengweather.module.weather.view.WeatherView;
import com.xuanfeng.xuanfengweather.widget.RotateImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 天气界面
 */
public class WeatherFragment extends BaseFragment implements WeatherView {

    @BindView(R.id.tv_left)
    TextView mTvLeft;
    @BindView(R.id.rl_header)
    RelativeLayout mRlHeader;
    @BindView(R.id.iv_left)
    ImageView mIvLeft;
    @BindView(R.id.tv_tittle)
    TextView mTvTittle;
    @BindView(R.id.riv_rotate)
    RotateImageView mRivRotate;
    @BindView(R.id.tv_today_temperature)
    TextView mTvTodayTemperature;
    @BindView(R.id.rv_broadcast)
    RecyclerView mRvBroadcast;
    private List<WeatherBean.DataBean.ForecastBean> mForecastBeanList;//预报的集合
    LocationClient mLocationClient;//百度定位
    private String mCity;//城市
    private WeatherAdapter mRecyclerViewBaseAdapter;//适配器
    private WeatherViewModel mWeatherViewModel;

    private void setAdapter() {
        mForecastBeanList = new ArrayList<>();
        mRecyclerViewBaseAdapter = new WeatherAdapter(getContext(), mForecastBeanList);
        mRvBroadcast.setLayoutManager(new LinearLayoutManager(getContext()));
        mRvBroadcast.setAdapter(mRecyclerViewBaseAdapter);
    }

    BDLocationListener mLocationListener = new BDLocationListener() {
        @Override//接受定位
        public void onReceiveLocation(BDLocation bdLocation) {
            if (bdLocation == null) {
                mTvLeft.setText("定位失败！");
                return;
            }
            mCity = bdLocation.getCity();
            mTvLeft.setText(mCity == null ? "" : mCity);
            getWeather(mCity);
        }
    };


    //获取天气
    private void getWeather(String city) {
        mWeatherViewModel.getWeather(city);
    }

    @Override
    public void onGetWeatherSuccess(List<WeatherBean.DataBean.ForecastBean> forecastBeanList) {
        mForecastBeanList.clear();
        mForecastBeanList.addAll(forecastBeanList);
        if (mForecastBeanList != null && mForecastBeanList.size() > 0) {
            mRecyclerViewBaseAdapter.notifyDataSetChanged();
            AnimUtil.runLayoutAnimation(mRvBroadcast, R.anim.layout_animation_from_right);
        }
    }

    @Override
    public void onGetWeatherError(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onResume() {
        super.onResume();
        if (mForecastBeanList == null || mForecastBeanList.isEmpty()) {
            if (!StringUtils.isEmpty(mCity)) {
                getWeather(mCity);
            } else {
                mLocationClient.requestLocation();
            }
        }
    }

    @OnClick({R.id.tv_left, R.id.tv_today_temperature})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_left:
                Intent intent = new Intent(getContext(), SelectCityActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_today_temperature:
                Toast.makeText(getContext(), mTvTodayTemperature.getText().toString(), Toast.LENGTH_SHORT).show();
                break;
        }
    }


    @Override
    public int getLayoutId() {
        return R.layout.fragment_weather;
    }

    @Override
    public void initPresenter() {
        mWeatherViewModel = new WeatherViewModel(getContext(), this);
    }

    @Override
    public void initData(Bundle bundle) {
        WeatherUtil.setTittleBar(getContext(), mTvLeft, mIvLeft, mRlHeader);
        setAdapter();
        mLocationClient = WeatherUtil.getLocationClient(getContext(), mLocationListener);
        WeatherUtil.setTittleSizeAndColor(getContext(), mTvTodayTemperature);
    }
}
