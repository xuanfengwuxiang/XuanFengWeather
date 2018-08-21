package com.xuanfeng.mylibrary.mvp;

import com.xuanfeng.mylibrary.http.HttpLoader;
import com.xuanfeng.mylibrary.http.HttpResponse;
import com.xuanfeng.mylibrary.http.HttpService;
import com.xuanfeng.mylibrary.http.httpMgr.BaseHttpMgr;

import java.util.LinkedHashMap;

import rx.Observable;

/**
 * Created by xuanfengwuxiang on 2018/3/1.
 * MVP网络请求部分
 */

public abstract class BaseModel extends BaseHttpMgr {
    BaseView mBaseView;

    public BaseModel(BaseView baseView) {
        mBaseView = baseView;
    }

    protected void get(String url, LinkedHashMap<String, String> params, HttpResponse httpResponse) {
        HttpService service = HttpLoader.getInstance().getService();
        Observable observable = service.callByGet(url, params);
        subscribeAndObserve(observable, httpResponse);
    }

    protected void post(String url, LinkedHashMap<String, String> params, HttpResponse httpResponse) {
        HttpService service = HttpLoader.getInstance().getService();
        Observable observable = service.callByPost(url, params);
        subscribeAndObserve(observable, httpResponse);
    }

    protected void postWithHeader(String url, LinkedHashMap<String, String> params, HttpResponse httpResponse) {
        String header = "";
        HttpService service = HttpLoader.getInstance().getService();
        Observable observable = service.callByPostWithHeader(url, header, params);
        subscribeAndObserve(observable, httpResponse);
    }

}
