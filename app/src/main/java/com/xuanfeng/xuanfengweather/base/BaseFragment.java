package com.xuanfeng.xuanfengweather.base;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.xuanfeng.mylibrary.rxbus.RxBean;
import com.xuanfeng.mylibrary.rxbus.RxBus;

import rx.Subscription;
import rx.functions.Action1;

//todo 等待完善
public abstract class BaseFragment extends Fragment {
    protected View mContentView;
    protected Context mContext;
    private Subscription mSubscription;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
    }

    protected abstract void initViews();

    protected abstract void setListeners();

    protected abstract void initData();

    //注册rxbus接收监听
    protected void registRxBus(Action1<RxBean> action1) {
        mSubscription = RxBus.getInstance().toObserverable(RxBean.class).subscribe(action1);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mSubscription != null && !mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }
    }


}
