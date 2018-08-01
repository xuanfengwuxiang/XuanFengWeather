package com.xuanfeng.xuanfengweather.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.xuanfeng.mylibrary.rxbus.RxBean;
import com.xuanfeng.mylibrary.rxbus.RxBus;
import com.xuanfeng.mylibrary.utils.StatusBarUtil;
import com.xuanfeng.xuanfengweather.R;

import rx.Subscription;
import rx.functions.Action1;

public abstract class BaseActivity extends AppCompatActivity {

    private Subscription mSubscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    protected abstract void initViews();

    protected abstract void setListeners();

    protected abstract void initData();

    protected void onResume() {
        super.onResume();
        StatusBarUtil.setStatusBarColor(this, R.color.white, true);
    }

    //注册rxbus接收监听
    protected void registRxBus(Action1<RxBean> action1) {
        mSubscription = RxBus.getInstance().toObserverable(RxBean.class).subscribe(action1);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mSubscription != null && !mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }
    }
}
