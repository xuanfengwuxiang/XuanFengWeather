package com.xuanfeng.mylibrary.rxbus;

/**
 * Created by xuanfengwuxiang on 2017/10/7.
 */

public class RxBean {
    public RxBean(int key, Object value) {
        this.key = key;
        this.value = value;
    }

    public RxBean(int key) {
        this.key = key;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    private int key;
    private Object value;

}
