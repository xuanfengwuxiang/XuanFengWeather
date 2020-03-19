package com.xuanfeng.mylibrary.contacts;

import android.os.Bundle;

import com.xuanfeng.mylibrary.R;
import com.xuanfeng.mylibrary.databinding.ActivityContactsBinding;
import com.xuanfeng.mylibrary.mvp.BaseActivity;
import com.xuanfeng.mylibrary.mvp.BasePresenter;


public class ContactsActivity extends BaseActivity<BasePresenter, ActivityContactsBinding> {


    @Override
    public int getLayoutId() {
        return R.layout.activity_contacts;
    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @Override
    public void initData(Bundle bundle) {

    }

    @Override
    public int getStatusBarColorResId() {
        return -1;
    }
}
