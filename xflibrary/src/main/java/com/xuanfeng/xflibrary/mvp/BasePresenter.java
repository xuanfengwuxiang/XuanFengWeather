package com.xuanfeng.xflibrary.mvp;

import androidx.lifecycle.ViewModel;

/**
 * Created by xuanfengwuxiang on 2018/3/1.
 * MVP网络请求与视图交互部分
 */

public interface BasePresenter<V extends BaseView, M extends ViewModel> {

    void attachView(V v, M m);

    void detachView();
}
