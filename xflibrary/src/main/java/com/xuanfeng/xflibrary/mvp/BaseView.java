package com.xuanfeng.xflibrary.mvp;

import android.os.Bundle;

import androidx.lifecycle.ViewModel;

/**
 * Created by xuanfengwuxiang on 2018/3/1.
 * 页面 公共部分 初始化
 */

public interface BaseView {

    boolean isFullScreen();//是否全屏展示

    int getLayoutId();//布局id

    BasePresenter initPresenter();//P层

    ViewModel initViewModel();

    void initData(Bundle bundle);//初始化数据

    int getStatusBarColorResId();

    void showProgress();

    void hideProgress();

}
