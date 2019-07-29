package com.xuanfeng.testcomponent.activity;

import android.os.Bundle;
import android.view.View;

import com.xuanfeng.mylibrary.mvp.BaseActivity;
import com.xuanfeng.mylibrary.mvp.BasePresenter;
import com.xuanfeng.testcomponent.R;
import com.xuanfeng.testcomponent.databinding.ActivityTestAidlBinding;


//AIDL测试界面
public class TestAIDLActivity extends BaseActivity<BasePresenter, ActivityTestAidlBinding> {


    @Override
    public int getLayoutId() {
        return R.layout.activity_test_aidl;
    }

    @Override
    public BasePresenter initPresenter() {
        mBinding.setListener(this);
        return null;
    }

    @Override
    public void initData(Bundle bundle) {
        mBinding.tvTittle.setText("AIDL客户端");
    }

    @Override
    public int getStatusBarColorResId() {
        return 0;
    }

    public void onClick(View view) {
        int i = view.getId();//发送消息到服务端
        if (i == R.id.iv_left) {
            finish();
        }
    }
}
