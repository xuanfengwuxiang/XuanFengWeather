package com.xuanfeng.mylibrary.mvp;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xuanfeng.mylibrary.widget.LoadingDialog;

import butterknife.ButterKnife;
import butterknife.Unbinder;

//基类Fragment
public abstract class BaseFragment<P extends BasePresenter, V extends ViewDataBinding> extends Fragment implements BaseView {

    private LoadingDialog mLoadingDialog;
    protected V mBinding;
    protected P mPresenter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(LayoutInflater.from(getActivity()), getLayoutId(), container, false);

        mPresenter = (P) initPresenter();//数据请求
        initData(getArguments());
        return mBinding.getRoot();
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
        if (mBinding != null) {
            mBinding.unbind();
        }
    }

    @Override
    public void onClick(View view) {

    }
}
