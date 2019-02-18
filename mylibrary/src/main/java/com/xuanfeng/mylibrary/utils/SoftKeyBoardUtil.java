package com.xuanfeng.mylibrary.utils;

import android.app.Activity;
import android.graphics.Rect;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;

/**
 * 软键盘弹收监听
 */
public class SoftKeyBoardUtil {

    private View mDecorView;//activity的根视图
    private int mWindowVisibleHeight;//纪录窗口的显示高度
    private KeyBoardListener mKeyBoardListener;
    private String TAG = getClass().getSimpleName();
    private ViewTreeObserver.OnGlobalLayoutListener mOnGlobalLayoutListener;

    public SoftKeyBoardUtil(Activity activity) {

        mDecorView = activity.getWindow().getDecorView();

        //监听视图树中全局布局发生改变或者视图树中的某个视图的可视状态发生改变
        mOnGlobalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {

                //获取 当前窗口 在屏幕上 显示的大小
                Rect r = new Rect();
                mDecorView.getWindowVisibleDisplayFrame(r);
                int visibleHeight = r.height();

                Log.i(TAG, "DecorViewHeight=" + mDecorView.getHeight());
                Log.i(TAG, "\nWinHeight=" + visibleHeight + "    top=" + r.top + "   bottom=" + r.bottom);

                //init状态记录无键盘窗口高度
                if (mWindowVisibleHeight == 0) {
                    mWindowVisibleHeight = visibleHeight;
                    return;
                }

                //根视图显示高度没有变化，可以看作软键盘显示／隐藏状态没有改变
                if (mWindowVisibleHeight == visibleHeight) {
                    return;
                }

                //根视图显示高度变小超过200，可以看作软键盘显示了
                if (mWindowVisibleHeight - visibleHeight > 200) {
                    if (mKeyBoardListener != null) {
                        mKeyBoardListener.keyBoardShow(mWindowVisibleHeight - visibleHeight);
                    }
                    mWindowVisibleHeight = visibleHeight;
                    return;
                }

                //根视图显示高度变大超过200，可以看作软键盘隐藏了
                if (visibleHeight - mWindowVisibleHeight > 200) {
                    if (mKeyBoardListener != null) {
                        mKeyBoardListener.keyBoardHide(visibleHeight - mWindowVisibleHeight);
                    }
                    mWindowVisibleHeight = visibleHeight;
                    return;
                }

            }
        };
        mDecorView.getViewTreeObserver().addOnGlobalLayoutListener(mOnGlobalLayoutListener);
    }

    private void setKeyBoardListener(KeyBoardListener keyBoardListener) {
        this.mKeyBoardListener = keyBoardListener;
    }

    public interface KeyBoardListener {
        void keyBoardShow(int height);

        void keyBoardHide(int height);
    }

    public static SoftKeyBoardUtil setListener(Activity activity, KeyBoardListener keyBoardListener) {
        SoftKeyBoardUtil softKeyBoardListener = new SoftKeyBoardUtil(activity);
        softKeyBoardListener.setKeyBoardListener(keyBoardListener);
        return softKeyBoardListener;
    }

    public void removeListener() {
        mKeyBoardListener = null;
        if (mDecorView != null && mDecorView.getViewTreeObserver() != null && mOnGlobalLayoutListener != null) {
            mDecorView.getViewTreeObserver().removeOnGlobalLayoutListener(mOnGlobalLayoutListener);
        }
    }
}

