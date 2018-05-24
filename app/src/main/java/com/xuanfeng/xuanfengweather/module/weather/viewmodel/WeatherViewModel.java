package com.xuanfeng.xuanfengweather.module.weather.viewmodel;

import android.content.Context;

import com.google.gson.JsonObject;
import com.xuanfeng.mylibrary.http.HttpResponse;
import com.xuanfeng.xuanfengweather.http.httpMgr.WeatherHttpMgr;
import com.xuanfeng.xuanfengweather.module.weather.bean.WeatherBean;
import com.xuanfeng.xuanfengweather.module.weather.view.WeatherView;
import com.xuanfeng.mylibrary.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xuanfengwuxiang on 2017/12/13.
 */

public class WeatherViewModel {

    private Context mContext;
    private WeatherView mWeatherView;
    private List<WeatherBean.DataBean.ForecastBean> mForecastBeanList;//预报的集合

    public WeatherViewModel(Context context, WeatherView weatherView) {
        mContext = context;
        mWeatherView = weatherView;
        mForecastBeanList = new ArrayList<>();
    }


    public void getWeather(String city) {
        WeatherHttpMgr.getWeather(city, new HttpResponse<JsonObject>() {
            @Override
            public void onSuccess(JsonObject jsonObject) {
                if (jsonObject == null) {
                    onError(new Throwable("返回了空数据"));
                    return;
                }
                WeatherBean weatherBean = (WeatherBean) StringUtils.fromJson(jsonObject.toString(), WeatherBean.class);
                if (weatherBean != null) {
                    WeatherBean.DataBean dataBean = weatherBean.getData();
                    if (dataBean != null) {
                        mForecastBeanList.clear();
                        mForecastBeanList.addAll(dataBean.getForecast());
                        if (mForecastBeanList != null && mForecastBeanList.size() > 0) {
                            mWeatherView.onGetWeatherSuccess(mForecastBeanList);
                        }
                    }
                }else{
                    onError(new Throwable("返回了空数据"));
                }
            }

            @Override
            public void onError(Throwable e) {
                mWeatherView.onGetWeatherError(e.toString());
            }

            @Override
            public void onComplete() {

            }
        });
    }


}
