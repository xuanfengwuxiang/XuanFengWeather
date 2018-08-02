package com.xuanfeng.xuanfengweather.module.menu;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xuanfeng.xuanfengweather.R;
import com.xuanfeng.xuanfengweather.base.BaseFragment;
import com.xuanfeng.xuanfengweather.module.test.activity.TestActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class MenuFragment extends BaseFragment {

    @BindView(R.id.adview_container)
    LinearLayout mAdviewContainer;
    Unbinder unbinder;
    @BindView(R.id.tv_test)
    TextView tvTest;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {
        mContentView = inflater.inflate(R.layout.fragment_menu, container, false);
        unbinder = ButterKnife.bind(this, mContentView);
        initViews();
        setListeners();
        initData();
        return mContentView;
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void setListeners() {

    }

    @Override
    protected void initData() {
        //AdUtils.showBaiduAd(getActivity(),mAdviewContainer,"侧边栏百度banner");

    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    //测试按钮
    @OnClick(R.id.tv_test)
    public void onViewClicked() {
        Intent intent = new Intent(mContext, TestActivity.class);
        startActivity(intent);
    }
}
