package com.xuanfeng.weather.exception;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.xuanfeng.weather.XuanFengApplication;

/**
 * Created by xuanfengwuxiang on 2017/12/19.
 * 自定义全局异常捕获
 */

public class ExceptionHandler implements Thread.UncaughtExceptionHandler {

    private static ExceptionHandler mExceptionHandler;

    private ExceptionHandler() {
    }

    //获取异常处理的单例
    public static synchronized ExceptionHandler getInstance() {
        if (mExceptionHandler != null) {
            return mExceptionHandler;
        } else {
            mExceptionHandler = new ExceptionHandler();
            return mExceptionHandler;
        }
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        ex.printStackTrace();//不能忘记打印异常信息
        Toast.makeText(XuanFengApplication.getApplication(), "程序异常,10秒之后自动重启~", Toast.LENGTH_SHORT).show();
        restartActivity();
        // kill current process
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1);
    }

    private final static long SLEEPTIME_RESTART_ACTIVITY = 10000;//重新启动应用程序指定Activity间隔时间.  毫秒.  1000ms = 1s

    /**
     * 重新启动应用程序
     */
    private void restartActivity() {
        //创建用于启动的 Intent , 与对应的数据
        Intent intent = XuanFengApplication.getApplication().getPackageManager().getLaunchIntentForPackage(XuanFengApplication.getApplication().getPackageName());
        intent.putExtra("autoStart", true);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(XuanFengApplication.getApplication(), 0, intent, PendingIntent.FLAG_ONE_SHOT);
        //获取闹钟管理器 , 用于定时执行我们的启动任务
        AlarmManager mgr = (AlarmManager) XuanFengApplication.getApplication().getSystemService(Context.ALARM_SERVICE);
        //设置执行PendingIntent的时间是当前时间+SLEEPTIME_RESTART_ACTIVITY 参数的值
        mgr.set(AlarmManager.RTC, System.currentTimeMillis() + SLEEPTIME_RESTART_ACTIVITY, pendingIntent);
    }
}
