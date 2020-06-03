package com.xuanfeng.mylibrary.rxbus;


import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

/**
 * Created by xuanfengwuxiang on 2017/10/7.
 */

public class RxBus {
    private static volatile RxBus rxBus;
    private final Subject<Object> mObjectSubject = PublishSubject.create().toSerialized();

    private RxBus() {
    }

    public static RxBus getInstance() {
        if (rxBus == null) {
            synchronized (RxBus.class) {
                rxBus = new RxBus();
            }
        }
        return rxBus;
    }

    public void send(Object o) {
        mObjectSubject.onNext(o);
    }

    public <T> Observable<T> toObserverable(Class<T> eventType) {
        return mObjectSubject.ofType(eventType);
    }


}
