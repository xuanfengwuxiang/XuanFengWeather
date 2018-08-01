package com.xuanfeng.xuanfengweather.module.weather.fragment;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.view.View;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.xuanfeng.mylibrary.utils.AnimUtil;
import com.xuanfeng.xuanfengweather.BR;
import com.xuanfeng.xuanfengweather.MainActivity;
import com.xuanfeng.xuanfengweather.R;
import com.xuanfeng.xuanfengweather.databinding.FragmentWeatherBinding;
import com.xuanfeng.xuanfengweather.module.weather.bean.WeatherBean;
import com.xuanfeng.xuanfengweather.module.weather.view.WeatherView;
import com.xuanfeng.xuanfengweather.module.weather.viewmodel.WeatherViewModel;
import com.xuanfeng.xuanfengweather.mvvm.BaseFragment;
import com.xuanfeng.xuanfengweather.mvvm.RecyclerViewBaseAdapter;
import com.xuanfeng.mylibrary.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 天气界面
 */
public class WeatherFragment extends BaseFragment<FragmentWeatherBinding> implements BDLocationListener, WeatherView {

    private List<WeatherBean.DataBean.ForecastBean> mForecastBeanList;//预报的集合
    LocationClient mLocationClient;//百度定位
    private String mCity;//城市
    private RecyclerViewBaseAdapter mRecyclerViewBaseAdapter;//适配器
    private WeatherViewModel mWeatherViewModel;

    @Override//初始化布局
    public int getLayoutId() {
        return R.layout.fragment_weather;
    }

    @Override//初始化控制器
    public void initViewModel() {
        mWeatherViewModel = new WeatherViewModel(mContext, this);
    }

    @Override
    public void initListener() {
        mBinding.setListener(this);
    }

    @Override//初始化数据
    public void initData(Bundle bundle) {
        setAdapter();
        initLocation();
        setTittleSizeAndColor();
    }

    //设置一个textview内的每个字大小颜色
    private void setTittleSizeAndColor() {
        String text = "最近5天的天气预报表情";
        SpannableString spannableString = new SpannableString(text);
        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#FF0000")), 1, 2, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#FF6600")), 2, 3, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        spannableString.setSpan(new AbsoluteSizeSpan(100), 2, 3, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#FFff00")), 3, 4, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#00ff00")), 4, 5, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#FF6600")), 5, 6, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#33FF33")), 6, 7, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        spannableString.setSpan(new AbsoluteSizeSpan(100), 6, 7, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        Drawable drawable = getResources().getDrawable(R.drawable.ic_cool);
        drawable.setBounds(0, 0, 100, 100);
        ImageSpan imageSpan = new ImageSpan(drawable);
        spannableString.setSpan(imageSpan, text.length() - 2, text.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        mBinding.tvTodayTemperature.setText(spannableString);
    }

    //获取百度定位
    private void initLocation() {
        mLocationClient = new LocationClient(mContext.getApplicationContext());
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        //可选，默认高精度，设置定位模式，高精度，低功耗，仅设备

        option.setCoorType("bd09ll");
        //可选，默认gcj02，设置返回的定位结果坐标系

        int span = 0;
        option.setScanSpan(span);
        //可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的

        option.setIsNeedAddress(true);
        //可选，设置是否需要地址信息，默认不需要

        option.setAddrType("all");

        option.setOpenGps(true);
        //可选，默认false,设置是否使用gps

        option.setLocationNotify(true);
        //可选，默认false，设置是否当GPS有效时按照1S/1次频率输出GPS结果

        option.setIsNeedLocationDescribe(true);
        //可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”

        option.setIsNeedLocationPoiList(true);
        //可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到

        option.setIgnoreKillProcess(false);
        //可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死

        option.SetIgnoreCacheException(false);
        //可选，默认false，设置是否收集CRASH信息，默认收集

        option.setEnableSimulateGps(false);
        //可选，默认false，设置是否需要过滤GPS仿真结果，默认需要

        mLocationClient.setLocOption(option);
        mLocationClient.registerLocationListener(this);
        mLocationClient.start();
    }

    private void setAdapter() {
        mForecastBeanList = new ArrayList<>();
        mRecyclerViewBaseAdapter = new RecyclerViewBaseAdapter(mContext, mForecastBeanList, R.layout.item_weather, BR.forecastBean);
        mBinding.rvBroadcast.setLayoutManager(new LinearLayoutManager(mContext));
        mBinding.rvBroadcast.setAdapter(mRecyclerViewBaseAdapter);
    }

    @Override//接受定位
    public void onReceiveLocation(BDLocation bdLocation) {
        if (bdLocation == null) {
            ((MainActivity) getActivity()).mTvLeft3.setText("定位失败！");
            return;
        }
        mCity = bdLocation.getCity();
        getWeather(mCity);
    }

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
            AnimUtil.runLayoutAnimation(mBinding.rvBroadcast,R.anim.layout_animation_from_right);
        }
    }

    @Override
    public void onGetWeatherError(String msg) {
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
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

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_today_temperature:
                Toast.makeText(mContext, mBinding.tvTodayTemperature.getText().toString(), Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
