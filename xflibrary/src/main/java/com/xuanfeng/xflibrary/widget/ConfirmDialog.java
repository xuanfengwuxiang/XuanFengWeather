package com.xuanfeng.xflibrary.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.xuanfeng.xflibrary.R;
import com.xuanfeng.xflibrary.utils.SystemUtils;

public class ConfirmDialog extends Dialog implements View.OnClickListener {

    private Context mContext;
    private TextView mTvTittle;
    private TextView mTvContent;
    private TextView mTvCancel;
    private TextView mTvOk;

    public ConfirmDialog(@NonNull Context context) {
        super(context, R.style.confirm_dialog_style);
        mContext = context;
        setContentView(R.layout.dialog_confirm);
        initView();
        initListener();
    }


    private void initView() {
        mTvTittle = findViewById(R.id.tv_tittle);
        mTvContent = findViewById(R.id.tv_content);
        mTvCancel = findViewById(R.id.tv_cancel);
        mTvOk = findViewById(R.id.tv_ok);
    }

    private void initListener() {
        mTvCancel.setOnClickListener(this);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window dialogWindow = getWindow();
        dialogWindow.setGravity(Gravity.CENTER);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = (int) (SystemUtils.getWidthInPx(mContext) * 0.72f);
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialogWindow.setAttributes(lp);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tv_cancel) {
            dismiss();
        }
    }


    public ConfirmDialog setTittle(String tittle) {
        mTvTittle.setText(tittle);
        return this;
    }

    public ConfirmDialog setContent(String content) {
        mTvContent.setText(content);
        return this;
    }

    public ConfirmDialog setCancelColor(int resId) {
        mTvCancel.setTextColor(mContext.getResources().getColor(resId));
        return this;
    }

    public ConfirmDialog setOkColor(int resId) {
        mTvOk.setTextColor(mContext.getResources().getColor(resId));
        return this;
    }

    public TextView getTvTittle() {
        return mTvTittle;
    }

    public TextView getTvCancel() {
        return mTvCancel;
    }

    public TextView getTvOk() {
        return mTvOk;
    }
}
