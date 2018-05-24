package com.xuanfeng.mylibrary.http.httpMgr;

import android.support.annotation.NonNull;

import com.xuanfeng.mylibrary.http.HttpResponse;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by zhujh on 2017/7/19.
 * 描述：网络请求管理基类，实现网络请求与回调的绑定
 */

public class BaseHttpMgr {


    //将网络请求与回调进行绑定，与设置监听器同理
    public static void subscribeAndObserve(Observable<?> observable, HttpResponse httpResponse) {
        observable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getOberver(httpResponse));
    }

    //将retrofit的回调改为自定义，方便后期切换框架
    private static <W> Observer<W> getOberver(final HttpResponse httpResponse) {
        Observer observer = new Observer<W>() {

            @Override
            public void onNext(@NonNull W w) {
                httpResponse.onSuccess(w);
            }

            @Override
            public void onCompleted() {
                httpResponse.onComplete();
            }

            @Override
            public void onError(@NonNull Throwable e) {
                httpResponse.onError(e);

            }

        };
        return observer;
    }
}
