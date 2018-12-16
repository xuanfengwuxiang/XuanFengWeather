package com.xuanfeng.mylibrary.rxbus;


import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

/**
 * Created by xuanfengwuxiang on 2017/10/7.
 */

public class RxBus {
    private static RxBus rxBus;
    private final Subject<Object> _bus = PublishSubject.create().toSerialized();;

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
