package com.xuanfeng.xflibrary.http

/**
 * Created by zhujh on 2017/7/19.
 * 描述：网络请求回调
 */
class HttpResponse<T> {

    var mOnSuccess: ((T?) -> Unit)? = null
    var mOnError: ((String) -> Unit)? = null
    var mOnComplete: (() -> Unit)? = null

    fun onSuccess(action: (T?) -> Unit) {
        this.mOnSuccess = action
    }

    fun onError(action: (String) -> Unit) {
        this.mOnError = action
    }

    fun onComplete(action: () -> Unit) {
        this.mOnComplete = action
    }
}