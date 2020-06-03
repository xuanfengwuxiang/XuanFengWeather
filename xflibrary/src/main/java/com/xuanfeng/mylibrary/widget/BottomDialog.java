package com.xuanfeng.mylibrary.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.xuanfeng.mylibrary.R;
import com.xuanfeng.mylibrary.utils.SystemUtils;
import com.xuanfeng.mylibrary.widget.BottomRecyclerView.OnItemClickListener;

import java.util.List;

/**
 * 底部弹窗
 */
public class BottomDialog extends Dialog implements OnItemClickListener, View.OnClickListener {

    private Context mContext;
    private BottomRecyclerView mRvFunc;
    private TextView mTvCancel;
    private OnItemClickListener mListener;
    private LinearLayout mLlRoot;

    public BottomDialog(@NonNull Context context) {
        super(context, R.style.SheetDialogStyle);
        mContext = context;
        setContentView(R.layout.dialog_xf_bottom);
        init();
        initListener();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window dialogWindow = getWindow();
        dialogWindow.setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialogWindow.setAttributes(lp);
        checkHeight();
    }

    private void init() {
        mRvFunc = findViewById(R.id.rv_func);
        mTvCancel = findViewById(R.id.tv_cancel);
        mLlRoot = findViewById(R.id.ll_root);
    }

    private void initListener() {
        mRvFunc.setListener(this);
        mTvCancel.setOnClickListener(this);
    }

    public void setData(List<String> list, OnItemClickListener listener) {
        mRvFunc.setData(list);
        mListener = listener;
    }

    @Override
    public void onItemClick(int position) {
        dismiss();
        if (mListener != null) {
            mListener.onItemClick(position);
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tv_cancel) {
            dismiss();
        }
    }

    private void checkHeight() {
        if (mLlRoot != null) {
            mLlRoot.post(new Runnable() {
                @Override
                public void run() {
                    int screenHeight = (int) SystemUtils.getHeightInPx(mContext);
                    int max = (int) (0.7f * screenHeight);
                    int rootH = mLlRoot.getMeasuredHeight();
                    if (rootH > max) {
                        ViewGroup.LayoutParams params = mLlRoot.getLayoutParams();
                        params.height = max;
                        mLlRoot.setLayoutParams(params);
                    }
                }
            });
        }

    }
}
