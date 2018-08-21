package com.xuanfeng.xuanfengweather.module.menu;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xuanfeng.mylibrary.mvp.BaseFragment;
import com.xuanfeng.xuanfengweather.R;
import com.xuanfeng.xuanfengweather.module.test.activity.TestActivity;

import butterknife.BindView;
import butterknife.OnClick;


public class MenuFragment extends BaseFragment {

    @BindView(R.id.adview_container)
    LinearLayout mAdviewContainer;

    @BindView(R.id.tv_test)
    TextView tvTest;

    //测试按钮
    @OnClick(R.id.tv_test)
    public void onViewClicked() {
        Intent intent = new Intent(getContext(), TestActivity.class);
        startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_menu;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initData(Bundle bundle) {

    }
}
