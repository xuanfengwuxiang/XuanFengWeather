package com.xuanfeng.xuanfengweather.module.test.activity;

import android.os.Bundle;

import com.xuanfeng.mylibrary.mvp.BaseActivity;
import com.xuanfeng.xuanfengweather.R;
import com.xuanfeng.xuanfengweather.module.test.widget.TestTouchView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 事件分发测试界面
 */
public class TestTouchEventActivity extends BaseActivity {

    @BindView(R.id.button)
    TestTouchView button;

    @Override
    public int getLayoutId() {
        return R.layout.activity_test_touch_event;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initData(Bundle bundle) {
        //此句无效：因为每次事件分发都会将标志位置为初始状态
        button.getParent().requestDisallowInterceptTouchEvent(true);
    }

    @Override
    public int getStatusBarColorResId() {
        return 0;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
