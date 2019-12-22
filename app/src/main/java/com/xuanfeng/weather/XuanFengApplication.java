package com.xuanfeng.weather;

import android.content.Context;
import android.text.TextUtils;

import androidx.multidex.MultiDexApplication;

import com.alibaba.android.arouter.launcher.ARouter;
import com.xuanfeng.mylibrary.utils.SystemUtils;
import com.xuanfeng.weather.exception.ExceptionHandler;
import com.xuanfeng.weather.utils.SystemPropertyUtil;
import com.xuanfeng.weather.variable.Variable;

/**
 * Created by zhujh on 2017/7/25.
 * 描述：应用入口
 */

public class XuanFengApplication extends MultiDexApplication {


    @Override
    public void onCreate() {
        super.onCreate();
        // 手动捕获Application错误异常
        Thread.setDefaultUncaughtExceptionHandler(ExceptionHandler.getInstance(this));
        init();
    }

    //初始化
    private void init() {
        if (TextUtils.isEmpty(Variable.FILE_PATH)) {
            SystemPropertyUtil.initSystemProperties(getApplicationContext());
        }
        initBugly();
        initARouter();
    }


    //初始化bugly
    private void initBugly() {
        Context context = getApplicationContext();

    }

    private void initARouter() {
        if (SystemUtils.isDebug(this)) {           // These two lines must be written before init, otherwise these configurations will be invalid in the init process
            ARouter.openLog();     // Print log
            ARouter.openDebug();   // Turn on debugging mode (If you are running in InstantRun mode, you must turn on debug mode! Online version needs to be closed, otherwise there is a security risk)
        }
        ARouter.init(this);
    }

}
