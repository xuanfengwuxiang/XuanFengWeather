package com.xuanfeng.mylibrary.http.httpMgr;

import android.text.TextUtils;

import com.google.gson.JsonObject;
import com.xuanfeng.mylibrary.http.HttpLoader;
import com.xuanfeng.mylibrary.http.HttpResponse;
import com.xuanfeng.mylibrary.http.HttpService;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Observable;

/**
 * Created by xuanfengwuxiang on 2018/8/20.
 * 网络请求外部调用类
 */

public class HttpManager extends BaseHttpMgr {

    //get请求
    public static void getJsonObjectByGet(String url, LinkedHashMap<String, String> params, HttpResponse<JsonObject> httpResponse) {
        HttpService service = HttpLoader.getInstance().getService();
        Observable observable = service.callByGet(url, params);
        subscribeAndObserve(observable, httpResponse);
    }

    //文件上传
    public static void uploadFiles(String url, List<String> filePaths, String token, HttpResponse<JsonObject> httpResponse) {
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
        subscribeAndObserve(observable, httpResponse);
    }
}
