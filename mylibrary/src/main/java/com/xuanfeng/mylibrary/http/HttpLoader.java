package com.xuanfeng.mylibrary.http;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by zhujh on 2017/7/19.
 * 描述：retrofit初始化，提供请求接口返回的方法
 */

public class HttpLoader {
    private static final int TIME_OUT = 20000;//超时
    private String BASE_URL = "http://api.mnks.cn/v1/jiaxiao/";
    private static HttpLoader httpLoader;
    private final HttpService mHttpService;

    private HttpLoader() {

        mHttpService = new Retrofit.Builder()
                .client(getOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())//Gson转换
                .addConverterFactory(ScalarsConverterFactory.create())//String转换
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//请求回参设为RxJava
                .baseUrl(BASE_URL)
                .build()
                .create(HttpService.class);
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


    //返回okHttp客户端
    private OkHttpClient getOkHttpClient() {
        return new OkHttpClient.Builder()
                .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))//log日志
                .addInterceptor(getHeaders())//请求头
                .build();
    }

    //添加统一请求头
    private Interceptor getHeaders() {

        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request()
                        .newBuilder()
                        .addHeader("test", "test")
                        .build();
                return chain.proceed(request);
            }
        };

    }
}
