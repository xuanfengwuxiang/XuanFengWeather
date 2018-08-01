package com.xuanfeng.xuanfengweather;

import android.content.Context;
import android.support.multidex.MultiDexApplication;
import android.text.TextUtils;

import com.bumptech.glide.Glide;
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader;
import com.bumptech.glide.load.model.GlideUrl;
import com.tencent.bugly.crashreport.CrashReport;
import com.xuanfeng.mylibrary.utils.HttpsImageUtil;
import com.xuanfeng.mylibrary.utils.SystemUtils;
import com.xuanfeng.xuanfengweather.constant.Constant;
import com.xuanfeng.xuanfengweather.exception.ExceptionHandler;
import com.xuanfeng.xuanfengweather.utils.SystemPropertyUtil;
import com.xuanfeng.xuanfengweather.variable.Variable;

import java.io.InputStream;

/**
 * Created by zhujh on 2017/7/25.
 * 描述：应用入口
 */

public class XuanFengApplication extends MultiDexApplication {

    private static XuanFengApplication mApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;
        // 手动捕获Application错误异常
        Thread.setDefaultUncaughtExceptionHandler(ExceptionHandler.getInstance());
        init();
    }

    //初始化
    private void init() {
        if (TextUtils.isEmpty(Variable.FILE_PATH)) {
            SystemPropertyUtil.initSystemProperties(getApplicationContext());
        }
        initBugly();
        initHttpsImage();
    }

    //初始化bugly
    private void initBugly() {
        Context context = getApplicationContext();
        // 获取当前包名
        String packageName = context.getPackageName();
        // 获取当前进程名
        String processName = SystemUtils.getProcessName(android.os.Process.myPid());
        // 设置是否为上报进程
        CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(context);
        String appChannel = "";
        if (BuildConfig.DEBUG) {
            appChannel = "develop";
        } else {
            appChannel = Variable.APP_CHANNEL;
        }
        strategy.setAppChannel(appChannel);  //设置渠道
        strategy.setAppVersion(Variable.APP_VERSION_NAME);      //App的版本
        strategy.setAppPackageName(Variable.PACKAGE_NAME);  //App的包名
        strategy.setUploadProcess(processName == null || processName.equals(packageName));
        // 初始化Bugly
        CrashReport.initCrashReport(getApplicationContext(), Constant.Bugly_App_ID, BuildConfig.DEBUG, strategy);

    }

    //初始化加载https的图片
    private void initHttpsImage() {
        Glide.get(this).register(GlideUrl.class, InputStream.class, new OkHttpUrlLoader.Factory(HttpsImageUtil.getOkHttpClient()));
    }

    public static XuanFengApplication getApplication() {
        return mApplication;
    }

}
