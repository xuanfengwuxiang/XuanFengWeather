package com.xuanfeng.mylibrary.mvp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

public abstract class BaseActivity extends AppCompatActivity implements IBaseBiew {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //全屏
        if (isFullScreen()) {
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }

        setContentView(getLayoutId());//布局
        initPresenter();//数据请求
        initData(getIntent().getExtras());

    }

    @Override//默认不全屏
    public boolean isFullScreen() {
        return false;
    }

    @Override
    public void showToast(String content) {
        Toast.makeText(this, content, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showProgress() {
       
    }

    @Override
    public void hideProgress() {

    }
}
