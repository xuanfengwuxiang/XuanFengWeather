package com.xuanfeng.mylibrary.mvp;

/**
 * Created by xuanfengwuxiang on 2018/3/1.
 * MVP网络请求与视图交互部分
 */

public abstract class BasePresenter<V extends BaseView, T extends BaseModel> {
    protected V mView;
    protected T mModel;

    public BasePresenter(V view, T model) {
        mView = view;
        mModel = model;
    }

}
