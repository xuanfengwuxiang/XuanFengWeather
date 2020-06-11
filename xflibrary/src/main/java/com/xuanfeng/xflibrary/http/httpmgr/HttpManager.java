package com.xuanfeng.xflibrary.http.httpmgr;

import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import com.xuanfeng.xflibrary.http.HttpLoader;
import com.xuanfeng.xflibrary.http.HttpResponse;
import com.xuanfeng.xflibrary.utils.FileUtil;

import java.io.File;
import java.util.HashMap;
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
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

import static com.uber.autodispose.AutoDispose.autoDisposable;


/**
 * Created by xuanfengwuxiang on 2018/8/20.
 * 网络请求外部调用类
 */

public class HttpManager {

    private static volatile HttpManager mHttpManager;
    private HashMap<String, Disposable> mDisposables = new HashMap<>();//请求取消集合


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
    public void getJsonObjectByGet(LifecycleOwner lifecycleOwner, String url, Map<String, String> params, HttpResponse<JsonObject> httpResponse) {
        getJsonObjectByGet(lifecycleOwner, url, params, System.currentTimeMillis() + "", httpResponse);
    }


    //get请求
    public void getJsonObjectByGet(LifecycleOwner lifecycleOwner, String url, Map<String, String> params, String cancelTag, HttpResponse<JsonObject> httpResponse) {
        Observable observable = HttpLoader.getInstance().getService().callByGet(url, params);
        observeOnUI(lifecycleOwner, observable, httpResponse, cancelTag);
    }

    //post请求，入参使用json
    public <T> void getJsonObjectByPostJson(LifecycleOwner lifecycleOwner, String url, T t, HttpResponse<JsonObject> httpResponse) {
        getJsonObjectByPostJson(lifecycleOwner, url, t, System.currentTimeMillis() + "", httpResponse);

    }


    //post请求，入参使用json
    public <T> void getJsonObjectByPostJson(LifecycleOwner lifecycleOwner, String url, T t, String cancelTag, HttpResponse<JsonObject> httpResponse) {
        String jsonString = new Gson().toJson(t);
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), jsonString);
        Observable observable = HttpLoader.getInstance().getService().callByPostUseJson(url, body);
        observeOnUI(lifecycleOwner, observable, httpResponse, cancelTag);

    }

    //post请求，入参key-value
    public void getJsonObjectByPost(LifecycleOwner lifecycleOwner, String url, LinkedHashMap<String, String> params, HttpResponse<JsonObject> httpResponse) {
        getJsonObjectByPost(lifecycleOwner, url, params, System.currentTimeMillis() + "", httpResponse);
    }

    public void getJsonObjectByPost(LifecycleOwner lifecycleOwner, String url, LinkedHashMap<String, String> params, String cancelTag, HttpResponse<JsonObject> httpResponse) {
        Observable observable = HttpLoader.getInstance().getService().callByPost(url, params);
        observeOnUI(lifecycleOwner, observable, httpResponse, cancelTag);
    }


    //文件上传
    public void uploadFiles(LifecycleOwner lifecycleOwner, String url, List<String> filePaths, String cancelTag, HttpResponse<JsonObject> httpResponse) {
        if (filePaths == null || filePaths.isEmpty()) {
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
        Observable observable = HttpLoader.getInstance().getService().uploadFiles(url, params);
        observeOnUI(lifecycleOwner, observable, httpResponse, cancelTag);
    }

    //文件下载
    public void downloadFile(LifecycleOwner lifecycleOwner, String url, final String savePath, String cancelTag, final HttpResponse<String> httpResponse) {
        Observable observable = HttpLoader.getInstance().getService().downloadFile(url);
        observeOnUI(lifecycleOwner, observable, new HttpResponse<ResponseBody>() {

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
                        .subscribe(getObserver(httpResponse, cancelTag));
            }

            @Override
            public void onError(Throwable e) {
                //do nothing
            }

            @Override
            public void onComplete() {
                //do nothing
            }
        }, cancelTag);
    }


    //取消某个接口
    public void cancelTag(String cancelTag) {

        if (!mDisposables.containsKey(cancelTag)) {
            return;
        }
        Disposable disposable = mDisposables.get(cancelTag);
        if (disposable != null && !disposable.isDisposed()) disposable.dispose();
        mDisposables.remove(cancelTag);
    }


    //1、子线程处理 主线程回调
    public void observeOnUI(LifecycleOwner lifecycleOwner, Observable<?> observable, HttpResponse httpResponse, String cancelTag) {
        observable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(lifecycleOwner)))
                .subscribe(getObserver(httpResponse, cancelTag));

    }

    //绑定自定义接口与Observer
    public <W> Observer<W> getObserver(final HttpResponse httpResponse, String cancelTag) {
        return new Observer<W>() {

            @Override
            public void onSubscribe(Disposable d) {
                mDisposables.put(cancelTag, d);
            }

            @Override
            public void onNext(@NonNull W w) {
                httpResponse.onSuccess(w);
            }

            @Override
            public void onComplete() {
                httpResponse.onComplete();

                if (mDisposables.containsKey(cancelTag)) {
                    mDisposables.remove(cancelTag);
                }
            }

            @Override
            public void onError(@NonNull Throwable e) {
                httpResponse.onError(e);

            }

        };
    }
}
