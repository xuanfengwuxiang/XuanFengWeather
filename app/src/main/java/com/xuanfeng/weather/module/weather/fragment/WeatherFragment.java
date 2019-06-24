package com.xuanfeng.weather.module.weather.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.xuanfeng.mylibrary.mvp.BaseFragment;
import com.xuanfeng.mylibrary.utils.StringUtils;
import com.xuanfeng.weather.R;
import com.xuanfeng.weather.module.weather.activity.SelectCityActivity;
import com.xuanfeng.weather.module.weather.presenter.WeatherPresenter;
import com.xuanfeng.weather.module.weather.utils.WeatherUtil;
import com.xuanfeng.weather.module.weather.view.WeatherView;
import com.xuanfeng.weather.module.weather.widget.WeatherRecyclerView;
import com.xuanfeng.weather.module.weather.widget.WeatherRecyclerView.WeatherBean.DataBean.ForecastBean;
import com.xuanfeng.weather.widget.RotateImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;

/**
 * 天气界面
 */
public class WeatherFragment extends BaseFragment implements WeatherView, BDLocationListener, Consumer {

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
    WeatherRecyclerView mRvBroadcast;
    LocationClient mLocationClient;//百度定位
    private String mCity;//城市
    private WeatherPresenter mWeatherPresenter;
    private double mLa;
    private double mLo;

    @Override
    public void initData(Bundle bundle) {
        WeatherUtil.setTittleBar(getContext(), mTvLeft, mIvLeft, mRlHeader);
        WeatherUtil.getLocationClient(getContext(), this, this);
        WeatherUtil.setTittleSizeAndColor(getContext(), mTvTodayTemperature);
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
            mTvLeft.setText("定位失败！");
            return;
        }
        mCity = bdLocation.getCity();
        mLa = bdLocation.getLatitude();
        mLo = bdLocation.getLongitude();
        mTvLeft.setText(mCity == null ? "" : mCity);
        mWeatherPresenter.getWeather(getActivity(), mCity);

    }

    @Override
    public void onGetWeatherSuccess(List<ForecastBean> forecastBeanList) {
        if (forecastBeanList != null && forecastBeanList.size() > 0) {
            mRvBroadcast.setData(forecastBeanList);
        }
        WeatherUtil.doAnim(mRvBroadcast);
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

    @OnClick({R.id.tv_left, R.id.tv_today_temperature})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_left:
                Intent intent = new Intent(getContext(), SelectCityActivity.class);
                intent.putExtra(SelectCityActivity.LATITUDE, mLa);
                intent.putExtra(SelectCityActivity.LONGITUDE, mLo);
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
        mWeatherPresenter = new WeatherPresenter(this);
        getLifecycle().addObserver(mWeatherPresenter);
    }
}
