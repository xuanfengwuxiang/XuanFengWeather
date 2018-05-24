package com.xuanfeng.mylibrary.http;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by zhujh on 2017/7/19.
 * 描述：retrofit初始化，提供请求接口返回的方法
 */

public class HttpLoader {
    private String BASE_URL = "http://api.mnks.cn/v1/jiaxiao/";
    private Retrofit mRetrofit;
    private static HttpLoader httpLoader;
    private final HttpService mHttpService;

    private HttpLoader() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build();
        mRetrofit = new Retrofit.Builder()
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())//Gson转换
                .addConverterFactory(ScalarsConverterFactory.create())//String转换
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())//
                .baseUrl(BASE_URL)
                .build();
        mHttpService = mRetrofit.create(HttpService.class);
    }

    //单例、双重检验加锁
    public static synchronized HttpLoader getInstance() {
        if (httpLoader == null) {
            synchronized (HttpLoader.class) {
                if (httpLoader == null) {
                    httpLoader = new HttpLoader();
                }
            }
        }
        return httpLoader;
    }

    //获取接口
    public HttpService getService() {
        return mHttpService;
    }
}
