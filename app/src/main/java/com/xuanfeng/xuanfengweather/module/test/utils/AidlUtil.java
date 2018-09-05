package com.xuanfeng.xuanfengweather.module.test.utils;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.RemoteException;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;

import com.xuanfeng.server.IDemandManager;
import com.xuanfeng.server.MessageBean;

//AIDL测试类的工具
public class AidlUtil {

    public static void startRemoteService(Activity activity, ServiceConnection connection) {
        Intent intent = new Intent();
        intent.setComponent(new ComponentName("com.xuanfeng.server", "com.xuanfeng.server.AIDLService"));
        activity.startService(intent);
        boolean result = activity.bindService(intent, connection, activity.BIND_AUTO_CREATE);
        Toast.makeText(activity, "" + result, Toast.LENGTH_SHORT).show();
    }

    public static void sendToServer(EditText mEtInput,Activity activity,IDemandManager demandManager){
        String sendMsg = mEtInput.getText().toString();
        if (TextUtils.isEmpty(sendMsg)) {
            Toast.makeText(activity, "请输入发送内容", Toast.LENGTH_SHORT).show();
            return;
        }
        if (demandManager != null) {
            try {
                demandManager.setDemandIn(new MessageBean(sendMsg, 1));
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }


}
