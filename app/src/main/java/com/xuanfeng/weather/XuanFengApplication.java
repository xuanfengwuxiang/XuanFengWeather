package com.xuanfeng.weather;

import android.content.Context;
import android.text.TextUtils;

import androidx.multidex.MultiDexApplication;

import com.alibaba.android.arouter.launcher.ARouter;
import com.tencent.bugly.crashreport.CrashReport;
import com.xuanfeng.mylibrary.utils.SystemUtils;
import com.xuanfeng.weather.constant.Constant;
import com.xuanfeng.weather.exception.ExceptionHandler;
import com.xuanfeng.weather.utils.SystemPropertyUtil;
import com.xuanfeng.weather.variable.Variable;

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
        initARouter();
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

    private void initARouter() {
        if (SystemUtils.isDebug(this)) {           // These two lines must be written before init, otherwise these configurations will be invalid in the init process
            ARouter.openLog();     // Print log
            ARouter.openDebug();   // Turn on debugging mode (If you are running in InstantRun mode, you must turn on debug mode! Online version needs to be closed, otherwise there is a security risk)
        }
        ARouter.init(mApplication);
    }

    public static XuanFengApplication getApplication() {
        return mApplication;
    }

}
