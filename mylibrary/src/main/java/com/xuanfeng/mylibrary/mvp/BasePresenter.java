package com.xuanfeng.mylibrary.mvp;

/**
 * Created by xuanfengwuxiang on 2018/3/1.
 * 数据管理者
 */

public abstract class BasePresenter<V extends BaseView, T extends BaseModel> {
    public V mView;
    public T mModel;

    public BasePresenter(V view, T model) {
        mView = view;
        mModel = model;
    }

}
