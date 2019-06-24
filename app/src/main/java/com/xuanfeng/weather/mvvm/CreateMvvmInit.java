package com.xuanfeng.weather.mvvm;

import android.os.Bundle;
import android.view.View;

/**
 * Created by xuanfengwuxiang on 2017/12/14.
 * 基于MVVM的页面初始化
 */

public interface CreateMvvmInit {

    int getLayoutId();//布局id

    void initViewModel();//初始化vm

    void initListener();//初始化监听器

    void initData(Bundle bundle);//初始化数据

    void onClick(View view);//点击事件

    boolean isFullScreen();//是否全屏展示

}
