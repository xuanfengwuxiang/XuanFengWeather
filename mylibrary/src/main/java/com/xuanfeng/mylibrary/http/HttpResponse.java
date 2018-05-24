package com.xuanfeng.mylibrary.http;

/**
 * Created by zhujh on 2017/7/19.
 * 描述：网络请求回调
 */

public interface HttpResponse<W> {
    public void onSuccess(W w);
    public void onError(Throwable e);
    public void onComplete();
}
