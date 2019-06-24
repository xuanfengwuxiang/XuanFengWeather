package com.xuanfeng.weather.module.weather.presenter;

import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.annotation.NonNull;
import android.util.Log;

import com.google.gson.JsonObject;
import com.xuanfeng.mylibrary.http.HttpResponse;
import com.xuanfeng.mylibrary.http.httpMgr.HttpManager;
import com.xuanfeng.mylibrary.utils.StringUtils;
import com.xuanfeng.weather.constant.HttpConstant;
import com.xuanfeng.weather.module.weather.view.WeatherView;
import com.xuanfeng.weather.module.weather.widget.WeatherRecyclerView.WeatherBean;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by xuanfengwuxiang on 2017/12/13.
 */

public class WeatherPresenter implements DefaultLifecycleObserver {

    private WeatherView mWeatherView;

    public WeatherPresenter(WeatherView weatherView) {
        mWeatherView = weatherView;
    }


    public void getWeather(LifecycleOwner lifecycleOwner, String city) {
        LinkedHashMap<String, String> params = new LinkedHashMap<>();
        params.put("city", city);
        mWeatherView.showProgress();
        HttpManager.getInstance().getJsonObjectByGet(lifecycleOwner, HttpConstant.WEATHER_URL, params, new HttpResponse<JsonObject>() {
            @Override
            public void onSuccess(JsonObject jsonObject) {
                mWeatherView.hideProgress();
                if (jsonObject == null) {
                    onError(new Throwable("返回了空数据"));
                    return;
                }
                WeatherBean weatherBean = (WeatherBean) StringUtils.fromJson(jsonObject.toString(), WeatherBean.class);
                if (weatherBean != null) {
                    WeatherBean.DataBean dataBean = weatherBean.getData();
                    if (dataBean != null) {
                        List<WeatherBean.DataBean.ForecastBean> list = dataBean.getForecast();
                        if (list != null && list.size() > 0) {
                            mWeatherView.onGetWeatherSuccess(list);
                        }
                    }
                } else {
                    onError(new Throwable("返回了空数据"));
                }
            }

            @Override
            public void onError(Throwable e) {
                mWeatherView.hideProgress();
            }

            @Override
            public void onComplete() {
                mWeatherView.hideProgress();
            }
        });
    }

    @Override
    public void onResume(@NonNull LifecycleOwner owner) {
        Log.i("WeatherPresenter", "onResume了");
    }

    @Override
    public void onDestroy(@NonNull LifecycleOwner owner) {
        Log.i("WeatherPresenter", "onDestroy走了");
    }
}