package com.xuanfeng.mylibrary.widget;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.support.annotation.NonNull;
import android.view.Window;
import android.widget.ImageView;

import com.xuanfeng.mylibrary.R;


/**
 * Created by xuanfengwuxiang on 2018/7/14.
 * 加载中弹窗
 */

public class LoadingDialog extends Dialog {


    public LoadingDialog(@NonNull Context context) {
        super(context);
        Window window = getWindow();
        window.requestFeature(Window.FEATURE_NO_TITLE);
        window.setDimAmount(0.2f);
        setContentView(R.layout.dialog_loading);
        setCanceledOnTouchOutside(false);
        setCancelable(false);
        init();
    }


    private void init() {
        initData();
    }


    private void initData() {
        ImageView imageView = findViewById(R.id.iv_loading);
        AnimationDrawable anim = (AnimationDrawable) imageView.getBackground();
        anim.start();
    }
}
