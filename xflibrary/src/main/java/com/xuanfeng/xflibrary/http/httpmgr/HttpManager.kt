package com.xuanfeng.xflibrary.http.httpmgr;

import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.xuanfeng.xflibrary.http.HttpLoader;
import com.xuanfeng.xflibrary.http.HttpResponse;
import com.xuanfeng.xflibrary.utils.FileUtil;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;


/**
 * Created by xuanfengwuxiang on 2018/8/20.
 * 网络请求外部调用类
 */

public class HttpManager {

    private static volatile HttpManager mHttpManager;


    public static HttpManager getInstance() {

        if (mHttpManager == null) {
            synchronized (HttpManager.class) {
                if (mHttpManager == null) {
                    mHttpManager = new HttpManager();
                }
            }
        }
        return mHttpManager;
    }


    //get请求
    public void getJO(String url, Map<String, Object> params, HttpResponse<JsonObject> httpResponse) {
        Observable observable = HttpLoader.getInstance().getService().getJO(url, params);
        observeOnUI(observable, httpResponse);
    }

    //post请求
    public void postJO(String url, Map<String, String> params, HttpResponse<JsonObject> httpResponse) {
        Observable observable = HttpLoader.getInstance().getService().postJO(url, params);
        observeOnUI(observable, httpResponse);
    }

    //post请求，入参使用json
    public <T> void postJson(String url, T t, HttpResponse<JsonObject> httpResponse) {
        String jsonString = new Gson().toJson(t);
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), jsonString);
        Observable observable = HttpLoader.getInstance().getService().postJsonJO(url, body);
        observeOnUI(observable, httpResponse);
    }

    //图片上传
    public void uploadImages(String url, List<String> filePaths, HttpResponse<JsonObject> httpResponse) {
        uploadFiles(url, filePaths, null, "image/jpg", httpResponse);
    }

    //图片上传--附带信息
    public void uploadImages(String url, List<String> filePaths, Map<String, String> extra, HttpResponse<JsonObject> httpResponse) {
        uploadFiles(url, filePaths, extra, "image/jpg", httpResponse);
    }

    //文件上传
    public void uploadFiles(String url, List<String> filePaths, HttpResponse<JsonObject> httpResponse) {
        uploadFiles(url, filePaths, null, "multipart/form-data", httpResponse);
    }

    //文件上传
    public void uploadFiles(String url, List<String> filePaths, Map<String, String> extra, String contentType, HttpResponse<JsonObject> httpResponse) {
        if (filePaths == null || filePaths.isEmpty()) {
            return;
        }
        LinkedHashMap<String, RequestBody> params = new LinkedHashMap<>();
        for (String path : filePaths) {
            if (!TextUtils.isEmpty(path)) {
                File file = new File(path);
                if (file.exists()) {
                    RequestBody requestFile = RequestBody.create(MediaType.parse(contentType), file);
                    params.put("file\"; filename=\"" + file.getName() + "", requestFile);
                }
            }
        }

        if (extra != null && !extra.isEmpty()) {
            for (Map.Entry<String, String> entry : extra.entrySet()) {
                if (entry != null) {
                    String key = entry.getKey() != null ? entry.getKey() : "";
                    String value = entry.getValue() != null ? entry.getValue() : "";
                    params.put(key, RequestBody.create(MediaType.parse("text/plain"), value));
                }
            }
        }
        Observable observable = HttpLoader.getInstance().getService().uploadFiles(url, params);
        observeOnUI(observable, httpResponse);
    }

    //上传单个图片
    public void uploadImage(String url, String filePath, HttpResponse<JsonObject> httpResponse) {
        uploadFile(url, filePath, "multipart/form-data", "image", httpResponse);
    }

    // 上传单个文件
    public void uploadFile(String url, String filePath, String contentType, String formDataName, HttpResponse<JsonObject> httpResponse) {
        File file = new File(filePath);
        RequestBody requestFile = RequestBody.create(MediaType.parse(contentType), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData(formDataName, file.getName(), requestFile);

        Observable observable = HttpLoader.getInstance().getService().uploadFile(url, body);
        observeOnUI(observable, httpResponse);
    }

    //文件下载
    public void downloadFile(String url, final String savePath, final HttpResponse<String> httpResponse) {
        Observable observable = HttpLoader.getInstance().getService().downloadFile(url);
        observeOnUI(observable, new HttpResponse<ResponseBody>() {

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
                //do nothing
            }

            @Override
            public void onComplete() {
                //do nothing
            }
        });
    }


    //1、子线程处理 主线程回调
    public void observeOnUI(Observable<?> observable, HttpResponse httpResponse) {
        observable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getObserver(httpResponse));
    }

    //绑定自定义接口与Observer
    public <W> Observer<W> getObserver(final HttpResponse httpResponse) {
        return new Observer<W>() {

            @Override
            public void onSubscribe(Disposable d) {
                //todo nothing
            }

            @Override
            public void onNext(@NonNull W w) {
                if (httpResponse != null) {
                    httpResponse.onSuccess(w);
                }
            }

            @Override
            public void onComplete() {
                if (httpResponse != null) {
                    httpResponse.onComplete();
                }
            }

            @Override
            public void onError(@NonNull Throwable e) {
                if (httpResponse != null) {
                    httpResponse.onError(e);
                }
            }

        };
    }
}
