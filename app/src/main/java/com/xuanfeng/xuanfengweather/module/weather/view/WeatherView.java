package com.xuanfeng.xuanfengweather.module.weather.view;

import com.xuanfeng.mylibrary.mvp.BaseView;
import com.xuanfeng.xuanfengweather.module.weather.widget.WeatherRecyclerView.WeatherBean.DataBean.ForecastBean;

import java.util.List;

/**
 * Created by xuanfengwuxiang on 2017/12/13.
 */

public interface WeatherView extends BaseView{
    void onGetWeatherSuccess(List<ForecastBean> mForecastBeanList);

    void onGetWeatherError(String msg);
}
