package com.xuanfeng.xuanfengweather.module.test.widget;

import android.content.Context;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.xuanfeng.xuanfengweather.module.test.utils.ActionUtil;

public class TestTouchView extends View {
    public TestTouchView(Context context) {
        super(context);
    }

    public TestTouchView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TestTouchView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    int count = 0;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.i("testTouch", "View获取事件:" + ActionUtil.getActionName(event.getAction()));

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                return true;
            case MotionEvent.ACTION_MOVE:
                if (count <3) {
                    count++;
                    return true;

                } else {
                    count++;
                    return false;
                }


            case MotionEvent.ACTION_UP:

                break;
        }
        return true;
    }
}
