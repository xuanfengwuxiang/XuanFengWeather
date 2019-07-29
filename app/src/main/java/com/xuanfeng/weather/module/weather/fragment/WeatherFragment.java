package com.xuanfeng.weather.module.weather.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.xuanfeng.mylibrary.mvp.BaseFragment;
import com.xuanfeng.mylibrary.mvp.BasePresenter;
import com.xuanfeng.mylibrary.utils.StringUtils;
import com.xuanfeng.weather.R;
import com.xuanfeng.weather.databinding.FragmentWeatherBinding;
import com.xuanfeng.weather.module.weather.activity.SelectCityActivity;
import com.xuanfeng.weather.module.weather.presenter.WeatherPresenter;
import com.xuanfeng.weather.module.weather.utils.WeatherUtil;
import com.xuanfeng.weather.module.weather.view.WeatherView;
import com.xuanfeng.weather.module.weather.widget.WeatherRecyclerView.WeatherBean.DataBean.ForecastBean;

import java.util.List;

import io.reactivex.functions.Consumer;

/**
 * 天气界面
 */
public class WeatherFragment extends BaseFragment<WeatherPresenter, FragmentWeatherBinding> implements WeatherView, BDLocationListener, Consumer {


    LocationClient mLocationClient;//百度定位
    private String mCity;//城市
    private double mLa;
    private double mLo;

    @Override
    public void initData(Bundle bundle) {
        WeatherUtil.setTittleBar(getContext(), mBinding.tvLeft, mBinding.ivLeft, mBinding.rlHeader);
        WeatherUtil.getLocationClient(getContext(), this, this);
        WeatherUtil.setTittleSizeAndColor(getContext(), mBinding.tvTodayTemperature);
    }

    @Override//百度定位初始化完成后回调
    public void accept(Object o) {
        if (o instanceof LocationClient) {
            mLocationClient = (LocationClient) o;
        }
    }


    @Override//接受定位
    public void onReceiveLocation(BDLocation bdLocation) {
        if (bdLocation == null) {
            mBinding.tvLeft.setText("定位失败！");
            return;
        }
        mCity = bdLocation.getCity();
        mLa = bdLocation.getLatitude();
        mLo = bdLocation.getLongitude();
        mBinding.tvLeft.setText(mCity == null ? "" : mCity);
        mPresenter.getWeather(getActivity(), mCity);

    }

    @Override
    public void onGetWeatherSuccess(List<ForecastBean> forecastBeanList) {
        if (forecastBeanList != null && !forecastBeanList.isEmpty()) {
            mBinding.rvBroadcast.setData(forecastBeanList);
        }
        WeatherUtil.doAnim(mBinding.rvBroadcast);
    }

    @Override
    public void onGetWeatherError(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onResume() {
        super.onResume();
        if (StringUtils.isEmpty(mCity) && mLocationClient != null && mLocationClient.isStarted()) {
            mLocationClient.requestLocation();
        }
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_left:
                Intent intent = new Intent(getContext(), SelectCityActivity.class);
                intent.putExtra(SelectCityActivity.LATITUDE, mLa);
                intent.putExtra(SelectCityActivity.LONGITUDE, mLo);
                startActivity(intent);
                break;
            case R.id.tv_today_temperature:
                Toast.makeText(getContext(), mBinding.tvTodayTemperature.getText().toString(), Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }


    @Override
    public int getLayoutId() {
        return R.layout.fragment_weather;
    }

    @Override
    public BasePresenter initPresenter() {
        WeatherPresenter presenter = new WeatherPresenter(this);
        getLifecycle().addObserver(presenter);
        mBinding.setListener(this);
        return presenter;
    }
}
