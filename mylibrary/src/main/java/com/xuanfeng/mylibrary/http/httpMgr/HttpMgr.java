package com.xuanfeng.mylibrary.http.httpMgr;

import com.google.gson.JsonObject;
import com.xuanfeng.mylibrary.http.HttpLoader;
import com.xuanfeng.mylibrary.http.HttpResponse;
import com.xuanfeng.mylibrary.http.HttpService;

import java.util.LinkedHashMap;

import rx.Observable;

/**
 * Created by xuanfengwuxiang on 2018/8/20.
 * 网络请求外部调用类
 */

public class HttpMgr extends BaseHttpMgr {

    //
    public static void getJsonObjectByGet(String url, LinkedHashMap<String, String> params, HttpResponse<JsonObject> httpResponse) {
        HttpService service = HttpLoader.getInstance().getService();
        Observable observable = service.callByGet(url, params);
        subscribeAndObserve(observable, httpResponse);
    }
}
