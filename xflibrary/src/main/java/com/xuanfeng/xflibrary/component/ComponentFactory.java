package com.xuanfeng.xflibrary.component;

import android.app.Application;

import java.util.ServiceLoader;

/**
 * 组件初始化工厂
 */
public class ComponentFactory {

    private static ComponentFactory mComponentFactory;
    private ServiceLoader<IComponentInterface> mInterfaces;

    private ComponentFactory() {
        mInterfaces = ServiceLoader.load(IComponentInterface.class);
    }

    public static ComponentFactory getInstance() {

        if (mComponentFactory == null) {

            synchronized (ComponentFactory.class) {
                mComponentFactory = new ComponentFactory();
            }
        }
        return mComponentFactory;
    }

    public void initAllModule(Application application) {
        for (IComponentInterface i : mInterfaces) {
            i.init(application);
        }
    }

    public ServiceLoader<IComponentInterface> getInterfaces() {
        return mInterfaces;
    }
}
