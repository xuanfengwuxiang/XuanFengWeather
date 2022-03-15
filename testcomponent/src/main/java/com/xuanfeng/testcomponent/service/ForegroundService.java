package com.xuanfeng.testcomponent.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;

import androidx.core.app.NotificationCompat;

import com.xuanfeng.testcomponent.R;
import com.xuanfeng.testcomponent.activity.TestActivity;

public class ForegroundService extends Service {
    public ForegroundService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw null;
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // TODO Auto-generated method stub
        Intent activityIntent = new Intent(this, TestActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplication(), 0, activityIntent, 0);
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        NotificationCompat.Builder notificationBuilder ;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("com.xuanfeng.testcomponent", "channel_one", manager.IMPORTANCE_HIGH);
            channel.enableLights(true);
            channel.setShowBadge(true);
            channel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
            manager.createNotificationChannel(channel);
            notificationBuilder = new NotificationCompat.Builder(this).setChannelId("com.xuanfeng.testcomponent");
        } else {
            notificationBuilder = new NotificationCompat.Builder(this);
        }

        Notification notification = notificationBuilder
                .setAutoCancel(true)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setTicker("前台Service启动")
                .setContentTitle("前台Service运行中")
                .setContentText("这是一个正在运行的前台Service")
                .setWhen(System.currentTimeMillis()).setContentIntent(pendingIntent).build();
        startForeground(1, notification);
        return START_STICKY;
    }

}