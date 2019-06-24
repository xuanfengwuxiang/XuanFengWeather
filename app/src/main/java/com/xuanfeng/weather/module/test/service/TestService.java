package com.xuanfeng.weather.module.test.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import androidx.annotation.Nullable;
import android.util.Log;

public class TestService extends Service {
    public TestService() {
    }



    @Override
    public void onCreate() {
        super.onCreate();
        Log.i("TestService","==onCreate");
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        Log.i("TestService","==onStart");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("TestService","==onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.i("TestService","==onBind");
        return null;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.i("TestService","==onUnbind");
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("TestService","==onDestroy");
    }
}
