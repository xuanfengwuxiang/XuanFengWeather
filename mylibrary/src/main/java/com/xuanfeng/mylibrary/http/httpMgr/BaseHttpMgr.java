package com.xuanfeng.mylibrary.http.httpMgr;

import android.arch.lifecycle.LifecycleOwner;
import android.support.annotation.NonNull;

import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import com.xuanfeng.mylibrary.http.HttpResponse;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.uber.autodispose.AutoDispose.autoDisposable;


/**
 * Created by zhujh on 2017/7/19.
 * 描述：网络请求管理基类，实现网络请求与回调的绑定
 */

public class BaseHttpMgr {


    //将网络请求与回调进行绑定，与设置监听器同理
    public static void subscribeAndObserve(LifecycleOwner lifecycleOwner, Observable<?> observable, HttpResponse httpResponse) {
        observable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(lifecycleOwner)))
                .subscribe(getObserver(httpResponse));

    }

    //将retrofit的回调改为自定义，方便后期切换框架
    public static <W> Observer<W> getObserver(final HttpResponse httpResponse) {
        Observer observer = new Observer<W>() {

            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(@NonNull W w) {
                httpResponse.onSuccess(w);
            }

            @Override
            public void onComplete() {
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
