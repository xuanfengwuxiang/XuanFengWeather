package com.xuanfeng.xuanfengweather.module.weather.view;

import com.xuanfeng.xuanfengweather.module.weather.bean.WeatherBean;

import java.util.List;

/**
 * Created by xuanfengwuxiang on 2017/12/13.
 */

public interface WeatherView {
    void onGetWeatherSuccess(List<WeatherBean.DataBean.ForecastBean> mForecastBeanList);
    void onGetWeatherError(String msg);
}
