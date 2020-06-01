package com.xuanfeng.mylibrary.mvp;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.xuanfeng.mylibrary.rxbus.RxBean;
import com.xuanfeng.mylibrary.rxbus.RxBus;
import com.xuanfeng.mylibrary.utils.StatusBarUtil;
import com.xuanfeng.mylibrary.widget.LoadingDialog;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;


public abstract class BaseActivity<P extends BasePresenter, V extends ViewDataBinding> extends AppCompatActivity implements BaseView {

    private Disposable mDisposable;
    private LoadingDialog mLoadingDialog;
    protected V mBinding;
    protected P mPresentor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //全屏
        if (isFullScreen()) {
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        mBinding = DataBindingUtil.setContentView(this, getLayoutId());

        mPresentor = (P) initPresenter();
        initData(getIntent().getExtras());

    }

    @Override//默认不全屏
    public boolean isFullScreen() {
        return false;
    }


    @Override
    public void showProgress() {
        if (mLoadingDialog == null) {
            mLoadingDialog = new LoadingDialog(this);
        }
        mLoadingDialog.show();
    }

    @Override
    public void hideProgress() {
        if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
            mLoadingDialog.dismiss();
        }
    }

    //注册rxbus接收监听
    protected void registRxBus(Consumer<RxBean> consumer) {
        mDisposable = RxBus.getInstance().toObserverable(RxBean.class).subscribe(consumer);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (-1 != getStatusBarColorResId()) {
            StatusBarUtil.setStatusBarColor(this, getStatusBarColorResId(), false);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mBinding != null) {
            mBinding.unbind();
        }
        if (mDisposable != null && !mDisposable.isDisposed()) {//解绑rxJava
            mDisposable.dispose();
        }
        if (mBinding != null) {
            mBinding.unbind();
        }
    }
}
