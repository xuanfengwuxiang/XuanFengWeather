package com.xuanfeng.mylibrary.widget;

import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.TextView;
import android.widget.Toast;

import com.xuanfeng.mylibrary.R;

import java.lang.reflect.Field;
import java.lang.reflect.Method;


/**
 * 自定义TOAST
 */
public class CustomToast {
    private String mTag = getClass().getSimpleName();
    private static CustomToast reflectToast = null;
    private Field mNextViewField;
    private Toast mToast;
    private Object obj;
    private Method showMethod;
    private Method hideMethod;

    private TextView textView;
    private Handler handler = new Handler();
    private boolean isShow = false;

    private CustomToast(Context context) {
        mToast = new Toast(context);
        mToast.setGravity(Gravity.CENTER, 0, 0);
        textView = (TextView) LayoutInflater.from(context).inflate(R.layout.yb_toast, null);
        if (Build.VERSION.SDK_INT >= 25) {
            mToast.setView(textView);
        } else {
            reflectionTN();
        }
    }

    public static CustomToast getInstance(Context context) {
        if (reflectToast == null) {
            reflectToast = new CustomToast(context.getApplicationContext());
        }
        return reflectToast;
    }

    private Runnable showRunnable = () -> {
        cancel();
        isShow = false;
    };

    public void showToast(String textString) {
        long mDuration = 1000;
        showToast(textString, mDuration);

    }

    public void showToast(String textString, long mDuration) {
        textView.setText(textString);
        if (Build.VERSION.SDK_INT >= 25) {
            if (mDuration > 2000) {
                mToast.setDuration(Toast.LENGTH_LONG);
            } else {
                mToast.setDuration(Toast.LENGTH_SHORT);
            }
            mToast.show();
            return;
        }
        if (!isShow) {
            show();
        } else {
            handler.removeCallbacks(showRunnable);
        }
        handler.postDelayed(showRunnable, mDuration);
    }

    private void show() {
        try {

            mNextViewField.set(obj, textView);
            showMethod.invoke(obj);
            isShow = true;
        } catch (Exception e) {
            Log.e(mTag,e.toString());
        }
    }

    private void cancel() {
        try {
            hideMethod.invoke(obj);
        } catch (Exception e) {
            Log.e(mTag,e.toString());
        }
    }

    private void reflectionTN() {
        try {
            Field field = mToast.getClass().getDeclaredField("mTN");
            field.setAccessible(true);
            obj = field.get(mToast);
            mNextViewField = obj.getClass().getDeclaredField("mNextView");
            mNextViewField.setAccessible(true);
            showMethod = obj.getClass().getDeclaredMethod("show");
            hideMethod = obj.getClass().getDeclaredMethod("hide");
        } catch (Exception e) {
            Log.e(mTag,e.toString());
        }
    }
}
