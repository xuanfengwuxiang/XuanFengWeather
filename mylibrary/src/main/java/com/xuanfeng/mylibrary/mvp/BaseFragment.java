package com.xuanfeng.mylibrary.mvp;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public abstract class BaseFragment extends Fragment implements IBaseBiew {
    protected Context mContext;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext = getActivity();
        initPresenter();//数据请求
        initData(getArguments());
        return inflater.inflate(getLayoutId(), container, false);
    }

    @Override//默认不全屏
    public boolean isFullScreen() {
        return false;
    }
}
