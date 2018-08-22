package com.xuanfeng.mylibrary.mvp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.xuanfeng.mylibrary.rxbus.RxBean;
import com.xuanfeng.mylibrary.rxbus.RxBus;
import com.xuanfeng.mylibrary.utils.StatusBarUtil;
import com.xuanfeng.mylibrary.widget.LoadingDialog;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import rx.Subscription;
import rx.functions.Action1;

public abstract class BaseActivity<P extends BasePresenter> extends AppCompatActivity implements BaseView {

    Unbinder unbinder;
    private Subscription mSubscription;
    private LoadingDialog mLoadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //全屏
        if (isFullScreen()) {
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }

        setContentView(getLayoutId());//布局
        unbinder = ButterKnife.bind(this);
        initPresenter();//数据请求
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
    protected void registRxBus(Action1<RxBean> action1) {
        mSubscription = RxBus.getInstance().toObserverable(RxBean.class).subscribe(action1);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (-1 != getStatusBarColorResId()) {
            StatusBarUtil.setStatusBarColor(this, getStatusBarColorResId(), true);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (unbinder != null) {//解绑butterKnife
            unbinder.unbind();
        }
        if (mSubscription != null && !mSubscription.isUnsubscribed()) {//解绑rxJava
            mSubscription.unsubscribe();
        }
    }
}
