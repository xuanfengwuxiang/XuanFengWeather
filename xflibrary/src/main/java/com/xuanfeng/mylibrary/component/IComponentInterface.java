package com.xuanfeng.mylibrary.component;

import android.app.Activity;
import android.app.Application;

/**
 * 组件交互接口
 */
public interface IComponentInterface {

    void init(Application application);

    boolean toRoutePage(Activity activity,String routeCode);
}
