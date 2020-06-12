package com.xuanfeng.xflibrary.mvp;

/**
 * Created by xuanfengwuxiang on 2018/3/1.
 * MVP网络请求与视图交互部分
 */

public abstract class BasePresenter<V extends BaseView> {
    protected V mView;

    public BasePresenter(V view) {
        mView = view;
    }

}
