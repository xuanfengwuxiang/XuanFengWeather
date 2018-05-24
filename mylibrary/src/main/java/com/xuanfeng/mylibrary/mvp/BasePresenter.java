package com.xuanfeng.mylibrary.mvp;

/**
 * Created by xuanfengwuxiang on 2018/3/1.
 * 数据管理者
 */

public abstract class BasePresenter<V extends IBaseBiew>  {
    public V mView;

    public BasePresenter(V view) {
        mView = view;
        initModel();
    }

    public abstract void initModel();
}
