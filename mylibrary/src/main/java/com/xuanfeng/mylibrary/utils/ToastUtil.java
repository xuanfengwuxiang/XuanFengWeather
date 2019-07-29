package com.xuanfeng.mylibrary.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by xuanfengwuxiang on 2018/8/2.
 */

public class ToastUtil {
    private ToastUtil() {
    }

    private static Toast toast;

    public static void showToast(Context context, String text, int duration) {
        if (toast == null) {
            toast = Toast.makeText(context, text, duration);
        }
        toast.setText(text);
        toast.setDuration(duration);
        toast.show();
    }

    public static void showToast(Context context, String text) {
        showToast(context, text, Toast.LENGTH_SHORT);
    }
}
