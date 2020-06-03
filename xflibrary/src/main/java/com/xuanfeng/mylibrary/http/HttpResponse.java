package com.xuanfeng.mylibrary.http;

/**
 * Created by zhujh on 2017/7/19.
 * 描述：网络请求回调
 */

public interface HttpResponse<W> {
    void onSuccess(W w);

    void onError(Throwable e);

    void onComplete();
}
