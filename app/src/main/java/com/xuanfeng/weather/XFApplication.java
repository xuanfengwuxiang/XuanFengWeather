package com.xuanfeng.weather;

import android.content.Context;
import android.text.TextUtils;

import androidx.multidex.MultiDexApplication;

import com.xuanfeng.mylibrary.component.ComponentFactory;
import com.xuanfeng.weather.exception.ExceptionHandler;
import com.xuanfeng.weather.utils.SystemPropertyUtil;
import com.xuanfeng.weather.variable.Variable;

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
        if (TextUtils.isEmpty(Variable.FILE_PATH)) {
            SystemPropertyUtil.initSystemProperties(getApplicationContext());
        }
        initBugly();
        initComponent();
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
