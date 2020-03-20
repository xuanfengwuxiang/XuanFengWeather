package com.xuanfeng.mylibrary;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;

import com.google.auto.service.AutoService;
import com.xuanfeng.mylibrary.component.IComponentInterface;
import com.xuanfeng.mylibrary.contacts.ContactsActivity;

/**
 * 测试模块组件
 */

@AutoService(IComponentInterface.class)
public class MyComponent implements IComponentInterface {
    @Override
    public void init(Application application) {

    }

    @Override
    public boolean toRoutePage(Activity activity, String routeCode) {

        boolean handled = false;
        if ("667".equals(routeCode)) {
            Intent intent = new Intent(activity, ContactsActivity.class);
            activity.startActivity(intent);
            handled = true;
        }
        return handled;
    }
}
