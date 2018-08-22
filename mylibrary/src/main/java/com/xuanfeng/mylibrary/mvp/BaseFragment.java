package com.xuanfeng.mylibrary.mvp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xuanfeng.mylibrary.widget.LoadingDialog;

import butterknife.ButterKnife;
import butterknife.Unbinder;

//基类Fragment
public abstract class BaseFragment<P extends BasePresenter> extends Fragment implements BaseView {

    Unbinder unbinder;
    private LoadingDialog mLoadingDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(getLayoutId(), container, false);
        unbinder = ButterKnife.bind(this, rootView);
        initPresenter();//数据请求
        initData(getArguments());
        return rootView;
    }

    @Override//默认不全屏
    public boolean isFullScreen() {
        return false;
    }

    @Override
    public int getStatusBarColorResId() {
        return -1;
    }

    @Override
    public void showProgress() {
        if (mLoadingDialog == null) {
            mLoadingDialog = new LoadingDialog(getContext());
        }
        mLoadingDialog.show();
    }

    @Override
    public void hideProgress() {
        if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
            mLoadingDialog.dismiss();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (unbinder != null) {
            unbinder.unbind();
        }
    }
}
