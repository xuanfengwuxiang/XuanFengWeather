package com.xuanfeng.xflibrary.contacts;

import android.os.Bundle;
import android.view.View;

import com.xuanfeng.xflibrary.R;
import com.xuanfeng.xflibrary.databinding.ActivityContactsBinding;
import com.xuanfeng.xflibrary.mvp.BaseActivity;
import com.xuanfeng.xflibrary.mvp.BasePresenter;


public class ContactsActivity extends BaseActivity<BasePresenter, ActivityContactsBinding> implements View.OnClickListener {


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
        mBinding.setContactsActivity(this);
    }

    @Override
    public int getStatusBarColorResId() {
        return R.color.baseThemeColor;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.iv_left) {
            finish();
        }
    }
}
