package com.xuanfeng.xflibrary.component;

import android.app.Activity;

import java.util.ServiceLoader;

/**
 * 组件化工具类
 */
public class ComponentUtil {

    //路由跳转
    public static void toRouterPage(Activity activity, String routeCode) {

        ServiceLoader<IComponentInterface> interfaces = ComponentFactory.getInstance().getInterfaces();
        for (IComponentInterface i : interfaces) {
            if (i.toRoutePage(activity, routeCode)) {
                break;
            }
        }
    }
}
