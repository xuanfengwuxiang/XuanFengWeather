package com.xuanfeng.weather;

import android.content.Context;

import androidx.multidex.MultiDexApplication;

import com.bumptech.glide.Glide;
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader;
import com.bumptech.glide.load.model.GlideUrl;
import com.xuanfeng.weather.exception.ExceptionHandler;
import com.xuanfeng.xflibrary.component.ComponentFactory;
import com.xuanfeng.xflibrary.http.SSLSocketClient;
import com.xuanfeng.xflibrary.utils.AppUtil;

import java.io.InputStream;

import okhttp3.OkHttpClient;

/**
 * Created by zhujh on 2017/7/25.
 * 描述：应用入口
 */

public class XFApplication extends MultiDexApplication {


    @Override
    public void onCreate() {
        super.onCreate();
        // 手动捕获Application错误异常
        Thread.setDefaultUncaughtExceptionHandler(ExceptionHandler.getInstance(this));
        init();
    }

    //初始化
    private void init() {
//        initBugly();
        initComponent();
        AppUtil.initAppWorkPath(this);
        try {
            Glide.get(this).getRegistry().replace(
                    GlideUrl.class, InputStream.class,
                    new OkHttpUrlLoader.Factory(new OkHttpClient.Builder()
                            .sslSocketFactory(SSLSocketClient.getSSLSocketFactory())
                            .hostnameVerifier(SSLSocketClient.getHostnameVerifier())
                            .build()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //初始化bugly
    private void initBugly() {
        Context context = getApplicationContext();

    }

    //初始化组件化
    private void initComponent() {
        ComponentFactory.getInstance().initAllModule(this);
    }

}
