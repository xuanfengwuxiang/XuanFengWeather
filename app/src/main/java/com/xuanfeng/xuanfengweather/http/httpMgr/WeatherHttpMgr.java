package com.xuanfeng.xuanfengweather.http.httpMgr;

import com.xuanfeng.mylibrary.http.httpMgr.BaseHttpMgr;
import com.google.gson.JsonObject;
import com.xuanfeng.mylibrary.http.HttpLoader;
import com.xuanfeng.mylibrary.http.HttpResponse;
import com.xuanfeng.mylibrary.http.HttpService;
import com.xuanfeng.xuanfengweather.constant.HttpConstant;

import java.util.LinkedHashMap;

import rx.Observable;

/**
 * Created by zhujh on 2017/7/19.
 * 描述：天气模块的http请求管理
 */

public class WeatherHttpMgr extends BaseHttpMgr {
    public static void getWeather(String city, HttpResponse<JsonObject> httpResponse){
        LinkedHashMap<String,String> params = new LinkedHashMap<>();
        params.put("city",city);
        HttpService service = HttpLoader.getInstance().getService();
        Observable observable = service.callByGet(HttpConstant.WEATHER_URL,params);
        subscribeAndObserve(observable,httpResponse);
    }
}
