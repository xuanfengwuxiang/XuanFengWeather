package com.xuanfeng.mylibrary.http.httpMgr;

import android.arch.lifecycle.LifecycleOwner;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.xuanfeng.mylibrary.http.HttpLoader;
import com.xuanfeng.mylibrary.http.HttpResponse;
import com.xuanfeng.mylibrary.http.HttpService;
import com.xuanfeng.mylibrary.utils.FileUtil;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;


/**
 * Created by xuanfengwuxiang on 2018/8/20.
 * 网络请求外部调用类
 */

public class HttpManager extends BaseHttpMgr {

    //get请求
    public static void getJsonObjectByGet(LifecycleOwner lifecycleOwner, String url, LinkedHashMap<String, String> params, HttpResponse<JsonObject> httpResponse) {
        HttpService service = HttpLoader.getInstance().getService();
        Observable observable = service.callByGet(url, params);
        subscribeAndObserve(lifecycleOwner, observable, httpResponse);
    }

    //post请求，入参使用json
    public static <T> void getJsonObjectByPostJson(LifecycleOwner lifecycleOwner, String url, T t, HttpResponse<JsonObject> httpResponse) {
        String jsonString = new Gson().toJson(t);
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), jsonString);
        HttpService service = HttpLoader.getInstance().getService();
        Observable observable = service.callByPostUseJson(url, body);
        subscribeAndObserve(lifecycleOwner, observable, httpResponse);

    }


    //文件上传
    public static void uploadFiles(LifecycleOwner lifecycleOwner, String url, List<String> filePaths, String token, HttpResponse<JsonObject> httpResponse) {
        if (filePaths == null || filePaths.size() == 0) {
            return;
        }
        LinkedHashMap<String, RequestBody> params = new LinkedHashMap<>();
        for (String path : filePaths) {
            if (!TextUtils.isEmpty(path)) {
                File file = new File(path);
                if (file.exists()) {
                    RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                    params.put("file\"; filename=\"" + file.getName() + "", requestFile);
                }
            }
        }
        HttpService service = HttpLoader.getInstance().getService();
        Observable observable = service.uploadFiles(url, params);
        subscribeAndObserve(lifecycleOwner, observable, httpResponse);
    }

    //文件下载
    public static void downloadFile(LifecycleOwner lifecycleOwner, String url, final String savePath, final HttpResponse<String> httpResponse) {
        HttpService service = HttpLoader.getInstance().getService();
        Observable observable = service.downloadFile(url);
        subscribeAndObserve(lifecycleOwner, observable, new HttpResponse<ResponseBody>() {

            @Override
            public void onSuccess(final ResponseBody responseBody) {
                Observable.create(new ObservableOnSubscribe<String>() {

                    @Override
                    public void subscribe(ObservableEmitter<String> emitter) throws Exception {

                        boolean result = FileUtil.writeResponseBodyToDisk(responseBody, savePath);
                        if (result) {
                            emitter.onNext("success");
                        } else {
                            emitter.onError(new Throwable("write to disk error"));
                        }
                    }
                }).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(getObserver(httpResponse));
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }
}
