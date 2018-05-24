package com.xuanfeng.mylibrary.rxbus;

import rx.Observable;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;

/**
 * Created by xuanfengwuxiang on 2017/10/7.
 */

public class RxBus {
    private static RxBus rxBus;
    private final Subject<Object, Object> _bus = new SerializedSubject<>(PublishSubject.create());

    private RxBus() {
    }

    public static RxBus getInstance() {
        if (rxBus == null) {
            synchronized (RxBus.class) {
                if (rxBus == null) {
                    rxBus = new RxBus();
                }
            }
        }
        return rxBus;
    }

    public void send(Object o) {
        _bus.onNext(o);
    }

    public<T> Observable<T> toObserverable(Class<T> eventType) {
        return _bus.ofType(eventType);
    }


}
