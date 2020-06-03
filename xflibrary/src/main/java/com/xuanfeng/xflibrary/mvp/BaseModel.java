package com.xuanfeng.xflibrary.mvp;

import com.xuanfeng.xflibrary.http.httpmgr.HttpManager;

/**
 * Created by xuanfengwuxiang on 2018/3/1.
 * MVP网络请求部分
 */

public abstract class BaseModel extends HttpManager {

    BaseView mBaseView;

    public BaseModel(BaseView baseView) {
        mBaseView = baseView;
    }


}
