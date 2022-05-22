package com.xuanfeng.weather.module.weather.fragment;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModel;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.xuanfeng.weather.R;
import com.xuanfeng.weather.databinding.FragmentWeatherBinding;
import com.xuanfeng.weather.module.weather.activity.SelectCityActivity;
import com.xuanfeng.weather.module.weather.presenter.WeatherPresenter;
import com.xuanfeng.weather.module.weather.utils.WeatherUtil;
import com.xuanfeng.weather.module.weather.view.WeatherView;
import com.xuanfeng.weather.module.weather.widget.WeatherRecyclerView.WeatherBean.DataBean.ForecastBean;
import com.xuanfeng.xflibrary.mvp.BaseFragment;
import com.xuanfeng.xflibrary.mvp.BasePresenter;
import com.xuanfeng.xflibrary.utils.PermissionUtil;
import com.xuanfeng.xflibrary.utils.ToastUtil;

import java.util.List;

import io.reactivex.functions.Consumer;

/**
 * 天气界面
 */
public class WeatherFragment extends BaseFragment<WeatherPresenter, ViewModel, FragmentWeatherBinding> implements WeatherView, BDLocationListener, Consumer {

    private final String TAG = "WeatherFragment";
    private final String[] PERMISSIONS = new String[]{Manifest.permission.ACCESS_FINE_LOCATION};
    private final int REQUEST_CODE = 1;

    private LocationClient mLocationClient;//百度定位
    private String mCity;//城市
    private double mLa;
    private double mLo;

    @Override
    public void initData(Bundle bundle) {
        WeatherUtil.setTittleBar(getContext(), mBinding.tvLeft, mBinding.ivLeft, mBinding.rlHeader);
        WeatherUtil.setTittleSizeAndColor(getContext(), mBinding.tvTodayTemperature);
        if (PermissionUtil.isGranted(getContext(), Manifest.permission.ACCESS_FINE_LOCATION))
            WeatherUtil.getLocationClient(getContext(), this, this);
        else
            requestPermissions(PERMISSIONS, REQUEST_CODE);
    }

    @Override//请求权限后的回调
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.i(TAG, "onRequestPermissionsResult...");
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE && PermissionUtil.isAllGranted(grantResults)) {
            WeatherUtil.getLocationClient(getContext(), this, this);
        }
    }

    @Override//百度定位初始化完成后回调
    public void accept(Object o) {
        if (o instanceof LocationClient) {
            mLocationClient = (LocationClient) o;
            mLocationClient.requestLocation();
        }
    }


    @Override//接受定位
    public void onReceiveLocation(BDLocation bdLocation) {
        Log.i(TAG, "city = " + (bdLocation == null ? "定位失败！" : bdLocation.getCity() + ""));
        if (bdLocation == null) {
            mBinding.tvLeft.setText("定位失败！");
            return;
        }
        mCity = bdLocation.getCity();
        mLa = bdLocation.getLatitude();
        mLo = bdLocation.getLongitude();
        mBinding.tvLeft.setText(mCity == null ? "" : mCity);
        mPresenter.getWeather(getActivity(), "南京");

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

    public void onClick(View view) {
        if (view.getId() == R.id.tv_left) {
            Intent intent = new Intent(getContext(), SelectCityActivity.class);
            intent.putExtra(SelectCityActivity.LATITUDE, mLa);
            intent.putExtra(SelectCityActivity.LONGITUDE, mLo);
            startActivity(intent);
        } else if (view.getId() == R.id.tv_today_temperature) {
            ToastUtil.showToast(getContext(), mBinding.tvTodayTemperature.getText().toString());
        } else if (view.getId() == R.id.iv_more) {
            ((DrawerLayout) getActivity().findViewById(R.id.drawLayout)).openDrawer(Gravity.RIGHT);
        }
    }


    @Override
    public int getLayoutId() {
        return R.layout.fragment_weather;
    }

    @Override
    public BasePresenter initPresenter() {
        WeatherPresenter presenter = new WeatherPresenter();
        getLifecycle().addObserver(presenter);
        mBinding.setListener(this);
        return presenter;
    }
}
