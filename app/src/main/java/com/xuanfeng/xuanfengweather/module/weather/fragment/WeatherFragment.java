package com.xuanfeng.xuanfengweather.module.weather.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.xuanfeng.mylibrary.utils.AnimUtil;
import com.xuanfeng.mylibrary.utils.StringUtils;
import com.xuanfeng.xuanfengweather.BR;
import com.xuanfeng.xuanfengweather.MainActivity;
import com.xuanfeng.xuanfengweather.R;
import com.xuanfeng.xuanfengweather.databinding.FragmentWeatherBinding;
import com.xuanfeng.xuanfengweather.module.weather.bean.WeatherBean;
import com.xuanfeng.xuanfengweather.module.weather.utils.WeatherUtil;
import com.xuanfeng.xuanfengweather.module.weather.view.WeatherView;
import com.xuanfeng.xuanfengweather.module.weather.viewmodel.WeatherViewModel;
import com.xuanfeng.xuanfengweather.mvvm.BaseFragment;
import com.xuanfeng.xuanfengweather.mvvm.RecyclerViewBaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 天气界面
 */
public class WeatherFragment extends BaseFragment<FragmentWeatherBinding> implements WeatherView {

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
        mLocationClient = WeatherUtil.getLocationClient(getContext(), mLocationListener);
        WeatherUtil.setTittleSizeAndColor(getContext(), mBinding.tvTodayTemperature);
    }


    private void setAdapter() {
        mForecastBeanList = new ArrayList<>();
        mRecyclerViewBaseAdapter = new RecyclerViewBaseAdapter(mContext, mForecastBeanList, R.layout.item_weather, BR.forecastBean);
        mBinding.rvBroadcast.setLayoutManager(new LinearLayoutManager(mContext));
        mBinding.rvBroadcast.setAdapter(mRecyclerViewBaseAdapter);
    }

    BDLocationListener mLocationListener = new BDLocationListener() {
        @Override//接受定位
        public void onReceiveLocation(BDLocation bdLocation) {
            if (bdLocation == null) {
                ((MainActivity) getActivity()).mTvLeft3.setText("定位失败！");
                return;
            }
            mCity = bdLocation.getCity();
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
            AnimUtil.runLayoutAnimation(mBinding.rvBroadcast, R.anim.layout_animation_from_right);
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
